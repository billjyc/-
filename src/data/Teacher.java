package data;

import java.io.Serializable;
import java.util.Calendar;

import network.LibraryServer;

public class Teacher extends Borrower implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5633244782169384520L;

	public Teacher(String id, String name, String password) {
		super(id, name, password);
		this.setMaxNumOfBorrow(20);
		this.setLevel(TEACHER);
	}
	
	public Teacher(String id,String password){
		super(id,password);
		this.setMaxNumOfBorrow(20);
		this.setLevel(TEACHER);
	}
	
	//请求图书
	public String requireBook(Book b){
		String result="lose";
		Borrower longestBorrower=BorrowerList.borrowerList.get(0);
		BorrowedBook longestBorrowedBook=longestBorrower.getBorrowedBookList().get(0);
		Calendar c=longestBorrowedBook.getReturnDate();
		long c1=c.getTimeInMillis();
		for(Borrower borrower:BorrowerList.borrowerList){
			for(BorrowedBook book:borrower.getBorrowedBookList()){
				if(book.getName().equals(b.getName())){
					if(!(book.getBorrower().getName().equals(this.getName()))){
				    Calendar d=book.getReturnDate();
				    long d1=d.getTimeInMillis();
				    long t=d1-c1;
				    if(t<=0){//还书日期更靠前
					    c1=d1;
					    c.setTimeInMillis(c1);
					    String message="教师"+this.getName()+"请求了你所借阅的图书"+"《"+b.getName()+"》，"+"请速归还！";
					    borrower.getMessage().add(message);  //向借阅者发送信息
					    result="success!";//end if
				    }else{
				    	c.setTimeInMillis(c1);
				    }
			    }// end if
			}
			}	
		}
		return result;
	}

}
