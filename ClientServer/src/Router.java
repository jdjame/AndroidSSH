import java.io.*;
import java.net.ServerSocket;
import java.util.Scanner;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;


public class Router {
    private int port;
    private ServerSocket s;
    public Router(int args) throws IOException{
        s= new ServerSocket(args);
    }
    public void terminal(){
        Scanner k= new Scanner(System.in);
        System.out.println("who am i printing to?  ");
        int i= k.nextInt();
        System.out.println("what is the message?  ");
        String j= k.next();
        
        ExecutorService e1= Executors.newFixedThreadPool(2);
        
        e1.execute(new ServerAccept());
        e1.execute(new ClientSend());
        
        e1.shutdown();
        
        
    }
}
