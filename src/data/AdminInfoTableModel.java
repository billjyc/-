package data;

import java.util.ArrayList;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class AdminInfoTableModel implements TableModel{
	private ArrayList<Administrator> adminInfoList=new ArrayList<Administrator>();

	@Override
	public void addTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public AdminInfoTableModel(ArrayList<Administrator> list){
		this.adminInfoList=list;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public String getColumnName(int columnIndex) {
		if(columnIndex==0){
			return "帐号";
		}else if(columnIndex==1){
			return "姓名";
		}else{
			return "出错了！";
		}
	}

	@Override
	public int getRowCount() {
		return adminInfoList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Administrator admin=adminInfoList.get(rowIndex);
		if(columnIndex==0){
			return admin.getId();
		}else if(columnIndex==1){
			return admin.getName();
		}else{
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
