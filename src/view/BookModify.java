package view;

import javax.swing.*;

import data.Book;
import data.BookInfo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.*;

public class BookModify extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7927340897187940111L;
	private Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
	private JLabel tipLbl=new JLabel("输入书名点'确定'，会显示此书的相关信息");
	private JLabel nameLbl,idLbl,isbnLbl,authorLbl,pressLbl;
	private JTextField nameField,idField,isbnField,authorField,pressField;
	private JButton modifyBtn,yesBtn,clearBtn,cancelBtn;
	private JPanel p1,p2,p3;
	public BookModify(){
		super("修改图书信息");
    	setSize(350,230);
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
		idLbl=new JLabel("编号：",JLabel.CENTER);
		isbnLbl=new JLabel("ISBN：",JLabel.CENTER);
		authorLbl=new JLabel("作者：",JLabel.CENTER);
		pressLbl=new JLabel("出版社：",JLabel.CENTER);
		
		nameField=new JTextField(20);
		idField=new JTextField(20);
		isbnField=new JTextField(20);
		authorField=new JTextField(20);
		pressField=new JTextField(20);
		
		clearBtn=new JButton("清空");
		yesBtn=new JButton("确定");
		modifyBtn=new JButton("修改");
		cancelBtn=new JButton("取消");
		
		clearBtn.addActionListener(this);
		yesBtn.addActionListener(this);
		modifyBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		
		p1.add(tipLbl);
		p2.setLayout(new GridLayout(5,2));
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
		p3.setLayout(new FlowLayout());
		p3.add(yesBtn);
		p3.add(modifyBtn);
		p3.add(clearBtn);
		p3.add(cancelBtn);
		
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
		}else if(e.getSource()==yesBtn){
			if(nameField.getText().equals("")){
				JOptionPane.showMessageDialog(null, "书名不能为空！");
			}else{
				if(!BookInfo.bookIsExist(nameField.getText())){
					JOptionPane.showMessageDialog(null, "书库里没有这本书！");
				}else{
					Book b=BookInfo.findBook(nameField.getText());
					idField.setText(b.getId());
					isbnField.setText(b.getISBN());
					authorField.setText(b.getAuthor());
					pressField.setText(b.getPress());
					nameField.setEditable(false);
				}
			}
		}else if(e.getSource()==modifyBtn){
			String name=nameField.getText();
			String id=idField.getText();
			String isbn=isbnField.getText();
			String author=authorField.getText();
			String press=pressField.getText();
			
			if(name.equals("")||id.equals("")||isbn.equals("")||press.equals("")||author.equals("")){
				JOptionPane.showMessageDialog(null,"请输入所有信息！");
			}else{
				BookInfo.modifyBook(name, isbn, author, press, id);
				JOptionPane.showMessageDialog(null, "修改成功！");
				this.dispose();
			}
		}
	}

}
