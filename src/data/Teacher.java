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
	
	//����ͼ��
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
				    if(t<=0){//�������ڸ���ǰ
					    c1=d1;
					    c.setTimeInMillis(c1);
					    String message="��ʦ"+this.getName()+"�������������ĵ�ͼ��"+"��"+b.getName()+"����"+"���ٹ黹��";
					    borrower.getMessage().add(message);  //������߷�����Ϣ
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
