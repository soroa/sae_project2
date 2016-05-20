
public class At12_no_oob {
	public static void m( int n) {
		//if n>0
		PrinterArray p1 = new PrinterArray(2);
		int b=-2; 
		for(int i=0; i<n; i++){
			b++;
		}
		int i =2;

		
		if(n>=2){
			if(n<4){
				p1.sendJob(b);
			}
		}
		
		
	}
}
