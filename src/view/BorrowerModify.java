package view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import data.Borrower;
import data.BorrowerInfoTableModel;
import data.BorrowerList;

public class BorrowerModify extends JFrame implements ActionListener{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1435437270278383183L;
	private Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
	private JPanel p1,p2;
	private JLabel pwLbl,pwConfirmLbl,idLbl,oldPwLbl;
	private JPasswordField pwField,pwConfirmField,oldPwField;
	private JTextField idField;
	private JButton updateBtn,cancelBtn;
	public BorrowerModify(){
		super("修改密码");
    	setSize(330,230);
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
		pwLbl=new JLabel("请输入新密码：",JLabel.RIGHT);
		pwConfirmLbl=new JLabel("确认新密码：",JLabel.RIGHT);
		oldPwLbl=new JLabel("请输入旧密码：",JLabel.RIGHT);
		idField=new JTextField(20);
		pwField=new JPasswordField(20);
		pwConfirmField=new JPasswordField(20);
		oldPwField=new JPasswordField(20);
		updateBtn=new JButton("更新");
		cancelBtn=new JButton("取消");
		
		p1.setLayout(new GridLayout(4,2));
		p1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		p1.add(idLbl);
		p1.add(idField);
		p1.add(oldPwLbl);
		p1.add(oldPwField);
		p1.add(pwLbl);
		p1.add(pwField);
		p1.add(pwConfirmLbl);
		p1.add(pwConfirmField);
		
		p2.setLayout(new FlowLayout(FlowLayout.CENTER,30,10));
		p2.add(updateBtn);
		p2.add(cancelBtn);
		updateBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		
		this.getContentPane().add(p1,BorderLayout.CENTER);
		this.getContentPane().add(p2,BorderLayout.SOUTH);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String id=idField.getText();
		char[] oldPw=oldPwField.getPassword();
		String oldPword=new String(oldPw);
		char[] pw=pwField.getPassword();
		String pword=new String(pw);
		char[] pwc=pwConfirmField.getPassword();
		String pwConfirm=new String(pwc);
		if(e.getSource()==cancelBtn){
			this.dispose();
		}else if(e.getSource()==updateBtn){
			if(idField.getText().equals("")||pwConfirm.equals("")||pword.equals("")||pwConfirm.equals("")){
				JOptionPane.showMessageDialog(null, "请填写所有项目！");
				return;
			}else{
				if(!pword.equals(pwConfirm)){
					JOptionPane.showMessageDialog(null, "两次输入的新密码不一致！");
					return;
				}else{
					Borrower b=new Borrower(id,pword,oldPword);
					boolean isModify=BorrowerList.modifyBorrower(b);
					if(isModify){
					    JOptionPane.showMessageDialog(null, "更新成功！");
					    AdminFrame.borrowerInfo=new BorrowerInfoTableModel(BorrowerList.borrowerList);
    				    AdminFrame.borrowerInfoTable.setModel(AdminFrame.borrowerInfo);
    				    this.dispose();
					}else{
						JOptionPane.showMessageDialog(null, "用户名/旧密码错误!");
						return;
					}
				}
			}
		}
	}
}
