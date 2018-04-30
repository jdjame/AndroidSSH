
import java.io.IOException;


public class Main {
    public static void main (String [] args) throws IOException{
        int q= Integer.parseInt(args[0]);
       
        Router r= new Router(q);
        
        
        r.terminal();
    }
}
