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
    	super("��ӽ�����");
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
		
		idLbl=new JLabel("�ʺţ�",JLabel.CENTER);
		passwordLbl=new JLabel("���룺",JLabel.CENTER);
		passwordConfirmLbl=new JLabel("ȷ�����룺",JLabel.CENTER);
		nameLbl=new JLabel("������",JLabel.CENTER);
		identityLbl=new JLabel("��ѡ�������Ȩ�ޣ�",JLabel.CENTER);
		
		idField=new JTextField(20);
		passwordField=new JPasswordField(20);
		passwordConfirmField=new JPasswordField(20);
		nameField=new JTextField(20);
		
		undergraduateRadioBtn=new JRadioButton("������");
		graduateRadioBtn=new JRadioButton("�о���");
		teacherRadioBtn=new JRadioButton("��ʦ");
		
		identityGroup.add(undergraduateRadioBtn);
		identityGroup.add(graduateRadioBtn);
		identityGroup.add(teacherRadioBtn);
		undergraduateRadioBtn.setSelected(true);
		
		addBtn=new JButton("���");
		cancelBtn=new JButton("ȡ��");
		clearBtn=new JButton("���");
		
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
				JOptionPane.showMessageDialog(null, "������Ϣû�б���д��");
			}else{
				if(!pword.equals(pwConfirm)){
					JOptionPane.showMessageDialog(null, "������д�����벻һ�£�");
				}else{
					boolean idExisted=BorrowerList.idExisted(id);
					if(idExisted){
						JOptionPane.showMessageDialog(null, "�Ѵ�����ͬ���˺ţ���������д");
					}else{
				    if(undergraduateRadioBtn.isSelected()){
					    b=new UnderGraduate(id,name,pword);
				    }else if(graduateRadioBtn.isSelected()){
					    b=new Graduate(id,name,pword);
				    }else if(teacherRadioBtn.isSelected()){
					    b=new Teacher(id,name,pword);
				    }
				    BorrowerList.addBorrower(b);
				    JOptionPane.showMessageDialog(null, "��ӳɹ���");
				    AdminFrame.borrowerInfo=new BorrowerInfoTableModel(BorrowerList.borrowerList);
				    AdminFrame.borrowerInfoTable.setModel(AdminFrame.borrowerInfo);
				    this.dispose();
				}
				}
			}
		}
		
	}
}
