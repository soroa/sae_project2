
public class t16_no_oob {
	public static void m(int n) {
		PrinterArray p1 = new PrinterArray(5);
		int i;
		int b = 0;

		if (n < 0) {
			return;
		}

		for(i=0; i<n; i++){
			b++;
		}		
		if(n>=2){
			if(n<4){
				p1.sendJob(i);
			}
		}
		
		
	}
}
