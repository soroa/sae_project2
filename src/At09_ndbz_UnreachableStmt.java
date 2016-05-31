
public class At09_ndbz_UnreachableStmt {
	public static void m() {
		int a=1; 
		if(a<0){
			PrinterArray pa = new PrinterArray(3);
			pa.sendJob(1/0);
			pa.sendJob(4);
		}
		
		
	}
}
