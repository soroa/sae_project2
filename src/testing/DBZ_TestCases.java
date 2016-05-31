package testing;


import static org.junit.Assert.*;

import org.junit.Test;

import ch.ethz.sae.Verifier;


public class DBZ_TestCases {
	
	@Test
	public void At1_dbz() {
		String args[] = {"At01_dbz"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " MAY_OUT_OF_BOUNDS", Verifier.response);
	}
	
	@Test
	public void At2_ndbz() {
		String args[] = {"At02_ndbz"};
		Verifier.main(args);
		assertEquals(args[0] + " NO_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	@Test
	public void At3_ndbz() {
		String args[] = {"At03_dbz"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	@Test
	public void At4_ndbz() {
		String args[] = {"At04_ndbz"};
		Verifier.main(args);
		assertEquals(args[0] + " NO_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	@Test
	public void At5_ndbz() {
		String args[] = {"At05_ndbz"};
		Verifier.main(args);
		assertEquals(args[0] + " NO_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	@Test
	public void At6_ndbz() {
		String args[] = {"At06_ndbz"};
		Verifier.main(args);
		assertEquals(args[0] + " NO_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	
	
	
	@Test
	public void At7_ndbz() {
		String args[] = {"At07_dbz"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	@Test
	public void At8_ndbzE() {
		String args[] = {"At08_ndbzE"};
		Verifier.main(args);
		assertEquals(args[0] + " NO_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	@Test
	public void At8_ndbzG() {
		String args[] = {"At08_ndbzG"};
		Verifier.main(args);
		assertEquals(args[0] + " NO_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	@Test
	public void At8_ndbzGE() {
		String args[] = {"At08_ndbzGE"};
		Verifier.main(args);
		assertEquals(args[0] + " NO_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	@Test
	public void At8_ndbzL() {
		String args[] = {"At08_ndbzL"};
		Verifier.main(args);
		assertEquals(args[0] + " NO_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	@Test
	public void At8_ndbzLE() {
		String args[] = {"At08_ndbzLE"};
		Verifier.main(args);
		assertEquals(args[0] + " NO_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	@Test
	public void At8_ndbzNE() {
		String args[] = {"At08_ndbzNE"};
		Verifier.main(args);
		assertEquals(args[0] + " NO_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	
	@Test
	public void At09_UnreachableStmt() {
		String args[] = {"At09_ndbz_UnreachableStmt"};
		Verifier.main(args);
		assertEquals(args[0] + " NO_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	
	
	@Test
	public void At10_dbz_ComplexExpression() {
		String args[] = {"At10_dbz_ComplexExpression"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	

	@Test
	public void At10_ndbz_ComplexExpression() {
		String args[] = {"At10_ndbz_ComplexExpression"};
		Verifier.main(args);
		assertEquals(args[0] + " NO_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	
	@Test
	public void At10_ndbz_NonZeroParableDenominator() {
		String args[] = {"At10_ndbz_NonZeroParableDenominator"};
		Verifier.main(args);
		assertEquals(args[0] + " NO_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	@Test
	public void At10_dbz_ComplexExprContainingArg() {
		String args[] = {"At10_dbz_ComplexExprContainingArg"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	@Test
	public void At11_ndbz_NdividedByN() {
		String args[] = {"At11_ndbz_NdividedByN"};
		Verifier.main(args);
		assertEquals(args[0] + " NO_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	
	
}


