package swing;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.Box.Filler;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import bin.CSE_Client;

import lib.ConnData;
import lib.DOMUtil;

import etc.Conf;

@SuppressWarnings("serial")
public class JIFAuth extends JInternalFrame implements InternalFrameListener {
	
	private JLabel jLabelID;
	private JLabel jLabelPass;
	private JTextField jTextID;
	private JPasswordField jTextPass;
	private JButton btLogin;
	private JButton btReset;
	private Filler fill1;
	
	private String title = new String();
	private JMenu menuWindow;
	
	// constructor
	public JIFAuth() {
		menuWindow = new JMenu();
		initComponents();
	}
	
	public JIFAuth(String title, JMenu menuWindow) {
		this.title = title;
		this.menuWindow = menuWindow;
		initComponents();
	}
	
	public void initComponents() {
		setTitle(title);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setClosable(true);
		setResizable(false);
		addInternalFrameListener(this);
		
		jLabelID = new JLabel("User ID");
		jLabelPass = new JLabel("Password");
		jTextID = new JTextField();
		jTextID.setColumns(15);
		jTextPass = new JPasswordField();
		jTextPass.setColumns(15);
		btLogin = new JButton("Login");
		btReset = new JButton("Reset");
		fill1 = new Filler(
						new Dimension(0, 5), 
						new Dimension(GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE), 
						new Dimension(0, Short.MAX_VALUE)
					);
		
		attachComponentEventListener();
		
		GroupLayout layout = new GroupLayout(getContentPane());
		setLayout(layout);
		
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addComponent(jLabelID, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(jTextID, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
						)
						.addGroup(layout.createSequentialGroup()
								.addComponent(jLabelPass, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(jTextPass, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
						)
						.addGroup(layout.createSequentialGroup()
								.addGap(0, 0, Short.MAX_VALUE)
								.addComponent(btReset, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btLogin, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
						)
						.addComponent(fill1, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				)
		);
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(jLabelID, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextID, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				)
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(jLabelPass, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(jTextPass, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				)
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(btReset, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btLogin, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				)
				.addComponent(fill1, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
		);
		
		layout.linkSize(SwingConstants.HORIZONTAL, jLabelID, jLabelPass);
		layout.linkSize(SwingConstants.HORIZONTAL, btLogin, btReset);
		pack();
	}
	
	private void attachComponentEventListener() {
		
		btLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btLoginActionPerformed(evt);
			}
		});
		
		btReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btResetActionPerformed(evt);
			}
		});
		
		jTextID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				jTextPass.requestFocusInWindow();
			}
		});
		
		jTextPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btLogin.requestFocusInWindow();
			}
		});
	}
	
	private void btLoginActionPerformed(ActionEvent evt) {
//		System.out.println(this.getParent().getName());
		try {
			StringBuilder sb = new StringBuilder();
			for(int i=0; i<jTextPass.getPassword().length; i++) {
				sb.append(jTextPass.getPassword()[i]);
			}
			String passText = sb.toString();
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = factory.newDocumentBuilder();
			String uri = new String("http://" + Conf.getAppSrvHost() + ":" + Conf.getAppSrvSocket() + Conf.getAppSrvURL()  +
					"/DoLogin" +
					"?userid=" + jTextID.getText() +
					"&password="  + passText);

			Document document = parser.parse(uri);
			
			ConnData connData = new ConnData();
			Element login = document.getDocumentElement();
			connData.setJsessid(DOMUtil.getSimpleElementText(login, "jsessid"));
			connData.setUsername(DOMUtil.getSimpleElementText(login, "user"));
			connData.setUserrole(DOMUtil.getSimpleElementText(login, "role"));
			CSE_Client.setConnData(connData);
			CSE_Client.setConnStatus(CSE_Client.CONNECTED_TO_SERVER);
			JOptionPane.showMessageDialog(this, "Berhasil melakukan login ke " + Conf.getAppSrvHost(), "Login Sukses", JOptionPane.INFORMATION_MESSAGE);
			try {
				setClosed(true);
			} catch (PropertyVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			JOptionPane.showMessageDialog(this, connData.getJsessid()+"\n"+connData.getUsername()+"\n"+connData.getUserrole()+"\n"+jTextPass.getPassword().toString());
		} catch(FileNotFoundException fnfe) {
			JOptionPane.showMessageDialog(this, "Tidak dapat melakukan koneksi ke server!", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) { // this block is also catches ConnectException 
//			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Unable to contact server. Please check your connection", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} 
		
	}
	
	private void btResetActionPerformed(ActionEvent evt) {
		jTextID.setText(new String());
		jTextPass.setText(new String());
		jTextID.requestFocus();
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.InternalFrameListener#internalFrameOpened(javax.swing.event.InternalFrameEvent)
	 */
	@Override
	public void internalFrameOpened(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.InternalFrameListener#internalFrameClosing(javax.swing.event.InternalFrameEvent)
	 */
	@Override
	public void internalFrameClosing(InternalFrameEvent e) {
		CSE_Client.jifman.closeJIF(this, CSE_Client.jifman.getJIFList(), CSE_Client.jifman.NO_CLOSE_CONFIRM);
		dispose();
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.InternalFrameListener#internalFrameClosed(javax.swing.event.InternalFrameEvent)
	 */
	@Override
	public void internalFrameClosed(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.InternalFrameListener#internalFrameIconified(javax.swing.event.InternalFrameEvent)
	 */
	@Override
	public void internalFrameIconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.InternalFrameListener#internalFrameDeiconified(javax.swing.event.InternalFrameEvent)
	 */
	@Override
	public void internalFrameDeiconified(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.InternalFrameListener#internalFrameActivated(javax.swing.event.InternalFrameEvent)
	 */
	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.InternalFrameListener#internalFrameDeactivated(javax.swing.event.InternalFrameEvent)
	 */
	@Override
	public void internalFrameDeactivated(InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

}
