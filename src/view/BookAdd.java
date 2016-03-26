package view;

import javax.swing.*;

import data.Book;
import data.BookInfo;
import data.BookInfoTableModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.*;
public class BookAdd extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8321623168910415427L;
	private Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
	private JLabel nameLbl,isbnLbl,idLbl,numLbl,authorLbl,pressLbl,rareLbl;
	private JTextField nameField,isbnField,idField,numField,authorField,pressField;
	private JRadioButton isRareRadioBtn,notRareRadioBtn;
	private JButton addBtn,cancelBtn,clearBtn;
	private ButtonGroup buttongroup=new ButtonGroup();
	private JPanel p1,p2,p3;
    public BookAdd(){
    	super("添加图书");
    	setSize(350,280);
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	int w=(int) (d.getWidth()-this.getWidth())/2;
        int h=(int) (d.getHeight()-this.getHeight())/2;
    	setLocation(w,h);
    	setVisible(true);
    	iniFrame();
    }
    
    public void iniFrame(){
    	p1=new JPanel();
    	p2=new JPanel();
    	p3=new JPanel();
    	
    	nameLbl=new JLabel("书名：",JLabel.CENTER);
    	isbnLbl=new JLabel("ISBN：",JLabel.CENTER);
    	idLbl=new JLabel("编号：",JLabel.CENTER);
    	authorLbl=new JLabel("作者：",JLabel.CENTER);
    	pressLbl=new JLabel("出版社：",JLabel.CENTER);
    	numLbl=new JLabel("新书数量：",JLabel.CENTER);
    	rareLbl=new JLabel("是否珍本：",JLabel.CENTER);
    	
    	nameField=new JTextField(20);
    	isbnField=new JTextField(20);
    	idField=new JTextField(20);
    	authorField=new JTextField(20);
    	pressField=new JTextField(20);
    	numField=new JTextField(20);
    	
    	isRareRadioBtn=new JRadioButton("是",false);
    	notRareRadioBtn=new JRadioButton("否",true);
    	buttongroup.add(isRareRadioBtn);
    	buttongroup.add(notRareRadioBtn);
    	
    	addBtn=new JButton("添加");
    	cancelBtn=new JButton("取消");
    	clearBtn=new JButton("清空");
    	addBtn.addActionListener(this);
    	cancelBtn.addActionListener(this);
    	clearBtn.addActionListener(this);
    	
    	p1.setLayout(new FlowLayout());
    	p1.add(rareLbl);
    	p1.add(isRareRadioBtn);
    	p1.add(notRareRadioBtn);
    	
    	p2.setLayout(new GridLayout(6,2));
    	p2.add(nameLbl);
    	p2.add(nameField);
    	p2.add(idLbl);
    	p2.add(idField);
    	p2.add(isbnLbl);
    	p2.add(isbnField);
    	p2.add(authorLbl);
    	p2.add(authorField);
    	p2.add(pressLbl);
    	p2.add(pressField);
    	p2.add(numLbl);
    	p2.add(numField);
    	p2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	
    	p3.setLayout(new FlowLayout(FlowLayout.CENTER,20,0));
    	p3.add(addBtn);
    	p3.add(cancelBtn);
    	p3.add(clearBtn);
    	p3.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	
    	this.getContentPane().add(p1,BorderLayout.NORTH);
    	this.getContentPane().add(p2,BorderLayout.CENTER);
    	this.getContentPane().add(p3,BorderLayout.SOUTH);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==cancelBtn){
			this.dispose();
		}else if(e.getSource()==clearBtn){
			nameField.setText("");
			idField.setText("");
			isbnField.setText("");
			authorField.setText("");
			pressField.setText("");
			numField.setText("");
		}else if(e.getSource()==addBtn){
			String name=nameField.getText();
			String id=idField.getText();
			String isbn=isbnField.getText();
			String author=authorField.getText();
			String press=pressField.getText();
			String num=numField.getText();
			
			if(name.equals("")||(id.equals(""))||(isbn.equals("")||(author.equals(""))||(press.equals(""))||(num.equals("")))){
				JOptionPane.showMessageDialog(null, "您必须填写所有的项目！");
			}else{
			    if(BookInfo.isExist(id)){
			    	JOptionPane.showMessageDialog(null, "此书已经存在！");
			    	return;
			    }else{
			    	
			    	Book newBook=new Book(name,isbn,author,press,id);
                    if(isRareRadioBtn.isSelected()){
			    		newBook.setRare(true);
			    	}else if(notRareRadioBtn.isSelected()){
			    		newBook.setRare(false);
			    	}
				    newBook.setNumOfBook(Integer.parseInt(num));
				    boolean isAdd=BookInfo.addBook(newBook);
				    if(isAdd){
				    	JOptionPane.showMessageDialog(null, "添加成功！");
				    	AdminFrame.bookInfo=new BookInfoTableModel(BookInfo.bookList);
				    	AdminFrame.bookInfoTable.setModel(AdminFrame.bookInfo);
				    	this.dispose();
				    }
			    }
			    
			}
		}
	}
}
