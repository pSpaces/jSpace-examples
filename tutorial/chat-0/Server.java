package chat0;

import org.jspace.FormalField;
import org.jspace.SequentialSpace;
import org.jspace.SpaceRepository;


public class Server {

    public static void main(String[] args) {
        try {
        	
        	// Create a repository 
        	SpaceRepository repository = new SpaceRepository();
        	
    		// Create a local space for the chat messages
        	SequentialSpace chat = new SequentialSpace();

        	// Add the space to the repository
    		repository.add("chat", chat);
        	
        	// Set the URI of the chat space
            String uri = "tcp://127.0.0.1:9001/chat?keep";
        				
        	// Open a gate
    		repository.addGate("tcp://127.0.0.1:9001/?keep");
			System.out.println("Opening repository gate at " + uri + "...");
        	        	
            // Keep reading chat messages and printing them 
    		while (true) {
    			Object[] t = chat.get(new FormalField(String.class), new FormalField(String.class));
    			System.out.println(t[0] + ":" + t[1]);
    		}

        } catch (InterruptedException e) {
        	e.printStackTrace();
        }
    }
}
