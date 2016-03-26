package data;

import java.io.Serializable;


public class Book implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8643541575887378046L;
	private String id;  //编号
	private String name;  //书名
	private String ISBN;
	private String author;  //作者
	private String press;  //出版社
	private boolean isRare;  //是否为珍本
	private boolean isAvailable; //是否可借
	private int numOfBook;   //当前书的数量
	private int numOfBorrowed;//已被借走的数量
	

	public Book(String id, String name, String iSBN) {
		super();
		this.id = id;
		this.name = name;
		ISBN = iSBN;
	}

	public int getNumOfBorrowed() {
		return numOfBorrowed;
	}

	public void setNumOfBorrowed(int numOfBorrowed) {
		this.numOfBorrowed = numOfBorrowed;
	}

	public int getNumOfBook() {
		if(numOfBook>0){
			this.isAvailable=true;
		}else{
			this.isAvailable=false;
		}
		return numOfBook;
	}

	public void setNumOfBook(int numOfBook) {
		this.numOfBook = numOfBook;
		if(numOfBook>0){
			this.isAvailable=true;
		}else{
			this.isAvailable=false;
		}
	}
	
	public Book(String name, String iSBN, String author, String press,String id) {
		this.id = id;
		this.name = name;
		ISBN = iSBN;
		this.author = author;
		this.press = press;
	}

	public Book(String name) {
		super();
		this.name = name;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPress() {
		return press;
	}

	public void setPress(String press) {
		this.press = press;
	}

	public boolean isRare() {
		return isRare;
	}

	public void setRare(boolean isRare) {
		this.isRare = isRare;
	}

	public boolean isAvailable() {
		return isAvailable;
	}
}

