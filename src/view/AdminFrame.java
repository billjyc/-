package view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import view.BorrowBook.IncomingReader;

import data.AdminInfoTableModel;
import data.AdministratorList;
import data.Book;
import data.BookInfo;
import data.BookInfoTableModel;
import data.BorrowerInfoTableModel;
import data.BorrowerList;
import data.Teacher;
import data.UnderGraduate;

import java.awt.*;
import java.awt.event.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class AdminFrame extends JFrame implements ActionListener,WindowListener{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5726571359064294394L;
	private JPanel mngPanel,mainPanel;  
	private JPanel listP,referP,brwP,adminP,exitP,bookP;
	private JButton listBtn,referBtn,adminBtn,brwBtn,bookBtn,exitBtn,searchBtn;
	private CardLayout cl=new CardLayout();
	private JTextField bookNameField,isbnField,idField;
	public static AdminInfoTableModel adminInfo;
	public static JTable adminInfoTable;
	public static JTable bookInfoTable;
	public static JTable borrowerInfoTable;
	public static BorrowerInfoTableModel borrowerInfo=new BorrowerInfoTableModel(BorrowerList.borrowerList);
	public static BookInfoTableModel bookInfo=new BookInfoTableModel(BookInfo.bookList);
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public AdminFrame(){
		super("����Ա����");
    	setSize(800,600);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	setVisible(true);
    	BorrowerList.borrowerList=BorrowerList.getBorrowerList();
    	iniFrame();
    }

	public void iniFrame() {
		setLayout(new BorderLayout()); 
		iniMngPanel();
		iniMainPanel();
		addWindowListener(this);
	}
	
	public void setUpNetWorking(){
		try{
    		socket=new Socket("127.0.0.1",4242);
    		
    		ois=new ObjectInputStream(socket.getInputStream());
    		oos=new ObjectOutputStream(socket.getOutputStream());
    		
    		System.out.println("client:������������������ӡ���");
    		Thread readerThread=new Thread(new IncomingReader());
        	readerThread.start();
    	}catch (Exception e) {
    		e.printStackTrace();
    		System.out.println("client������ʧ��!");
		}
	}
	
	public void iniMngPanel(){
		mngPanel=new JPanel();
		
		listBtn=new JButton("�о�ͼ��");
		referBtn=new JButton("��ѯͼ��");
		adminBtn=new JButton("����ϵͳ����Ա");
		brwBtn=new JButton("���������");
		bookBtn=new JButton("����ͼ��");
		exitBtn=new JButton("�˳�ϵͳ");
		
		listBtn.addActionListener(this);
		referBtn.addActionListener(this);
		adminBtn.addActionListener(this);
		brwBtn.addActionListener(this);
		bookBtn.addActionListener(this);
		exitBtn.addActionListener(this);
		
		mngPanel.setLayout(new FlowLayout(FlowLayout.LEFT,20,0));
		mngPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		mngPanel.add(listBtn);
		mngPanel.add(referBtn);
		mngPanel.add(adminBtn);
		mngPanel.add(brwBtn);
		mngPanel.add(bookBtn);
		mngPanel.add(exitBtn);
		
		this.getContentPane().add(mngPanel,BorderLayout.NORTH);
	}
	
	public void iniMainPanel(){
		mainPanel=new JPanel();
		listP=new JPanel();
		referP=new JPanel();
		brwP=new JPanel();
		bookP=new JPanel();
		adminP=new JPanel();
		exitP=new JPanel();
		
		iniListP();   //�о�ͼ��
		iniReferP();  //��ѯͼ��
		iniAdminP();  //����ϵͳ����Ա
		iniBrwP();    //���������
		iniBookP();   //����ͼ��
		
		mainPanel.setLayout(cl);
		mainPanel.add(listP,"�о�ͼ��");
		mainPanel.add(referP,"��ѯͼ��");
		mainPanel.add(adminP,"����ϵͳ����Ա");
		mainPanel.add(brwP,"���������");
		mainPanel.add(bookP,"����ͼ��");
		mainPanel.add(exitP,"�˳�ϵͳ");
	
		this.getContentPane().add(mainPanel,BorderLayout.CENTER);
	}

	public void iniBookP() {
		bookP.setLayout(new BorderLayout());
		JPanel p1=new JPanel();
		JPanel p2=new JPanel();
	    bookInfoTable=new JTable();
		//bookInfo=new BookInfoTableModel(BookInfo.bookList);
		bookInfoTable.setModel(bookInfo);
		//System.out.println(BookInfo.bookList.size());
		bookInfoTable.setFont(new Font("����",Font.PLAIN,13));
		bookInfoTable.setPreferredScrollableViewportSize(new Dimension(700,300));
		JScrollPane js=new JScrollPane(bookInfoTable);
		js.setViewportView(bookInfoTable);
		
		JButton addBookBtn=new JButton("���ͼ��");
		JButton deleteBookBtn=new JButton("ɾ��ͼ��");
		JButton modifyBookBtn=new JButton("�޸���Ϣ");
		
		//��ʾ��ͷ
	    js.setAutoscrolls(true);
	    js.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        js.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        js.setViewportView(bookInfoTable);
        
        //������ͷ����
	    RowSorter<TableModel> sorter=new TableRowSorter<TableModel>(bookInfoTable.getModel());
	    bookInfoTable.setRowSorter(sorter);
		
		addBookBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new BookAdd();
				bookInfoTable.revalidate();
				bookInfoTable.updateUI();
				bookInfoTable.repaint();
			}
		});
		
		deleteBookBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new BookDelete();
				bookInfoTable.revalidate();
			}
		});
		
		modifyBookBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new BookModify();
				bookInfoTable.revalidate();
			}
		});
		
		p1.setLayout(new FlowLayout());
		p1.add(addBookBtn);
		p1.add(deleteBookBtn);
		p1.add(modifyBookBtn);
		p1.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
		p2.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20),"ͼ���б�"));
		p2.add(js);
		
		bookP.add(p1,BorderLayout.NORTH);
		bookP.add(p2,BorderLayout.CENTER);
	}

	public void iniBrwP() {
		brwP.setLayout(new BorderLayout());
		JPanel p1=new JPanel();
		JPanel p2=new JPanel();
		
		JButton addBorrowerBtn=new JButton("��ӽ�����");
		JButton deleteBorrowerBtn=new JButton("ɾ��������");
		JButton modifyBorrowerBtn=new JButton("�޸���Ϣ");
		
		borrowerInfoTable=new JTable();
		borrowerInfoTable.setModel(borrowerInfo);
		//System.out.println(BorrowerList.borrowerList.size());
		borrowerInfoTable.setFont(new Font("����",Font.PLAIN,13));
		borrowerInfoTable.setPreferredScrollableViewportSize(new Dimension(700,300));
		JScrollPane js=new JScrollPane(borrowerInfoTable);
		js.setViewportView(borrowerInfoTable);
		
		//js.setAutoscrolls(true);
	    js.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        js.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        js.setViewportView(borrowerInfoTable);
        
        //������ͷ����
	    RowSorter<TableModel> sorter=new TableRowSorter<TableModel>(borrowerInfoTable.getModel());
	    borrowerInfoTable.setRowSorter(sorter);
		
		p1.setLayout(new FlowLayout());
		p1.add(addBorrowerBtn);
		p1.add(deleteBorrowerBtn);
		p1.add(modifyBorrowerBtn);
		p1.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
		p2.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20),"�������б�"));
		p2.add(js);
		
		addBorrowerBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new BorrowerAdd();
			}
		});	
		
		deleteBorrowerBtn.addActionListener(new ActionListener(){
            @Override
			public void actionPerformed(ActionEvent arg0) {
				new BorrowerDelete();
			}
		});
		
		modifyBorrowerBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new BorrowerModify();
			}
		});
		
		brwP.add(p1,BorderLayout.NORTH);
		brwP.add(p2,BorderLayout.CENTER);
	}

	public void iniAdminP() {
		adminP.setLayout(new BorderLayout());
		JPanel p1=new JPanel();
		JPanel p2=new JPanel();
	    adminInfoTable=new JTable();
		adminInfo=new AdminInfoTableModel(AdministratorList.getAdminList());
		System.out.println(AdministratorList.adminList.size());
		adminInfoTable.setModel(adminInfo);
		adminInfoTable.setFont(new Font("����",Font.PLAIN,13));
		adminInfoTable.setPreferredScrollableViewportSize(new Dimension(680,300));
		JScrollPane js=new JScrollPane(adminInfoTable);
		
		JButton addAdminBtn=new JButton("���ϵͳ����Ա");
		JButton deleteAdminBtn=new JButton("ɾ��ϵͳ����Ա");
		JButton modifyAdminBtn=new JButton("�޸���Ϣ");
		
		addAdminBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new AdminAdd();
				adminInfoTable.revalidate();
			}
		});
		
		deleteAdminBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new AdminDelete();
				adminInfoTable.revalidate();
			}
		});
		
		modifyAdminBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new AdminModify();
				adminInfoTable.revalidate();
			}
		});
		
		p1.setLayout(new FlowLayout());
		p1.add(addAdminBtn);
		p1.add(deleteAdminBtn);
		p1.add(modifyAdminBtn);
		p1.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
		p2.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20),"ϵͳ����Ա�б�"));
		p2.add(js);
		
		adminP.add(p1,BorderLayout.NORTH);
		adminP.add(p2,BorderLayout.CENTER);
	}

	public void iniReferP() {
		referP.setLayout(new BoxLayout(referP,BoxLayout.Y_AXIS));
		JPanel p1=new JPanel();
		JPanel p2=new JPanel();
		JLabel bookNameLabel=new JLabel("������",JLabel.CENTER);
		JLabel isbnLabel=new JLabel("ISBN��",JLabel.CENTER);
		JLabel idLabel=new JLabel("��ţ�",JLabel.CENTER);
		bookNameField=new JTextField(20);
		isbnField=new JTextField(20);
		idField=new JTextField(20);
		
		final JTable bookInfoTable=new JTable();
		bookInfoTable.setFont(new Font("����",Font.PLAIN,13));
		BookInfoTableModel model=new BookInfoTableModel();
		bookInfoTable.setModel(model);
		bookInfoTable.setPreferredScrollableViewportSize(new Dimension(700,280));
		JScrollPane s=new JScrollPane(bookInfoTable);
		searchBtn=new JButton("��ѯ");
        searchBtn.addActionListener(new ActionListener(){
        	@Override
			public void actionPerformed(ActionEvent e) {
				String name=bookNameField.getText().trim();
				String isbn=isbnField.getText().trim();
				String id=idField.getText().trim();
				
				ArrayList<Book> searchList=BookInfo.searchBook(name, isbn, id);
				if(searchList.isEmpty()){
					JOptionPane.showMessageDialog(null,"û������Ҫ��ѯ��ͼ�飡");
					bookNameField.setText("");
					isbnField.setText("");
					idField.setText("");
	    			return;
				}else{
				    BookInfoTableModel model2=new BookInfoTableModel(searchList);
				    bookInfoTable.setModel(model2);
				}
				bookNameField.setText("");
				isbnField.setText("");
				idField.setText("");
			}
		});
        
		p1.setBorder(BorderFactory.createEmptyBorder(0, 250, 30, 250));
		p1.add(bookNameLabel);
		p1.add(bookNameField);
		p1.add(isbnLabel);
		p1.add(isbnField);
		p1.add(idLabel);
		p1.add(idField);
		p1.add(searchBtn);
		p2.add(s);
		
		referP.add(p1);
		referP.add(p2);
	}

	public void iniListP() {
		JButton updateBtn=new JButton("����");
	    BookInfo.bookList=BookInfo.getBookList();
		listP.setLayout(new BorderLayout());
		final JTable table=new JTable();
	    //bookInfoTable=new JTable();
	    table.setFont(new Font("����",Font.PLAIN,13));
	    table.setModel(bookInfo);
	    
	    //������ISBN�����߿��ܶ��Ƚϳ�
	    table.getColumnModel().getColumn(1).setPreferredWidth(100);
	    table.getColumnModel().getColumn(2).setPreferredWidth(120);
	    table.getColumnModel().getColumn(3).setPreferredWidth(100);
	    
	    //��ʾ��ͷ
	    JScrollPane sp=new JScrollPane(table);
	    table.setPreferredScrollableViewportSize(new Dimension(100,10));
	    sp.setAutoscrolls(true);
	    sp.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.setViewportView(table);
        
        //������ͷ����
	    RowSorter<TableModel> sorter=new TableRowSorter<TableModel>(table.getModel());
	    table.setRowSorter(sorter);
	    
	    
	    updateBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println(BookInfo.bookList.size());
				bookInfo=new BookInfoTableModel(BookInfo.bookList);
		    	table.setModel(bookInfo);
			}
	    });
	    
	    listP.add(updateBtn,BorderLayout.SOUTH);
	    listP.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	    listP.add(sp,BorderLayout.CENTER);
	}
	
	
	public void exit(){
		final JDialog dl=new JDialog(this,"�˳�ϵͳ");
		dl.setResizable(false);
		dl.setSize(240, 140);
		dl.setLocation(550,280);
		dl.setLayout(new BorderLayout());
		
		JPanel p1=new JPanel();
		JPanel p2=new JPanel();
		JButton yb=new JButton("��");
		JButton nb=new JButton("��");
		JLabel jb=new JLabel("�Ƿ�ȷ���˳�ϵͳ��");
		
		p1.setLayout(new FlowLayout());
		p1.add(jb);
		p1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		p2.setLayout(new FlowLayout(FlowLayout.CENTER,30,0));
		p2.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		p2.add(yb);
		p2.add(nb);
		
		yb.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		nb.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				dl.dispose();
			}
		});
		
		dl.getContentPane().add(p1,BorderLayout.NORTH);
		dl.getContentPane().add(p2,BorderLayout.SOUTH);
		dl.setVisible(true);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==exitBtn){
			exit();
		}else if(e.getSource()==listBtn){
			cl.show(mainPanel, "�о�ͼ��");
		}else if(e.getSource()==referBtn){
			cl.show(mainPanel, "��ѯͼ��");
		}else if(e.getSource()==adminBtn){
			cl.show(mainPanel, "����ϵͳ����Ա");
		}else if(e.getSource()==brwBtn){
			cl.show(mainPanel, "���������");
		}else if(e.getSource()==bookBtn){
			cl.show(mainPanel, "����ͼ��");
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		
		exit();
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		//exit();
	}
	
	public class IncomingReader implements Runnable{
		@Override
		public void run() {
			try {
				String str=(String) ois.readObject();
				System.out.println(str);
				
				oos.close();
				ois.close();
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}