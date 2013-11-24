/**
 * Original filename : acccodeTableModel.java
 * Created at 7:47:09 AM on Sep 6, 2013
 */
package swing.tabmodel;

import javax.swing.table.AbstractTableModel;

import swing.JIFAccCode;

/**
 * @author Soni Setiawan Marta <sonismcnx@gmail.com>
 *
 */
public class AcccodeTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] colName = new String[] {"No.","Kode","Nama","Acc Gen","Level","Tipe","Kategori"};
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return JIFAccCode.getTableData().length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return JIFAccCode.getTableData()[0].length;
	}
	
	@Override
	public String getColumnName(int column) {
		return colName[column];
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return JIFAccCode.getTableData()[rowIndex][columnIndex];
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Class getColumnClass(int column) {
		return JIFAccCode.getTableData()[0][column].getClass(  );
	}
}
