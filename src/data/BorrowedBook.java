package data;

import java.io.Serializable;
import java.util.Calendar;

public class BorrowedBook extends Book implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5602324500009186750L;
	private boolean canRenew;  //是否可续借
	private Calendar borrowDate;  //借入日期
	private Calendar returnDate;  //归还日期
	private Borrower borrower;    //借阅人
	private boolean isRequired;   //是否被请求
	
	public boolean isRequired() {
		return isRequired;
	}

	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	public Borrower getBorrower() {
		return borrower;
	}

	public void setBorrower(Borrower borrower) {
		this.borrower = borrower;
	}

	public boolean isCanRenew() {
		return canRenew;
	}

	public void setCanRenew(boolean canRenew) {
		this.canRenew = canRenew;
	}

    public Calendar getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(Calendar borrowDate) {
		this.borrowDate = borrowDate;
	}

	public void setReturnDate(Calendar returnDate) {
		this.returnDate = returnDate;
	}

	public Calendar getReturnDate() {
		return returnDate;
	}

	public BorrowedBook(String name, String iSBN, String author, String press,
			String id) {
		super(name, iSBN, author, press, id);
		// TODO Auto-generated constructor stub
	}

	public BorrowedBook(String id, String name, String iSBN) {
		super(id, name, iSBN);
		// TODO Auto-generated constructor stub
	}

	

}
