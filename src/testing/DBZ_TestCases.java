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
}


