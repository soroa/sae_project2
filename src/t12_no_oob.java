// Apron is not smart enough to deduce i <= 9 from i != 10 and i <= 10

public class t12_no_oob {
	public static void m1(int i) {
		PrinterArray p1;
		PrinterArray p2;
		p1 = new PrinterArray(10);
		p2 = new PrinterArray(200);

		if (i <= 10) {
			if (i != 10) {
				if (i >= 0) {
					p2 = p1;
					p2.sendJob(i);
				}
			}
		}
	}
}
