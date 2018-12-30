package rpc;

import org.jspace.*;

public class Server {
	
	private static Integer foo(Integer x, Integer y, Integer z){
		return x + y + z;
	}
	
	private static String bar(String x, String y){
		return x + y;
	}

	public static void main(String[] args) {
		try {

			// Create a repository 
			SpaceRepository repository = new SpaceRepository();

			// Create a local space for the rpc messages
			SequentialSpace rpc = new SequentialSpace();

			// Add the space to the repository
			repository.add("rpc",rpc);

			// Set the URI of the gate
			String uri = "tcp://127.0.0.1:9001/rpc?keep";

			// Open a gate
			repository.addGate("tcp://127.0.0.1:9001/?keep");
			System.out.println("Opening repository gate at " + uri + "...");


			Object[] request;
			Object[] arguments;
			String callID;
			String f;
			
			// Keep serving requests to enter chatrooms
			while (true) {

				request = rpc.get(new FormalField(String.class), new ActualField("func"), new FormalField(String.class));
				callID = (String) request[0];
				f = (String) request[2];
				System.out.print("Serving request " + callID + " to run " + f + "(");
				switch (f) {
				case "foo":
					arguments = rpc.get(new ActualField(callID), new ActualField("args"), new FormalField(Integer.class), new FormalField(Integer.class), new FormalField(Integer.class));
					System.out.println("...)");
					rpc.put(callID, "result", foo((Integer) arguments[2], (Integer) arguments[3], (Integer) arguments[4]));
				case "bar":
					arguments = rpc.get(new ActualField(callID), new ActualField("args"), new FormalField(String.class), new FormalField(String.class));
					System.out.println("...)");
					rpc.put(callID, "result", bar((String)arguments[2], (String)arguments[3]));
				default:
					// ignore RPC for unknown functions
					continue;
				}


			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
