package lib;

import java.awt.Point;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

/**
 * 
 * @author Soni Setiawan Marta <sonismcnx@gmail.com>
 *
 */

public class JIFManager {
    
	private List<String> jifs;
	public final int CLOSE_CONFIRM = 0;
	public final int NO_CLOSE_CONFIRM = 1;
	
//	constructor
	public JIFManager() {
		jifs = new ArrayList<String>();
	}
	
	public List<String> getJIFList() {
		return jifs;
	}
	
    public void openJIF(JFrame parent, JDesktopPane dpane, JInternalFrame jif, List<String> jifList) {
    	jif.setLayer(99);
        jif.setVisible(true);
        dpane.add(jif);
        
        try {
        	jif.setSelected(true);
        } catch (PropertyVetoException e) {
        	e.printStackTrace();
        }
        
        addJIFList(jif.getName(), jifList);
    }
    
    /**
     * Close a JInternal Frame<br/>
     * <br/>
	 * <b>WARNING</b>:<br/>
	 * Do not forget to set the respective JIntenalFrame setDefaultCloseOperation(Boolean)<br/> 
     * to DO_NOTHING_ON_CLOSE to avoid weird thing from happening ;p
     * <br/>
     * 
     * @param jif
     * @param jifList
     * @param showConfirm
     */
    public boolean closeJIF(JInternalFrame jif, List<String> jifList, int showConfirm) {
    	int close = 0;
    	
    	if(showConfirm == CLOSE_CONFIRM) {
    		close = JOptionPane.showInternalConfirmDialog(jif, 
    				"Do you really want to close this window?", 
    				"Close Window", 
    				JOptionPane.YES_NO_OPTION, 
    				JOptionPane.WARNING_MESSAGE);
    	}

    	if(close == 0) {
    		delJIFList(jif.getName(), jifList);
    		return true;
    	} 
    	return false;
    }
    
    /**
     * Close a JInternal Frame<br/>
     * <br/>
	 * <b>WARNING</b>:<br/>
	 * Do not forget to set the respective JIntenalFrame setDefaultCloseOperation(Boolean)<br/> 
     * to DO_NOTHING_ON_CLOSE to avoid weird thing from happening ;p
     * <br/>
     * 
     * @param jif
     * @param jifList
     */
    public boolean closeJIF(JInternalFrame jif, List<String> jifList) {
    	return closeJIF(jif, jifList, CLOSE_CONFIRM);
    }
    
    public void selectJifByName(String jifName, JDesktopPane dpane) {
    	for(JInternalFrame currJif : dpane.getAllFrames()) {
			if(currJif.getName().equals(jifName)) {
				try {
					currJif.setSelected(true);
				} catch (PropertyVetoException pve) {
					pve.printStackTrace();
				}
			}
		}
    }
    
    public Boolean checkJIF(String jifName, List<String> jifList) {
        Boolean res = jifList.contains(jifName);
        return res;
    }
    
    private void addJIFList(String jifName, List<String> jifList) {
        jifList.add(jifName);
    }
    
    private void delJIFList(String jifName, List<String> jifList) {
    	Iterator<String> it = jifList.iterator();
    	while(it.hasNext()) {
    		if(it.next().equals(jifName)) it.remove();
    	}
    		
//        jifList.remove(jifName);
    }
    
    public Point getJIFLocation(JDesktopPane parent, JInternalFrame child) {
    	int posX = ((parent.getWidth()/2) - (child.getWidth()/2));
    	int posY = ((parent.getHeight()/2) - (child.getHeight()/2));
    	Point pos = new Point(posX, posY);
    	return pos;
    }
    
}