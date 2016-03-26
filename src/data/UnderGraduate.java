package data;

import java.io.Serializable;

public class UnderGraduate extends Borrower implements Serializable{

	public UnderGraduate(String id, String password) {
		super(id, password);
		this.setMaxNumOfBorrow(5);
		this.setLevel(UNDERGRADUATE);
	}
	
	public UnderGraduate(String id,String name,String password){
		super(id,name,password);
		this.setMaxNumOfBorrow(5);
		this.setLevel(UNDERGRADUATE);
	}

}
