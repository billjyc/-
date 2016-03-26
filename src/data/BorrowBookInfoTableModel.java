package data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class BorrowBookInfoTableModel implements TableModel{
	private Vector<BorrowedBook> bookInfoList;

	@Override
	public void addTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public BorrowBookInfoTableModel(Vector<BorrowedBook> list){
		this.bookInfoList=list;
	}
	
	public BorrowBookInfoTableModel(){
		this.bookInfoList=new Vector<BorrowedBook>();
	}

	//ָ��ĳ�е����ͣ��ݶ�ΪString����
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	//�õ�������bookInfo�м������Ծ��м���
	@Override
	public int getColumnCount() {
		return 6;
	}

	//ȡÿһ�е�����
	@Override
	public String getColumnName(int columnIndex) {
		if(columnIndex==0){
			return "���";
		}else if(columnIndex==1){
			return "����";
		}else if(columnIndex==2){
			return "ISBN";
		}else if(columnIndex==3){
			return "��������";
		}else if(columnIndex==4){
			return "��������";
		}else if(columnIndex==5){
			return "�Ƿ������";
		}else{
		    return "�����ˣ�";
		}
	}

	//�õ��������б����м���bookInfo���󣬾��м���
	@Override
	public int getRowCount() {
		return bookInfoList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		//�ڼ��У������б��еڼ���bookInfo����
		BorrowedBook book=bookInfoList.get(rowIndex);
		boolean canRenew=book.isCanRenew();
		Date borrowDate=book.getBorrowDate().getTime();
		Date returnDate=book.getReturnDate().getTime();
		DateFormat df11 = new SimpleDateFormat("yyyy��M��d��");   
		String borrowdate = df11.format(borrowDate);
        String returndate=df11.format(returnDate);
		if(columnIndex==0){
			return book.getId();
		}else if(columnIndex==1){
			return book.getName();
		}else if(columnIndex==2){
			return book.getISBN();
		}else if (columnIndex==3) {
			return borrowdate;
		}else if (columnIndex==4) {
			return returndate;
		}else if(columnIndex==5){
			if(canRenew){
				return "������";
			}else{
				return "��������";
			}
		}
		else{
			return "����" ;
		}
	}

	//�ƶ�ĳ��Ԫ���Ƿ�ɱ��༭
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setValueAt(Object arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
