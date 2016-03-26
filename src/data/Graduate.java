package data;

import java.io.Serializable;

public class Graduate extends Borrower implements Serializable{

	public Graduate(String id, String name, String password) {
		super(id, name, password);
		this.setMaxNumOfBorrow(10);
		this.setLevel(GRADUATE);
	}
	
	public Graduate(String id, String password) {
		super(id, password);
		this.setMaxNumOfBorrow(10);
		this.setLevel(GRADUATE);
	}


}
