/**
 * An example application you may want to analyze to test your analysis.
 *
 */
public class t3_maybe {
    public static void foo() {
        PrinterArray pa = new PrinterArray(3); 
        
        int i = 2;
        if (true) {
     	   pa = new PrinterArray(7);
        } else {
   	   pa = new PrinterArray(5);
        }
        pa.sendJob(6);
    }
}
