package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class AdministratorList {
	public static ArrayList<Administrator> adminList=new ArrayList<Administrator>();
    public static ArrayList<Administrator> getAdminList(){
    	BufferedReader reader=null;
    	try {
			reader= new BufferedReader(new FileReader("file/系统管理员列表.txt"));
			String line=null;
			while((line=reader.readLine())!=null){
				makeAdmin(line);
				//System.out.println(adminList.size());
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
    	
    	if (reader!= null) {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    	return adminList;
    }
    
    public static void makeAdmin(String line){
		String[] adminInfo=line.split("/");
		Administrator newAdmin=new Administrator(adminInfo[0],adminInfo[1],adminInfo[2]);
		adminList.add(newAdmin);
	}
    
    public static Administrator isExist(String id,String password){
    	adminList=getAdminList();
    	//System.out.println(adminList.size());
    	Administrator admin=null;
    	boolean isExist=false;
    	for(int i=0;i<adminList.size();i++){
    		if((id.equals(adminList.get(i).getId()))&&(password.equals(adminList.get(i).getPassword()))){
    			isExist= true;
    			admin=adminList.get(i);
    			break;
    		}else{
    			isExist=false;
    		}
    	}
		return admin;
    }
    
    public static boolean idIsExist(String id){
    	boolean isExist=false;
    	for(int i=0;i<adminList.size();i++){
    		if(adminList.get(i).getId().equals(id)){
    			isExist=true;
    		}else{
    			isExist=false;
    		}
    	}
    	return isExist;
    }
    
    public static void addAdmin(Administrator admin){
    	adminList.add(admin);
    	//System.out.println(adminList.size());
    	saveAdminList();
    }
    
    public static boolean deleteAdmin(Administrator admin){
    	boolean isDelete=false;
    	//System.out.println(admin.getId());
    	for(int i=0;i<adminList.size();i++){
    		if((adminList.get(i).getId().equals(admin.getId()))&&(adminList.get(i).getPassword().equals(admin.getPassword()))){
    			adminList.remove(adminList.get(i));
    			//System.out.println("sw");
    			//System.out.println(adminList.size());
    			saveAdminList();
    			isDelete=true;
    			break;
    		}else{
    			isDelete=false;
    		}
    	}
    	return isDelete;
    }
    
    public static boolean modifyAdmin(Administrator admin){
    	boolean isModify=false;
    	for(int i=0;i<adminList.size();i++){
    		if((adminList.get(i).getId().equals(admin.getId()))&&(adminList.get(i).getPassword().equals(admin.getName()))){
    			//System.out.println(123);
    			adminList.get(i).setPassword(admin.getPassword());
    			saveAdminList();
    			isModify=true;
    			break;
    		}else{
    			isModify=false;
    		}
    	}
    	return isModify;
    }
    
    public static void saveAdminList(){
		try{
			BufferedWriter writer=new BufferedWriter(new FileWriter("file/系统管理员列表.txt"));
		    for(Administrator admin:adminList){
		    	writer.write(admin.getId()+"/"+admin.getPassword()+"/"+admin.getName());
		    	writer.newLine();
		    }
		    writer.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
