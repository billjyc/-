package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;

import data.Administrator;
import data.AdministratorList;
import data.Book;
import data.BookInfo;
import data.BorrowedBook;
import data.Borrower;
import data.BorrowerList;
import data.Teacher;

public class LibraryServer {
	
	ArrayList<ObjectOutputStream> clientOutputStreams;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	Borrower b;
	Administrator admin;
	Book book;
	ServerSocket serverSocket;
	public class ClientHandler implements Runnable{
        Socket sock;
        public ClientHandler(Socket clientSocket){
        	try{
        		sock=clientSocket;
        		
        	}catch (Exception e) {
				e.printStackTrace();
			}
        }
		@Override
		public void run() {
			Object s;
			String info;
			try{
				s=ois.readObject();
				info=(String)s;
				if(info.startsWith("borrower login")){
				    System.out.println("server���յ������˵�½����----->");
					String id=(String)ois.readObject();
					String name=(String)ois.readObject();
					Borrower borrower=new Borrower(id,name);
					borrowerLogin(borrower);
				}else if(info.startsWith("admin login")){
				    System.out.println("server���յ�ϵͳ����Ա��½����----->");
				    String id=(String)ois.readObject();
					String name=(String)ois.readObject();
					Administrator admin=new Administrator(id,name);
					adminLogin(admin);
				}else if(info.startsWith("refer a book")){
				    System.out.println("server���յ���ѯͼ����������----->");
					String id=(String) ois.readObject();
					String name=(String) ois.readObject();
					referBook(id,name);
				}else if(info.startsWith("borrow a book")){
					System.out.println("server���յ���������--->");
					Book book=(Book)ois.readObject();
					System.out.println(book.getName());
					borrowBook(book);
				}else if(info.startsWith("require a book")){
					System.out.println("server���յ�����ͼ������--->");
					Book book=(Book) ois.readObject();
					requireBook(book);
				}else if(info.startsWith(("message receive"))){
					System.out.println("server���յ���Ϣ����");
					messageReceive();
				}else if(info.startsWith("renew a book")){
					System.out.println("server���յ���������--->");
					String id=(String) ois.readObject();
					String name=(String) ois.readObject();
					String isbn=(String) ois.readObject();
					BorrowedBook book=new BorrowedBook(id,name,isbn);
					renewBook(book);
				}else if(info.startsWith("return a book")){
					System.out.println("server���յ��黹����--->");
					String id=(String) ois.readObject();
					String name=(String) ois.readObject();
					String isbn=(String) ois.readObject();
					BorrowedBook book=new BorrowedBook(id,name,isbn);
					returnBook(book);
				}else if(info.startsWith("refer borrowed book")){
					System.out.println("server���յ���ѯ�ѽ�ͼ������---->");
					referBorrowedBook();
				}
			}catch (Exception e) {
				System.out.println("������δ���յ�����");
				e.printStackTrace();
			}
		}
	}
	
	public void go(){
		clientOutputStreams=new ArrayList<ObjectOutputStream>();
		String condition=null;
		try{
		serverSocket=new ServerSocket(4242);
			while (true) {
				Socket clientSocket=serverSocket.accept();
				if(clientSocket!=null){
				oos=new ObjectOutputStream(clientSocket.getOutputStream());
				ois=new ObjectInputStream(clientSocket.getInputStream());
				
				Thread t=new Thread(new ClientHandler(clientSocket));
				t.start();
				}		
				System.out.println("server����ʼ��ͻ������ӡ���");
			}
		}catch (BindException e) {
			JOptionPane.showMessageDialog(null, "����������ռ�ã�");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//�����˵�½
	public void borrowerLogin(Borrower borrower){
		try{
			oos.writeObject("borrower login");
			b=BorrowerList.isExist(borrower);
			BookInfo.bookList=BookInfo.getBookList();
			if(b.getLevel()<=0){
				oos.writeObject("nothing");
				oos.reset();
			}else{
                oos.writeObject(b);
				oos.reset();
			}
			System.out.println("server���ڷ��ͽ����˵�½��Ϣ����");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//ϵͳ����Ա��½
	public void adminLogin(Administrator admin){
		try{
				oos.writeObject("admin login");
				Administrator admin2=AdministratorList.isExist(admin.getId(), admin.getPassword());
				if(admin2.getName().equals(null)){
					oos.writeObject(admin);
				}else{
					oos.writeObject(admin2);
				}
				oos.flush();
				oos.reset();
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	
	//��ѯͼ������
	public void referBook(String id,String name){
		Book book2;
		try{
			oos.writeObject("refer completed");
			book2=b.referBook(id, name);
			oos.writeObject(book2);
			oos.reset();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//����ͼ��
	public void borrowBook(Book book){
		System.out.println("��ʼ����---->");
		try{
			String result=b.borrowBook(book);
			System.out.println(result);
			oos.writeObject(result);
			oos.flush();
			oos.reset();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//����ͼ��
	public void requireBook(Book book){
		System.out.println("��ʼ����ͼ��---->");
		try{
			Teacher t=(Teacher) b;
			String result=t.requireBook(book);
			oos.writeObject(result);
			oos.flush();
			oos.reset();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//�鿴�ѽ�ͼ��
	public void referBorrowedBook(){
		try{
			Vector<BorrowedBook> list=new Vector<BorrowedBook>();
			list=b.getBorrowedBookList();
			System.out.println(list.size());
			oos.writeObject("�ѷ����ѽ�ͼ����Ϣ");
			oos.writeObject(list);
			oos.flush();
			oos.reset();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//��Ϣ����
	public void messageReceive(){
		System.out.println("��ʼ������Ϣ---->");
		try {
			ArrayList<String> list=new ArrayList<String>();
			list=b.getMessage();
			oos.writeObject(list);
			oos.flush();
			oos.reset();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//����ͼ��
	public void renewBook(BorrowedBook book){
		System.out.println("��ʼ����---->");
		try{
			Vector<BorrowedBook> list=new Vector<BorrowedBook>();
			list=b.getBorrowedBookList();
			String result=b.renewBook(book);
			oos.writeObject(result);
			oos.writeObject(list);
			oos.flush();
			oos.reset();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void returnBook(BorrowedBook book){
		System.out.println("��ʼ�黹---->");
		try{
			String result=b.returnBook(book);
			Vector<BorrowedBook> list=new Vector<BorrowedBook>();
			list=b.getBorrowedBookList();
			oos.writeObject(result);
			System.out.println(result);
			oos.writeObject(list);
			oos.flush();
			oos.reset();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
    public static void main(String[] args){
    	LibraryServer server=new LibraryServer();
    	server.go();
    }
}
