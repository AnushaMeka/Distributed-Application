package myapp;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class Test2 implements Runnable{
	private int myID;
	
	public Test2(int id) {
		this.myID = id;
	}
	
	public void run() {
		String host = null;
		try {
			Registry registry = LocateRegistry.getRegistry(host);
			RemoteInterface stub = (RemoteInterface) registry.lookup("Store");
			
			System.out.println("Thread number: " + myID + " is ordering...");
			order(1, stub);
		} catch(Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
	
	
		public void search(String topic, RemoteInterface stub) {
			try {
				ArrayList<BookIds> searchResp = stub.search(topic);
				if( searchResp != null) {
					for(BookIds book : searchResp) {
						System.out.println("Book title: " + book.bookTitle);
						System.out.println("Item number: " + book.itemNumber);
					}
				}else {
					System.out.println("No results found under topic: " + topic);
				}
			}catch(Exception e) {
				System.err.println("Client exception: " + e.toString());
				e.printStackTrace();
			}
		}
		
		
		public void lookup(int itemNum, RemoteInterface stub) {
			try {
				BookIds lookupBook = stub.lookup(itemNum);
				if(lookupBook != null) {
					System.out.println("Book title: " + lookupBook.bookTitle);
					System.out.println("Qty in stock: " + lookupBook.stockQty);
					System.out.println("Item cost: $" + lookupBook.bookCost);
					System.out.println("Book topic: " + lookupBook.bookTopic);
				}else {
					System.out.println("Item number not found");
				}
			}catch(Exception e) {
				System.err.println("Client exception: " + e.toString());
				e.printStackTrace();
			}
		}
	
		public void order(int itemNum, RemoteInterface stub) {
			try {
				String orderMsg = stub.order(itemNum);
				System.out.println(orderMsg);
			}catch(Exception e) {
				System.err.println("Client exception: " + e.toString());
				e.printStackTrace();
			}
		}
}

