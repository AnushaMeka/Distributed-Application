package myapp;

import java.rmi.*;
import java.util.*;
import java.io.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;


public class Client{
	
	private Client() {}  //Constructor


	public void search(String topic, RemoteInterface stub) {
		ArrayList<BookIds> searchResp = new ArrayList<BookIds>();
		try {
			searchResp = stub.search(topic);
			if( searchResp != null) {
			 	for(BookIds book : searchResp) {
					System.out.println("Book title: " + book.bookTitle);
					System.out.println("Item number: " + book.itemNumber);
				}
			}else {
				System.out.println("No results found under the requested topic: " + topic);
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
				System.out.println("# available items in stock: " + lookupBook.stockQty);
				System.out.println("BOok cost: $" + lookupBook.bookCost);
				System.out.println("Book topic: " + lookupBook.bookTopic);
			}else {
				System.out.println("Item number you requested was not found");
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
	
	
	private void displayMenu() {
		System.out.println("Select a number from below for a particular service: ");
		System.out.println("1 :: Search");
		System.out.println("2 :: Lookup");
		System.out.println("3 :: Order");
		System.out.println("4 :: Report Requests Number");
		System.out.println("5 :: Report Good Orders");
		System.out.println("6 :: Report Failed Orders");
		System.out.println("7 :: Report Service Performance");
		System.out.println("8 :: Exit program");
	}
	

	public static void main(String[] args) {
		
		String host = null;
		Client clientObj = new Client();
		BufferedReader strReader = new BufferedReader(new InputStreamReader(System.in));
		Scanner intReader = new Scanner(System.in);
		boolean exit = false;
		
		try {
			Registry registry = LocateRegistry.getRegistry(host);
			RemoteInterface stub = (RemoteInterface) registry.lookup("RemoteInterface");
			
			while( exit == false) {
				clientObj.displayMenu();
				int choice = intReader.nextInt();
				switch(choice) {
					case 1:
						System.out.println("Searching myBooks.com?? \nselect topic in the string format as below...\ndistributed systems or graduate school");
						String searchTopic = strReader.readLine().toLowerCase();
						clientObj.search(searchTopic, stub);
						break;
					case 2:
						System.out.println("Lookup myBooks.com?? \nselect a number from 1 to 4");
						int itemNum = intReader.nextInt();
						clientObj.lookup(itemNum, stub);
						break;
					case 3:
						System.out.println("Book Order?? \nselect a number from 1 to 4");
						int itemNbr = intReader.nextInt();
						clientObj.order(itemNbr, stub);
						break;
					case 4:
						System.out.println("Report request?? \nselect one service from below:\nsearch, lookup, order");
						String serviceReport = strReader.readLine().toLowerCase();
						clientObj.reportRequestsNumber(serviceReport, stub);
						break;
					case 5: 
						clientObj.reportGoodOrders(stub);
						break;
					case 6:
						clientObj.reportFailedOrders(stub);
						break;
					case 7:
						System.out.println("Service Performance report?? \nselect one service from below:\nsearch, lookup, order");
						String perfReport = strReader.readLine().toLowerCase();
						clientObj.reportServicePerformance(perfReport, stub);
						break;
					case 8:
						exit = true;
						break;
					default:
						System.out.println("Incorrect selection!! Please enter a number from 1 to 8.");
						break;
				}
			}
		} catch(Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
		intReader.close();	
	}
}
