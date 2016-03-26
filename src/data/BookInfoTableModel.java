package data;

import java.util.ArrayList;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class BookInfoTableModel implements TableModel{
	private ArrayList<Book> bookInfoList=new ArrayList<Book>();

	@Override
	public void addTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public BookInfoTableModel(ArrayList<Book> list){
		this.bookInfoList=list;
	}
	
	public BookInfoTableModel(){
		
	}

	//ָ��ĳ�е����ͣ��ݶ�ΪString����
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	//�õ�������bookInfo�м������Ծ��м���
	@Override
	public int getColumnCount() {
		return 8;
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
			return "����";
		}else if(columnIndex==4){
			return "������";
		}else if(columnIndex==5){
			return "�Ƿ�ɽ�";
		}else if(columnIndex==6){
			return "�Ƿ��䱾";
		}else if(columnIndex==7){
			return "ʣ������";
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
		Book book=bookInfoList.get(rowIndex);
		boolean isRare=book.isRare();
		boolean isAvailable=book.isAvailable();
		if(columnIndex==0){
			return book.getId();
		}else if(columnIndex==1){
			return book.getName();
		}else if(columnIndex==2){
			return book.getISBN();
		}else if(columnIndex==3){
			return book.getAuthor();
		}else if(columnIndex==4){
			return book.getPress();
		}else if(columnIndex==5){
			if(isAvailable){
				return "�ɽ�";
			}else{
				return "���ɽ�";
			}
		}else if(columnIndex==6){
			if(isRare){
				return "�䱾";
			}else{
				return "���䱾";
			}
		}else if(columnIndex==7){
			return book.getNumOfBook();
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
