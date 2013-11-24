/**
 * Original filename : JIFPembelian.java
 * Created at 4:05:37 PM on Sep 23, 2013
 */
package swing;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.PatternSyntaxException;

import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import lib.DOMUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import etc.Conf;

import bin.CSE_Client;

import swing.tabcolmodel.BarangColumnModel;
import swing.tabcolmodel.PembelianBrgColumnModel;
import swing.tabmodel.BarangTableModel;
import swing.tabmodel.PembelianBrgTableModel;

/**
 * @author Soni Setiawan Marta <sonismcnx@gmail.com>
 *
 */
public class JIFPembelianBrg extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private JMenu menuWindow;
	private JMenuItem menuItem;
	
	private TableRowSorter<PembelianBrgTableModel> sorter;
	private static Object[][] tableData = new Object[][] {{1, new String(), new String(), new String(), new String(), new String(), new String()}};
	private static Object[][] cbSupplierData = new Object[][] {{new String(), new String()}};
	
	private String idPembelian = new String();
	private JTextField tfKodeBarang;
	private JTextField tfHrgBeli;
	private JTextField tfJlhBeli;
	private JFormattedTextField ftTglBeli;
	private JComboBox<String> cbSupplier;
	private JTable table;
	private JButton btnTambah;
	private JTextField tfCari;
	private JButton btnEdit;
	private JButton btnHapus;
	private JButton btnBatal;
	private JRadioButton rdbtnKodeBarang;
	private JRadioButton rdbtnTanggalBeli;
	private JRadioButton rdbtnSupplier;
	private JScrollPane scrollPane;
	/**
	 * 
	 */
	public JIFPembelianBrg() {
		this.title = new String();
		this.menuWindow = new JMenu();
		initComponents();
	}
	
	public JIFPembelianBrg(String title, JMenu menuWindow) {
		this.title = title;
		this.menuWindow = menuWindow;
		initComponents();
	}
	
	private void initComponents() {
		setTitle(title);
		setMaximizable(true);
		setClosable(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		JLabel lblKodeBarang = new JLabel("Kode Barang");
		
		idPembelian = new String();
		
		tfKodeBarang = new JTextField();
		tfKodeBarang.setEditable(false);
		lblKodeBarang.setLabelFor(tfKodeBarang);
		tfKodeBarang.setColumns(14);
		
		JLabel lblHargaBeli = new JLabel("Harga Beli");
		
		tfHrgBeli = new JTextField();
		tfHrgBeli.setEditable(false);
		tfHrgBeli.setColumns(10);
		
		JLabel lblJumlahEceran = new JLabel("Jumlah (Eceran)");
		
		tfJlhBeli = new JTextField();
		tfJlhBeli.setEditable(false);
		tfJlhBeli.setColumns(10);
		
		JLabel lblTanggalPembelian = new JLabel("Tanggal Pembelian");
		
		ftTglBeli = new JFormattedTextField(createFormatter("####-##-##"));
		ftTglBeli.setEditable(false);
		ftTglBeli.setColumns(10);
		
		JLabel lblSupplier = new JLabel("Supplier");
		
		cbSupplier = new JComboBox<String>();
		cbSupplier.addItem(new String());
		cbSupplier.setEnabled(false);
		
		scrollPane = new JScrollPane();
		
		btnTambah = new JButton("Tambah");
		btnTambah.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnTambahActionPerformed(e);				
			}
		});
		
		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnEditActionPerformed(e);
			}
		});
		
		btnHapus = new JButton("Hapus");
		btnHapus.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnHapusActionPerformed(e);
				
			}
		});
		
		btnBatal = new JButton("Batal");
		btnBatal.setEnabled(false);
		btnBatal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnBatalActionPerformed(e);
			}
		});
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.BELOW_TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblKodeBarang)
						.addComponent(tfKodeBarang, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblHargaBeli)
						.addComponent(tfHrgBeli, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblJumlahEceran)
						.addComponent(tfJlhBeli, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTanggalPembelian)
						.addComponent(ftTglBeli, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSupplier)
						.addComponent(cbSupplier, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnTambah)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnEdit))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnHapus)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnBatal))
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblKodeBarang)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfKodeBarang, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblHargaBeli)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfHrgBeli, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblJumlahEceran)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfJlhBeli, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblTanggalPembelian)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(ftTglBeli, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblSupplier)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cbSupplier, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnTambah)
								.addComponent(btnEdit))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnHapus)
								.addComponent(btnBatal))
							.addGap(18)
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {btnTambah, btnEdit, btnHapus, btnBatal});
		
		JLabel lblKeyword = new JLabel("Keyword");
		
		tfCari = new JTextField();
		tfCari.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Filter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
						.addComponent(lblKeyword, Alignment.LEADING)
						.addComponent(tfCari, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(lblKeyword)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tfCari, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		rdbtnKodeBarang = new JRadioButton("Kode Barang");
		rdbtnKodeBarang.setSelected(true);
		
		rdbtnTanggalBeli = new JRadioButton("Tanggal Beli");
		
		rdbtnSupplier = new JRadioButton("Supplier");
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnKodeBarang);
		buttonGroup.add(rdbtnTanggalBeli);
		buttonGroup.add(rdbtnSupplier);
		
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnKodeBarang)
						.addComponent(rdbtnTanggalBeli)
						.addComponent(rdbtnSupplier))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(rdbtnKodeBarang)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnTanggalBeli)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnSupplier)
					.addContainerGap(7, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
		
		sorter = new TableRowSorter<PembelianBrgTableModel> (new PembelianBrgTableModel());
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setColumnModel(new PembelianBrgColumnModel());
		table.setModel(new PembelianBrgTableModel());
		table.setRowSorter(sorter);
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int viewRow = table.getSelectedRow();
                if (viewRow < 0) {
                    //Selection got filtered away.
                	idPembelian = new String();
                    tfKodeBarang.setText(new String());
                    tfHrgBeli.setText(new String());
                    tfJlhBeli.setText(new String());
                    ftTglBeli.setText(new String());
                    cbSupplier.setSelectedIndex(0);
                } else {
                    int modelRow = table.convertRowIndexToModel(viewRow);
                    idPembelian = tableData[modelRow][1].toString();
                    tfKodeBarang.setText(tableData[modelRow][2].toString());
                    tfHrgBeli.setText(tableData[modelRow][3].toString());
                    tfJlhBeli.setText(tableData[modelRow][4].toString());
                    ftTglBeli.setText(tableData[modelRow][5].toString());
                    if(!(tableData[modelRow][6].toString().equals("0"))) {
                    	cbSupplier.setSelectedItem(getCbSupplierData(tableData[modelRow][6].toString()));
                    } else { cbSupplier.setSelectedItem(0); }
                }
			}
		});
		
		getContentPane().setLayout(groupLayout);
		
		addInternalFrameListener(new InternalFrameListener() {
			
			@Override
			public void internalFrameOpened(InternalFrameEvent e) {
				formInternalFrameOpened(e);
			}
			
			@Override
			public void internalFrameIconified(InternalFrameEvent e) {
				
			}
			
			@Override
			public void internalFrameDeiconified(InternalFrameEvent e) {
				
			}
			
			@Override
			public void internalFrameDeactivated(InternalFrameEvent e) {
				
			}
			
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				formInternalFrameClosing(e);
			}
			
			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
				
			}
			
			@Override
			public void internalFrameActivated(InternalFrameEvent e) {
				
			}
		});
		
		pack();
	}

	protected MaskFormatter createFormatter(String s) {
	    MaskFormatter formatter = null;
	    try {
	        formatter = new MaskFormatter(s);
	    } catch (java.text.ParseException exc) {
	        System.err.println("formatter is bad: " + exc.getMessage());
	        System.exit(-1);
	    }
	    return formatter;
	}
	
	/**
	 * @return the tableData
	 */
	public static Object[][] getTableData() {
		return tableData;
	}

	private void formInternalFrameOpened(InternalFrameEvent evt) {
		//loading data
		try {
			populateTableData();
			getCbSupplierData();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			refreshTableModel();
		}
		
		//menu item init
		menuItem = new JMenuItem(getTitle());

		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					setSelected(true);
				} catch (PropertyVetoException pve) {
					pve.printStackTrace();
				}
				
			}
		});
	}
	
	public void newFilterText() {
		RowFilter<PembelianBrgTableModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
        	int searchIndex = 1;
        	if(rdbtnKodeBarang.isSelected()) searchIndex = 1; 
        	else if (rdbtnTanggalBeli.isSelected()) searchIndex = 5;
        	else if (rdbtnSupplier.isSelected()) searchIndex = 6;
        	
        	rf = RowFilter.regexFilter(tfCari.getText(), searchIndex);
        } catch (PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
	}
	
	/**
	 * 
	 */
	private void refreshTableModel() {
		try {
			table.setColumnModel(new PembelianBrgColumnModel());
			table.setModel(new PembelianBrgTableModel());
			} catch (NullPointerException | ArrayIndexOutOfBoundsException npe) {
				String message = "Unable to achieve data.\nPlease reconnect to server";
				String title = "error";
				JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
				emptyTableData();
			}
	}
	
	private void formInternalFrameClosing(InternalFrameEvent evt) {
		if(CSE_Client.jifman.closeJIF(this, CSE_Client.jifman.getJIFList(), CSE_Client.jifman.NO_CLOSE_CONFIRM)){
			menuWindow.remove(menuItem);
			dispose();
		}
	}
	
	private void populateTableData() throws ParserConfigurationException, SAXException, IOException {
//		http://localhost:8080/KopKar/DB/PembelianBrg/selectDataAll.jsp;jsessionid=FA9696616036C881CE51BF3D2838E548
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = factory.newDocumentBuilder();
		String strUri = new String("http://" + Conf.getAppSrvHost() + ":" + Conf.getAppSrvSocket() + Conf.getAppSrvURL()  +
				"/DB/PembelianBrg/selectDataAll.jsp;jsessionid=" + CSE_Client.getConnData().getJsessid());
//		JOptionPane.showMessageDialog(this, uri);
		try {
			Document document = parser.parse(strUri);
			try {
				Element dbdata = document.getDocumentElement();
				NodeList datarows = dbdata.getElementsByTagName("datarow");
				
				if(datarows.getLength() > 0) {
					emptyTableData(datarows.getLength(), 7);
				}
				else {
					emptyTableData();
				}
				
				Element datarow;
				
				for(int i = 0; i < datarows.getLength(); i++) {
					datarow = (Element) datarows.item(i);
					tableData[i][0] = i+1;
					tableData[i][1] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "id"));
					tableData[i][2] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "barang_id"));
					tableData[i][3] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "jlhbeli"));
					tableData[i][4] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "hrgbeli"));
					tableData[i][5] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "tglbeli"));
					tableData[i][6] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "supplier_id"));
				}
				
			} catch (NullPointerException npe) {
				Element message = document.getDocumentElement();
				String msgType = message.getAttribute("value");
				String msgContent = DOMUtil.getSimpleElementText(message);
				JOptionPane.showMessageDialog(this, msgContent, msgType, JOptionPane.ERROR_MESSAGE);
			}
		} catch (FileNotFoundException e) {
			throw new IOException();
		}
	}
	
	private void getCbSupplierData() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = factory.newDocumentBuilder();
		String strUri = new String("http://" + Conf.getAppSrvHost() + ":" + Conf.getAppSrvSocket() + Conf.getAppSrvURL()  +
				"/DB/PembelianBrg/selectSupplier.jsp;jsessionid=" + CSE_Client.getConnData().getJsessid());
