package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import network.LibraryServer;

import data.Administrator;
import data.AdministratorList;
import data.Borrower;
import data.Graduate;
import data.Teacher;

public class LoginFrame extends JFrame implements ActionListener,ItemListener{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6134735524189533566L;
	private JButton loginButton;
    private JButton cancelButton;
    private JTextField name;
    private JPasswordField password;
    private JLabel nameLabel,identityLabel;
    private JLabel pwLabel;
    private JComboBox userIdentityComboBox;
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
    private ImageIcon background;
	public LoginFrame(){
    	setTitle("图书借阅系统V1.3");
    	setSize(400,190);
    	//让窗口居中显示
    	int w=(int) (d.getWidth()-this.getWidth())/2;
        int h=(int) (d.getHeight()-this.getHeight())/2;
    	setLocation(w,h);
    	
    	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    	this.setResizable(false);
    	setVisible(true);
    	iniFrame();
    	
    	setUpNetWorking();
    }
	
    private void iniFrame() {
    	
    	JPanel p1=new JPanel();
        JPanel p2=new JPanel();
        JPanel p3=new JPanel();
    	
    	loginButton=new JButton("登录");
    	loginButton.addActionListener(this);
    	
    	cancelButton=new JButton("退出");
    	cancelButton.addActionListener(this);
    	
    	name=new JTextField(20);
    	nameLabel=new JLabel("用户名：",JLabel.CENTER);
    	password=new JPasswordField(20);
    	pwLabel=new JLabel("密码：",JLabel.CENTER);
        identityLabel=new JLabel("登陆身份：",JLabel.CENTER);
    	
    	String[] userIdentityStringList=new String[]{"借阅人","系统管理员"};
    	userIdentityComboBox=new JComboBox(userIdentityStringList);
    	
    	
    	p1.setLayout(new FlowLayout());
    	p1.add(identityLabel);
    	p1.add(userIdentityComboBox);
    	
    	GridLayout g=new GridLayout(2,2);
    	g.setVgap(5);
    	p2.setLayout(g);
    	p2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	p2.add(nameLabel);
    	p2.add(name);
    	p2.add(pwLabel);
    	p2.add(password);
    	
    	p3.setLayout(new FlowLayout(FlowLayout.CENTER,10,0));
    	p3.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
    	p3.add(loginButton);
    	p3.add(cancelButton);
    	background=new ImageIcon("image/login.jpg");
    	
    	this.getContentPane().add(p1,BorderLayout.NORTH);
    	this.getContentPane().add(p2,BorderLayout.CENTER);
    	this.getContentPane().add(p3,BorderLayout.SOUTH);
    	
	}
    
    public void setUpNetWorking(){
    	try{
    		socket=new Socket("127.0.0.1",4242);
    		
    		ois=new ObjectInputStream(socket.getInputStream());
    		oos=new ObjectOutputStream(socket.getOutputStream());
    	
    		Thread readerThread=new Thread(new IncomingReader());
        	readerThread.start();
    		
    		System.out.println("client:正在与服务器进行连接……");
    	}catch (Exception e) {
    		e.printStackTrace();
    		System.out.println("client:连接失败！");
		}
    }
    
    public void actionPerformed(ActionEvent e){
    	if(e.getSource()==cancelButton){
    		this.dispose();
    		//LibraryServer.stop();
    		System.exit(0);
    	}else{
    		String n=name.getText();
    		char[] pw=password.getPassword();
    		String pword=new String(pw);
    		
    		if(name.getText().trim().equals("")||(pword.equals(""))){
    			JOptionPane.showMessageDialog(null,"用户名/密码不可为空!");
    			return;
    		}else{
    			String identity=(String) userIdentityComboBox.getSelectedItem();
    			if(identity.equals("系统管理员")){
    				try{
    					oos.writeObject("admin login");
    					oos.writeObject(n);
    					oos.writeObject(pword);
    					oos.flush();
    				}catch(Exception e2){
    					e2.printStackTrace();
    				}
    			}else if(identity.equals("借阅人")){
    				try {
    					oos.writeObject("borrower login");
    					oos.writeObject(n);
    					oos.writeObject(pword);
    					System.out.println("client发送："+n+" "+pword);
    					oos.flush();
    				} catch (IOException e1) {
						System.out.println("sorry,you can't send information to the server.");
						e1.printStackTrace();
					}
				}
    			this.dispose();
    		}
    	}
    }

    @Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		
	}
    
    public class IncomingReader implements Runnable{

		@Override
		public void run() {
			Object o;
			Object str=null;
			String info;
			try{
				//while((str=ois.readObject())!=null){
				str=ois.readObject();
				System.out.println(str);
				info=(String)str;
				//接收从服务器发送过来的借阅人信息
				if(info.contains("borrower login")){
					o= ois.readObject();
				    if(o instanceof Borrower){
				    	Borrower b=(Borrower)o;
				    	JOptionPane.showMessageDialog(null, "借阅人"+b.getName()+",欢迎您！");
				    	System.out.println(b.getName()+"　"+b.getLevel());
				    	new BorrowerFrame(b);
				    }else{
				    	JOptionPane.showMessageDialog(null, "用户名/密码错误！");
				    }
				    //接收从服务器发送来的管理员的信息
				}else if(info.equals("admin login")){
					o=ois.readObject();
					Administrator admin=(Administrator)o;
					if(admin.getName().equals(null)){
						JOptionPane.showMessageDialog(null, "用户名/密码错误！");
						name.setText("");
						password.setText("");
					}else{
						JOptionPane.showMessageDialog(null, "系统管理员"+admin.getName()+",欢迎您！");
						
						new AdminFrame();
					}
				}
				//}
			}catch (Exception e) {
				System.out.println("client:未接收");
				e.printStackTrace();
			}finally{
				
					try {
						if (ois!=null) {
							ois.close();
						}
						if(oos!=null){
							oos.close();
						}
						if(socket!=null){
							socket.close();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					
				}
			}
		}
    }
}