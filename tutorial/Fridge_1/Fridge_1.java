package common.src.main;

import org.jspace.*;

import java.util.Arrays;
import java.util.List;

public class Fridge_1 {

	public static void main(String[] argv) throws InterruptedException {

		// Creating a space.
		Space fridge = new SequentialSpace(); 

		new Thread(new Alice(fridge)).start();
		new Thread(new Bob(fridge)).start();
		new Thread(new Charlie(fridge)).start();

	}

}

class Alice implements Runnable {
	private Space fridge;

	public Alice(Space fridge){
		this.fridge = fridge;
	}

	@Override
	public void run() {
		try {
			fridge.put("milk", 2);
			fridge.put("butter", 1);
			fridge.put("shop!");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


class Bob implements Runnable {
	private Space fridge;

	public Bob(Space fridge){
		this.fridge = fridge;
	}


	@Override
	public void run() {
		try{
			fridge.query(new ActualField("shop!"));
			while (true) {
				Object[] item = fridge.get(new FormalField(String.class), new FormalField(Integer.class));
				System.out.println("Bob: I am shopping " + item[1] +" items of " + item[0] + "...");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

class Charlie implements Runnable {
	private Space fridge;

	public Charlie(Space fridge){
		this.fridge = fridge;
	}


	@Override
	public void run() {
		try{
			Object[] t = fridge.queryp(new ActualField("shop!"));
			if (t != null) {
				while (true) {
					Object[] item = fridge.get(new FormalField(String.class), new FormalField(Integer.class));
					System.out.println("Charlie: I am shopping " + item[1] +" items of " + item[0] + "...");
				}
			} else {
				System.out.println("Charlie: I am just relaxing...");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
