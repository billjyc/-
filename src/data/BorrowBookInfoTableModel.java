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

	//指定某列的类型，暂定为String类型
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	//得到列数，bookInfo有几个属性就有几列
	@Override
	public int getColumnCount() {
		return 6;
	}

	//取每一列的列名
	@Override
	public String getColumnName(int columnIndex) {
		if(columnIndex==0){
			return "编号";
		}else if(columnIndex==1){
			return "书名";
		}else if(columnIndex==2){
			return "ISBN";
		}else if(columnIndex==3){
			return "借入日期";
		}else if(columnIndex==4){
			return "还书日期";
		}else if(columnIndex==5){
			return "是否可续借";
		}else{
		    return "出错了！";
		}
	}

	//得到行数，列表中有几个bookInfo对象，就有几行
	@Override
	public int getRowCount() {
		return bookInfoList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		//第几行，就是列表中第几个bookInfo对象
		BorrowedBook book=bookInfoList.get(rowIndex);
		boolean canRenew=book.isCanRenew();
		Date borrowDate=book.getBorrowDate().getTime();
		Date returnDate=book.getReturnDate().getTime();
		DateFormat df11 = new SimpleDateFormat("yyyy年M月d日");   
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
				return "可续借";
			}else{
				return "不可续借";
			}
		}
		else{
			return "出错！" ;
		}
	}

	//制定某单元格是否可被编辑
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
