// Server Application
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Date;
import java.util.logging.*;
import java.util.concurrent.*;
public class Server {
	
	private ServerSocket serverSocket = null;
	private Protocol spt = null;
	private final static Logger auditLogger = Logger.getLogger("requests");
	
	public static void main( String[] args ) {
		Server server = new Server();
		server.runServer();
	}
	
	public Server() {
		try {
			serverSocket = new ServerSocket(8888); 
		}
		catch (IOException e) {
            System.err.println("Could not listen on port: 8888.");
            System.exit(1);
        }
        ExecutorService es = Executors.newFixedThreadPool(10);
        spt = new Protocol();
	}
	
	public void runServer() {
		Socket clientSocket = null;
		ExecutorService es = Executors.newFixedThreadPool(10);
        while( true ){

            try {
   	            clientSocket = serverSocket.accept();
            } 
            catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

			System.out.println("clientSocket port: " + clientSocket.getPort() );
			es.submit(new ClientHandler(clientSocket));
            try {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
    	    	                	new InputStreamReader(
    	                        	clientSocket.getInputStream()));
                String inputLine, outputline;
                outputline = spt.processInput(null);
                //out.println(outputLine);
				int state = 0;
                while ((inputLine = in.readLine()) != null) {
                     outputline = spt.processInput(inputLine);
                     out.println(outputline);
                     if (outputline.equalsIgnoreCase("list"))
                     	state += 1;
                     if (outputline.equalsIgnoreCase("get"))
                     	state += 1;
                     if (outputline.equalsIgnoreCase("put"))
                     	state += 1;
                     if (state == 3)
                        break;
                }
                out.close();
                in.close();
                clientSocket.close();
            }
            catch (IOException e) {
                System.out.println( e );
            }
        }
	}
}
