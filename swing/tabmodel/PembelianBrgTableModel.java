/**
 * Original filename : PersediaanTableModel.java
 * Created at 7:08:38 PM on Sep 23, 2013
 */
package swing.tabmodel;

import javax.swing.table.AbstractTableModel;

import swing.JIFPembelianBrg;

/**
 * @author Soni Setiawan Marta <sonismcnx@gmail.com>
 *
 */
public class PembelianBrgTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String[] colName = new String[] {"No.", "Id", "Kode Barang", "Harga Beli", "Jumlah Eceran", "Tanggal Beli", "Suplier"};

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return JIFPembelianBrg.getTableData().length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		 return JIFPembelianBrg.getTableData()[0].length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return JIFPembelianBrg.getTableData()[rowIndex][columnIndex];
	}
	
	@Override
	public String getColumnName(int column) {
		return colName[column];
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Class getColumnClass(int column) {
		return JIFPembelianBrg.getTableData()[0][column].getClass();
	}
}
