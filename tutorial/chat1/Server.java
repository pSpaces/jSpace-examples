package chat1;

import java.util.HashMap;
import java.util.Map;
import org.jspace.*;

public class Server {

	public static void main(String[] args) {
		try {

			// Create a repository 
			SpaceRepository repository = new SpaceRepository();

			// Create a local space for the chat messages
			SequentialSpace lobby = new SequentialSpace();

			// Add the space to the repository
			repository.add("lobby",lobby);

			// Set the URI of the chat space
			String uri = "tcp://127.0.0.1:9001/lobby?keep";

			// Open a gate
			repository.addGate("tcp://127.0.0.1:9001/?keep");
			System.out.println("Opening repository gate at " + uri + "...");

			// This space room identifiers to port numbers
			SequentialSpace rooms = new SequentialSpace();

			// Keep serving requests to enter chatrooms
			while (true) {

				// roomN will be used to ensure every chat space has a unique name
				Integer roomC = 0;

				String roomURI;

				while (true) {
					// Read request
					Object[] request = lobby.get(new ActualField("enter"),new FormalField(String.class), new FormalField(String.class));
					String who = (String) request[1];
					String roomID = (String) request[2];
					System.out.println(who + " requesting to enter " + roomID + "...");					

					// If room exists just prepare the response with the corresponding URI
					Object[] the_room = rooms.queryp(new ActualField(roomID),new FormalField(Integer.class));
					if (the_room != null) {
						roomURI = "tcp://127.0.0.1:9001/chat" + the_room[1] + "?keep";
					} 
					// If the room does not exist, create the room and launch a room handler
					else {
						System.out.println("Creating room " + roomID + " for " + who + " ...");	
						roomURI = "tcp://127.0.0.1:9001/chat" + roomC + "?keep";
						System.out.println("Setting up chat space " + roomURI + "...");
						new Thread(new roomHandler(roomID,"chat"+roomC,roomURI,repository)).start();
						rooms.put(roomID,roomC);
						roomC++;
					}

					// Sending response back to the chat client
					System.out.println("Telling " + who + " to go for room " + roomID + " at " + roomURI + "...");
					lobby.put("roomURI", who, roomID, roomURI);
				}


			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class roomHandler implements Runnable {

	private Space chat;
	private String roomID;
	private String spaceID;

	public roomHandler(String roomID, String spaceID, String uri, SpaceRepository repository) {

		this.roomID = roomID;
		this.spaceID = spaceID;
		
		// Create a local space for the chatroom
		chat = new SequentialSpace();

		// Add the space to the repository
		repository.add(this.spaceID, chat);

	}

	@Override
	public void run() {
		try {

			// Keep reading chat messages and printing them 
			while (true) {
				Object[] message = chat.get(new FormalField(String.class), new FormalField(String.class));
				System.out.println("ROOM " + roomID + " | " + message[0] + ":" + message[1]);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


	}
}
