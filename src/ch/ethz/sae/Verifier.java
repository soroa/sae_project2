package ch.ethz.sae;

import java.util.HashMap;

import apron.ApronException;
import soot.Unit;
import soot.jimple.internal.JInvokeStmt;
import soot.jimple.internal.JNewExpr;
import soot.jimple.internal.JSpecialInvokeExpr;
import soot.jimple.internal.JVirtualInvokeExpr;
import soot.jimple.spark.sets.DoublePointsToSet;
import soot.jimple.spark.sets.P2SetVisitor;
import soot.jimple.spark.SparkTransformer;
import soot.jimple.spark.pag.AllocNode;
import soot.jimple.spark.pag.Node;
import soot.jimple.spark.pag.PAG;
import soot.Local;
import soot.PatchingChain;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.toolkits.graph.BriefUnitGraph;
import soot.jimple.internal.*;
import soot.jimple.*;

public class Verifier {
	
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: java -classpath soot-2.5.0.jar:./bin ch.ethz.sae.Verifier <class to test>");
			System.exit(-1);
		}
		String analyzedClass = args[0];
		SootClass c = loadClass(analyzedClass);

		PAG pointsToAnalysis = doPointsToAnalysis(c);

		int programCorrectFlag = 1;
		int divisionByZeroFlag = 1;

		for (SootMethod method : c.getMethods()) {
			System.out.println("name of method is "+ method.getName());
			Analysis analysis = new Analysis(new BriefUnitGraph(method.retrieveActiveBody()), c);
			analysis.run();

			if (!verifyBounds(method, analysis, pointsToAnalysis)) {
				programCorrectFlag = 0;
			}
			if (!verifyDivisionByZero(method, analysis)) {
				divisionByZeroFlag = 0;
			}
		}
		
		if (divisionByZeroFlag == 1) {
			System.out.println(analyzedClass + " NO_DIV_ZERO");
		} else {
			System.out.println(analyzedClass + " MAY_DIV_ZERO");
		}
		
		if (programCorrectFlag == 1) {
            System.out.println(analyzedClass + " NO_OUT_OF_BOUNDS");
        } else {
            System.out.println(analyzedClass + " MAY_OUT_OF_BOUNDS");
        }
	}
	
	private static boolean verifyDivisionByZero(SootMethod method, Analysis fixPoint) {
		for (Unit u : method.retrieveActiveBody().getUnits()) {
			AWrapper state = fixPoint.getFlowBefore(u);
			try {
		    		if (state.get().isBottom(Analysis.man))
	    			// unreachable code
					continue;
			} catch (ApronException e) {
				e.printStackTrace();
			} 
			
			//TODO: Check that all divisors are not zero
	    }
		
		//Return false if the method may have division by zero errors
	    return false;
	}

	private static boolean verifyBounds(SootMethod method, Analysis fixPoint,
			PAG pointsTo) {
				
		//TODO: Create a list of all allocation sites for PrinterArray
		
		HashMap<JNewExpr, Integer> printerArraySizeOf = new HashMap<JNewExpr, Integer>();
		PatchingChain<Unit> unitChain = method.retrieveActiveBody().getUnits();
		
		for (Unit u : unitChain) {
			AWrapper state = fixPoint.getFlowBefore(u);
			
		
			if (u instanceof JAssignStmt) {
				JAssignStmt assign = (JAssignStmt) u;
				
				if (assign.getRightOp() instanceof JNewExpr) {
					// the statement following this newexpr will hopefully be a
					// constructor call, so we will remember the argument of that call in printerArraySizeOf
					Unit successor = unitChain.getSuccOf(u);
					JSpecialInvokeExpr constructorCall = (JSpecialInvokeExpr) ((JInvokeStmt) successor)
							.getInvokeExpr();

					int size = ((IntConstant) constructorCall.getArg(0)).value;
					printerArraySizeOf.put((JNewExpr) assign.getRightOp(), size);
				}
			}
			
			try {
				if (state.get().isBottom(Analysis.man)) {
					// unreachable code
					
					// commented because the analysis does nothing yet, but we still want to check outofbounds
					//continue;
				} 
			} catch (ApronException e) {
				e.printStackTrace();
			} 

			
			if (u instanceof JInvokeStmt && ((JInvokeStmt) u).getInvokeExpr() instanceof JSpecialInvokeExpr) {
				// TODO: Get the size of the PrinterArray given as argument to the constructor
				
				// this todo is implemented above...
				
			}
			
			if (u instanceof JInvokeStmt && ((JInvokeStmt) u).getInvokeExpr() instanceof JVirtualInvokeExpr) {
				
				JInvokeStmt jInvStmt = (JInvokeStmt)u;
				
				JVirtualInvokeExpr invokeExpr = (JVirtualInvokeExpr)jInvStmt.getInvokeExpr();
				
				Local base = (Local) invokeExpr.getBase();
				DoublePointsToSet pts = (DoublePointsToSet) pointsTo
						.reachingObjects(base);
				
				if (invokeExpr.getMethod().getName().equals(Analysis.functionName)) {
					
					// TODO: Check whether the 'sendJob' method's argument is within bounds
					int argument = ((IntConstant) invokeExpr.getArg(0)).value;
					// Visit all allocation sites that the base pointer may reference
					MyP2SetVisitor visitor = new MyP2SetVisitor(
							printerArraySizeOf, argument);
					pts.forall(visitor);
					if (visitor.outOfBoundsDetected) {
						return false;
					}
				}
			}
		}

		return true;
	}

	private static SootClass loadClass(String name) {
		SootClass c = Scene.v().loadClassAndSupport(name);
		c.setApplicationClass();
		return c;
	}

	private static PAG doPointsToAnalysis(SootClass c) {
		Scene.v().setEntryPoints(c.getMethods());

		HashMap<String, String> options = new HashMap<String, String>();
		options.put("enabled", "true");
		options.put("verbose", "false");
		options.put("propagator", "worklist");
		options.put("simple-edges-bidirectional", "false");
		options.put("on-fly-cg", "true");
		options.put("set-impl", "double");
		options.put("double-set-old", "hybrid");
		options.put("double-set-new", "hybrid");

		SparkTransformer.v().transform("", options);
		PAG pag = (PAG) Scene.v().getPointsToAnalysis();

		return pag;
	}	
}

class MyP2SetVisitor extends P2SetVisitor {

	HashMap<JNewExpr, Integer> printerArraySizeOf;
	int argument;
	boolean outOfBoundsDetected;

	public MyP2SetVisitor(HashMap<JNewExpr, Integer> printerArraySizeOf,
			int argument) {
		this.printerArraySizeOf = printerArraySizeOf;
		this.argument = argument;
		outOfBoundsDetected = false;
	}

	@Override
	public void visit(Node arg0) {
		// TODO: Check whether the argument given to sendJob is within bounds
		AllocNode alloc = (AllocNode) arg0;
		if (printerArraySizeOf.get(alloc.getNewExpr()) <= argument
				|| 0 > argument) {
			outOfBoundsDetected = true;
		}
	}
}