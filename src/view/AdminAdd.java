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

public class AdminAdd extends JFrame implements ActionListener{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3872732027258843110L;
	private Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
	private JLabel idLbl,passwordLbl,passwordConfirmLbl,nameLbl;
	private JButton addBtn,cancelBtn;
	private JPanel p1,p2;
	private JTextField idField,nameField;
	private JPasswordField passwordField,passwordConfirmField;
	
	public AdminAdd(){
    	super("���ϵͳ����Ա");
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
		idLbl=new JLabel("�ʺţ�",JLabel.RIGHT);
		passwordLbl=new JLabel("���룺",JLabel.RIGHT);
		passwordConfirmLbl=new JLabel("ȷ�����룺",JLabel.RIGHT);
		nameLbl=new JLabel("�ǳƣ�",JLabel.RIGHT);
		idField=new JTextField(20);
		nameField=new JTextField(20);
		passwordField=new JPasswordField(20);
		passwordConfirmField=new JPasswordField(20);
		
		addBtn=new JButton("���");
		cancelBtn=new JButton("ȡ��");
		addBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		
		p1.setLayout(new GridLayout(4,2,10,10));
		p1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		p1.add(idLbl);
		p1.add(idField);
		p1.add(passwordLbl);
		p1.add(passwordField);
		p1.add(passwordConfirmLbl);
		p1.add(passwordConfirmField);
		p1.add(nameLbl);
		p1.add(nameField);
		
		p2.setLayout(new FlowLayout(FlowLayout.CENTER,30,10));
		p2.add(addBtn);
		p2.add(cancelBtn);
		
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
		String name=nameField.getText();
		if(e.getSource()==addBtn){
			//����һ���Ϊ��
			if(idField.getText().equals("")||pword.equals("")||pwConfirm.equals("")||nameField.getText().equals("")){
				JOptionPane.showMessageDialog(null, "��������д���е���Ŀ��");
				return;
			}else{
				if(!pword.equals(pwConfirm)){
	    			JOptionPane.showMessageDialog(null, "������д�����벻һ�£�");
	    			return;
	    		}else{
	    			if(AdministratorList.idIsExist(id)){
	    				JOptionPane.showMessageDialog(null, "���˺��Ѿ����ڣ�");
	    				return;
	    			}else{
	    				Administrator newAdmin=new Administrator(id,pword,name);
	    				AdministratorList.addAdmin(newAdmin);
	    				JOptionPane.showMessageDialog(null, "��ӳɹ���");
	    				AdminFrame.adminInfo=new AdminInfoTableModel(AdministratorList.adminList);
	    				AdminFrame.adminInfoTable.setModel(AdminFrame.adminInfo);
	    				this.dispose();
	    			}
	    		}
			}
		}else if(e.getSource()==cancelBtn){
			this.dispose();
		}
		
	}
}
