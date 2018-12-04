package myapp;

import java.util.*;
import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;


public class Server implements RemoteInterface {
	
	private static final int INITIAL_BOOK_QTY = 20;
	
	private int searchCount = 0, lookupCount = 0, orderCount = 0, failOrderCount = 0;	//Counters
	private long searchTime, lookupTime, orderTime;		//Timers
	private int itemID = 1;
	private String topic1 = "distributed systems";
	private String topic2 = "graduate school";

	//all books in stock belong to one of two topics. So, 2 book maps one for distributed systems and other for graduate school	(the	last	two	books) Two book maps, one for each book topic.
 
	private Map<BookIds, Integer> category1 = new LinkedHashMap<BookIds, Integer>();
	private Map<BookIds, Integer> category2 = new LinkedHashMap<BookIds, Integer>();
	ArrayList<BookIds> responseList = new ArrayList<BookIds>();
	
	public Server() {} //Constructor

	synchronized public ArrayList<BookIds> search(String topic) {
		long beginTime = System.nanoTime();
		searchCount++;
		responseList.clear();	//clear the list before each search
		if(topic.toLowerCase().equals(topic1)) {
			for(BookIds book : category1.keySet()) {
				responseList.add(book);
			}
			long endTime = System.nanoTime();
			searchTime += (endTime - beginTime);
			return responseList;
		}else if (topic.toLowerCase().equals(topic2)) {
			for(BookIds book : category2.keySet()) {
				responseList.add(book);
			}
			long endTime = System.nanoTime();
			searchTime += (endTime - beginTime);
			return responseList;
		}
		responseList = null;
		long endTime = System.nanoTime();
		searchTime += (endTime - beginTime);
		return null;
	}

	synchronized public BookIds lookup(int itemNumber) {
		long beginTime = System.nanoTime();
		lookupCount++;
		for(BookIds book : category1.keySet()) {
			if(book.itemNumber == itemNumber) {
				book.setStockQty(category1.get(book));
				long endTime = System.nanoTime();
				lookupTime += (endTime - beginTime);
				return book;
			}
		}
		for(BookIds book : category2.keySet()) {
			if(book.itemNumber == itemNumber) {
				book.setStockQty(category2.get(book));
				long endTime = System.nanoTime();
				lookupTime += (endTime - beginTime);
				return book;
			}
		}
		long endTime = System.nanoTime();
		lookupTime += (endTime - beginTime);
		BookIds book = null;
		return book;
	}


	synchronized public String order(int itemNumber) {
		long beginTime = System.nanoTime();
		orderCount++;
		BookIds book = lookup(itemNumber);
		lookupCount--;	//decrease lookup counter, since the previous call to lookup() was not made by a Client
		if(book != null) {
			if(book.stockQty > 0) {
				//update stock qty
				if(category1.containsKey(book)) {
					category1.replace(book, book.stockQty - 1);
				}else {
					category2.replace(book, book.stockQty - 1);
				}
				long endTime = System.nanoTime();
				orderTime += (endTime - beginTime);
				return "order successfull " + book.bookTitle;
			}else {
				long endTime = System.nanoTime();
				orderTime += (endTime - beginTime);
				failOrderCount++;
				return "Requested item is out of stock";
			}
		}else {
			long endTime = System.nanoTime();
			orderTime += (endTime - beginTime);
			failOrderCount++;
			return "Requested item number not found";
		}
	}


	synchronized public String reportRequestsNumber(String service) {
		if(service.toLowerCase().equals("search")) { 
			return "Total # reuqests for service " + service + ": " + searchCount;
		}else if(service.toLowerCase().equals("lookup")) {
			return "Total # reuqests for service " + service + ": " + lookupCount;
		}else if(service.toLowerCase().equals("order")) {
			return "Total # reuqests for service " + service + ": " + orderCount;
		}
		return service + " Requested service not available";
	}


	synchronized public String reportGoodOrders() {
		return "# books sold successfully: " + (orderCount - failOrderCount);
	}
	
	
	synchronized public String reportFailedOrders() {
		return "# failed orders: " + failOrderCount;
	}


	synchronized public String reportServicePerformance(String service) {
		if(service.toLowerCase().equals("search")) { 
			return "Average servicing time for " + service + ": " + ((searchTime/1000) / (long)searchCount) + " ms/request";
		}else if(service.toLowerCase().equals("lookup")) {
			return "Average servicing time for " + service + ": " + ((lookupTime/1000) / (long)lookupCount) + " ms/request";
		}else if(service.toLowerCase().equals("order")) {
			return "Average servicing time for " + service + ": " + ((orderTime/1000) / (long)orderCount) + " ms/request";
		}
		return "The service" + service + "you requested is not available";
	}


	private void initBookStore() {
		BookIds book_1 = new BookIds("How to be Good at CS5523", topic1, 55, itemID);
		category1.put(book_1, INITIAL_BOOK_QTY);
		itemID += 1;
		BookIds book_2 = new BookIds("RPCs and RMI in Distributed Systems", topic1, 65, itemID);
		category1.put(book_2, INITIAL_BOOK_QTY);
		itemID += 1;
		BookIds book_3 = new BookIds("Why Go to the Graduate School", topic2, 75, itemID);
		category2.put(book_3, INITIAL_BOOK_QTY);
		itemID += 1;
		BookIds book_4 = new BookIds("How to Survive the Graduate School", topic2, 45, itemID);
		category2.put(book_4, INITIAL_BOOK_QTY);
		itemID += 1;
		
	}



	public static void main(String[] args) {
		try {
			Server obj = new Server();
			RemoteInterface stub = (RemoteInterface) UnicastRemoteObject.exportObject(obj, 0);
			
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind("RemoteInterface", stub);
	
			obj.initBookStore();
			
			System.err.println("Server ready!!\nOpen other terminal for client and run the application.");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}

	}

}
