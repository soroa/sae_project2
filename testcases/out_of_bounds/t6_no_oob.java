
public class t6_no_oob {
	static public void m(int i) {
		int a = 2;
		PrinterArray p = new PrinterArray(3);
		int y = 0;
		if (i > 2) {
			a = 2;
			y = 0;
		} else {
			if(i > 1) {
				p.sendJob(i);
			}
			a = 2;
			y = 0;
		}
		p.sendJob(a+y);
	}
}
