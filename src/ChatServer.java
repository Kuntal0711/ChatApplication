import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer{
	
	ServerSocket server = null;
	Socket clientSocket = null;
	DataInputStream stdin = null;
	
	public ChatServer(int portnumber) throws IOException{
		
		try{
			server = new ServerSocket(portnumber);
			System.out.println("trying to make connection to client");
			System.out.println("Opening the connection on Server side");
			clientSocket = server.accept();
			System.out.println("Connection made to client");
			open();
			boolean flag = false;
			
			while(!flag){
				try{
					String line = stdin.readUTF();
					System.out.println(line);
					if(line == "bye"){
						flag = true;
						System.out.println("Closing the connection");
						close();
					}
				}
				catch(IOException io){
					System.out.println("IOException" +io);
				}
			}
		}
		catch(IOException io){
			System.out.println("IOException" +io);
		}
	}
	
	public void open() throws IOException{
		try{
			stdin = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
		}
		catch(IOException e){
			System.err.println("Throwing IOException" +e);
		}
	}
	
	public void close() throws IOException{
		try{
			if(server != null){
				server.close();
			}
			if(clientSocket != null){
				clientSocket.close();
			}
		}
		catch(IOException e){
			System.out.println("Throwing IOException" +e);
		}
	}
	public static void main(String args[]) throws IOException {
		
	if(args.length != 1)
	{
		System.err.println("PortNo not given , please provide the portnumber");
		System.exit(1);
	}
	int portNumber = Integer.parseInt(args[0]);
	ChatServer chatServer = new ChatServer(portNumber);

	
	}
}
