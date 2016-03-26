package view;

import java.awt.*;

import javax.swing.*;

import data.Borrower;
import data.BorrowerInfoTableModel;
import data.BorrowerList;

import java.awt.event.*;
public class BorrowerDelete extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3601058436424392005L;
	private Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
	private JLabel idLbl,passwordLbl,passwordConfirmLbl;
	private JTextField idField;
	private JPasswordField passwordField,passwordConfirmField;
	private JButton deleteBtn,cancelBtn;
	private JPanel p1,p2;
	
	public BorrowerDelete(){
		super("删除借阅人");
    	setSize(330,190);
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
		idLbl=new JLabel("请输入帐号：",JLabel.RIGHT);
		passwordLbl=new JLabel("请输入密码：",JLabel.RIGHT);
		passwordConfirmLbl=new JLabel("确认密码：",JLabel.RIGHT);
		idField=new JTextField(20);
		passwordField=new JPasswordField(20);
		passwordConfirmField=new JPasswordField(20);
		deleteBtn=new JButton("删除");
		cancelBtn=new JButton("取消");
		
		p1.setLayout(new GridLayout(3,2));
		p1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		p1.add(idLbl);
		p1.add(idField);
		p1.add(passwordLbl);
		p1.add(passwordField);
		p1.add(passwordConfirmLbl);
		p1.add(passwordConfirmField);
		
		p2.setLayout(new FlowLayout(FlowLayout.CENTER,30,10));
		p2.add(deleteBtn);
		p2.add(cancelBtn);
		deleteBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		
		this.getContentPane().add(p1,BorderLayout.CENTER);
		this.getContentPane().add(p2,BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String id=idField.getText();
		char[] pw=passwordField.getPassword();
		String pword=new String(pw);
		char[] pwc=passwordConfirmField.getPassword();
		String pwConfirm=new String(pwc);
		if(e.getSource()==cancelBtn){
			this.dispose();
		}else if(e.getSource()==deleteBtn){
			if(pword.equals("")||pwConfirm.equals("")){
				JOptionPane.showMessageDialog(null, "请填写密码！");
				return;
			}else{
				if(!pword.equals(pwConfirm)){
					JOptionPane.showMessageDialog(null, "两次输入的密码不一致！");
					return;
				}else{
					Borrower b=new Borrower(id,pword);
					boolean isDelete=BorrowerList.deleteBorrower(b);
					if(isDelete){
						JOptionPane.showMessageDialog(null, "删除成功！");
						AdminFrame.borrowerInfo=new BorrowerInfoTableModel(BorrowerList.borrowerList);
	    				AdminFrame.borrowerInfoTable.setModel(AdminFrame.borrowerInfo);
	    				this.dispose();
						}else{
							JOptionPane.showMessageDialog(null, "用户名/密码错误!");
							return;
						}
				}
			}
		}
	}

}
