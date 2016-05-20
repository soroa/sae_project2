package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.ethz.sae.Verifier;

public class TestCases {

	@Test
	public void t1_no_oob() {
		String args[] = {"t1_no_oob"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	
	@Test
	public void t2_no_oob() {
		String args[] = {"t2_no_oob"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	
	@Test
	public void t3_maybe() {
		String args[] = {"t3_maybe"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}

	@Test
	public void t4_oob() {
		String args[] = {"t4_oob"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " MAY_OUT_OF_BOUNDS", Verifier.response);
	}	
	
	@Test
	public void t5_no_oob() {
		String args[] = {"t5_no_oob"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	
	@Test
	public void t6_no_oob() {
		String args[] = {"t6_no_oob"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	
	@Test
	public void t7_no_oob() {
		String args[] = {"t7_no_oob"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	
	@Test
	public void t8_threshold() {
		String args[] = {"t8_threshold"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " MAY_OUT_OF_BOUNDS", Verifier.response);
	}
	
	@Test
	public void t9_oob() {
		String args[] = {"t9_oob"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " MAY_OUT_OF_BOUNDS", Verifier.response);
	}
	
	@Test
	public void t9_no_oob() {
		String args[] = {"t9_no_oob"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	
	@Test
	public void t11_oob() {
		String args[] = {"t11_oob"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " MAY_OUT_OF_BOUNDS", Verifier.response);
	}
	
	@Test
	public void t12_no_oob() {
		String args[] = {"t12_no_oob"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	
	@Test
	public void t13_no_oob() {
		String args[] = {"t13_no_oob"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	
	@Test
	public void t14_no_oob() {
		String args[] = {"t14_no_oob"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	
	@Test
	public void t15_no_oob() {
		String args[] = {"t15_no_oob"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}

	@Test
	public void At10_no_oob() {
		String args[] = {"At10_no_oob"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	
	@Test
	public void At10_oob() {
		String args[] = {"At10_oob"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " MAY_OUT_OF_BOUNDS", Verifier.response);
	}
	@Test
	public void At11_oob() {
		String args[] = {"At11_oob"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " MAY_OUT_OF_BOUNDS", Verifier.response);
	}
	
	@Test
	public void At12_no_oob() {
		String args[] = {"At12_no_oob"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
}
