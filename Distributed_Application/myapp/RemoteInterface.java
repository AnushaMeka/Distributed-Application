package myapp;

import java.rmi.*;
import java.util.*;

public interface RemoteInterface extends Remote {  
   
	public ArrayList<BookIds> search(String topic) throws RemoteException;
	public BookIds lookup(int item_number) throws RemoteException;
	public String order(int item_number) throws RemoteException;
	
	public String reportRequestsNumber(String service) throws RemoteException;
	public String reportGoodOrders() throws RemoteException;
	public String reportFailedOrders() throws RemoteException;
	public String reportServicePerformance(String service) throws RemoteException;
}  
