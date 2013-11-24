/**
 * Original filename : JIFAccCode.java
 * Created at 5:43:11 PM on Sep 5, 2013
 */
package swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;
import java.util.regex.PatternSyntaxException;

import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import lib.DOMUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import swing.tabcolmodel.AcccodeColumnModel;
import swing.tabmodel.AcccodeTableModel;
import swing.utils.MyFocusTraversalPolicy;
import bin.CSE_Client;
import etc.Conf;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JSeparator;

/**
 * @author Soni Setiawan Marta <sonismcnx@gmail.com>
 *
 */
public class JIFAccCode extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private JMenu menuWindow;
	private JMenuItem menuItem;
	private JTable table;
	private static Object[][] tableData = new Object[][] 
			{
				{1, new String(), new String(), new String(), new String(), new String(), new String()}
			};
	private static Object[] arrAccgen = new Object[] {new String()};
	private static Object[][] arrAcctype = new Object[][] {{new String(), new String()}};
	private static Object[][] arrAcccat = new Object[][] {{new String(), new String()}};
	private TableRowSorter<AcccodeTableModel> sorter;
	private JTextField tfNama;
	private JTextField tfLevel;
	private JTextField tfKode;
	private JTextField tfCari;
	private JComboBox<String> cbKategori;
	private JComboBox<String> cbTipe;
	private JComboBox<String> cbAccgen;
	private JButton btnEdit;
	private JButton btnHapus;
	private JButton btnBatal;
	private JRadioButton rbKode;
	private JRadioButton rbNama;
	private JButton btnTambah;
	
	private static MyFocusTraversalPolicy focusPolicy;
	
	public JIFAccCode() {
		this.title = new String();
		this.menuWindow = new JMenu();
		tableData = new Object[][] 
				{
					{1, new String(), new String(), new String(), new String(), new String(), new String()}
				};
		arrAccgen = new Object[] {new String()};
		arrAcctype = new Object[][] {{new String(), new String()}};
		arrAcccat =  new Object[][] {{new String(), new String()}};
		initComponents();
	}
	
	public JIFAccCode(String title, JMenu menuWindow) {
		this.title = title;
		this.menuWindow = menuWindow;
		tableData = new Object[][] 
			{
				{1, new String(), new String(), new String(), new String(), new String(), new String()}
			};
		arrAccgen = new Object[] {new String()};
		arrAcctype = new Object[][] {{new String(), new String()}};
		arrAcccat =  new Object[][] {{new String(), new String()}};
		initComponents();
	}
	
	private void initComponents() {
		setTitle(title);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setClosable(true);
		setMaximizable(true);
		this.addInternalFrameListener(new InternalFrameListener() {

			@Override
			public void internalFrameOpened(InternalFrameEvent e) {
				formInternalFrameOpened(e);
			}

			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				formInternalFrameClosing(e);
			}

			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
			}

			@Override
			public void internalFrameIconified(InternalFrameEvent e) {
				
			}

			@Override
			public void internalFrameDeiconified(InternalFrameEvent e) {
				
			}

			@Override
			public void internalFrameActivated(InternalFrameEvent e) {
				
			}

			@Override
			public void internalFrameDeactivated(InternalFrameEvent e) {
				
			}
			
		});
		
		sorter = new TableRowSorter<AcccodeTableModel>(new AcccodeTableModel());
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setColumnModel(new AcccodeColumnModel());
		table.setModel(new AcccodeTableModel());
		table.setRowSorter(sorter);
		
		table.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                	@Override
                    public void valueChanged(ListSelectionEvent event) {
                		setFormInputEditableState(false);
                		setFormInputInitButtonState();
                        int viewRow = table.getSelectedRow();
                        if (viewRow < 0) {
                            //Selection got filtered away.
                            tfKode.setText(new String());
                            tfNama.setText(new String());
                            cbAccgen.setSelectedIndex(0);
                            tfLevel.setText(new String());
                            cbTipe.setSelectedIndex(0);
                            cbKategori.setSelectedIndex(0);
                        } else {
                            int modelRow = table.convertRowIndexToModel(viewRow);
                            tfKode.setText(tableData[modelRow][1].toString());
                            tfNama.setText(tableData[modelRow][2].toString());
                            cbAccgen.setSelectedItem(getArrAccgenFieldId(tableData[modelRow][3].toString()));
                            tfLevel.setText(tableData[modelRow][4].toString());
                            cbTipe.setSelectedItem(getArrAccTypeFieldName(tableData[modelRow][5].toString()));
                            cbKategori.setSelectedItem(getArrAccKategoriFieldName(tableData[modelRow][6].toString()));
                        }
                    }
                }
        );
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1002, Short.MAX_VALUE)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1002, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JLabel lblAccgen = new JLabel("Acc Gen");
		
		JLabel lblLevel = new JLabel("Level");
		
		JLabel label_2 = new JLabel("Tipe");
		
		JLabel lblKategori = new JLabel("Kategori");
		
		JLabel lblNama = new JLabel("Nama");
		
		JLabel lblKode = new JLabel("Kode");
		
		tfKode = new JTextField();
		lblKode.setLabelFor(tfKode);
		tfKode.setEditable(false);
		tfKode.setColumns(13);
		
		tfNama = new JTextField();
		lblNama.setLabelFor(tfNama);
		tfNama.setEditable(false);
		tfNama.setColumns(30);
		
		cbKategori = new JComboBox<String>();
		lblKategori.setLabelFor(cbKategori);
		cbKategori.addItem(new String());
		cbKategori.setEnabled(false);
		
		cbTipe = new JComboBox<String>();
		label_2.setLabelFor(cbTipe);
		cbTipe.addItem(new String());
		cbTipe.setEnabled(false);
		
		cbAccgen = new JComboBox<String>();
		lblAccgen.setLabelFor(cbAccgen);
		cbAccgen.addItem(new String());
		cbAccgen.setEnabled(false);
		cbAccgen.setSelectedIndex(0);
		cbAccgen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cbAccgenActionPerformed(evt);
			}
		});
		
		tfLevel = new JTextField();
		lblLevel.setLabelFor(tfLevel);
		tfLevel.setEditable(false);
		tfLevel.setColumns(2);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(Color.BLACK);

		btnTambah = new JButton("Tambah");
		btnTambah.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnTambahActionPerformed(evt);
			}
		});
		
		btnHapus = new JButton("Hapus");
		btnHapus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnHapusActionPerformed(evt);
			}
		});
		
		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnEditActionPerformed(evt);
			}
		});
		
		btnBatal = new JButton("Batal");
		btnBatal.setEnabled(false);
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnBatalActionPerformed(evt);
			}
		});
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Cari Data", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, null));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new LineBorder(new Color(57, 105, 138)), "Filter Berdasarkan", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		rbKode = new JRadioButton("Kode");
		rbKode.setSelected(true);
		
		rbNama = new JRadioButton("Nama");

		ButtonGroup bgRbKeyword = new ButtonGroup();
		bgRbKeyword.add(rbKode);
		bgRbKeyword.add(rbNama);
		
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 289, Short.MAX_VALUE)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(rbKode, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbNama, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(215, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 86, Short.MAX_VALUE)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addComponent(rbKode, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbNama, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(10, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		
		JLabel label_6 = new JLabel("Keyword");
		
		tfCari = new JTextField();
		tfCari.setColumns(20);
		tfCari.getDocument().addDocumentListener(
				new DocumentListener() {

					@Override
					public void insertUpdate(DocumentEvent e) {
						newFilterText();
						
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						newFilterText();
						
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
						newFilterText();
						
					}
			
		});
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_panel_1.createSequentialGroup()
							.addComponent(label_6)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfCari, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(66))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_6)
						.addComponent(tfCari, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(15, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(21)
								.addComponent(lblKode, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(16)
								.addComponent(lblNama, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)))
						.addComponent(lblLevel, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAccgen, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblKategori, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(tfLevel, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfKode, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbKategori, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfNama, GroupLayout.PREFERRED_SIZE, 342, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbAccgen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbTipe, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnEdit, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnHapus, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBatal, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnTambah))
					.addPreferredGap(ComponentPlacement.RELATED, 152, Short.MAX_VALUE)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 322, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(separator, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap(10, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(tfKode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfNama, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(6)
							.addComponent(lblKode)
							.addGap(18)
							.addComponent(lblNama)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblLevel)
								.addComponent(tfLevel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAccgen)
								.addComponent(cbAccgen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_2)
						.addComponent(cbTipe, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbKategori, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblKategori))
					.addContainerGap())
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnTambah)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnEdit)
							.addGap(6)
							.addComponent(btnHapus)
							.addGap(6)
							.addComponent(btnBatal)))
					.addContainerGap(28, Short.MAX_VALUE))
		);
		gl_panel.linkSize(SwingConstants.HORIZONTAL, new Component[] {btnTambah, btnHapus, btnEdit, btnBatal});
		
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);
		
		Vector<Component> order = new Vector<Component>();
        order.add(table);
        order.add(tfCari);
        order.add(rbKode);
        order.add(rbNama);
        order.add(tfKode);
        order.add(tfNama);
        order.add(tfLevel);
        order.add(cbAccgen);
        order.add(cbTipe);
        order.add(cbKategori);
        order.add(btnTambah);
        order.add(btnEdit);
        order.add(btnHapus);
        order.add(btnBatal);
        focusPolicy = new MyFocusTraversalPolicy(order);
        setFocusTraversalPolicy(focusPolicy);
        
		pack();
	}
	
	public void formInternalFrameOpened(InternalFrameEvent e) {
		refreshTableAndCombo();
		
		menuItem = new JMenuItem(getTitle());

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					setSelected(true);
				} catch (PropertyVetoException e) {
					e.printStackTrace();
				}
			}
		});

		menuWindow.add(menuItem);
	}
	
	public void refreshTableAndCombo() {
		try {
			if(CSE_Client.getConnStatus() == CSE_Client.CONNECTED_TO_SERVER) {
				populateTableData();
				getCbAccgenData();
				getCbTipeData();
				getCbKategoriData();
			} else {
				String message = "Unable to achieve data.\nPlease reconnect to server";
				String title = "error";
				JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
			}
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		} catch (SAXException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			//this block is also catch FileNotFoundException
			String message = "Unable to contact server";
			String title = "error";
			JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
		} finally {
//			refresh table data
			try {
				table.setColumnModel(new AcccodeColumnModel());
				table.setModel(new AcccodeTableModel());
			} catch (NullPointerException | ArrayIndexOutOfBoundsException npe) {
				String message = "Unable to achieve data.\nPlease reconnect to server";
				String title = "error";
				JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
				tableData = new Object[][] 
						{
							{1, new String(), new String(), new String(), new String(), new String(), new String()}
						};
				arrAccgen = new Object[] {new String()};
				arrAcctype = new Object[][] {{new String(), new String()}};
				arrAcccat =  new Object[][] {{new String(), new String()}};
			}
		}
	}
	
	public void formInternalFrameClosing(InternalFrameEvent e) {
		if(CSE_Client.jifman.closeJIF(this, CSE_Client.jifman.getJIFList(), CSE_Client.jifman.NO_CLOSE_CONFIRM)){
			menuWindow.remove(menuItem);
			dispose();
		}
	}
	
	public void newFilterText() {
		RowFilter<AcccodeTableModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
        	int searchIndex = 1;
        	if(rbKode.isSelected()) searchIndex = 1; 
        	else if (rbNama.isSelected()) searchIndex = 2;
        	
            rf = RowFilter.regexFilter(tfCari.getText(), searchIndex);
        } catch (PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
	}
	
	private void btnTambahActionPerformed(ActionEvent evt) {
		if(btnTambah.getText().equals("Tambah")) {
			setFormInputInitState();
			setFormInputEditableState(true);
			btnTambah.setText("Simpan");
			btnEdit.setEnabled(false);
			btnHapus.setEnabled(false);
			btnBatal.setEnabled(true);
			tfKode.requestFocusInWindow();
		} else {
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			
			try {
//				disables all input
				holdAllInputControl(true);
//				check whether the input is valid
				if( !( tfKode.getText().equals(new String()) || 
						tfNama.getText().equals(new String()) ||
						tfLevel.getText().equals(new String()) ||
						(cbTipe.getSelectedIndex() == 0) ||
						(cbKategori.getSelectedIndex() == 0) ) ) {
//					get input value
					String id = tfKode.getText();
					String name = tfNama.getText();
					String accgen = cbAccgen.getSelectedItem().toString();
					String level = tfLevel.getText();
					String acctype_id = arrAcctype[cbTipe.getSelectedIndex() - 1][0].toString();
					String acccat_id = arrAcccat[cbKategori.getSelectedIndex() - 1][0].toString();
					
//					prepare request to server 
					DocumentBuilder parser = factory.newDocumentBuilder();
					String uri = new String("http://" + Conf.getAppSrvHost() + ":" + Conf.getAppSrvSocket() + Conf.getAppSrvURL()  +
						"/DB/AccCode/dbOperation.jsp" +
						";jsessionid=" + CSE_Client.getConnData().getJsessid()) +  "?" +
						"id=" + id + "&" +
						"name=" + name + "&" +
						"accgen=" + accgen + "&" +
						"level=" + level + "&" +
						"acctype_id=" + acctype_id + "&" +
						"acccat_id=" + acccat_id + "&" +
						"dbRequest=insert";
//					JOptionPane.showMessageDialog(this, uri);
//					get server response
					Document document = parser.parse(uri);
//					process server response
					Element dbData = document.getDocumentElement();

					NodeList messages = dbData.getElementsByTagName("message");
					Element message = (Element) messages.item(0);
					String messageVal = message.getAttribute("value");
					String messageStr = message.getTextContent();
					
					if(messageVal.equals("ok")) {
						JOptionPane.showMessageDialog(this, "Proses berhasil", "Sukses", JOptionPane.INFORMATION_MESSAGE);
						populateTableData();
						refreshTableAndCombo();
					} else {
						JOptionPane.showMessageDialog(this, messageStr, messageVal, JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(this, "Data Belum Lengkap!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			} catch (ParserConfigurationException e) {
				JOptionPane.showMessageDialog(this, "Bad Server Response:\nUnsupported Format", "Error", JOptionPane.ERROR_MESSAGE);
			} catch (SAXException e) {
				JOptionPane.showMessageDialog(this, "Bad Server Response:\nMalformed Data", "Error", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Tidak dapat melakukan komunikasi ke server!\nSilahkan periksa koneksi anda.", "Error", JOptionPane.ERROR_MESSAGE);
			} finally {
				setFormInputInitState();
			}
			
		}
		
	}
	
	private void btnEditActionPerformed(ActionEvent evt) {
		if(btnEdit.getText().equals("Edit")) {
			setFormInputEditableState(true);
			tfKode.setEditable(false);
			btnTambah.setEnabled(false);
			btnEdit.setText("Simpan");
			btnHapus.setEnabled(false);
			btnBatal.setEnabled(true);
			tfNama.requestFocusInWindow();
		} else {
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			
			try {
//				disables all input
				holdAllInputControl(true);
//				check whether the input is valid
				if( !( tfKode.getText().equals(new String()) || 
						tfNama.getText().equals(new String()) ||
						tfLevel.getText().equals(new String()) ||
						(cbTipe.getSelectedIndex() == 0) ||
						(cbKategori.getSelectedIndex() == 0) ) ) {
//					get input value
					String id = tfKode.getText();
					String name = tfNama.getText();
					String accgen = new String();
					if(cbAccgen.getSelectedIndex() != 0) {
						accgen = cbAccgen.getSelectedItem().toString();
					}
					else {
						accgen = new String();
					}
					String level = tfLevel.getText();
					String acctype_id = arrAcctype[cbTipe.getSelectedIndex() - 1][0].toString();
					String acccat_id = arrAcccat[cbKategori.getSelectedIndex() - 1][0].toString();
					
//					prepare request to server 
					DocumentBuilder parser = factory.newDocumentBuilder();
					String uri = new String("http://" + Conf.getAppSrvHost() + ":" + Conf.getAppSrvSocket() + Conf.getAppSrvURL()  +
						"/DB/AccCode/dbOperation.jsp" +
						";jsessionid=" + CSE_Client.getConnData().getJsessid()) +  "?" +
						"id=" + id + "&" +
						"name=" + name + "&" +
						"accgen=" + accgen + "&" +
						"level=" + level + "&" +
						"acctype_id=" + acctype_id + "&" +
						"acccat_id=" + acccat_id + "&" +
						"dbRequest=update";
//					JOptionPane.showMessageDialog(this, uri);
//					get server response
					Document document = parser.parse(uri);
//					process server response
					Element dbData = document.getDocumentElement();

					NodeList messages = dbData.getElementsByTagName("message");
					Element message = (Element) messages.item(0);
					String messageVal = message.getAttribute("value");
					String messageStr = message.getTextContent();
					
					if(messageVal.equals("ok")) {
						JOptionPane.showMessageDialog(this, "Proses berhasil", "Sukses", JOptionPane.INFORMATION_MESSAGE);
						populateTableData();
						refreshTableAndCombo();
						setFormInputInitState();
					} else {
						JOptionPane.showMessageDialog(this, messageStr, messageVal, JOptionPane.ERROR_MESSAGE);
						setFormInputInitState();
					}
				} else {
					JOptionPane.showMessageDialog(this, "Data Belum Lengkap!\nSilahkan pilih kembali data yang akan di-edit.", "Error", JOptionPane.ERROR_MESSAGE);
					setFormInputInitState();
				}
				
			} catch (ParserConfigurationException e) {
				JOptionPane.showMessageDialog(this, "Bad Server Response:\nUnsupported Format", "Error", JOptionPane.ERROR_MESSAGE);
				setFormInputInitState();
			} catch (SAXException e) {
				JOptionPane.showMessageDialog(this, "Bad Server Response:\nMalformed Data", "Error", JOptionPane.ERROR_MESSAGE);
				setFormInputInitState();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Tidak dapat melakukan komunikasi ke server!\nSilahkan periksa koneksi anda.", "Error", JOptionPane.ERROR_MESSAGE);
				setFormInputInitState();
			} finally {
				setFormInputInitState();
			}
			
		}
	}
	
	private void btnHapusActionPerformed(ActionEvent evt) {
		Object[] confirmBtns = new Object[] {"Tidak","Ya"};
		int confirm = JOptionPane.showOptionDialog(
				this, 
				"Anda yakin ingin menghapus entry ini?", 
				"Konfirmasi",
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE,
				null,
				confirmBtns,
				confirmBtns[0]);
		
		if(confirm == 1) {
			try {
				holdAllInputControl(true);
			
				if( !( tfKode.getText().equals(new String()) ) ) {
				
					String id = tfKode.getText();
				
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder parser = factory.newDocumentBuilder();
					String uri = new String("http://" + Conf.getAppSrvHost() + ":" + Conf.getAppSrvSocket() + Conf.getAppSrvURL()  +
							"/DB/AccCode/dbOperation.jsp" +
							";jsessionid=" + CSE_Client.getConnData().getJsessid()) +  "?" +
							"id=" + id + "&" +
							"dbRequest=delete";
//				JOptionPane.showMessageDialog(this, uri);
//				get server response
					Document document = parser.parse(uri);
//				process server response
					Element dbData = document.getDocumentElement();

					NodeList messages = dbData.getElementsByTagName("message");
					Element message = (Element) messages.item(0);
					String messageVal = message.getAttribute("value");
					String messageStr = message.getTextContent();
				
					if(messageVal.equals("ok")) {
						JOptionPane.showMessageDialog(this, "Proses berhasil", "Sukses", JOptionPane.INFORMATION_MESSAGE);
						populateTableData();
						setFormInputInitState();
					} else {
						JOptionPane.showMessageDialog(this, messageStr, messageVal, JOptionPane.ERROR_MESSAGE);
						refreshTableAndCombo();
						setFormInputInitState();
					}
				} else {
					JOptionPane.showMessageDialog(this, "Silahkan pilih data yang akan di-hapus.", "Error", JOptionPane.ERROR_MESSAGE);
					setFormInputInitState();
				}
//			TODO This block is not finish yet
			} catch (ParserConfigurationException e) {
				JOptionPane.showMessageDialog(this, "Bad Server Response:\nUnsupported Format", "Error", JOptionPane.ERROR_MESSAGE);
				setFormInputInitState();
			} catch (SAXException e) {
				JOptionPane.showMessageDialog(this, "Bad Server Response:\nMalformed Data", "Error", JOptionPane.ERROR_MESSAGE);
				setFormInputInitState();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Tidak dapat melakukan komunikasi ke server!\nSilahkan periksa koneksi anda.", "Error", JOptionPane.ERROR_MESSAGE);
				setFormInputInitState();
			} finally {
				setFormInputInitState();
			}
		}
	}
	
	private void btnBatalActionPerformed(ActionEvent evt) {
		setFormInputInitState();
	}
	
	private void cbAccgenActionPerformed(ActionEvent evt) {
		try{
			if(cbAccgen.getSelectedItem().toString().equals(tfKode.getText())) {
				if(btnTambah.getText().equals("Simpan")) {
					btnTambah.setEnabled(false);
				} else if(btnEdit.getText().equals("Simpan")) {
					btnEdit.setEnabled(false);
				}
			} else {
				if(btnTambah.getText().equals("Simpan")) {
					btnTambah.setEnabled(true);
				} else if(btnEdit.getText().equals("Simpan")) {
					btnEdit.setEnabled(true);
				}
			}
		} catch (NullPointerException npe) {
			
		}
	}
	
	private void setFormInputInitState() {
		tfCari.setText(new String());
		tfCari.setEnabled(true);
		rbKode.setEnabled(true);
		rbNama.setEnabled(true);
		tfKode.setText(new String());
		tfKode.setEditable(false);
		tfNama.setText(new String());
		tfNama.setEditable(false);
		cbAccgen.setSelectedIndex(0);
		cbAccgen.setEnabled(false);
		tfLevel.setText(new String());
		tfLevel.setEditable(false);
		cbTipe.setSelectedIndex(0);
		cbTipe.setEnabled(false);
		cbKategori.setSelectedIndex(0);
		cbKategori.setEnabled(false);
		setFormInputInitButtonState();
	}
	
	private void holdAllInputControl(boolean state) {

		tfCari.setEnabled(!state);
		rbKode.setEnabled(!state);
		rbNama.setEnabled(!state);
		
		tfKode.setEditable(state);
		tfNama.setEditable(!state);
		cbAccgen.setEnabled(!state);
		tfLevel.setEditable(!state);
		cbTipe.setEnabled(!state);
		cbKategori.setEnabled(!state);
		// button state
		btnTambah.setEnabled(!state);
		btnEdit.setEnabled(!state);
		btnHapus.setEnabled(!state);
		btnBatal.setEnabled(!state);
	}
	
	private void setFormInputEditableState(boolean state) {
		tfKode.setEditable(state);
		tfNama.setEditable(state);
		cbAccgen.setEnabled(state);
		tfLevel.setEditable(state);
		cbTipe.setEnabled(state);
		cbKategori.setEnabled(state);
	}
	
	private void setFormInputInitButtonState() {
		btnTambah.setText("Tambah");
		btnTambah.setEnabled(true);
		btnEdit.setText("Edit");
		btnEdit.setEnabled(true);
		btnHapus.setEnabled(true);
		btnBatal.setEnabled(false);
	}
	
	public void populateTableData() throws ParserConfigurationException, SAXException, IOException, FileNotFoundException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = factory.newDocumentBuilder();
		String uri = new String("http://" + Conf.getAppSrvHost() + ":" + Conf.getAppSrvSocket() + Conf.getAppSrvURL()  +
				"/DB/AccCode/selectDataAll.jsp;jsessionid=" + CSE_Client.getConnData().getJsessid());
//		JOptionPane.showMessageDialog(this, uri);
		try {
			Document document = parser.parse(uri);
			try {
				Element dbdata = document.getDocumentElement();
				NodeList datarows = dbdata.getElementsByTagName("datarow");
				
				tableData = new Object[datarows.getLength()][7];
				arrAccgen = new Object[] {new String()};
				
				Element datarow;
				
				for(int i = 0; i < datarows.getLength(); i++) {
					datarow = (Element) datarows.item(i);
					tableData[i][0] = i+1;
					tableData[i][1] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "id"));
					tableData[i][2] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "name"));
					tableData[i][3] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "accgen"));
					tableData[i][4] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "level"));
					tableData[i][5] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "acctype_id"));
					tableData[i][6] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "acccat_id"));
				}
				
			} catch (NullPointerException npe) {
				Element message = document.getDocumentElement();
				String msgType = message.getAttribute("value");
				String msgContent = DOMUtil.getSimpleElementText(message);
				JOptionPane.showMessageDialog(this, msgContent, msgType, JOptionPane.ERROR_MESSAGE);
			}
		} catch (FileNotFoundException fnfe) {
			throw new IOException();
		}
		
	}
	
	private void getCbTipeData() throws ParserConfigurationException, SAXException, IOException {
		cbTipe.removeAllItems();
		cbTipe.addItem(new String());
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = factory.newDocumentBuilder();
		String uri = new String("http://" + Conf.getAppSrvHost() + ":" + Conf.getAppSrvSocket() + Conf.getAppSrvURL()  +
				"/DB/AccCode/selectAccType.jsp;jsessionid=" + CSE_Client.getConnData().getJsessid());

		try {
			Document document = parser.parse(uri);
			try {
				Element dbdata = document.getDocumentElement();
				NodeList datarows = dbdata.getElementsByTagName("datarow");

				arrAcctype = new Object[datarows.getLength()][2];

				Element datarow;
				
				for(int i = 0; i < datarows.getLength(); i++) {
					datarow = (Element) datarows.item(i);
					arrAcctype[i][0] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "id"));
					arrAcctype[i][1] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "name"));
					cbTipe.addItem(arrAcctype[i][1].toString());
				}
			} catch (NullPointerException npe) {
				Element message = document.getDocumentElement();
				String msgType = message.getAttribute("value");
				String msgContent = DOMUtil.getSimpleElementText(message);
				JOptionPane.showMessageDialog(this, msgContent, msgType, JOptionPane.ERROR_MESSAGE);
			}
		} catch (FileNotFoundException fnfe) {
			throw new IOException();
		}
	}
	
	private String getArrAccTypeFieldName(String strIdx) {
		String accTypeName = null;
		for(int i = 0; i < arrAcctype.length; i++) {
			if(arrAcctype[i][0].toString().equals(strIdx)) {
				accTypeName = arrAcctype[i][1].toString();
				break;
			}
		}
		return accTypeName;
	}
	
	private void getCbKategoriData() throws ParserConfigurationException, SAXException, IOException {
		cbKategori.removeAllItems();
		cbKategori.addItem(new String());
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = factory.newDocumentBuilder();
		String uri = new String("http://" + Conf.getAppSrvHost() + ":" + Conf.getAppSrvSocket() + Conf.getAppSrvURL()  +
				"/DB/AccCode/selectAccCat.jsp;jsessionid=" + CSE_Client.getConnData().getJsessid());

		try {
			Document document = parser.parse(uri);
			try {
				Element dbdata = document.getDocumentElement();
				NodeList datarows = dbdata.getElementsByTagName("datarow");

				arrAcccat = new Object[datarows.getLength()][2];

				Element datarow;
				
				for(int i = 0; i < datarows.getLength(); i++) {
					datarow = (Element) datarows.item(i);
					arrAcccat[i][0] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "id"));
					arrAcccat[i][1] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "name"));
					cbKategori.addItem(arrAcccat[i][1].toString());
				}
			} catch (NullPointerException npe) {
				Element message = document.getDocumentElement();
				String msgType = message.getAttribute("value");
				String msgContent = DOMUtil.getSimpleElementText(message);
				JOptionPane.showMessageDialog(this, msgContent, msgType, JOptionPane.ERROR_MESSAGE);
			}
		} catch (FileNotFoundException fnfe) {
			throw new IOException();
		}
	}
	
	private String getArrAccKategoriFieldName(String strIdx) {
		String accKategoriName = null;
		for(int i = 0; i < arrAcccat.length; i++) {
			if(arrAcccat[i][0].toString().equals(strIdx)) {
				accKategoriName = arrAcccat[i][1].toString();
				break;
			}
		}
		return accKategoriName;
	}
	
	private void getCbAccgenData() throws ParserConfigurationException, SAXException, IOException {
		cbAccgen.removeAllItems();
		cbAccgen.addItem(new String());
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = factory.newDocumentBuilder();
		String uri = new String("http://" + Conf.getAppSrvHost() + ":" + Conf.getAppSrvSocket() + Conf.getAppSrvURL()  +
				"/DB/AccCode/selectAccGen.jsp;jsessionid=" + CSE_Client.getConnData().getJsessid());

		try {
			Document document = parser.parse(uri);
			try {
				Element dbdata = document.getDocumentElement();
				NodeList datarows = dbdata.getElementsByTagName("datarow");

				arrAccgen = new Object[datarows.getLength()];

				Element datarow;
				
				for(int i = 0; i < datarows.getLength(); i++) {
					datarow = (Element) datarows.item(i);
					arrAccgen[i] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "id"));
					cbAccgen.addItem(arrAccgen[i].toString());
				}
			} catch (NullPointerException npe) {
				Element message = document.getDocumentElement();
				String msgType = message.getAttribute("value");
				String msgContent = DOMUtil.getSimpleElementText(message);
				JOptionPane.showMessageDialog(this, msgContent, msgType, JOptionPane.ERROR_MESSAGE);
			}
		} catch (FileNotFoundException fnfe) {
			throw new IOException();
		}
	}
	
	private String getArrAccgenFieldId(String strIdx) {
		String accGenId = null;
		for(int i = 0; i < arrAccgen.length; i++) {
			if(arrAccgen[i].toString().equals(strIdx)) {
				accGenId = arrAccgen[i].toString();
				break;
			}
		}
		return accGenId;
	}

	/**
	 * @return the tableData
	 */
	public static Object[][] getTableData() {
		return tableData;
	}

	/**
	 * @param tableData the tableData to set
	 */
	public static void setTableData(Object[][] tableData) {
		JIFAccCode.tableData = tableData;
	}
}
