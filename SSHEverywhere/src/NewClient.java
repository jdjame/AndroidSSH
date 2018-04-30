import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author baddie
 */
public class NewClient {
    public static void main(String [] args) throws IOException{
        Scanner r=new Scanner(System.in);
        System.out.println("what is the number of the port you would like to connect to ? ");
        int port= r.nextInt();
        Socket c= new Socket("localhost", port);
        
        PrintWriter out= new PrintWriter(c.getOutputStream(),true);
        
        BufferedReader in= new BufferedReader(new InputStreamReader(c.getInputStream()));
        
        System.out.println(in.readLine());
        System.out.println(in.readLine());
        String x=r.next();
        out.println(x);
        System.out.println(in.readLine());
        x=r.next();
        out.println(x);
        System.out.println(in.readLine());
        System.out.println(in.readLine());
        System.out.println(in.readLine());
        String cmd = r.next();
        out.println(cmd);
        while(!(cmd.toLowerCase().equals("quit"))){
           
            x=in.readLine();
            while(!(x.equals("What is your next command? "))){
                System.out.println(x);
                x=in.readLine();
            } 
            System.out.println(x);
             cmd=r.nextLine();
             out.println(cmd);
        }
        if (cmd.toLowerCase().equals("quit")){
            System.out.println(in.readLine());
            System.exit(0);
        }
    }
}
