package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import data.AdminInfoTableModel;
import data.Administrator;
import data.AdministratorList;

public class AdminDelete extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8727325460993763715L;
	private Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
	private JPanel p1,p2;
	private JLabel pwLbl,pwConfirmLbl,idLbl;
	private JPasswordField pwField,pwConfirmField;
	private JTextField idField;
	private JButton deleteBtn,cancelBtn;
	public AdminDelete(){
		super("ɾ��ϵͳ����Ա");
    	setSize(330,230);
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
		idLbl=new JLabel("�������ʺţ�",JLabel.RIGHT);
		pwLbl=new JLabel("���������룺",JLabel.RIGHT);
		pwConfirmLbl=new JLabel("ȷ�����룺",JLabel.RIGHT);
		idField=new JTextField(20);
		pwField=new JPasswordField(20);
		pwConfirmField=new JPasswordField(20);
		deleteBtn=new JButton("ɾ��");
		cancelBtn=new JButton("ȡ��");
		
		p1.setLayout(new GridLayout(3,2));
		p1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		p1.add(idLbl);
		p1.add(idField);
		p1.add(pwLbl);
		p1.add(pwField);
		p1.add(pwConfirmLbl);
		p1.add(pwConfirmField);
		
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
		char[] pw=pwField.getPassword();
		String pword=new String(pw);
		char[] pwc=pwConfirmField.getPassword();
		String pwConfirm=new String(pwc);
		if(e.getSource()==cancelBtn){
			this.dispose();
		}else if(e.getSource()==deleteBtn){
			if(pword.equals("")||pwConfirm.equals("")){
				JOptionPane.showMessageDialog(null, "����д���룡");
				return;
			}else{
				if(!pword.equals(pwConfirm)){
					JOptionPane.showMessageDialog(null, "������������벻һ�£�");
					return;
				}else{
					Administrator admin=new Administrator(id,pword);
					boolean isDelete=AdministratorList.deleteAdmin(admin);
					if(isDelete){
					JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
					AdminFrame.adminInfo=new AdminInfoTableModel(AdministratorList.adminList);
    				AdminFrame.adminInfoTable.setModel(AdminFrame.adminInfo);
    				this.dispose();
					}else{
						JOptionPane.showMessageDialog(null, "�û���/�������!");
						return;
					}
				}
			}
		}
		
	}

}
