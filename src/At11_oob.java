
public class At11_oob {
	public static void m() {
		int a= 1; 
		PrinterArray p0; 
		PrinterArray p1 = new PrinterArray(3);
		PrinterArray p2 = new PrinterArray(9);
		if(a==1){
			p0=p1;
			p0.sendJob(5);
		}else{
			p0=p2;
			p0.sendJob(5);
		}
	

	}
}
