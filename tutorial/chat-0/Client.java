package chat0;

import org.jspace.RemoteSpace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

public class Client {

    public static void main(String[] args) {

		try {

			// Set the URI of the chat space
			String uri = "tcp://127.0.0.1:9001/chat?keep";
			
			// Connect to the remote chat space 
			System.out.println("Connecting to chat space " + uri + "...");
	    	RemoteSpace chat = new RemoteSpace(uri);
			
			// Read user name from the console			
			System.out.print("Enter your name: ");
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			String name = input.readLine();

			// Keep sending whatever the user types
			System.out.println("Start chatting...");
			while(true) {
				String message = input.readLine();
				chat.put(name, message);
			}			
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
