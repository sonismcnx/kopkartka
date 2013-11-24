/**
 * Original filename : AnggotaTableModel.java
 * Created at 2:55:21 PM on Oct 26, 2013
 */
package swing.tabmodel;

import javax.swing.table.AbstractTableModel;

import swing.JIFAnggota;

/**
 * @author cnx
 *
 */
public class AnggotaTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] colName = new String[] {
			"No.",
			"Kode",
			"Nama",
			"Kode Divisi",
			"Tgl. Masuk",
			"Acc. Hutang",
			"Acc. Simp. Pokok",
			"Acc. Simp. Sukarela"
			};
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return JIFAnggota.getTableData().length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return JIFAnggota.getTableData()[0].length;
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
		return JIFAnggota.getTableData()[rowIndex][columnIndex];
	}
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public Class getColumnClass(int column) {
		return JIFAnggota.getTableData()[0][column].getClass(  );
	}
}
