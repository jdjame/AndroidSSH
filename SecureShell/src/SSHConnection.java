import java.io.InputStream;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 *
 * @author baddie
 */
public class SSHConnection {
    public static void main(String[] args){
        String host = "mimi.cs.mcgill.ca";
        String username="jdjame";
        String pss= "G00dlyfe!";
        String cmd="ls -la";
        
        try{
            java.util.Properties config = new java.util.Properties(); 
	    	config.put("StrictHostKeyChecking", "no");
                JSch jsch= new JSch();
                Session session=jsch.getSession(username,host,22);
                session.setPassword(pss);
                session.setConfig(config);
                session.connect();
                System.out.print("Connected");
                Channel channel=session.openChannel("exec");
                ((ChannelExec)channel).setCommand(cmd);
	        channel.setInputStream(null);
	        ((ChannelExec)channel).setErrStream(System.err);
                InputStream in=channel.getInputStream();
	        channel.connect();
	        byte[] tmp=new byte[1024];
	        while(true){
	          while(in.available()>0){
	            int i=in.read(tmp, 0, 1024);
	            if(i<0)break;
	            System.out.print(new String(tmp, 0, i));
	          }
	          if(channel.isClosed()){
	            System.out.println("exit-status: "+channel.getExitStatus());
	            break;
	          }
	          try{Thread.sleep(1000);}catch(Exception ee){}
	        }
	        channel.disconnect();
	        session.disconnect();
	        System.out.println("DONE");
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
        }
    }
