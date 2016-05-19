public class t11_oob {
	public static void m1(int i) {
		PrinterArray p1;
		PrinterArray p2;
		p1 = new PrinterArray(10);
		p2 = new PrinterArray(200);
	if (i != 10) {
		p2 = p1;
	}
		p2.sendJob(2*5);
	}
}
