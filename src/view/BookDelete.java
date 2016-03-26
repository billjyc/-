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
	private JLabel TipLabel=new JLabel("请输入要删除的书名：",JLabel.CENTER);
	private JTextField BookDeleteTextField=new JTextField(15);
	private JButton deleteBtn,cancelBtn;
	private JPanel panel1=new JPanel();
	private JPanel panel2=new JPanel();
	public BookDelete(){
		super("删除图书");
    	setSize(350,140);
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	int w=(int) (d.getWidth()-this.getWidth())/2;
        int h=(int) (d.getHeight()-this.getHeight())/2;
    	setLocation(w,h);
    	setVisible(true);
    	iniFrame();
	}

	public void iniFrame() {
		deleteBtn=new JButton("删除");
		cancelBtn=new JButton("取消");
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
				JOptionPane.showMessageDialog(null, "请输入要删除图书的姓名！");
			}else{
				boolean isBorrow=BookInfo.isBorrow(name);
				if(isBorrow){
					JOptionPane.showMessageDialog(null, "此书仍然被外借，所以你不能删除这本书！");
				}else{
					BookInfo.deleteBook(name);
					JOptionPane.showMessageDialog(null, "删除成功！");
					this.dispose();
				}
			}
		}
	}
}
