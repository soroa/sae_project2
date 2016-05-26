// outcome depends on the widening threshold


public class t8_threshold {
	public static void m1() {
		PrinterArray p1;
		p1 = new PrinterArray(10);
		int a = 0;
		int b = 0;
		for(int i = 0; i <= 8; ++i) {
			a++;
		}
		p1.sendJob(a);
	}
}
