package ch.ethz.sae;

import java.util.HashMap;

import apron.Abstract1;
import apron.ApronException;
import apron.Interval;
import apron.MpqScalar;
import apron.Tcons1;
import apron.Texpr1BinNode;
import apron.Texpr1CstNode;
import apron.Texpr1Intern;
import apron.Texpr1Node;
import apron.Texpr1VarNode;
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
import soot.*;
import soot.toolkits.graph.BriefUnitGraph;
import soot.jimple.internal.*;
import soot.jimple.*;

public class Verifier {
	
	/* field to record the output for junit */
	public static String response;

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err
					.println("Usage: java -classpath soot-2.5.0.jar:./bin ch.ethz.sae.Verifier <class to test>");
			System.exit(-1);
		}
		String analyzedClass = args[0];
		SootClass c = loadClass(analyzedClass);

		PAG pointsToAnalysis = doPointsToAnalysis(c);

		int programCorrectFlag = 1;
		int divisionByZeroFlag = 1;

		for (SootMethod method : c.getMethods()) {
			Analysis analysis = new Analysis(new BriefUnitGraph(
					method.retrieveActiveBody()), c, method);
			analysis.run();

			if (!verifyBounds(method, analysis, pointsToAnalysis)) {
				programCorrectFlag = 0;
			}
			if (!verifyDivisionByZero(method, analysis)) {
				divisionByZeroFlag = 0;
			}
		}

		if (divisionByZeroFlag == 1) {
			response = analyzedClass + " NO_DIV_ZERO";
			System.out.println(response);
		} else {
			response = analyzedClass + " MAY_DIV_ZERO";
			System.out.println(response);
		}
		response += "\n";
		if (programCorrectFlag == 1) {
			response += analyzedClass + " NO_OUT_OF_BOUNDS";
			System.out.println(analyzedClass + " NO_OUT_OF_BOUNDS");
		} else {
			response += analyzedClass + " MAY_OUT_OF_BOUNDS";
			System.out.println(analyzedClass + " MAY_OUT_OF_BOUNDS");
		}
	}

	private static boolean verifyDivisionByZero(SootMethod method,
			Analysis fixPoint) {
		for (Unit u : method.retrieveActiveBody().getUnits()) {
			AWrapper state = fixPoint.getFlowBefore(u);
			System.out.println("unit is" + u.toString());
			try {
				if (state.get().isBottom(Analysis.man))
					// unreachable code
					continue;
			} catch (ApronException e) {
				e.printStackTrace();
			}

			if (u instanceof JAssignStmt) {
				JAssignStmt assign = (JAssignStmt) u;
				Value left = assign.getLeftOp();
				Value right = assign.getRightOp();
				// division assigned to an int variable
				// right can be Intconstnat or JimpleLocal
				// System.out.println("right is " +
				// right.getClass().toString());
				// System.out.println("ligt is " + left.getClass().toString());

				// case 1: assignment to local variable
				if (left instanceof JimpleLocal && right instanceof JDivExpr) {

					System.out
							.println("Division right side of assignment detected");

					JDivExpr divExp = (JDivExpr) right;
					if (divExpressionDividesByZero(divExp, state)) {
						return false;
					}

				}// end if assignment with division on the side
			}// end if assignemnt

			// case 2: call to sendJob() -> argument could divide by
			if (u instanceof JInvokeStmt
					&& ((JInvokeStmt) u).getInvokeExpr() instanceof JVirtualInvokeExpr) {
				
				JVirtualInvokeExpr sendJobCallExpr = (JVirtualInvokeExpr) ((JInvokeStmt) u).getInvokeExpr();
				Value sendJobArg = sendJobCallExpr.getArg(0);
				if (sendJobArg instanceof JDivExpr) {
					if(divExpressionDividesByZero((JDivExpr)sendJobArg, state)){
						return false;
					}
				}
			}
			// TODO: Check that all divisors are not zero
		}

		// Return false if the method may have division by zero errors
		return true;
	}

	private static boolean divExpressionDividesByZero(JDivExpr divExp,
			AWrapper state) {
		Value divisor = divExp.getOp2();
		// 1.1 divisor is IntConstat and 0
		if (divisor instanceof IntConstant) {
			IntConstant divisorInt = (IntConstant) divisor;
			if (divisorInt.value == 0) {

				System.out
						.println("division by 0 detected! divisor is a 0 constant");
				return true;

			}
		}

		if (divisor instanceof JimpleLocal) {
			JimpleLocal divisorVar = (JimpleLocal) divisor;
			String varName = divisorVar.getName();
			Interval intr;
			try {
				intr = state.get().getBound(state.man, varName);
				System.out.println("state  is "+ state.get());
				System.out.println("interval of var " + varName + " of divisor is " + intr.toString());

				if (intr.sup().cmp(-1) == 1 && intr.inf().cmp(1) == -1) {
					System.out
							.println("division by 0 detected! divisor is a potentially 0 variable");
					return true;
				}
			} catch (ApronException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return false;
	}

	private static boolean verifyBounds(SootMethod method, Analysis fixPoint,
			PAG pointsTo) {
		
		// TODO: Create a list of all allocation sites for PrinterArray

		HashMap<JNewExpr, Integer> printerArraySizeOf = new HashMap<JNewExpr, Integer>();
		PatchingChain<Unit> unitChain = method.retrieveActiveBody().getUnits();

		for (Unit u : unitChain) {
			AWrapper state = fixPoint.getFlowBefore(u);

			System.out.println(state.get());
			System.out.println(u);

			if (u instanceof JAssignStmt) {
				JAssignStmt assign = (JAssignStmt) u;

				if (assign.getRightOp() instanceof JNewExpr) {
					// the statement following this newexpr will hopefully be a
					// constructor call, so we will remember the argument of
					// that call in printerArraySizeOf
					Unit successor = unitChain.getSuccOf(u);
					JSpecialInvokeExpr constructorCall = (JSpecialInvokeExpr) ((JInvokeStmt) successor)
							.getInvokeExpr();

					int size = -1;
					if (constructorCall.getArg(0) instanceof IntConstant) {
						// we only need to handle integer constants as arguments
						size = ((IntConstant) constructorCall.getArg(0)).value;
						
					}
					printerArraySizeOf.put((JNewExpr) assign.getRightOp(),
							size);
				}
			}

			try {
				if (state.get().isBottom(Analysis.man)) {
					// unreachable code

					// commented because the analysis does nothing yet, but we
					// still want to check outofbounds
					 continue;
				}
			} catch (ApronException e) {
				e.printStackTrace();
			}

			if (u instanceof JInvokeStmt
					&& ((JInvokeStmt) u).getInvokeExpr() instanceof JSpecialInvokeExpr) {
				// TODO: Get the size of the PrinterArray given as argument to
				// the constructor

				// this todo is implemented above...

			}

			if (u instanceof JInvokeStmt
					&& ((JInvokeStmt) u).getInvokeExpr() instanceof JVirtualInvokeExpr) {

				JInvokeStmt jInvStmt = (JInvokeStmt) u;

				JVirtualInvokeExpr invokeExpr = (JVirtualInvokeExpr) jInvStmt
						.getInvokeExpr();

				Local base = (Local) invokeExpr.getBase();
				DoublePointsToSet pts = (DoublePointsToSet) pointsTo
						.reachingObjects(base);

				if (invokeExpr.getMethod().getName()
						.equals(Analysis.functionName)) {
					
					System.out.println("in set of " + u.toString() + " is " + state.get().toString());

					// TODO: Check whether the 'sendJob' method's argument is
					// within bounds
					Value argument = invokeExpr.getArg(0);
					

					// Visit all allocation sites that the base pointer may
					// reference
					MyP2SetVisitor visitor = new MyP2SetVisitor(
							printerArraySizeOf, argument, state);
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
	Value argument;
	boolean outOfBoundsDetected;
	AWrapper state;

	public MyP2SetVisitor(HashMap<JNewExpr, Integer> printerArraySizeOf,
			Value argument, AWrapper state) {
		this.printerArraySizeOf = printerArraySizeOf;
		this.argument = argument;
		outOfBoundsDetected = false;
		this.state = state;
	}

	@Override
	public void visit(Node arg0) {
		// TODO: Check whether the argument given to sendJob is within bounds
		AllocNode alloc = (AllocNode) arg0;
		// get the size of the printerArray that we remembered earlier in the
		// hashmap
		int maxindex = printerArraySizeOf.get(alloc.getNewExpr())-1;
		Texpr1CstNode sizeNode = new Texpr1CstNode(new MpqScalar(maxindex));
		// create the node representing the argument of sendJob
		Texpr1Node argNode = null;
		if (argument instanceof JimpleLocal) {
			argNode = new Texpr1VarNode(((JimpleLocal) argument).getName());
		} else if (argument instanceof IntConstant) {
			argNode = new Texpr1CstNode(new MpqScalar(
					((IntConstant) argument).value));
		}
		/*
		 * Texpr1Intern argIntern = new
		 * Texpr1Intern(state.get().getEnvironment(), argNode); if
		 * (state.satisfy(state.man, arg1, arg2))
		 */

		Texpr1Node upperBoundExpr = new Texpr1BinNode(Texpr1BinNode.OP_SUB,
				sizeNode, argNode);
		Texpr1Intern upperExprIntern = new Texpr1Intern(state.get()
				.getEnvironment(), upperBoundExpr);
		Tcons1 upperBound = new Tcons1(Tcons1.SUPEQ, upperExprIntern);
		Texpr1Node lowerBoundExpr = argNode;
		Texpr1Intern lowerExprIntern = new Texpr1Intern(state.get()
				.getEnvironment(), lowerBoundExpr);
		Tcons1 lowerBound = new Tcons1(Tcons1.SUPEQ, lowerExprIntern);
		
		Texpr1CstNode test = new Texpr1CstNode(new MpqScalar(3));
		Texpr1BinNode bin = new Texpr1BinNode(Texpr1BinNode.OP_SUB ,test, argNode );
		Texpr1Intern intern = new Texpr1Intern(state.get().getEnvironment(), bin);
		Tcons1 tcons = new Tcons1(Tcons1.SUP, intern);
		
		try {
			
			if (!state.get().satisfy(state.man, upperBound) || !state.get().satisfy(state.man, lowerBound)) {
				outOfBoundsDetected = true;
			}
		} catch (Exception e) {
		}
		/*
		 * if (argument instanceof IntConstant) {
		 * 
		 * int argInt = ((IntConstant) argument).value; } else if (argument
		 * instanceof JimpleLocal) { argInt = } if
		 * (printerArraySizeOf.get(alloc.getNewExpr()) <= argument || 0 >
		 * argument) { outOfBoundsDetected = true; }
		 */

	}
}
