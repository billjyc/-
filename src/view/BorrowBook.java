package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

import javax.swing.*;

import view.ReferBook.IncomingReader;

import data.Book;
import data.BookInfo;
import data.BorrowedBook;
import data.Borrower;
import data.Graduate;
import data.Teacher;
import data.UnderGraduate;

public class BorrowBook extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2643724900940572010L;
	private Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
	private JLabel idLbl,nameLbl;
	private JTextField idField,nameField;
	private JTextArea infoArea;
	private JButton borrowBtn,clearBtn,cancelBtn,requireBtn,searchBtn;
	private JPanel p1,p2,p3;
	private Borrower borrower;
	private Book b;
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
    public BorrowBook(Borrower borrower){
    	super("借阅图书");
    	setSize(450,380);
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	int w=(int) (d.getWidth()-this.getWidth())/2;
        int h=(int) (d.getHeight()-this.getHeight())/2;
    	setLocation(w,h);
    	setVisible(true);
    	setResizable(false);
    	if(borrower.getLevel()==Borrower.TEACHER){
    		this.borrower=(Teacher) borrower;
    		System.out.println("teacher");
    	}else if(borrower.getLevel()==Borrower.GRADUATE){
    		this.borrower=(Graduate) borrower;
    		System.out.println("graduate");
    		//System.out.println(borrower.getBorrowedBookList().size());
    	}else if(borrower.getLevel()==Borrower.UNDERGRADUATE){
			this.borrower=(UnderGraduate) borrower;
			System.out.println("undergraduate");
		}
    	iniFrame();
    	//setUpNetWorking();
    	
    }
    
    public void iniFrame(){
    	p1=new JPanel();
    	p2=new JPanel();
    	p3=new JPanel();
    	
    	idLbl=new JLabel("编号：",JLabel.CENTER);
    	idField=new JTextField(6);
    	nameLbl=new JLabel("名称：",JLabel.CENTER);
    	nameField=new JTextField(15);
    	infoArea=new JTextArea(10,32);
    	infoArea.setEditable(false);
    	JScrollPane scroller=new JScrollPane(infoArea);
    	infoArea.setLineWrap(true);
    	scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    	scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    	
    	searchBtn=new JButton("搜索");
    	borrowBtn=new JButton("借阅");
    	clearBtn=new JButton("清空");
    	cancelBtn=new JButton("取消");
    	requireBtn=new JButton("请求");
    	
    	searchBtn.addActionListener(this);
    	borrowBtn.addActionListener(this);
    	clearBtn.addActionListener(this);
    	cancelBtn.addActionListener(this);
    	requireBtn.addActionListener(this);
    	
    	p1.setLayout(new FlowLayout());
    	p1.add(idLbl);
    	p1.add(idField);
    	p1.add(nameLbl);
    	p1.add(nameField);
    	p1.add(searchBtn);
    	
    	p2.add(scroller);
    	
    	p3.setLayout(new FlowLayout());
    	p3.add(borrowBtn);
    	p3.add(requireBtn);
    	p3.add(clearBtn);
    	p3.add(cancelBtn);
    	requireBtn.setEnabled(false);
    	borrowBtn.setEnabled(false);
    	p3.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
    	
    	this.getContentPane().add(p1,BorderLayout.NORTH);
    	this.getContentPane().add(p2,BorderLayout.CENTER);
    	this.getContentPane().add(p3,BorderLayout.SOUTH);
    }
    
    public void setUpNetWorking(){
    	try{
    		socket=new Socket("127.0.0.1",4242);
    		
    		ois=new ObjectInputStream(socket.getInputStream());
    		oos=new ObjectOutputStream(socket.getOutputStream());
    		
    		System.out.println("client:正在与服务器进行连接……");
    		Thread readerThread=new Thread(new IncomingReader());
        	readerThread.start();
    	}catch (Exception e) {
    		e.printStackTrace();
    		System.out.println("client：连接失败!");
		}
    }
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String id=idField.getText();
		String name=nameField.getText();
		if(e.getSource()==cancelBtn){
			this.dispose();
		}else if(e.getSource()==clearBtn){
			idField.setText("");
			nameField.setText("");
			infoArea.setText("");
			borrowBtn.setEnabled(false);
			requireBtn.setEnabled(false);
		}else if(e.getSource()==searchBtn){
			try {
				setUpNetWorking();
				oos.writeObject("refer a book");
				oos.writeObject(id);
				oos.writeObject(name);
				oos.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}else if(e.getSource()==borrowBtn){
			try {
				setUpNetWorking();
				oos.writeObject("borrow a book");
				System.out.println("client发送借阅请求---->");
				
				oos.writeObject(b);	
				oos.flush();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}else if(e.getSource()==requireBtn){
			try{
				setUpNetWorking();
				oos.writeObject("require a book");
				System.out.println("client发送请求图书---->");
				oos.writeObject(b);
				oos.flush();
			}catch (Exception e3) {
				e3.printStackTrace();
			}
		}
	}
	
	public class IncomingReader implements Runnable{
		@Override
		public void run() {
			try {
				String str=(String) ois.readObject();
				System.out.println(str);
				if(str.contains("refer completed")){
					b=(Book) ois.readObject();
					infoArea.setText("");
				if(b.getName().equals("")){
					infoArea.append("没有你要找的图书！");
				}else{
					infoArea.append("书名："+b.getName()+"\n");
					infoArea.append("编号："+b.getId()+"\n");
					infoArea.append("ISBN："+b.getISBN()+"\n");
					infoArea.append("作者："+b.getAuthor()+"\n");
					infoArea.append("出版社："+b.getPress()+"\n");
					infoArea.append("是否珍本："+b.isRare()+"\n");
					infoArea.append("剩余本数："+b.getNumOfBook()+"\n");
					borrowBtn.setEnabled(true);
					oos.reset();
					if(!b.isAvailable()){
						JOptionPane.showMessageDialog(null, "此书不可借！");
						borrowBtn.setEnabled(false);
					}
					socket.close();
					oos.close();
					ois.close();
				}
				}else if(str.startsWith("borrow successfully!")){
					System.out.println("借阅成功！");
					JOptionPane.showMessageDialog(null, "借阅成功！");
					idField.setText("");
					nameField.setText("");
					infoArea.setText("");
				}else if(str.startsWith("此书已没有库存！")){
					JOptionPane.showMessageDialog(null,"此书已没有库存！" );
					if(borrower instanceof Teacher){
						requireBtn.setEnabled(true);
					}
				}else if(str.startsWith("本科生没有借阅珍本图书的权限！")){
					JOptionPane.showMessageDialog(null, "本科生没有借阅珍本图书的权限！");
					if (borrower instanceof UnderGraduate) {
						borrowBtn.setEnabled(false);
					}
				}else if(str.startsWith("借阅失败")){
					JOptionPane.showMessageDialog(null, "您借阅的图书数量已经达到上限！");
				}else if(str.startsWith("success!")){
					JOptionPane.showMessageDialog(null, "请求成功！");
					requireBtn.setEnabled(false);
				}else if(str.startsWith("lose")){
					JOptionPane.showMessageDialog(null, "请求失败！");
				}
				oos.close();
				ois.close();
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}