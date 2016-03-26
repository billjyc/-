package view;

import javax.swing.*;

import data.BookInfo;

import java.awt.event.*;
import java.awt.*;

public class BookDelete extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5678291509914957177L;
	private Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
	private JLabel TipLabel=new JLabel("������Ҫɾ����������",JLabel.CENTER);
	private JTextField BookDeleteTextField=new JTextField(15);
	private JButton deleteBtn,cancelBtn;
	private JPanel panel1=new JPanel();
	private JPanel panel2=new JPanel();
	public BookDelete(){
		super("ɾ��ͼ��");
    	setSize(350,140);
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	int w=(int) (d.getWidth()-this.getWidth())/2;
        int h=(int) (d.getHeight()-this.getHeight())/2;
    	setLocation(w,h);
    	setVisible(true);
    	iniFrame();
	}

	public void iniFrame() {
		deleteBtn=new JButton("ɾ��");
		cancelBtn=new JButton("ȡ��");
		deleteBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		
		panel1.add(deleteBtn);
		panel1.add(cancelBtn);
		panel1.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		
		panel2.setLayout(new FlowLayout());
		panel2.add(TipLabel);
		panel2.add(BookDeleteTextField);
		panel2.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		
		this.getContentPane().add(panel1,BorderLayout.SOUTH);
		this.getContentPane().add(panel2,BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==cancelBtn){
			this.dispose();
		}else if(e.getSource()==deleteBtn){
			String name=BookDeleteTextField.getText();
			if(name.equals("")){
				JOptionPane.showMessageDialog(null, "������Ҫɾ��ͼ���������");
			}else{
				boolean isBorrow=BookInfo.isBorrow(name);
				if(isBorrow){
					JOptionPane.showMessageDialog(null, "������Ȼ����裬�����㲻��ɾ���Ȿ�飡");
				}else{
					BookInfo.deleteBook(name);
					JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
					this.dispose();
				}
			}
		}
	}
}
