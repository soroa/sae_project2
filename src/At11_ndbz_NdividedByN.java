
public class At11_ndbz_NdividedByN {
	public static int m(int n) {

		if(n>0){
		int d = 1/(n/n+1);
		return d;
		}
//		if(n<0){
//			int d = 1/(n/n+1);
//			return d;
//			}
		
		return 0;
		
//NB: the following if statement does the same thing but will give MAY_DIV_BY_ZERO because n is set to universal
//		if(n>0||n<0){
//			int d = 1/(n/n+1);
//			return d;
//			}
//		
//		
	}
}
