/**
 * Original filename : BarangTableModel.java
 * Created at 11:16:10 PM on Sep 17, 2013
 */
package swing.tabmodel;

import javax.swing.table.AbstractTableModel;

import swing.JIFBarang;

/**
 * @author Soni Setiawan Marta <sonismcnx@gmail.com>
 *
 */
public class BarangTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] colName = new String[] {
			"No.",
			"Kode",
			"Barcode",
			"Nama",
			"Kategori",
			"Acc Code",
			"Status",
			"Satuan",
			"Jlh per Unit",
			"Keterangan"};
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return JIFBarang.getTableData().length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return JIFBarang.getTableData()[0].length;
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
		return JIFBarang.getTableData()[rowIndex][columnIndex];
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Class getColumnClass(int column) {
		return JIFBarang.getTableData()[0][column].getClass(  );
	}
}
