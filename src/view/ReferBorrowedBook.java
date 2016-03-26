package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import view.BorrowBook.IncomingReader;

import data.Book;
import data.BorrowBookInfoTableModel;
import data.BorrowedBook;
import data.Borrower;

public class ReferBorrowedBook extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
	private JTable bookTable;
	private JPanel p1,p2;
	private JScrollPane sp;
	private JButton getBorrowedBookBtn,exitBtn,renewBtn,returnBtn;
	private Borrower borrower;
	private ArrayList book=new ArrayList();  //被选中的一行的信息
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private BorrowBookInfoTableModel model;
	public ReferBorrowedBook(Borrower borrower) {
		super("查看已借图书");
		setSize(650,450);
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	int w=(int) (d.getWidth()-this.getWidth())/2;
        int h=(int) (d.getHeight()-this.getHeight())/2;
    	setLocation(w,h);
    	setVisible(true);
    	setResizable(false);
    	this.borrower=borrower;
    	System.out.println(borrower.getName());
    	bookTable=new JTable();
    	//model=new BorrowBookInfoTableModel(borrower.getBorrowedBookList());
    	//bookTable.setModel(model);
    	setUpNetWorking();
    	iniTable();
    	iniFrame();
	}
	
	public void iniFrame(){
		
		p1=new JPanel();
		p2=new JPanel();
		
		exitBtn=new JButton("取消");
		renewBtn=new JButton("续借");
		returnBtn=new JButton("归还图书");
		getBorrowedBookBtn=new JButton("查看已借图书");
		renewBtn.setEnabled(false);
		returnBtn.setEnabled(false);
		
		bookTable.setFont(new Font("仿宋",Font.PLAIN,13));
	    //显示表头
	    sp=new JScrollPane(bookTable);
	    bookTable.setPreferredScrollableViewportSize(new Dimension(630,380));
	    sp.setAutoscrolls(true);
	    sp.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.setViewportView(bookTable);
        
        exitBtn.addActionListener(this);
        renewBtn.addActionListener(this);
        returnBtn.addActionListener(this);
        getBorrowedBookBtn.addActionListener(this);
       
	    p1.add(sp);
	    p1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	    p2.add(getBorrowedBookBtn);
	    p2.add(renewBtn);
	    p2.add(returnBtn);
	    p2.add(exitBtn);
	    
	    this.getContentPane().add(p1,BorderLayout.CENTER);
	    this.getContentPane().add(p2,BorderLayout.SOUTH);
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
	    	System.out.println("client：连接失败!");
		}
	}
	
	public void iniBookTable(){
		//单击表头排序
	    RowSorter<TableModel> sorter=new TableRowSorter<TableModel>(bookTable.getModel());
	    bookTable.setRowSorter(sorter);
	    bookTable.addMouseListener(new MouseListener(){
            @Override
			public void mouseClicked(MouseEvent e) {
				book.clear();
				int r=bookTable.getSelectedRow();
				int c=bookTable.getColumnCount();
				for(int i=0;i<c;i++){            //获取单击的那一行的数据
					book.add(bookTable.getValueAt(r, i));  
				}
				returnBtn.setEnabled(true);
				String canRenew=(String) book.get(5);
				if(canRenew.equals("可续借")){
					renewBtn.setEnabled(true);
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
	    	
	    });
	    
	    bookTable.setPreferredScrollableViewportSize(new Dimension(630,380));
	    sp.setAutoscrolls(true);
	    sp.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.setViewportView(bookTable);
	}
	
	public void iniTable(){
		try{
			oos.writeObject("refer borrowed book");
			oos.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==exitBtn) {
			this.dispose();
		}else if(e.getSource()==getBorrowedBookBtn){
			setUpNetWorking();
			try{
				oos.writeObject("refer borrowed book");
				oos.flush();
			}catch (Exception e1) {
				e1.printStackTrace();
			}
		}else if(e.getSource()==renewBtn){
			setUpNetWorking();
			String id=(String) book.get(0);
			String name=(String) book.get(1);
			String isbn=(String) book.get(2);
			try {
				oos.writeObject("renew a book");
				oos.writeObject(id);
				oos.writeObject(name);
				oos.writeObject(isbn);
				oos.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}else if(e.getSource()==returnBtn){
			setUpNetWorking();
			String id=(String) book.get(0);
			String name=(String) book.get(1);
			String isbn=(String) book.get(2);
			try{
				oos.writeObject("return a book");
				oos.writeObject(id);
				oos.writeObject(name);
				oos.writeObject(isbn);
				oos.flush();
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}
	}
	
	public class IncomingReader implements Runnable{
		@Override
		public void run() {
			try {
				String info=(String) ois.readObject();
				System.out.println(info);
				if(info.contains("续借成功")||info.startsWith("归还成功")){
					JOptionPane.showMessageDialog(null, info);
					Vector<BorrowedBook> list=(Vector<BorrowedBook>)ois.readObject();
					p1.remove(sp);
					if(bookTable!=null){
						bookTable=null;
					}
					
					bookTable=new JTable();
					sp=new JScrollPane(bookTable);
					model=new BorrowBookInfoTableModel(list);
					bookTable.setModel(model);
					p1.add(sp);
					p1.validate();
					iniBookTable();
				}else if((info.contains("您并没有借阅这本图书！"))||(info.contains("此书已经达到最大可续借次数！"))||(info.contains("此书已被请求！"))||(info.contains("归还失败"))){
					JOptionPane.showMessageDialog(null, info);
				}else if(info.startsWith("已发送已借图书信息")){
					Vector<BorrowedBook> list=(Vector<BorrowedBook>)ois.readObject();
					System.out.println(list.size());
					for(BorrowedBook book:list){
						System.out.println(book.getName());
					}
					p1.remove(sp);
					if(bookTable!=null){
						bookTable=null;
					}
					
					bookTable=new JTable();
					sp=new JScrollPane(bookTable);
					model=new BorrowBookInfoTableModel(list);
					bookTable.setModel(model);
					p1.add(sp);
					p1.validate();
					iniBookTable();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
