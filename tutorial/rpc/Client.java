package rpc;

import org.jspace.ActualField;
import org.jspace.FormalField;
import org.jspace.RemoteSpace;
import java.io.IOException;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) {

		try {

			// Set the URI of the rpc server
			String uri = "tcp://127.0.0.1:9001/rpc?keep";

			// Connect to the server
			System.out.println("Connecting to " + uri + "...");
			RemoteSpace server = new RemoteSpace(uri);
									
			// Invoke foo(1,2,3) remotely
			System.out.println("Invoking foo(1,2,3) on server...");
			server.put("Alice1", "func", "foo");
			server.put("Alice1", "args", 1, 2, 3);

			// Get the result
			Object[] response;
			System.out.println("Waiting for a response...");
			response = server.get(new ActualField("Alice1"), new ActualField("result"), new FormalField(Integer.class));
			System.out.println("Server says foo(1,2,3) = " + response[2]);

			
			// Invoke bar("a","b") remotely
			System.out.println("Invoking bar(\"a\",\"b\") on server...");
			server.put("Alice2", "func", "bar");
			server.put("Alice2", "args", "a", "b");

			// Get the result
			System.out.println("Waiting for a response...");
		    response = server.get(new ActualField("Alice2"), new ActualField("result"), new FormalField(String.class));
			System.out.println("Server says bar(\"a\",\"b\") = " + response[2]);


		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