//		JOptionPane.showMessageDialog(this, uri);
		try {
			Document document = parser.parse(strUri);
			try {
				Element dbdata = document.getDocumentElement();
				NodeList datarows = dbdata.getElementsByTagName("datarow");
				
				if(datarows.getLength() > 0) {
					emptyCbSupplierData(datarows.getLength(), 2);
				}
				else {
					emptyCbSupplierData();
				}
				
				Element datarow;
				
				for(int i = 0; i < datarows.getLength(); i++) {
					datarow = (Element) datarows.item(i);
					cbSupplierData[i][0] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "id"));
					cbSupplierData[i][1] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "name"));
					cbSupplier.addItem(cbSupplierData[i][1].toString());
				}
				
			} catch (NullPointerException npe) {
				Element message = document.getDocumentElement();
				String msgType = message.getAttribute("value");
				String msgContent = DOMUtil.getSimpleElementText(message);
				JOptionPane.showMessageDialog(this, msgContent, msgType, JOptionPane.ERROR_MESSAGE);
			}
		} catch (FileNotFoundException e) {
			throw new IOException();
		}
	}

	/**
	 * 
	 */
	private void emptyTableData() {
		tableData = new Object[][] {{1, new String(), new String(), new String(), new String(), new String(), new String()}};
	}

	/**
	 * @param length
	 * @param i
	 */
	private void emptyTableData(int row, int coll) {
		tableData = new Object[row][coll];
	}

	private void emptyCbSupplierData() {
		cbSupplierData = new Object[][] {{new String(), new String()}};
	}
	
	private void emptyCbSupplierData(int row, int col) {
		cbSupplierData = new Object[row][col];
	}
	
	private String getCbSupplierData(String idx) {
		String supplier = null;
		for(int i = 0; i < cbSupplierData[0].length; i++) {
			if(cbSupplierData[i][0].toString().equals(idx)) {
				supplier = cbSupplierData[i][1].toString();
				break;
			}
		}
		return supplier;
	}
	
	private void btnTambahActionPerformed(ActionEvent evt) {
//		if(btnTambah.getText().equals("Tambah")) {
////			emptyFormInputControl();
////			formInputEditable(true);
////			formButtonsInitState();
//			btnTambah.setText("Simpan");
//			btnEdit.setEnabled(false);
//			btnHapus.setEnabled(false);
//			btnBatal.setEnabled(true);
//		} else {
//			
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//			
//			try {
////				disables all input
//
////				check whether the input is valid
//				if( !( tfKodeBarang.getText().equals(new String()) || 
//						tfJlhBeli.getText().equals(new String()) ||
//						tfHrgBeli.getText().equals(new String()) ||
//						ftTglBeli.getText().equals(new String()) ) ) {
////					get input value
//					Locale local = new Locale("de","DE");
//					DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, local);
//					String idPartDate =  dateFormat.format(new Date()).replace(".", new String());
//					String id = tfKodeBarang.getText();
////					TODO check whether the data is already exist in table barang. if it is not, show a confirm dialog to ask user's
////					permission to add new entry into table barang if the user
//					String barang_id = new String();
//					String hrgBeli = tfHrgBeli.getText();
//					String jlhBeli = tfJlhBeli.getText();
//					String tglBeli = ftTglBeli.getText(); 
//										
//					String barcode = tfBarcode.getText();
//					String name = tfNama.getText();
//					String kategoribrg = arrKategori[cbKategori.getSelectedIndex() -1][0].toString();
//					String statusbrg = arrStatus[cbStatus.getSelectedIndex() -1][0].toString();
//					String acccode = cbAcccode.getSelectedItem().toString();
//					String satuan = tfSatuan.getText();
//					String isisatuan = tfIsiPerSatuan.getText();
//					String keterangan = taKeterangan.getText();
//
////					prepare request to server 
//					DocumentBuilder parser = factory.newDocumentBuilder();
//					String uri = new String("http://" + Conf.getAppSrvHost() + ":" + Conf.getAppSrvSocket() + Conf.getAppSrvURL()  +
//						"/DB/Barang/dbOperation.jsp" +
//						";jsessionid=" + CSE_Client.getConnData().getJsessid()) +  "?" +
//						"id=" + id + "&" +
//						"barcode=" + barcode + "&" +
//						"name=" + name + "&" +
//						"kategoribrg_id=" + kategoribrg + "&" +
//						"statusbrg_id=" + statusbrg + "&" +
//						"acccode_id=" + acccode + "&" +
//						"namasatuan=" + satuan + "&" +
//						"jlhperunit=" + isisatuan + "&" +
//						"keterangan=" + keterangan + "&" +
//						"dbRequest=insert";
//
////					get server response
//					Document document = parser.parse(uri);
////					process server response
//					Element dbData = document.getDocumentElement();
//
//					NodeList messages = dbData.getElementsByTagName("message");
//					Element message = (Element) messages.item(0);
//					String messageVal = message.getAttribute("value");
//					String messageStr = message.getTextContent();
//					
//					if(messageVal.equals("ok")) {
//						JOptionPane.showMessageDialog(this, "Proses berhasil", "Sukses", JOptionPane.INFORMATION_MESSAGE);
//						populateTableData();
//					} else {
//						JOptionPane.showMessageDialog(this, messageStr, messageVal, JOptionPane.ERROR_MESSAGE);
//					}
//				} else {
//					JOptionPane.showMessageDialog(this, "Data Belum Lengkap!", "Error", JOptionPane.ERROR_MESSAGE);
//				}
//				
//			} catch (ParserConfigurationException e) {
//				JOptionPane.showMessageDialog(this, "Bad Server Response:\nUnsupported Format", "Error", JOptionPane.ERROR_MESSAGE);
//			} catch (SAXException e) {
//				JOptionPane.showMessageDialog(this, "Bad Server Response:\nMalformed Data", "Error", JOptionPane.ERROR_MESSAGE);
//			} catch (IOException e) {
//				e.printStackTrace();
//				JOptionPane.showMessageDialog(this, "Tidak dapat melakukan komunikasi ke server!\nSilahkan periksa koneksi anda.", "Error", JOptionPane.ERROR_MESSAGE);
//			} finally {
////				TODO refresh table data, model, column model
//			}
//		}
	}
	
	private void btnEditActionPerformed(ActionEvent evt) {
//		TODO code this part
	}
	
	private void btnHapusActionPerformed(ActionEvent evt) {
//		TODO code this part
	}
	
	private void btnBatalActionPerformed(ActionEvent evt) {
//		TODO code this part
	}
}
