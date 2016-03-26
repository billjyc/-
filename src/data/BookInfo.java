package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class BookInfo {
	public static ArrayList<Book> bookList=new ArrayList<Book>();
	public static File file=new File("file/book.ser");
    public static ArrayList<Book> getBookList(){
    	
    	Book book1=new Book("ʧ��33��","324973290843248","���� �װٺ�","CHINA","001");
    	book1.setRare(false);
    	book1.setNumOfBook(3);
    	bookList.add(book1);
    	
    	Book book2=new Book("���¿˰���","3408924890890","�����u ���","TAIWAN","006");
    	book2.setRare(true);
    	book2.setNumOfBook(2);
    	bookList.add(book2);
    	
    	Book book3=new Book("����ʮ����","32043248394039","����","CHINA","003");
    	book3.setRare(true);
    	book3.setNumOfBook(5);
    	bookList.add(book3);
    	
    	Book book4=new Book("������־��","324099423904","��ǧ�� ������","HONG KONG","002");
    	book4.setRare(false);
    	book4.setNumOfBook(5);
    	bookList.add(book4);
    	
    	Book book5=new Book("L.O.V.E","303932434234","��ޱ ���","TAIWAN","005");
    	book5.setRare(true);
    	book5.setNumOfBook(5);
    	bookList.add(book5);
    	
    	Book book6=new Book("���䵱","3289749258","���� ,����׿","CHINA","004");
    	book6.setRare(false);
    	book6.setNumOfBook(5);
    	bookList.add(book6);
    	
    	Book book7=new Book("��Ƥ2","98435798754","��ޱ����Ѹ","CHINA","007");
    	book7.setRare(true);
    	book7.setNumOfBook(1);
    	bookList.add(book7);
    	
    	/*
    	try{
    		ObjectInputStream is=new ObjectInputStream(new FileInputStream("Book.ser"));
    		int num=is.readByte();
    		while(num>0){
    			Book b=(Book)is.readObject();
    			bookList.add(b);
    			num--;
    		}
    	}catch(IOException e){
    		e.printStackTrace();
    	} catch (ClassNotFoundException e) {
	        e.printStackTrace();
		}*/
    	return bookList;
    }
    
    public static ArrayList<Book> searchBook(String name,String isbn,String id){
    	ArrayList<Book> searchList=new ArrayList<Book>();
    	for(int i=0;i<bookList.size();i++){
    		if((name.contains(bookList.get(i).getName()))||(isbn.equals(bookList.get(i).getISBN())||(id.equals(bookList.get(i).getId())))){
    			searchList.add(bookList.get(i));
    		}
    	}
    	return searchList;
    }
    
    public static Book searchBook(String isbn){
    	Book b=new Book("");
    	for(int i=0;i<bookList.size();i++){
    		if(isbn.equals(bookList.get(i).getISBN())){
    			b=bookList.get(i);
    			break;
    		}
    	}
    	return b;
    }
    
    
    //�޸�ͼ����ȷ�����Ƿ��������
    public static boolean bookIsExist(String name){
    	boolean isExist=false;
    	for(int i=0;i<bookList.size();i++){
    		if(bookList.get(i).getName().equals(name)){
    			isExist=true;
    			break;
    		}
    	}
    	return isExist;
    }
    
    public static Book findBook(String name){
    	Book b=null;
    	for(int i=0;i<bookList.size();i++){
    		if(bookList.get(i).getName().equals(name)){
    			b=bookList.get(i);
    			break;
    		}
    	}
    	return b;
    }
    
    public static boolean addBook(Book b){
    	boolean isAdd=false;
    	bookList.add(b);
    	System.out.println(bookList.size());
    	isAdd=true;
    	saveBookList();
    	return isAdd;
    }
    
    public static boolean isExist(String id){
    	boolean isExist=false;
    	for(int i=0;i<bookList.size();i++){
    		if(bookList.get(i).getId().equals(id)){
    			isExist=true;
    			break;
    		}
    	}
    	return isExist;
    }
    
    //�����Ƿ��ѱ�����
    public static boolean isBorrow(String name){
    	boolean isBorrow=false;
    	for(int i=0;i<bookList.size();i++){
    		if(bookList.get(i).getName().equals(name)){
    			if(bookList.get(i).getNumOfBorrowed()>0){
    				isBorrow=true;
    			}
    		}
    	}
    	return isBorrow;
    }
    
    public static void modifyBook(String name,String isbn,String author,String press,String id){
    	for(int i=0;i<bookList.size();i++){
    		if(bookList.get(i).getName().equals(name)){
    			bookList.get(i).setId(id);
    			bookList.get(i).setISBN(isbn);
    			bookList.get(i).setAuthor(author);
    			bookList.get(i).setPress(press);
    			break;
    		}
    	}
    	saveBookList();
    }
    
    public static void deleteBook(String name){
    	for(int i=0;i<bookList.size();i++){
    		if(bookList.get(i).getName().equals(name)){
    			bookList.remove(bookList.get(i));
    			break;
    		}
    	}
    	saveBookList();
    }
    
    public static void saveBookList(){
    	try{
    		file.delete();
    		ObjectOutputStream os=new ObjectOutputStream(new FileOutputStream("file/book.ser"));
    		//System.out.println("����֮ǰbookList��"+bookList.size());
    		os.writeByte(bookList.size());
    		for(int i=0;i<bookList.size();i++){
    			os.writeObject(bookList.get(i));
    			//System.out.println("��"+i+"��֮��booklist��"+bookList.size()+"��");
    		}
    		System.out.println(bookList.size());
    		os.close();
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    }
}
