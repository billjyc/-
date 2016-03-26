package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import data.Borrower;
import data.BorrowerInfoTableModel;
import data.BorrowerList;
import data.Graduate;
import data.Teacher;
import data.UnderGraduate;

public class BorrowerAdd extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4706613594063090925L;
	private Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
	private JLabel idLbl,passwordLbl,passwordConfirmLbl,nameLbl,identityLbl;
	private JTextField idField,nameField;
	private JPasswordField passwordField,passwordConfirmField;
	private JRadioButton undergraduateRadioBtn,graduateRadioBtn,teacherRadioBtn;
	private JButton addBtn,cancelBtn,clearBtn;
	private ButtonGroup identityGroup=new ButtonGroup();
	private JPanel p1,p2,p3;
	protected Borrower b;
    public BorrowerAdd(){
    	super("添加借阅人");
    	setSize(350,220);
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	int w=(int) (d.getWidth()-this.getWidth())/2;
        int h=(int) (d.getHeight()-this.getHeight())/2;
    	setLocation(w,h);
    	setVisible(true);
    	iniFrame();
    }
    
	public void iniFrame() {
		p1=new JPanel();
		p2=new JPanel();
		p3=new JPanel();
		
		idLbl=new JLabel("帐号：",JLabel.CENTER);
		passwordLbl=new JLabel("密码：",JLabel.CENTER);
		passwordConfirmLbl=new JLabel("确认密码：",JLabel.CENTER);
		nameLbl=new JLabel("姓名：",JLabel.CENTER);
		identityLbl=new JLabel("请选择借阅人权限：",JLabel.CENTER);
		
		idField=new JTextField(20);
		passwordField=new JPasswordField(20);
		passwordConfirmField=new JPasswordField(20);
		nameField=new JTextField(20);
		
		undergraduateRadioBtn=new JRadioButton("本科生");
		graduateRadioBtn=new JRadioButton("研究生");
		teacherRadioBtn=new JRadioButton("教师");
		
		identityGroup.add(undergraduateRadioBtn);
		identityGroup.add(graduateRadioBtn);
		identityGroup.add(teacherRadioBtn);
		undergraduateRadioBtn.setSelected(true);
		
		addBtn=new JButton("添加");
		cancelBtn=new JButton("取消");
		clearBtn=new JButton("清空");
		
		addBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		clearBtn.addActionListener(this);
		
		p1.setLayout(new FlowLayout());
		p1.add(identityLbl);
		p1.add(undergraduateRadioBtn);
		p1.add(graduateRadioBtn);
		p1.add(teacherRadioBtn);
		
		p2.setLayout(new GridLayout(4,2));
		p2.add(idLbl);
		p2.add(idField);
		p2.add(nameLbl);
		p2.add(nameField);
		p2.add(passwordLbl);
		p2.add(passwordField);
		p2.add(passwordConfirmLbl);
		p2.add(passwordConfirmField);
		
		p3.setLayout(new FlowLayout());
		p3.add(addBtn);
		p3.add(cancelBtn);
		p3.add(clearBtn);
		
		this.getContentPane().add(p1,BorderLayout.NORTH);
		this.getContentPane().add(p2,BorderLayout.CENTER);
		this.getContentPane().add(p3,BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==cancelBtn){
			this.dispose();
		}else if(e.getSource()==clearBtn){
			idField.setText("");
			nameField.setText("");
			passwordField.setText("");
			passwordConfirmField.setText("");
			idField.requestFocus();
		}else if(e.getSource()==addBtn){
			String id=idField.getText();
			String name=nameField.getText();
			char[] pw=passwordField.getPassword();
			String pword=new String(pw);
			char[] pwc=passwordConfirmField.getPassword();
			String pwConfirm=new String(pwc);
			
			
			if(id.equals("")||name.equals("")||pword.equals("")||pwConfirm.equals("")){
				JOptionPane.showMessageDialog(null, "仍有信息没有被填写！");
			}else{
				if(!pword.equals(pwConfirm)){
					JOptionPane.showMessageDialog(null, "两次填写的密码不一致！");
				}else{
					boolean idExisted=BorrowerList.idExisted(id);
					if(idExisted){
						JOptionPane.showMessageDialog(null, "已存在相同的账号！请重新填写");
					}else{
				    if(undergraduateRadioBtn.isSelected()){
					    b=new UnderGraduate(id,name,pword);
				    }else if(graduateRadioBtn.isSelected()){
					    b=new Graduate(id,name,pword);
				    }else if(teacherRadioBtn.isSelected()){
					    b=new Teacher(id,name,pword);
				    }
				    BorrowerList.addBorrower(b);
				    JOptionPane.showMessageDialog(null, "添加成功！");
				    AdminFrame.borrowerInfo=new BorrowerInfoTableModel(BorrowerList.borrowerList);
				    AdminFrame.borrowerInfoTable.setModel(AdminFrame.borrowerInfo);
				    this.dispose();
				}
				}
			}
		}
		
	}
}
