/**
 * Original filename : JIFBarang.java
 * Created at 4:09:14 AM on Aug 23, 2013
 */
package swing;

import javax.swing.JInternalFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Component;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.PatternSyntaxException;

import javax.swing.JRadioButton;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import lib.DOMUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import swing.tabcolmodel.BarangColumnModel;
import swing.tabmodel.BarangTableModel;

import etc.Conf;

import bin.CSE_Client;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.beans.PropertyVetoException;

/**
 * @author Soni Setiawan Marta <sonismcnx@gmail.com>
 *
 */
public class JIFBarang extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenu menuWindow;
	private JMenuItem menuItem;
	private JTable tableBarang;
	private String title;
	private JTextField tfKode;
	private JTextField tfBarcode;
	private JTextField tfNama;
	private JComboBox<String> cbKategori;
	private JComboBox<String> cbStatus;
	private JButton btnHapus;
	private JButton btnEdit;
	private JButton btnTambah;
	private JTextField tfCari;
	private JRadioButton rdbtnKode;
	private JRadioButton rdbtnBarcode;
	private JRadioButton rdbtnNama;
	private JRadioButton rdbtnKategori;
	private JRadioButton rdbtnStatus;
	
	private TableRowSorter<BarangTableModel> sorter;
	private static Object[][] tableData = new Object[][]
			{
				{1, new String(), new String(), new String(), new String(), new String(), new String(), new String(), new String(), new String()}
	};

	private Object[][] arrKategori = new Object[][] {{new String(), new String()}};
	private Object[][] arrStatus = new Object[][] {{new String(), new String()}};
	private JComboBox<String> cbAcccode;
	private Object[] arrAcccode = new Object[] {new String()};
	private JButton btnBatal;
	private JScrollPane scrollPane;
	private JLabel lblSatuan;
	private JTextField tfSatuan;
	private JLabel lblKeterangan;
	private JTextArea taKeterangan;
	private JLabel lblIsiPerSatuan;
	private JTextField tfIsiPerSatuan;
	private JLabel label_8;

	public JIFBarang(String title, JMenu menuWindow) {
		this.title = title;
		this.menuWindow = menuWindow;
		emptyTableData();
		emptyComboBoxesData();
		initComponents();
	}
	
	/**
	 * 
	 */
	public JIFBarang() {
		this.title = new String();
		this.menuWindow = new JMenu();
		emptyTableData();
		emptyComboBoxesData();
		initComponents();
	}
	
	public void initComponents() {
		setTitle(title);
		setMaximizable(true);
		setClosable(true);
		setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
		
		addInternalFrameListener(new InternalFrameListener() {

			@Override
			public void internalFrameOpened(InternalFrameEvent e) {
				formInternalFrameOpened(e);
			}

			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				formInternalFrameClosing(e);
			}

			@Override
			public void internalFrameClosed(InternalFrameEvent e) {}

			@Override
			public void internalFrameIconified(InternalFrameEvent e) {}

			@Override
			public void internalFrameDeiconified(InternalFrameEvent e) {}

			@Override
			public void internalFrameActivated(InternalFrameEvent e) {}

			@Override
			public void internalFrameDeactivated(InternalFrameEvent e) {}
		});
		
		sorter = new TableRowSorter<BarangTableModel>(new BarangTableModel());
		
		
		JLabel label = new JLabel("Kode Barang");
		
		JLabel label_1 = new JLabel("Account Code");
		
		JLabel label_3 = new JLabel("Status");
		
		JLabel label_5 = new JLabel("Kategori");
		
		JLabel label_6 = new JLabel("Nama");
		
		JLabel label_7 = new JLabel("Barcode");
		
		tfKode = new JTextField();
		tfKode.setColumns(7);
		
		tfBarcode = new JTextField();
		tfBarcode.setEditable(false);
		tfBarcode.setColumns(20);
		
		tfNama = new JTextField();
		tfNama.setEditable(false);
		tfNama.setColumns(30);
		
		cbKategori = new JComboBox<String>();
		cbKategori.addItem(new String());
		
		cbStatus = new JComboBox<String>();
		cbStatus.addItem(new String());
		
		cbAcccode = new JComboBox<String>();
		cbAcccode.addItem(new String());;
		
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
		
		btnTambah = new JButton("Tambah");
		btnTambah.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnTambahActionPerformed(evt);
			}
		});
		
		btnBatal = new JButton("Batal");
		btnBatal.setEnabled(false);
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnBatalActionPerformed(evt);
			}
		}); //she sells the seashell by the seashore. tounge twister
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		scrollPane = new JScrollPane();
		
		lblSatuan = new JLabel("Satuan");
		
		tfSatuan = new JTextField();
		tfSatuan.setColumns(15);
		
		lblKeterangan = new JLabel("Keterangan");
		
		taKeterangan = new JTextArea();
		taKeterangan.setRows(3);
		taKeterangan.setColumns(30);
		
		lblIsiPerSatuan = new JLabel("Isi per Satuan");
		
		tfIsiPerSatuan = new JTextField();
		tfIsiPerSatuan.setColumns(10);
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(38)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addGap(11)
								.addComponent(label, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
							.addComponent(label_7, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
							.addComponent(label_6, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
						.addComponent(label_5, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSatuan)
						.addComponent(lblIsiPerSatuan)
						.addComponent(lblKeterangan))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(tfKode, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfBarcode, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfNama, GroupLayout.PREFERRED_SIZE, 342, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbKategori, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbAcccode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfSatuan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfIsiPerSatuan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(taKeterangan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnTambah)
						.addComponent(btnEdit)
						.addComponent(btnHapus)
						.addComponent(btnBatal)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(8))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(label)
								.addComponent(tfKode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_7)
								.addComponent(tfBarcode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_6)
								.addComponent(tfNama, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_5)
								.addComponent(cbKategori, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_3)
								.addComponent(cbStatus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_1)
								.addComponent(cbAcccode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblSatuan)
								.addComponent(tfSatuan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblIsiPerSatuan)
								.addComponent(tfIsiPerSatuan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblKeterangan)
								.addComponent(taKeterangan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnTambah)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnEdit)
							.addGap(6)
							.addComponent(btnHapus)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnBatal)
							.addGap(18)
							.addComponent(panel, 0, 0, Short.MAX_VALUE)))
					.addGap(9)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {btnHapus, btnEdit, btnTambah, btnBatal});
		
		tableBarang = new JTable();
		scrollPane.setViewportView(tableBarang);
		tableBarang.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableBarang.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableBarang.setColumnModel(new BarangColumnModel());
		tableBarang.setModel(new BarangTableModel());
		tableBarang.setRowSorter(sorter);
		
		tableBarang.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                	@Override
                    public void valueChanged(ListSelectionEvent event) {
                		formInputEditable(false);
                		formButtonsInitState();
                        int viewRow = tableBarang.getSelectedRow();
                        if (viewRow < 0) {
                            //Selection got filtered away.
                            tfKode.setText(new String());
                            tfBarcode.setText(new String());
                            tfNama.setText(new String());
                            cbKategori.setSelectedIndex(0);
                            cbStatus.setSelectedIndex(0);
                            cbAcccode.setSelectedIndex(0);
                            tfSatuan.setText(new String());
                            tfIsiPerSatuan.setText(new String());
                            taKeterangan.setText(new String());
                        } else {
                            int modelRow = tableBarang.convertRowIndexToModel(viewRow);
                            tfKode.setText(tableData[modelRow][1].toString());
                            tfBarcode.setText(tableData[modelRow][2].toString());
                            tfNama.setText(tableData[modelRow][3].toString());
                            cbKategori.setSelectedItem(getArrKategoriData(tableData[modelRow][4].toString()));
                            cbAcccode.setSelectedItem(getArrAcccodeData(tableData[modelRow][5].toString()));
                            cbStatus.setSelectedItem(getArrStatusData(tableData[modelRow][6].toString()));
                            tfSatuan.setText(tableData[modelRow][7].toString());
                            tfIsiPerSatuan.setText(tableData[modelRow][8].toString());
                            taKeterangan.setText(tableData[modelRow][9].toString());
                        }
                    }
                }
        );
		
		JLabel lblKeyword = new JLabel("Keyword");
		
		tfCari = new JTextField();
		lblKeyword.setLabelFor(tfCari);
		tfCari.setColumns(10);tfCari.getDocument().addDocumentListener(
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
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Filter Berdasarkan", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblKeyword)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfCari, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblKeyword)
						.addComponent(tfCari, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel.setAutoCreateGaps(true);
		gl_panel.setAutoCreateContainerGaps(true);
		
		ButtonGroup btngroup = new ButtonGroup();
		panel_1.setLayout(new GridLayout(0, 2, 5, 5));
		
		rdbtnKode = new JRadioButton("Kode");
		rdbtnKode.setSelected(true);
		btngroup.add(rdbtnKode);
		panel_1.add(rdbtnKode);
		
		rdbtnStatus = new JRadioButton("Status");
		btngroup.add(rdbtnStatus);
		panel_1.add(rdbtnStatus);
		
		rdbtnBarcode = new JRadioButton("Barcode");
		btngroup.add(rdbtnBarcode);
		panel_1.add(rdbtnBarcode);
		
		rdbtnKategori = new JRadioButton("Kategori");
		btngroup.add(rdbtnKategori);
		panel_1.add(rdbtnKategori);
		
		rdbtnNama = new JRadioButton("Nama");
		btngroup.add(rdbtnNama);
		panel_1.add(rdbtnNama);
		
		label_8 = new JLabel("");
		panel_1.add(label_8);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);
		pack();
	}
	
	public void newFilterText() {
		RowFilter<BarangTableModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
        	int searchIndex = 1;
        	if(rdbtnKode.isSelected()) searchIndex = 1; 
        	else if (rdbtnBarcode.isSelected()) searchIndex = 2;
        	else if (rdbtnNama.isSelected()) searchIndex = 3;
        	else if (rdbtnKategori.isSelected()) searchIndex = 4;
        	else if (rdbtnStatus.isSelected()) searchIndex = 6;
        	
            rf = RowFilter.regexFilter(tfCari.getText(), searchIndex);
        } catch (PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
	}
	
	private void formInternalFrameOpened(InternalFrameEvent evt) {
		try {
			if(CSE_Client.getConnStatus() == CSE_Client.CONNECTED_TO_SERVER) {
				formInputEditable(false);
				populateTableData();
				getCbKategoriData();
				getCbStatusData();
				getCbAcccodeData();
			} else {
				String message = "Unable to achieve data.\nPlease reconnect to server";
				String title = "error";
				JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			refreshTableModel();
		}
		
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
	
	private void formInternalFrameClosing(InternalFrameEvent evt) {
		if(CSE_Client.jifman.closeJIF(this, CSE_Client.jifman.getJIFList(), CSE_Client.jifman.NO_CLOSE_CONFIRM)){
			menuWindow.remove(menuItem);
			dispose();
		}
	}
	
	/**
	 * @return the tableData
	 */
	public static Object[][] getTableData() {
		return tableData;
	}

	private void emptyTableData() {	
		tableData = new Object[][] {
				{1, 
					new String(), 
					new String(), 
					new String(), 
					new String(), 
					new String(), 
					new String(), 
					new String(), 
					new String(), 
					new String()}
			};
	}
	
	private void emptyTableData(int i, int j) {
		tableData = new Object[i][j];
	}
	
	private void emptyComboBoxesData() {
		arrKategori = new Object[][] {
			{
				new String(),
				new String()
			}
		};
		
		arrStatus = new Object[][] {
				{
					new String(),
					new String()
				}
			};
		
		arrAcccode = new Object[] {
				new String()
		};
	}
	
	private void emptyFormInputControl() {
		tfCari.setText(new String());
        tfKode.setText(new String());
        tfBarcode.setText(new String());
        tfNama.setText(new String());
        cbKategori.setSelectedIndex(0);
        cbStatus.setSelectedIndex(0);
        cbAcccode.setSelectedIndex(0);
        tfSatuan.setText(new String());
        tfIsiPerSatuan.setText(new String());
        taKeterangan.setText(new String());
	}
	
	private void populateTableData() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = factory.newDocumentBuilder();
		String strUri = new String("http://" + Conf.getAppSrvHost() + ":" + Conf.getAppSrvSocket() + Conf.getAppSrvURL()  +
				"/DB/Barang/selectDataAll.jsp;jsessionid=" + CSE_Client.getConnData().getJsessid());
//		JOptionPane.showMessageDialog(this, uri);
		try {
			Document document = parser.parse(strUri);
			try {
				Element dbdata = document.getDocumentElement();
				NodeList datarows = dbdata.getElementsByTagName("datarow");
				
				if(datarows.getLength() > 0) {
					emptyTableData(datarows.getLength(), 10);
				}
				else {
					emptyTableData();
				}
				
				Element datarow;
				
				for(int i = 0; i < datarows.getLength(); i++) {
					datarow = (Element) datarows.item(i);
					tableData[i][0] = i+1;
					tableData[i][1] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "id"));
					tableData[i][2] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "bcode"));
					tableData[i][3] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "name"));
					tableData[i][4] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "kategoribrg_id"));
					tableData[i][5] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "acccode_id"));
					tableData[i][6] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "statusbrg_id"));
					tableData[i][7] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "satuanunit"));
					tableData[i][8] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "jlhperunit"));
					tableData[i][9] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "keterangan"));
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
	
	private void refreshTableModel() {
		try {
			tableBarang.setColumnModel(new BarangColumnModel());
			tableBarang.setModel(new BarangTableModel());
		} catch (NullPointerException | ArrayIndexOutOfBoundsException npe) {
			String message = "Unable to achieve data.\nPlease reconnect to server";
			String title = "error";
			JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
			emptyTableData();
			emptyComboBoxesData();
		}
	}

	private void getCbKategoriData() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = factory.newDocumentBuilder();
		String uri = new String("http://" + Conf.getAppSrvHost() + ":" + Conf.getAppSrvSocket() + Conf.getAppSrvURL()  +
				"/DB/Barang/selectKategori.jsp;jsessionid=" + CSE_Client.getConnData().getJsessid());

		try {
			Document document = parser.parse(uri);
			try {
				Element dbdata = document.getDocumentElement();
				NodeList datarows = dbdata.getElementsByTagName("datarow");

				arrKategori = new Object[datarows.getLength()][2];

				Element datarow;
				
				for(int i = 0; i < datarows.getLength(); i++) {
					datarow = (Element) datarows.item(i);
					arrKategori[i][0] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "id"));
					arrKategori[i][1] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "name"));
					cbKategori.addItem(arrKategori[i][1].toString());
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
	
	private void getCbStatusData() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = factory.newDocumentBuilder();
		String uri = new String("http://" + Conf.getAppSrvHost() + ":" + Conf.getAppSrvSocket() + Conf.getAppSrvURL()  +
				"/DB/Barang/selectStatus.jsp;jsessionid=" + CSE_Client.getConnData().getJsessid());

		try {
			Document document = parser.parse(uri);
			try {
				Element dbdata = document.getDocumentElement();
				NodeList datarows = dbdata.getElementsByTagName("datarow");

				arrStatus = new Object[datarows.getLength()][2];
				
				Element datarow;
				
				for(int i = 0; i < datarows.getLength(); i++) {
					datarow = (Element) datarows.item(i);
					arrStatus[i][0] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "id"));
					arrStatus[i][1] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "name"));
					cbStatus.addItem(arrStatus[i][1].toString());
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
	
	private void getCbAcccodeData() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = factory.newDocumentBuilder();
		String uri = new String("http://" + Conf.getAppSrvHost() + ":" + Conf.getAppSrvSocket() + Conf.getAppSrvURL()  +
				"/DB/Barang/selectAcccode.jsp;jsessionid=" + CSE_Client.getConnData().getJsessid());

		try {
			Document document = parser.parse(uri);
			try {
				Element dbdata = document.getDocumentElement();
				NodeList datarows = dbdata.getElementsByTagName("datarow");

				arrAcccode = new Object[datarows.getLength()];
				
				Element datarow;
				
				for(int i = 0; i < datarows.getLength(); i++) {
					datarow = (Element) datarows.item(i);
					arrAcccode[i] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "id"));
					cbAcccode.addItem(arrAcccode[i].toString());
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
	
	private void formInputEditable(boolean state) {
		tfKode.setEditable(state);
		tfBarcode.setEditable(state);
		tfNama.setEditable(state);
		cbKategori.setEnabled(state);
		cbStatus.setEnabled(state);
		cbAcccode.setEnabled(state);
		tfSatuan.setEditable(state);
		tfIsiPerSatuan.setEditable(state);
		taKeterangan.setEditable(state);
	}
	
	private void formButtonsInitState() {
		btnTambah.setText("Tambah");
		btnTambah.setEnabled(true);
		
		btnEdit.setText("Edit");
		btnEdit.setEnabled(true);
		
		btnHapus.setText("Hapus");
		btnHapus.setEnabled(true);
		
		btnBatal.setText("Batal");
		btnBatal.setEnabled(false);
	}
	
	private void holdAllInputControl(boolean state) {
		//Form Barang
		formInputEditable(!state);
		//Data Manipulation Button
		btnTambah.setEnabled(!state);
		btnEdit.setEnabled(!state);
		btnHapus.setEnabled(!state);
		btnBatal.setEnabled(state);
		//Form Cari
		tfCari.setEnabled(!state);
		rdbtnKode.setEnabled(!state);
		rdbtnBarcode.setEnabled(!state);
		rdbtnNama.setEnabled(!state);
		rdbtnKategori.setEnabled(!state);
		rdbtnStatus.setEnabled(!state);
	}
	
	private String getArrKategoriData(String idx) {
		String kategoriName = null;
		for(int i = 0; i < arrKategori.length; i++) {
			if(arrKategori[i][0].toString().equals(idx)) {
				kategoriName = arrKategori[i][1].toString();
				break;
			}
		}
		return kategoriName;
	}
	
	private String getArrStatusData(String idx) {
		String statusName = null;
		for(int i = 0; i < arrStatus.length; i++) {
			if(arrStatus[i][0].toString().equals(idx)) {
				statusName = arrStatus[i][1].toString();
				break;
			}
		}
		return statusName;
	}
	
	private String getArrAcccodeData(String idx) {
		String acccode = null;
		for(int i = 0; i < arrAcccode.length; i++) {
			if(arrAcccode[i].toString().equals(idx)) {
				acccode = arrAcccode[i].toString();
				break;
			}
		}
		return acccode;
	}
	
	private void btnTambahActionPerformed(ActionEvent evt) {
		if(btnTambah.getText().equals("Tambah")) {
			emptyFormInputControl();
			formInputEditable(true);
			formButtonsInitState();
			btnTambah.setText("Simpan");
			btnEdit.setEnabled(false);
			btnHapus.setEnabled(false);
			btnBatal.setEnabled(true);
		} else {
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			
			try {
//				disables all input
				holdAllInputControl(true);
//				check whether the input is valid
				if( !( tfKode.getText().equals(new String()) || 
						tfNama.getText().equals(new String()) ||
						(cbKategori.getSelectedIndex() == 0) ||
						(cbStatus.getSelectedIndex() == 0) ) ) {
//					get input value
					String id = tfKode.getText();
					String barcode = tfBarcode.getText();
					String name = tfNama.getText();
					String kategoribrg = arrKategori[cbKategori.getSelectedIndex() -1][0].toString();
					String statusbrg = arrStatus[cbStatus.getSelectedIndex() -1][0].toString();
					String acccode = cbAcccode.getSelectedItem().toString();
					String satuan = tfSatuan.getText();
					String isisatuan = tfIsiPerSatuan.getText();
					String keterangan = taKeterangan.getText();

//					prepare request to server 
					DocumentBuilder parser = factory.newDocumentBuilder();
					String uri = new String("http://" + Conf.getAppSrvHost() + ":" + Conf.getAppSrvSocket() + Conf.getAppSrvURL()  +
						"/DB/Barang/dbOperation.jsp" +
						";jsessionid=" + CSE_Client.getConnData().getJsessid()) +  "?" +
						"id=" + id + "&" +
						"barcode=" + barcode + "&" +
						"name=" + name + "&" +
						"kategoribrg_id=" + kategoribrg + "&" +
						"statusbrg_id=" + statusbrg + "&" +
						"acccode_id=" + acccode + "&" +
						"namasatuan=" + satuan + "&" +
						"jlhperunit=" + isisatuan + "&" +
						"keterangan=" + keterangan + "&" +
						"dbRequest=insert";

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
				emptyFormInputControl();
				refreshTableModel();
				holdAllInputControl(false);
				formInputEditable(false);
				formButtonsInitState();
			}
		}
	}
	
	private void btnEditActionPerformed(ActionEvent evt) {
		if(btnEdit.getText().equals("Edit")) {
			formInputEditable(true);
			formButtonsInitState();
			btnEdit.setText("Simpan");
			btnTambah.setEnabled(false);
			btnHapus.setEnabled(false);
			btnBatal.setEnabled(true);
		} else {
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			
			try {
//				disables all input
				holdAllInputControl(true);
//				check whether the input is valid
				if( !( tfKode.getText().equals(new String()) || 
						tfNama.getText().equals(new String()) ||
						(cbKategori.getSelectedIndex() == 0) ||
						(cbStatus.getSelectedIndex() == 0) ) ) {
//					get input value
					String id = tfKode.getText();
					String barcode = tfBarcode.getText();
					String name = tfNama.getText();
					String kategoribrg = arrKategori[cbKategori.getSelectedIndex() -1][0].toString();
					String statusbrg = arrStatus[cbStatus.getSelectedIndex() -1][0].toString();
					String acccode = cbAcccode.getSelectedItem().toString();
					String satuan = tfSatuan.getText();
					String isisatuan = tfIsiPerSatuan.getText();
					String keterangan = taKeterangan.getText();

//					prepare request to server 
					DocumentBuilder parser = factory.newDocumentBuilder();
					String uri = new String("http://" + Conf.getAppSrvHost() + ":" + Conf.getAppSrvSocket() + Conf.getAppSrvURL()  +
							"/DB/Barang/dbOperation.jsp" +
							";jsessionid=" + CSE_Client.getConnData().getJsessid()) +  "?" +
							"id=" + id + "&" +
							"barcode=" + barcode + "&" +
							"name=" + name + "&" +
							"kategoribrg_id=" + kategoribrg + "&" +
							"statusbrg_id=" + statusbrg + "&" +
							"acccode_id=" + acccode + "&" +
							"namasatuan=" + satuan + "&" +
							"jlhperunit=" + isisatuan + "&" +
							"keterangan=" + keterangan + "&" +
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
				emptyFormInputControl();
				refreshTableModel();
				holdAllInputControl(false);
				formInputEditable(false);
				formButtonsInitState();
			}
		}
	}
	
	public void btnHapusActionPerformed(ActionEvent evt) {
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
				if( !( tfKode.getText().equals(new String()) )) {
				
					String id = tfKode.getText();
				
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder parser = factory.newDocumentBuilder();
					String uri = new String("http://" + Conf.getAppSrvHost() + ":" + Conf.getAppSrvSocket() + Conf.getAppSrvURL()  +
						"/DB/Barang/dbOperation.jsp" +
						";jsessionid=" + CSE_Client.getConnData().getJsessid()) +  "?" +
						"id=" + id + "&" +
						"dbRequest=delete";
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
//						setFormInputInitState();
					} else {
						JOptionPane.showMessageDialog(this, messageStr, messageVal, JOptionPane.ERROR_MESSAGE);
//						setFormInputInitState();
					}
				} else {
					JOptionPane.showMessageDialog(this, "Silahkan pilih data yang akan di-hapus.", "Error", JOptionPane.ERROR_MESSAGE);
//					setFormInputInitState();
				}
			} catch (ParserConfigurationException e) {
				JOptionPane.showMessageDialog(this, "Bad Server Response:\nUnsupported Format", "Error", JOptionPane.ERROR_MESSAGE);
//				setFormInputInitState();
			} catch (SAXException e) {
				JOptionPane.showMessageDialog(this, "Bad Server Response:\nMalformed Data", "Error", JOptionPane.ERROR_MESSAGE);
//				setFormInputInitState();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Tidak dapat melakukan komunikasi ke server!\nSilahkan periksa koneksi anda.", "Error", JOptionPane.ERROR_MESSAGE);
//				setFormInputInitState();
			} finally {
				emptyFormInputControl();
				refreshTableModel();
				holdAllInputControl(false);
				formInputEditable(false);
				formButtonsInitState();
			}
		}
	}
	
	public void btnBatalActionPerformed(ActionEvent evt) {
		emptyFormInputControl();
		refreshTableModel();
		holdAllInputControl(false);
		formInputEditable(false);
		formButtonsInitState();
	}
}