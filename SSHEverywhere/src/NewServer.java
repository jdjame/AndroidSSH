import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.InputStream;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 *
 * @author baddie
 */
public class NewServer {
    public static void main (String [] args) throws IOException, JSchException{
        String u1 = null, p1 = null, u0 = null, p0 = null, ip0 = null, us0 = null, pa0 = null, ip1 = null, us1 = null, pa1 = null, fuse = null,fip = null,fpas = null;
        try{
            File xml= new File("users.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xml);
            Element root = doc.getDocumentElement();
            
            u0=root.getElementsByTagName("username").item(0).getTextContent();
            p0=root.getElementsByTagName("password").item(0).getTextContent();
            
            u1=root.getElementsByTagName("username").item(1).getTextContent();
            p1=root.getElementsByTagName("password").item(1).getTextContent();
            
            File xml1= new File("ssh_service.xml");
            DocumentBuilderFactory dbFactory1 = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder1 = dbFactory.newDocumentBuilder();
            Document doc1 = dBuilder1.parse(xml1);
            Element root1 = doc1.getDocumentElement();

            ip0=root1.getElementsByTagName("ip").item(0).getTextContent();
            us0=root1.getElementsByTagName("username").item(0).getTextContent();
            pa0=root1.getElementsByTagName("password").item(0).getTextContent();
            
            ip1=root1.getElementsByTagName("ip").item(1).getTextContent();
            us1=root1.getElementsByTagName("username").item(1).getTextContent();
            pa1=root1.getElementsByTagName("password").item(1).getTextContent();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        
        Scanner r=new Scanner(System.in);
        System.out.println("what is the port number? ");
        int port= r.nextInt();
        ServerSocket server= new ServerSocket(port);
        try{
            while(true){
                Socket c= server.accept();
                try{
                    
                    PrintWriter out= new PrintWriter(c.getOutputStream(), true);
                    
                    BufferedReader in= new BufferedReader(new InputStreamReader(c.getInputStream()));
                    
                    out.println("Connected !");
                    out.println("what is your username ? ");
                    String use=in.readLine();
                    out.println("what is your password ? ");
                    String pas=in.readLine();
                    
                    if(use.equals(u0)&&pas.equals(p0)){
                        out.println("welcome "+ u0);
                        fuse=us0;
                        fip=ip0;
                        fpas=pa0;
                    }
                    else if (use.equals(u1)&&pas.equals(p1)){
                        out.println("Welcome "+ u1);
                        fuse=us1;
                        fip=ip1;
                        fpas=pa1;
                    }
                    else{
                        out.println("Either user name and/or password is in the wrong form");
                        System.exit(0);
                    }
                    
                    try{
                        java.util.Properties config = new java.util.Properties(); 
                        config.put("StrictHostKeyChecking", "no");
                        
                        JSch jsch= new JSch();
                        Session session=jsch.getSession(fuse,fip,22);
                        session.setPassword(fpas);
                        session.setConfig(config);
                        session.connect();
                        out.println("SSH connection successful");
                        
                        Channel channel=session.openChannel("exec");
                        
                        out.println("What is your command?");

                        String cmd=in.readLine();
                        while(!(cmd.toLowerCase().equals("quit"))){
                            
                            channel=session.openChannel("exec");
                            ((ChannelExec)channel).setCommand(cmd);
                            InputStream in0=channel.getInputStream();
                            channel.connect();
                            byte[] tmp=new byte[1024];
                            boolean t=true;
                            while(t){
                                while(in0.available()>0){
                                    int i=in0.read(tmp, 0, 1024);
                                    if(i<0)break;
                                    //out.println(new String(tmp, 0, i));
                                    out.println(new String(tmp));
                                    System.out.println(new String(tmp,0,i));
                                }
                                
                                if(channel.isClosed()){
                                    //out.println("exit-status: "+channel.getExitStatus());
                                    t=false;
                                }
                                try{Thread.sleep(1000);}catch(Exception ee){}
                            }
                            out.println("What is your next command? ");
                            cmd=in.readLine();
                            //System.out.println(cmd);
                        }
                        channel.disconnect();
                        session.disconnect();
                        out.println("Thank you for logging in :)");
                        System.exit(0);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        }
                    
                }
                finally{
                }
            }
        }
            finally{
                //c.close();
                //server.closer()
                    }
    }
}
