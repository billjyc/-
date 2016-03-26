package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data.Borrower;

public class BorrowerFrame extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
	private JPanel p1;
	private Borrower borrower;   //ʹ�øý���Ľ�����
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private JButton referBtn,referBorrowedBookBtn,borrowBookBtn,renewBookBtn,returnBookBtn,messageBtn,exitBtn;
    public BorrowerFrame(Borrower borrower){
    	super("�����˽���");
    	setSize(400,320);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	int w=(int) (d.getWidth()-this.getWidth())/2;
        int h=(int) (d.getHeight()-this.getHeight())/2;
    	setLocation(w,h);
    	setVisible(true);
    	setResizable(false);
    	this.borrower=borrower;
    	iniFrame();
    }
    
    public void iniFrame(){
    	p1=new JPanel();
    	referBtn=new JButton("��ѯͼ��");
    	referBorrowedBookBtn=new JButton("��ѯ�ѽ�ͼ��");
    	//renewBookBtn=new JButton("����ͼ��");
    	borrowBookBtn=new JButton("����ͼ��");
    	//returnBookBtn=new JButton("�黹ͼ��");
    	messageBtn=new JButton("��Ϣ����");
    	exitBtn=new JButton("�˳�ϵͳ");
    	
    	referBtn.addActionListener(this);
    	referBorrowedBookBtn.addActionListener(this);
    	//renewBookBtn.addActionListener(this);
    	borrowBookBtn.addActionListener(this);
    	//returnBookBtn.addActionListener(this);
    	messageBtn.addActionListener(this);
    	exitBtn.addActionListener(this);
    	
    	GridLayout g=new GridLayout(6,1);
    	g.setVgap(5);
    	p1.setLayout(g);
    	p1.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
    	
    	p1.add(referBtn);
    	p1.add(referBorrowedBookBtn);
    	p1.add(borrowBookBtn);
    	//p1.add(renewBookBtn);
    	//p1.add(returnBookBtn);
    	p1.add(messageBtn);
    	p1.add(exitBtn);
    	
    	this.getContentPane().add(p1,BorderLayout.CENTER);
    }
    
    
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==exitBtn){
			exit();
		}else if(e.getSource()==referBtn){
		    new ReferBook();
		}else if(e.getSource()==referBorrowedBookBtn){
			new ReferBorrowedBook(borrower);
		}else if(e.getSource()==borrowBookBtn){
			new BorrowBook(borrower);
		}else if(e.getSource()==messageBtn){
			new MessageReceive(borrower);
		}
	}
	
	public Borrower getBorrower() {
		return borrower;
	}

	public void setBorrower(Borrower borrower) {
		this.borrower = borrower;
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
}

