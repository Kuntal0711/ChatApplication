import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ChatClient {
	
	Socket clientSocket = null;
	DataOutputStream stdout = null;
	DataInputStream stdin = null;
	
	public ChatClient(String hostname , int portnumber) throws IOException
	{
		System.out.println("Establishing connection....");
		try{
		clientSocket = new Socket(hostname, portnumber);
		start();
		System.out.println("Starting the connection");
		}
		catch(IOException e){
			System.err.println("Portnumber not open" +e);
		}
		catch(UnknownError e){
			System.err.println("Hostname not given" +e);
		}
		
		String line = "";
		
		while(!line.equals("bye")){
			try{
				System.out.println("Input line is " + line);
				line = stdin.readLine();
				stdout.writeUTF(line);
				System.out.println("Writing to the stream");
				stdout.flush();
			}
			catch(IOException io){
				System.out.println("IOException" +io);
			}
		}
	}
	
	public void start()throws IOException{
		stdin = new DataInputStream(System.in);
		stdout = new DataOutputStream(clientSocket.getOutputStream());
	}
	
	public void stop() throws IOException{
		try{
			if( clientSocket != null){
				clientSocket.close();
			}
			if(stdin != null){
				stdin.close();
			}
			if(stdout != null){
				stdout.close();
			}
		}
		catch(IOException e){
			System.err.println("Error closing" +e);
		}
	}
	
public static void main(String[] args)throws IOException{
		
		if(args.length != 2){
		System.err.println("Please pass proper argumnents : Hostname and Portnumber");
		System.exit(1);
		}
		
		String hostname = args[0];
	    int portnumber = Integer.parseInt(args[1]);
	 
	    ChatClient client = new ChatClient(hostname, portnumber);
	}
	
}
