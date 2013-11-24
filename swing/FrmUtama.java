package swing;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDesktopPane;
import javax.swing.JMenuBar;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import bin.CSE_Client;
import etc.Conf;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
public class FrmUtama extends JFrame {

	private JDesktopPane jDesktopPane;
	
	private JMenuBar jMenuBar;
	
	private JMenu menuSystem;
	private JMenuItem menuSystemConnect;
	private JMenuItem menuSystemQuit;
	
	private JMenu menuData;
	private JMenu menuDataBarang;
	private JMenuItem menuDataAccounts;
	private JMenuItem menuDataAnggota;
	private JMenuItem menuDataBarangDagangan;
	private JMenuItem menuDataBarangPersediaan;
	
	private JMenu menuWindow;
	
	private PanelDetail panelDetail;
	
	// constructor
	public FrmUtama() {
		initComponents();
	}
	
	private void initComponents() {
		// init components
		jDesktopPane = new JDesktopPane();
		jDesktopPane.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		jMenuBar = new JMenuBar();
		panelDetail = new PanelDetail();
		panelDetail.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));

		attachComponentEventListener();
		
		// frame properties
		setTitle(Conf.getAppTitle() + " " + Conf.getAppVer());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(true);
		setType(Type.NORMAL);
		Dimension minSize = new Dimension(1024,740);
		setPreferredSize(minSize);
		setMinimumSize(minSize);
		
		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent evt) {
				formWindowOpened(evt);
			}
		});
		
		setupJMenuBar();
		setJMenuBar(jMenuBar);
		
		// workaround for menu & mouse problem in GNOME 3
				if (Arrays.asList("gnome-shell", "gnome", "mate", "other...").contains(System.getenv("DESKTOP_SESSION"))) {
					try {
						Class<?> xwm = Class.forName("sun.awt.X11.XWM");
						Field awt_wmgr = xwm.getDeclaredField("awt_wmgr");
						awt_wmgr.setAccessible(true);
						Field other_wm = xwm.getDeclaredField("OTHER_WM");
						other_wm.setAccessible(true);
						if (awt_wmgr.get(null).equals(other_wm.get(null))) {
							Field metacity_wm = xwm.getDeclaredField("METACITY_WM");
							metacity_wm.setAccessible(true);
							awt_wmgr.set(null, metacity_wm.get(null));
						}
					}
					catch (NullPointerException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (NoSuchFieldException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
		// end of workaround
		
		// frame layout setting
		createContentPaneLayout();
		
		// set frame location at the center of the screen
		java.awt.Dimension frameSize = getSize();
		setLocation(
                ((java.awt.Toolkit.getDefaultToolkit().getScreenSize().width)/2)-((frameSize.width/2))
                , 
                ((java.awt.Toolkit.getDefaultToolkit().getScreenSize().height)/2)-((frameSize.height/2))
            );
		
		// wrap everything
		pack();
	}
	
	private void attachComponentEventListener() {
		// TODO there is nothing to do for now. 
	}
	
	private void setupJMenuBar() {
		menuSystemConnect = new JMenuItem("Connect");
		menuSystemConnect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		menuSystemConnect.setMnemonic(KeyEvent.VK_O);
		
		menuSystemQuit = new JMenuItem("Quit");
		menuSystemQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		menuSystemQuit.setMnemonic(KeyEvent.VK_Q);
		
		menuSystem = new JMenu("System");
		menuSystem.setMnemonic(KeyEvent.VK_S);
		menuSystem.add(menuSystemConnect);
		menuSystem.addSeparator();
		menuSystem.add(menuSystemQuit);

		menuDataBarang = new JMenu("Data Barang");
		
		menuDataBarangDagangan = new JMenuItem("Barang Dagangan");
		menuDataBarang.add(menuDataBarangDagangan);
		
		menuDataAccounts = new JMenuItem("Account");
		
		menuDataAnggota = new JMenuItem("Anggota");
		
		menuData = new JMenu("Data");
		menuData.setMnemonic(KeyEvent.VK_D);
		menuData.add(menuDataBarang);
		
		menuDataBarangPersediaan = new JMenuItem("Persediaan");
		menuDataBarang.add(menuDataBarangPersediaan);
		menuData.add(menuDataAccounts);
		menuData.add(menuDataAnggota);
		menuWindow = new JMenu("Window");
		
		jMenuBar.add(menuSystem);
		jMenuBar.add(menuData);
		jMenuBar.add(menuWindow);
		
		menuSystemConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				menuFileConnectActionPerformed(evt);
			}
		});
		
		menuSystemQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				menuFileQuitActionPerformed(evt);
			}
		});
		
		menuDataAccounts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				menuDataAccountsActionPerformed(evt);
			}
		});
		
		menuDataAnggota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				menuDataAnggotaActionPerformed(evt);
			}
		});
		
		menuDataBarangDagangan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				menuDataBarangDaganganActionPerformed(evt);
			}
		});
		
		menuDataBarangPersediaan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				menuDataBarangPersediaanActionPerformed(evt);
			}
		});
	}
	
	private void createContentPaneLayout() {
		GroupLayout frameLayout = new GroupLayout(getContentPane());
		getContentPane().setLayout(frameLayout);
		
		frameLayout.setHorizontalGroup(
				frameLayout.createParallelGroup(Alignment.LEADING)
					.addComponent(jDesktopPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(panelDetail, 0, 0, Short.MAX_VALUE)
		);
		
		frameLayout.setVerticalGroup(
				frameLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(frameLayout.createSequentialGroup()
						.addComponent(jDesktopPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panelDetail, 0, 20, 20)
				)
		);
	}
	
	private void formWindowOpened(WindowEvent evt) {
		
	}
	
	private void menuFileConnectActionPerformed(ActionEvent evt) {
		String jifName = "jifAuth";
		List<String> jifList = CSE_Client.jifman.getJIFList();
		if(CSE_Client.jifman.checkJIF(jifName, jifList) == false) {
			JIFAuth jifAuth = new JIFAuth("User Login",new JMenu());
			jifAuth.setName(jifName);
			CSE_Client.jifman.openJIF(this, jDesktopPane, jifAuth, jifList);
			jifAuth.setLocation(CSE_Client.jifman.getJIFLocation(jDesktopPane, jifAuth));
		} else {
			JOptionPane.showMessageDialog(
    				this, 
    				"You must close currently opened window of the same type before opening a new one!", 
    				"Error", 
    				JOptionPane.ERROR_MESSAGE);
			CSE_Client.jifman.selectJifByName(jifName, jDesktopPane);
		}
	}
	
	private void menuFileQuitActionPerformed(ActionEvent evt) {
		System.exit(0);
	}

	private void menuDataAccountsActionPerformed(ActionEvent evt) {
		String jifName = "jifAccCode";
		List<String> jifList = CSE_Client.jifman.getJIFList();
		if(CSE_Client.jifman.checkJIF(jifName, jifList) == false) {
			JIFAccCode jifAccCode = new JIFAccCode("Data Account", menuWindow);
			jifAccCode.setName(jifName);
			CSE_Client.jifman.openJIF(this, jDesktopPane, jifAccCode, CSE_Client.jifman.getJIFList());
			jifAccCode.setLocation(CSE_Client.jifman.getJIFLocation(jDesktopPane, jifAccCode));
		} else {
			JOptionPane.showMessageDialog(
    				this, 
    				"You must close currently opened window of the same type before opening a new one!", 
    				"Error", 
    				JOptionPane.ERROR_MESSAGE);
			CSE_Client.jifman.selectJifByName(jifName, jDesktopPane);
		}
	}

	private void menuDataBarangDaganganActionPerformed(ActionEvent evt) {
		String jifName = "jifBarang";
		List<String> jifList = CSE_Client.jifman.getJIFList();
		if(CSE_Client.jifman.checkJIF(jifName, jifList) == false) {
			JIFBarang jifBarang = new JIFBarang("Data Barang Dagangan", menuWindow);
			jifBarang.setName(jifName);
			CSE_Client.jifman.openJIF(this, jDesktopPane, jifBarang, CSE_Client.jifman.getJIFList());
			jifBarang.setLocation(CSE_Client.jifman.getJIFLocation(jDesktopPane, jifBarang));
		} else {
			JOptionPane.showMessageDialog(
    				this, 
    				"You must close currently opened window of the same type before opening a new one!", 
    				"Error", 
    				JOptionPane.ERROR_MESSAGE);
			CSE_Client.jifman.selectJifByName(jifName, jDesktopPane);
		}
	}
	
	private void menuDataBarangPersediaanActionPerformed(ActionEvent evt) {
		String jifName = "jifPersediaan";
		List<String> jifList = CSE_Client.jifman.getJIFList();
		if(CSE_Client.jifman.checkJIF(jifName, jifList) == false) {
		JIFPembelianBrg jifPembelianBrg = new JIFPembelianBrg("Data Persediaan Barang", menuWindow);
		jifPembelianBrg.setName(jifName);
		CSE_Client.jifman.openJIF(this, jDesktopPane, jifPembelianBrg, CSE_Client.jifman.getJIFList());
		jifPembelianBrg.setLocation(CSE_Client.jifman.getJIFLocation(jDesktopPane, jifPembelianBrg));
		} else {
			JOptionPane.showMessageDialog(
    				this, 
    				"You must close currently opened window of the same type before opening a new one!", 
    				"Error", 
    				JOptionPane.ERROR_MESSAGE);
			CSE_Client.jifman.selectJifByName(jifName, jDesktopPane);
		}
	}
	
	private void menuDataAnggotaActionPerformed(ActionEvent evt) {
		String jifName = "jifAnggota";
		List<String> jifList = CSE_Client.jifman.getJIFList();
		if(CSE_Client.jifman.checkJIF(jifName, jifList) == false) {
		JIFAnggota jifAnggota = new JIFAnggota("Data Anggota", menuWindow);
		jifAnggota.setName(jifName);
		CSE_Client.jifman.openJIF(this, jDesktopPane, jifAnggota, CSE_Client.jifman.getJIFList());
		jifAnggota.setLocation(CSE_Client.jifman.getJIFLocation(jDesktopPane, jifAnggota));
		} else {
			JOptionPane.showMessageDialog(
    				this, 
    				"You must close currently opened window of the same type before opening a new one!", 
    				"Error", 
    				JOptionPane.ERROR_MESSAGE);
			CSE_Client.jifman.selectJifByName(jifName, jDesktopPane);
		}
	}
}
