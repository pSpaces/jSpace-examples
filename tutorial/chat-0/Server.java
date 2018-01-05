package com.mycompany.chatServer;

import org.jspace.FormalField;
import org.jspace.SequentialSpace;
import org.jspace.SpaceRepository;


public class Server {

    public static void main(String[] args) {
        try {
        	
        	SequentialSpace chat = new SequentialSpace();
        	        	
        	SpaceRepository repository = new SpaceRepository();
    		repository.addGate("tcp://127.0.0.1:9001/?keep");
    		repository.add("chat", chat);

            // Here we wait for our result
    		while (true) {
    			Object[] t = chat.query(new FormalField(String.class), new FormalField(String.class));
    			System.out.println(t[0] + ":" + t[1]);
    		}

        } catch (InterruptedException e) {}
    }
}
