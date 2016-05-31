package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.ethz.sae.Verifier;

public class SharedTestCases {

	
	@Test
	public void Test1() {
		String args[] = {"Test1"};
		Verifier.main(args);
		assertEquals(args[0] + " NO_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}

	@Test
	public void Test2() {
		String args[] = {"Test2"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	//not relevant, constructor initialized with local variable
	//@Test
	public void Test3() {
		String args[] = {"Test3"};
		Verifier.main(args);
		assertEquals(args[0] + " NO_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	@Test
	public void Test4() {
		String args[] = {"Test4"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " MAY_OUT_OF_BOUNDS", Verifier.response);
	}
	
	//not relevant var in constructor
	//@Test
	public void Test5() {
		String args[] = {"Test5"};
		Verifier.main(args);
		assertEquals(args[0] + " MAY_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	@Test
	public void Test6() {
		String args[] = {"Test6"};
		Verifier.main(args);
		assertEquals(args[0] + " NO_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	@Test
	public void Test7() {
		String args[] = {"Test7"};
		Verifier.main(args);
		assertEquals(args[0] + " NO_DIV_ZERO\n" + args[0] + " MAY_OUT_OF_BOUNDS", Verifier.response);
	}
	@Test
	public void Test10() {
		String args[] = {"Test10"};
		Verifier.main(args);
		assertEquals(args[0] + " NO_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	@Test
	public void Test9() {
		String args[] = {"Test9"};
		Verifier.main(args);
		assertEquals(args[0] + " NO_DIV_ZERO\n" + args[0] + " MAY_OUT_OF_BOUNDS", Verifier.response);
	}
	@Test
	public void Test8() {
		String args[] = {"Test8"};
		Verifier.main(args);
		assertEquals(args[0] + " NO_DIV_ZERO\n" + args[0] + " MAY_OUT_OF_BOUNDS", Verifier.response);
	}
	@Test
	public void Test11() {
		String args[] = {"Test11"};
		Verifier.main(args);
		assertEquals(args[0] + " NO_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
	//not relevant
	//@Test
	public void Test12() {
		String args[] = {"Test12"};
		Verifier.main(args);
		assertEquals(args[0] + " NO_DIV_ZERO\n" + args[0] + " NO_OUT_OF_BOUNDS", Verifier.response);
	}
}
