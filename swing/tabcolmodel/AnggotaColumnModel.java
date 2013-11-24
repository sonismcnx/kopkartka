/**
 * Original filename : AnggotaColumnModel.java
 * Created at 2:52:36 PM on Oct 26, 2013
 */
package swing.tabcolmodel;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

/**
 * @author cnx
 *
 */
public class AnggotaColumnModel extends DefaultTableColumnModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int colNum = 1;

	/**
	 * 
	 */
    @Override
    public void addColumn(TableColumn aColumn) {
        
    	if( colNum == 1 ) {
            aColumn.setPreferredWidth(60);
            aColumn.sizeWidthToFit();
            colNum++;
    	}
    	else if( colNum == 2 )
        {
            aColumn.setPreferredWidth(130);
            aColumn.sizeWidthToFit();
            colNum++;
        }
    	else if( colNum==3 ) {
    		aColumn.setPreferredWidth(200);
            aColumn.sizeWidthToFit();
            colNum++;
    	}
        else if( colNum == 4 )
        {
            aColumn.setPreferredWidth(100);
            aColumn.sizeWidthToFit();
            colNum++;
        }
        else if( colNum == 5 )
        {
            aColumn.setPreferredWidth(120);
            aColumn.sizeWidthToFit();
            colNum++;
        }
        else if(colNum == 6 || colNum == 7 || colNum == 8 )
        {
            aColumn.setPreferredWidth(130);
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
