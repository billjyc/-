package data;

import java.util.ArrayList;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class BorrowerInfoTableModel implements TableModel{
	private ArrayList<Borrower> borrowerInfoList=new ArrayList<Borrower>();

	@Override
	public void addTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public BorrowerInfoTableModel(ArrayList<Borrower> list){
		this.borrowerInfoList=list;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public String getColumnName(int columnIndex) {
		if(columnIndex==0){
			return "帐号";
		}else if(columnIndex==1){
			return "姓名";
		}else if(columnIndex==2){
			return "身份";
		}else if(columnIndex==3){
			return "已借数量";
		}else{
			return "出错了！";
		}
	}

	@Override
	public int getRowCount() {
		return borrowerInfoList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Borrower borrower=borrowerInfoList.get(rowIndex);
		if(columnIndex==0){
			return borrower.getId();
		}else if(columnIndex==1){
			return borrower.getName();
		}else if(columnIndex==2){
			if(borrower.getLevel()==Borrower.UNDERGRADUATE){
				return "本科生";
			}else if(borrower.getLevel()==Borrower.GRADUATE){
				return "研究生";
			}else if(borrower.getLevel()==Borrower.TEACHER){
				return "教师";
			}else{
				return "错误！";
			}
		}else if(columnIndex==3){
			return borrower.getNumOfBorrowed();
		}
		else{
			return "出错了！";
		}
	}

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
