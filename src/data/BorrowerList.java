package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class BorrowerList {
    public static ArrayList<Borrower> borrowerList=new ArrayList<Borrower>();
    public static File file=new File("file/borrower.ser");
    public static ArrayList<Borrower> getBorrowerList(){
    	/*
    	Borrower b1=new UnderGraduate("111250066","ÍõÐ¡»¢","111250066");
    	borrowerList.add(b1);
    	
    	Borrower b2=new Graduate("111250067","ÀîÒäÈç", "111250067");
    	borrowerList.add(b2);
    	
    	Borrower b3=new Teacher("111250303","ÓîÎÄÍØ","111250303");
    	borrowerList.add(b3);
    	
    	Borrower b4=new UnderGraduate("111250065", "³Â¾¸³ð", "111250065");
    	borrowerList.add(b4);
    	
    	Borrower b5=new Graduate("111250069", "ÍØ°ÏÓñ¶ù", "111250069");
    	borrowerList.add(b5);
    	
    	Borrower b6=new Graduate("111250070","ÓÚÐ¡Ñ©","111250070");
    	borrowerList.add(b6);
		//return borrowerList;
    	*/
    	
    	try{
    		ObjectInputStream is=new ObjectInputStream(new FileInputStream("file/borrower.ser"));
    		int num=is.readByte();
    		while(num>0){
    			Borrower b=(Borrower)is.readObject();
    			borrowerList.add(b);
    			num--;
    		}
    	}catch(IOException e){
    		e.printStackTrace();
    	} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
    	return borrowerList;
    }
    
    public static void addBorrower(Borrower b){
    	borrowerList.add(b);
    	saveBorrowerList();
    }
    
    public static Borrower isExist(Borrower borrower){
    	borrowerList=getBorrowerList();
    	for(int i=0;i<borrowerList.size();i++){
    		if(borrowerList.get(i).getId().equals(borrower.getId())&&borrowerList.get(i).getPassword().equals(borrower.getPassword())){
    			borrower=borrowerList.get(i);
    			//System.out.println(borrower.getBorrowedBookList().size());
    			break;
    		}
    	}
		return borrower;
    	
    }
    
    public static boolean idExisted(String id){
    	boolean isExisted=false;
    	for(int i=0;i<borrowerList.size();i++){
    		if(id.equals(borrowerList.get(i).getId())){
    			isExisted=true;
    		}
    	}
    	return isExisted;
    }
    
    public static boolean deleteBorrower(Borrower b){
    	boolean isDelete=false;
    	for(int i=0;i<borrowerList.size();i++){
    		if((borrowerList.get(i).getId().equals(b.getId()))&&(borrowerList.get(i).getPassword().equals(b.getPassword()))){
    			borrowerList.remove(borrowerList.get(i));
    			saveBorrowerList();
    			isDelete=true;
    			break;
    		}else{
    			isDelete=false;
    		}
    	}
    	return isDelete;
    }
    
    public static boolean modifyBorrower(Borrower b){
    	boolean isModify=false;
    	for(int i=0;i<borrowerList.size();i++){
    		if((borrowerList.get(i).getId().equals(b.getId()))&&(borrowerList.get(i).getPassword().equals(b.getPassword()))){
    			borrowerList.get(i).setPassword(b.getName());
    			saveBorrowerList();
    			isModify=true;
    			break;
    		}else{
    			isModify=false;
    		}
    	}
    	return isModify;
    }
    
    public static void saveBorrowerList(){
        try{
        	file.delete();
    		ObjectOutputStream os=new ObjectOutputStream(new FileOutputStream("file/borrower.ser"));
    		os.writeByte(borrowerList.size());
    		for(int i=0;i<borrowerList.size();i++){
    			os.writeObject(borrowerList.get(i));
    		}
    		os.close();
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    }
}
