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

	//指定某列的类型，暂定为String类型
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	//得到列数，bookInfo有几个属性就有几列
	@Override
	public int getColumnCount() {
		return 8;
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
			return "作者";
		}else if(columnIndex==4){
			return "出版社";
		}else if(columnIndex==5){
			return "是否可借";
		}else if(columnIndex==6){
			return "是否珍本";
		}else if(columnIndex==7){
			return "剩余数量";
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
				return "可借";
			}else{
				return "不可借";
			}
		}else if(columnIndex==6){
			if(isRare){
				return "珍本";
			}else{
				return "非珍本";
			}
		}else if(columnIndex==7){
			return book.getNumOfBook();
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
