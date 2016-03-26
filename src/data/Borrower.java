package data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JTable;

import network.LibraryServer;

public class Borrower implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7300069276476659969L;
	private String id;
    private String name;
    private String password;
    private ArrayList<String> message=new ArrayList<String>();  //��Ϣ
    private Vector<BorrowedBook> borrowedBookList=new Vector<BorrowedBook>();
    private int numOfBorrowed; //�ѽ�ͼ�������
    private int maxNumOfBorrow;  //����������
    private int level;
    public static final int UNDERGRADUATE=1;
    public static final int GRADUATE=2;
    public static final int TEACHER=3;
    
    public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Borrower(String id, String name, String password) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public Borrower(String id, String password) {
		super();
		this.id = id;
		this.password = password;
	}

	public Book referBook(String id,String name){
		Book book=new Book("");
    	for(int i=0;i<BookInfo.bookList.size();i++){
    		if(BookInfo.bookList.get(i).getId().equals(id)){
    			book=BookInfo.bookList.get(i);
    			break;
    		}
    		System.out.println("��ѯ���booklist��ʣ"+BookInfo.bookList.size());
    	}
    	return book;
    }
    
    public void referBookBorrowed(){
    	
    }
    
    public void receiveMessage(){
    	for(BorrowedBook book3:this.getBorrowedBookList()){
    		Calendar c1=book3.getReturnDate();
    		Calendar c2=Calendar.getInstance();
    		int d1=c1.get(Calendar.DAY_OF_YEAR);
    		int d2=c2.get(Calendar.DAY_OF_YEAR);
    		if((d1-d2)<=7){     //�������7��黹ͼ��
    			message.add("�������ͼ�顶"+book3.getName()+"������"+(d1-d2)+"�������ڣ����ٹ黹!");
    		}
    		if(book3.isRequired()){
    			message.add("�������ͼ�顶"+book3.getName()+"���ڽ��ձ�������Ѹ�ٹ黹��");
    		}
    	}
    }
    
    public String borrowBook(Book b){
    	System.out.println("����ǰǰ��"+BookInfo.bookList.size());
    	BorrowedBook book=new BorrowedBook(b.getName(), b.getISBN(), b.getAuthor(), b.getPress(), b.getId());
    	book.setRare(b.isRare());
    	boolean isSuccess=false;
    	String str="����ʧ�ܣ�";
    	//����ﵽ������ĵ�����飬���������
    	if((this.getNumOfBorrowed())>=(this.getMaxNumOfBorrow())){
    		isSuccess=false;
    		str="���Ѿ��ﵽͼ����ĵ�������ޣ�";
    	}else{
    		if(b.isRare()&&(this.getLevel()<=Borrower.UNDERGRADUATE)){
    			isSuccess=false;
    			str="������û�н����䱾ͼ���Ȩ�ޣ�";
    		}else {
				if(!b.isAvailable()){
					isSuccess=false;
					str="������û�п�棡";
				}else{
					SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
					Calendar c=Calendar.getInstance();
					System.out.println(df.format(c.getTime()));
					int d=c.get(Calendar.DAY_OF_MONTH);
					d=d+30;
					c.set(Calendar.DAY_OF_MONTH, d);
					System.out.println(df.format(c.getTime()));
					
					for(Book book2:BookInfo.bookList){
						if(book2.getId().equals(b.getId())){
							book2.setNumOfBorrowed(b.getNumOfBorrowed()+1);
							book2.setNumOfBook(b.getNumOfBook()-1);
							book.setBorrowDate(Calendar.getInstance());
							book.setReturnDate(c);
							book2.setNumOfBorrowed(this.getNumOfBorrowed()+1);
							this.getBorrowedBookList().add(book);
							this.setNumOfBorrowed(getNumOfBorrowed()+1);
							book.setBorrower(this);
							book.setCanRenew(true);
							System.out.println("������"+this.getName()+"������ͼ�顶"+b.getName()+"��һ��");
							System.out.println("��Ҫ��"+df.format(c.getTime())+"֮ǰ�黹");
							isSuccess=true;
							str="borrow successfully!";
							break;
						}
					
					}
				    //System.out.println(BookInfo.bookList.size());
					BorrowerList.saveBorrowerList();
					BookInfo.saveBookList();
					System.out.println(BookInfo.bookList.size());
					//System.out.println(BorrowerList.borrowerList.size());
					//LibraryServer.bookList=BookInfo.getBookList();
				}
			}
    	}
    	return str;
    }
    
    public String renewBook(BorrowedBook b){
    	String result="����û�н����Ȿͼ�飡";
    	for(int i=0;i<this.getBorrowedBookList().size();i++){
    		if(this.getBorrowedBookList().get(i).getId().equals(b.getId())){
    			b=this.getBorrowedBookList().get(i);
    			break;
    		}
    	}
    	if(!b.isCanRenew()){
    		result="�����Ѿ��ﵽ�������������";
    	}else if(b.isRequired()){
    		result="�����ѱ�����";
    	}else{
    		Calendar c=b.getReturnDate();
    		int d=c.get(Calendar.DAY_OF_MONTH);
    		d=d+30;
    		c.set(Calendar.DAY_OF_MONTH, d);
    		b.setReturnDate(c);
    		b.setCanRenew(false);
    		result="����ɹ���";
    		BorrowerList.saveBorrowerList();
			BookInfo.saveBookList();
    	}
    	return result;
    }
    
    public String returnBook(BorrowedBook b){
    	String result="�黹ʧ�ܣ�";
    	for(BorrowedBook book:this.getBorrowedBookList()){
    		if(book.getId().equals(b.getId())){
    			this.getBorrowedBookList().remove(book);
    			this.setNumOfBorrowed(getNumOfBorrowed()-1);
    			System.out.println("size of borrowedbookList is "+getBorrowedBookList().size());
    			for(Book book1:BookInfo.bookList){
    				if(book1.getName().equals(b.getName())){
    					book1.setNumOfBook(book1.getNumOfBook()+1);
    					book1.setNumOfBorrowed(book1.getNumOfBorrowed()-1);
                        result="�黹�ɹ���";
                        break;
    				}
    			}
    			break;
    		}
    	}
    	System.out.println(result);
    	BorrowerList.saveBorrowerList();
    	BookInfo.saveBookList();
    	return result;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getNumOfBorrowed() {
		return numOfBorrowed;
	}

	public void setNumOfBorrowed(int numOfBorrowed) {
		this.numOfBorrowed = numOfBorrowed;
	}

	public int getMaxNumOfBorrow() {
		return maxNumOfBorrow;
	}

	public void setMaxNumOfBorrow(int maxNumOfBorrow) {
		this.maxNumOfBorrow = maxNumOfBorrow;
	}

	public ArrayList<String> getMessage() {
		return message;
	}

	public void setMessage(ArrayList<String> message) {
		this.message = message;
	}

	public Vector<BorrowedBook> getBorrowedBookList() {
		return borrowedBookList;
	}

	public void setBorrowedBookList(Vector<BorrowedBook> borrowedBookList2) {
		this.borrowedBookList = borrowedBookList2;
	}
}
