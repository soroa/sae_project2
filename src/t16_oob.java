// test if it crashes with variable argument to PrinterArray constructor

public class t16_oob {
	public static void m1(int a) {
		PrinterArray p1;
		p1 = new PrinterArray(a);
		p1.sendJob(a);
	}
}
