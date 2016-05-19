// the polka domain is not sufficient to detect that this example is never out of bounds

public class t14_no_oob {
	public static void m1(int a) {
		PrinterArray p1;
		p1 = new PrinterArray(10);
		int i;
		if (a > 20) {
			i = -2;
		} else {
			i = 3;
		}

		if (i != -2) {
			p1.sendJob(i);
		}

	}
}
