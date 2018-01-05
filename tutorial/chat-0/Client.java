package com.mycompany.chatClient;

import org.jspace.RemoteSpace;
import java.io.IOException;
import java.net.UnknownHostException;

public class Client {

    public static void main(String[] args) {
        RemoteSpace chat;
		try {
			chat = new RemoteSpace("tcp://127.0.0.1:9001/chat?keep");
			
			while (true) {
				chat.put("Alice","hello");
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
