package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import view.BorrowBook.IncomingReader;

import data.Book;
import data.Borrower;
import data.Teacher;
import data.UnderGraduate;

public class MessageReceive extends JFrame implements ActionListener{
	private Borrower borrower;
	private Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
	private JTextArea messageArea;
	private JPanel p1=new JPanel();
	private JPanel p2=new JPanel();
	private JButton exitBtn;
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	public MessageReceive(Borrower borrower) {
		super("消息接收");
		setSize(400,250);
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	int w=(int) (d.getWidth()-this.getWidth())/2;
        int h=(int) (d.getHeight()-this.getHeight())/2;
    	setLocation(w,h);
    	setVisible(true);
    	setResizable(false);
    	this.borrower=borrower;
    	
    	setUpNetWorking();
    	Thread readerThread=new Thread(new IncomingReader());
    	readerThread.start();
    	iniFrame();
	}
	
	public void iniFrame(){
		exitBtn=new JButton("退出");
		exitBtn.addActionListener(this);
		messageArea=new JTextArea(11,30);
		messageArea.setEditable(false);
		JScrollPane js=new JScrollPane(messageArea);
		messageArea.setLineWrap(true);
		js.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		js.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		p1.add(js);
		p2.add(exitBtn);
		this.getContentPane().add(p1,BorderLayout.CENTER);
		this.getContentPane().add(p2,BorderLayout.SOUTH);
		sendRequire();
	}
	
	public void sendRequire(){
		try {
			oos.writeObject("message receive");
			oos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==exitBtn){
			try {
				socket.close();
				ois.close();
				oos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			this.dispose();
		}
	}
	
	public void setUpNetWorking(){
	    try{
	    	socket=new Socket("127.0.0.1",4242);
	    		
	    	ois=new ObjectInputStream(socket.getInputStream());
	    	oos=new ObjectOutputStream(socket.getOutputStream());
	    		
	    	System.out.println("client:正在与服务器进行连接……");
	    }catch (Exception e) {
	    	e.printStackTrace();
	    	System.out.println("client：连接失败!");
		}
	}
	  
	  public class IncomingReader implements Runnable{
			@Override
			public void run() {
				try {
					ArrayList<String> message=(ArrayList<String>) ois.readObject();
					for(String s:message){
						messageArea.append(s+"\n");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
}
