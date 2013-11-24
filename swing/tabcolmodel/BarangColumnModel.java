/**
 * Original filename : BarangColumnModel.java
 * Created at 11:36:09 PM on Sep 17, 2013
 */
package swing.tabcolmodel;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

/**
 * @author Soni Setiawan Marta <sonismcnx@gmail.com>
 *
 */
public class BarangColumnModel extends DefaultTableColumnModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int colNum = 1;
    @Override
    public void addColumn(TableColumn aColumn) {
        
    	if( colNum == 1 ) {
            aColumn.setPreferredWidth(60);
            aColumn.sizeWidthToFit();
            colNum++;
    	}
    	else if( colNum == 2 || colNum == 8 )
        {
            aColumn.setPreferredWidth(130);
            aColumn.sizeWidthToFit();
            colNum++;
        }
    	else if( colNum==3 ) {
    		aColumn.setPreferredWidth(160);
            aColumn.sizeWidthToFit();
            colNum++;
    	}
        else if( colNum == 4 )
        {
            aColumn.setPreferredWidth(300);
            aColumn.sizeWidthToFit();
            colNum++;
        }
        else if( colNum == 6 ) {
        	aColumn.setPreferredWidth(200);
            aColumn.sizeWidthToFit();
            colNum++;
        }
        else if(colNum == 5 || colNum == 7 )
        {
            aColumn.setPreferredWidth(75);
            aColumn.sizeWidthToFit();
            colNum++;
        }
        else {
        	aColumn.setPreferredWidth(100);
            aColumn.sizeWidthToFit();
            colNum++;
        }
    	
        super.setColumnMargin(10);
        super.addColumn(aColumn);
        
    }

}
