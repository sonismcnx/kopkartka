/**
 * Original filename : PersediaanColumnModel.java
 * Created at 7:09:53 PM on Sep 23, 2013
 */
package swing.tabcolmodel;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

/**
 * @author Soni Setiawan Marta <sonismcnx@gmail.com>
 *
 */
public class PembelianBrgColumnModel extends DefaultTableColumnModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	int colNum = 1;
	public void addColumn(TableColumn aColumn) {
		
		if(colNum == 1) {
			aColumn.setPreferredWidth(55);
			aColumn.sizeWidthToFit();
	        colNum++;
		} else if(colNum == 2) {
			aColumn.setPreferredWidth(100);
			aColumn.sizeWidthToFit();
	        colNum++;
		} else if(colNum == 3) {
			aColumn.setPreferredWidth(130);
			aColumn.sizeWidthToFit();
	        colNum++;
		} else if(colNum == 4) {
			aColumn.setPreferredWidth(100);
			aColumn.sizeWidthToFit();
	        colNum++;
		} else if(colNum == 5) {
			aColumn.setPreferredWidth(100);
			aColumn.sizeWidthToFit();
	        colNum++;
		} else if(colNum == 6) {
			aColumn.setPreferredWidth(100);
			aColumn.sizeWidthToFit();
	        colNum++;
		} else if(colNum == 7) {
			aColumn.setPreferredWidth(100);
			aColumn.sizeWidthToFit();
	        colNum++;
		}
    	
        super.setColumnMargin(10);
        super.addColumn(aColumn);
	}

}
