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
public class AdminModify extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8190279518798812300L;
	private Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
	private JPanel p1,p2;
	private JLabel pwLbl,pwConfirmLbl,idLbl,oldPwLbl;
	private JPasswordField pwField,pwConfirmField,oldPwField;
	private JTextField idField;
	private JButton updateBtn,cancelBtn;
    public AdminModify(){
    	super("�޸�����");
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
		pwLbl=new JLabel("�����������룺",JLabel.RIGHT);
		pwConfirmLbl=new JLabel("ȷ�������룺",JLabel.RIGHT);
		oldPwLbl=new JLabel("����������룺",JLabel.RIGHT);
		idField=new JTextField(20);
		pwField=new JPasswordField(20);
		pwConfirmField=new JPasswordField(20);
		oldPwField=new JPasswordField(20);
		updateBtn=new JButton("����");
		cancelBtn=new JButton("ȡ��");
		
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
				JOptionPane.showMessageDialog(null, "����д������Ŀ��");
				return;
			}else{
				if(!pword.equals(pwConfirm)){
					JOptionPane.showMessageDialog(null, "��������������벻һ�£�");
					return;
				}else{
					Administrator admin=new Administrator(id,pword,oldPword);
					boolean isModify=AdministratorList.modifyAdmin(admin);
					if(isModify){
					JOptionPane.showMessageDialog(null, "���³ɹ���");
					AdminFrame.adminInfo=new AdminInfoTableModel(AdministratorList.adminList);
    				AdminFrame.adminInfoTable.setModel(AdminFrame.adminInfo);
    				this.dispose();
					}else{
						JOptionPane.showMessageDialog(null, "�û���/���������!");
						return;
					}
				}
			}
		}
		
	}
}
