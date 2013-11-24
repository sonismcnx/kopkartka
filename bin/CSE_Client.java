package bin;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.parsers.ParserConfigurationException;

import lib.ConnData;
import lib.JIFManager;

import org.xml.sax.SAXException;

import etc.AppMsg;
import etc.Conf;
import swing.FrmUtama;


public class CSE_Client {

	public static AppMsg appMsg;
	public static JIFManager jifman;
	public static ConnData connData;
	public static String serverSession;
	private static int connStatus = 0;
	public final static int DISCONNECTED_TO_SERVER = 0;
	public final static int CONNECTED_TO_SERVER = 1;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		appMsg = new AppMsg();
		jifman = new JIFManager();
		connData = new ConnData();
//		DbMsg dbMsg = new DbMsg();
		
		try {
			Conf.getConf();
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(
					null, 
					appMsg.getMsgCategory(105) + ": 105\n" + appMsg.getMsgContent(105), 
					appMsg.getMsgCategory(105).toString(), 
					JOptionPane.ERROR_MESSAGE
			);
		} catch (SAXException e1) {
			e1.printStackTrace();JOptionPane.showMessageDialog(
					null, 
					appMsg.getMsgCategory(104) + ": 104\n" + appMsg.getMsgContent(104), 
					appMsg.getMsgCategory(104).toString(), 
					JOptionPane.ERROR_MESSAGE
			);
		} catch (IOException e1) {
			e1.printStackTrace();JOptionPane.showMessageDialog(
					null, 
					appMsg.getMsgCategory(105) + ": 105\n" + appMsg.getMsgContent(105), 
					appMsg.getMsgCategory(105).toString(), 
					JOptionPane.ERROR_MESSAGE
			);
		}
				
		for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			try {
				if("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			} catch ( ClassNotFoundException cnfe ) {
				cnfe.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new FrmUtama().setVisible(true);
			}
			
		});

	}

	/**
	 * @return the connData
	 */
	public static ConnData getConnData() {
		return connData;
	}

	/**
	 * @param connData the connData to set
	 */
	public static void setConnData(ConnData connData) {
		CSE_Client.connData = connData;
	}

	/**
	 * @return the connStatus
	 */
	public static int getConnStatus() {
		return connStatus;
	}

	/**
	 * @param connStatus the connStatus to set
	 */
	public static void setConnStatus(int connStatus) {
		CSE_Client.connStatus = connStatus;
	}

}
