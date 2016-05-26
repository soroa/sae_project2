public class t15_no_oob {
	public static void m1(int a) {
		PrinterArray p1;
		p1 = new PrinterArray(10);
		int i;
		if (a > 20) {
			i = 3;
		} else {
			i = 10;
		}

		if (i < 10) {
			p1.sendJob(i);
		}

	}
}
