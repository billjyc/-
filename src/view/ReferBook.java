package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.ietf.jgss.Oid;

import data.Book;

import view.LoginFrame.IncomingReader;

public class ReferBook extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -234854970133732343L;
	private Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
	private JPanel p1,p2;
	private JLabel idLbl,nameLbl;
	private JTextField idField,nameField;
	private JButton queryBtn,cancelBtn;
	private JTextArea resultArea;
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
    public ReferBook(){
    	super("查询图书");
    	setSize(400,320);
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	int w=(int) (d.getWidth()-this.getWidth())/2;
        int h=(int) (d.getHeight()-this.getHeight())/2;
    	setLocation(w,h);
    	setVisible(true);
    	setResizable(false);
    	iniFrame();
    	//setUpNetWorking();
    	
    }
    
    public void iniFrame(){
    	p1=new JPanel();
    	p2=new JPanel();
    	idLbl=new JLabel("编号：",JLabel.CENTER);
    	nameLbl=new JLabel("书名：",JLabel.CENTER);
    	idField=new JTextField(20);
    	nameField=new JTextField(20);
    	resultArea=new JTextArea(8,32);
    	resultArea.setEditable(false);
    	JScrollPane scroller=new JScrollPane(resultArea);
    	resultArea.setLineWrap(true);
    	scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    	scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    	
    	queryBtn=new JButton("查询");
    	cancelBtn=new JButton("取消");
    	queryBtn.addActionListener(this);
    	cancelBtn.addActionListener(this);
    	
    	p1.setLayout(new GridLayout(3, 2, 15, 10));
    	p1.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
    	p1.add(idLbl);
    	p1.add(idField);
    	p1.add(nameLbl);
    	p1.add(nameField);
    	p1.add(queryBtn);
    	p1.add(cancelBtn);
    	
    	p2.add(scroller);
    	
    	this.getContentPane().add(p1,BorderLayout.NORTH);
    	this.getContentPane().add(p2,BorderLayout.CENTER);
    }
    
    public void setUpNetWorking(){
    	try{
    		socket=new Socket("127.0.0.1",4242);
    		ois=new ObjectInputStream(socket.getInputStream());
    		oos=new ObjectOutputStream(socket.getOutputStream());
    		Thread readerThread=new Thread(new IncomingReader());
        	readerThread.start();
    		System.out.println("查询图书client:正在与服务器进行连接……");
    	}catch (Exception e) {
    		e.printStackTrace();
    		System.out.println("查询图书client：连接失败!");
		}
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==cancelBtn) {
			this.dispose();
		}else if (e.getSource()==queryBtn) {
			setUpNetWorking();
			String id=idField.getText();
			String name=nameField.getText();
			if(id.equals("")&&(name.equals(""))){
				JOptionPane.showMessageDialog(null, "您至少要填写一项内容！");
			}else{
				try {
					oos.writeObject("refer a book");
					oos.writeObject(id);
					oos.writeObject(name);
					oos.flush();
					System.out.println("向服务器发送查询图书种类请求……");
				} catch (Exception e1) {
					System.out.println("发送图书查询请求失败");
					e1.printStackTrace();
				}
			}
		}
	}

	public class IncomingReader implements Runnable{
		@Override
		public void run() {
			Book book;
			String info;
			try {
                //while((info=(String)ois.readObject())!=null){
                info=(String)ois.readObject();
				//System.out.println(info);
				//String id=(String)ois.readObject();
				//String name=(String) ois.readObject();
				//String isbn=(String) ois.readObject();
				book=(Book) ois.readObject();
				System.out.println(book.getName());
				if(book.getName().equals("")){
					resultArea.setText("没有你要查询的图书！");
					nameField.setText("");
					idField.setText("");
					nameField.requestFocus();
				}else{
					resultArea.setText("");
					resultArea.append("编号："+book.getId()+"\n");
					resultArea.append("书名："+book.getName()+"\n");
					resultArea.append("ISBN："+book.getISBN()+"\n");
					resultArea.append("作者："+book.getAuthor()+"\n");
					resultArea.append("出版社："+book.getPress()+"\n");
					resultArea.append("是否珍本："+book.isRare()+"\n");
					resultArea.append("是否可借："+book.isAvailable()+"\n");
					resultArea.append("剩余本数："+book.getNumOfBook()+"\n");
				}
				//}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
