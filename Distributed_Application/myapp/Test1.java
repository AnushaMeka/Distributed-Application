package myapp;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class Test1{
	
	private Test1() {}


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
	

	public void reportRequestsNumber(String service, RemoteInterface stub) {
		try {
			String requestsReport = stub.reportRequestsNumber(service);
			System.out.println(requestsReport);
		}catch(Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
	

	public void reportGoodOrders(RemoteInterface stub) {
		try {
			String ordersReport = stub.reportGoodOrders();
			System.out.println(ordersReport);
		}catch(Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
	
	
	public void reportFailedOrders(RemoteInterface stub) {
		try {
			String failedOrders = stub.reportFailedOrders();
			System.out.println(failedOrders);
		}catch(Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
	
	
	public void reportServicePerformance(String service, RemoteInterface stub) {
		try {
			String performanceReport = stub.reportServicePerformance(service);
			System.out.println(performanceReport);
		}catch(Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		String host = null;
		Test1 clientApp = new Test1();
		
		try {
			Registry registry = LocateRegistry.getRegistry(host);
			RemoteInterface stub = (RemoteInterface) registry.lookup("Store");
			
			System.out.println("*** TESTING search() SERVICE ***");
			System.out.println("Searching for: distributed systems");
			clientApp.search("distributed systems", stub);
			System.out.println("Searching for: graduate school");
			clientApp.search("graduate school", stub);
			System.out.println("Searching for: computer science");
			clientApp.search("computer science", stub);
			
			System.out.println("*** TESTING lookup() SERVICE ***");
			System.out.println("Looking up item number: 1");
			clientApp.lookup(1, stub);
			System.out.println("Looking up item number: 2");
			clientApp.lookup(2, stub);
			System.out.println("Looking up item number: 3");
			clientApp.lookup(3, stub);
			System.out.println("Looking up item number: 4");
			clientApp.lookup(4, stub);
			System.out.println("Looking up item number: 6");
			clientApp.lookup(6, stub);
			
			System.out.println("*** TESTING order() SERVICE ***");
			System.out.println("Ordering item number: 1");
			clientApp.order(1, stub);
			System.out.println("Ordering item number: 2");
			clientApp.order(2, stub);
			System.out.println("Ordering item number: 3");
			clientApp.order(3, stub);
			System.out.println("Ordering item number: 4");
			clientApp.order(4, stub);
			System.out.println("Ordering item number: 6");
			clientApp.order(6, stub);
			for(int i = 0; i < 5; i++) {
				System.out.println("Ordering item number: 2");
				clientApp.order(2, stub);
			}
			System.out.println("Looking up item number: 2");
			clientApp.lookup(2, stub);
			
			System.out.println("*** TESTING reportRequestsNumber() ***");
			System.out.println("Reporting requests for: search");
			clientApp.reportRequestsNumber("search", stub);
			System.out.println("Reporting requests for: lookup");
			clientApp.reportRequestsNumber("lookup", stub);
			System.out.println("Reporting requests for: order");
			clientApp.reportRequestsNumber("order", stub);
			
			System.out.println("*** TESTING reportGoodOrders() ***");
			clientApp.reportGoodOrders(stub);
			
			System.out.println("*** TESTING reportFailedOrders() ***");
			clientApp.reportFailedOrders(stub);
			
			System.out.println("*** TESTING reportServicePerformance() ***");
			System.out.println("Reporting service performance for: search");
			clientApp.reportServicePerformance("search", stub);
			System.out.println("Reporting service performance for: lookup");
			clientApp.reportServicePerformance("lookup", stub);
			System.out.println("Reporting service performance for: order");
			clientApp.reportServicePerformance("order", stub);
			
		} catch(Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
}
