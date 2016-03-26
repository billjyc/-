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
    	
    	Book book1=new Book("失恋33天","324973290843248","文章 白百何","CHINA","001");
    	book1.setRare(false);
    	book1.setNumOfBook(3);
    	bookList.add(book1);
    	
    	Book book2=new Book("赛德克巴莱","3408924890890","徐若瑄 温岚","TAIWAN","006");
    	book2.setRare(true);
    	book2.setNumOfBook(2);
    	bookList.add(book2);
    	
    	Book book3=new Book("金陵十三钗","32043248394039","倪妮","CHINA","003");
    	book3.setRare(true);
    	book3.setNumOfBook(5);
    	bookList.add(book3);
    	
    	Book book4=new Book("春娇与志明","324099423904","杨千嬅 余文乐","HONG KONG","002");
    	book4.setRare(false);
    	book4.setNumOfBook(5);
    	bookList.add(book4);
    	
    	Book book5=new Book("L.O.V.E","303932434234","赵薇 舒淇","TAIWAN","005");
    	book5.setRare(true);
    	book5.setNumOfBook(5);
    	bookList.add(book5);
    	
    	Book book6=new Book("大武当","3289749258","杨幂 ,赵文卓","CHINA","004");
    	book6.setRare(false);
    	book6.setNumOfBook(5);
    	bookList.add(book6);
    	
    	Book book7=new Book("画皮2","98435798754","赵薇，周迅","CHINA","007");
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
    
    
    //修改图书中确认书是否在书库中
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
    
    //此书是否已被借走
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
    		//System.out.println("保存之前bookList有"+bookList.size());
    		os.writeByte(bookList.size());
    		for(int i=0;i<bookList.size();i++){
    			os.writeObject(bookList.get(i));
    			//System.out.println("第"+i+"次之后booklist有"+bookList.size()+"本");
    		}
    		System.out.println(bookList.size());
    		os.close();
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    }
}
