package myapp;
import java.io.*;

public class BookIds implements Serializable{
	String bookTitle;
	String bookTopic;
	double bookCost;
	int itemNumber;
	int stockQty;
	
	public BookIds() {}  //Constructor
	
	public BookIds(String title, String topic, double cost, int itemNum) {
		this.bookTitle = title;
		this.bookTopic = topic;
		this.bookCost = cost;
		this.itemNumber = itemNum;
	}
	
	public void setTitle(String title) {
		this.bookTitle = title;
	}
	
	public void setTopic(String topic) {
		this.bookTopic = topic;
	}
	
	public void setCost(double cost) {
		this.bookCost = cost;
	}
	
	public void setItemNumber(int num) {
		this.itemNumber = num;
	}
	
	public void setStockQty(int qty) {
		this.stockQty = qty;
	}
	
	public double getBookCost() {
		return bookCost;
	}
	
	public String getBookTitle() {
		return bookTitle;
	}
	
	public String getBookTopic() {
		return bookTopic;
	}
	
	public int getBookItemNumber() {
		return itemNumber;
	}
	
	public int getStockQty() {
		return stockQty;
	}
}
