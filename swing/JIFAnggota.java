/**
 * Original filename : JIFAnggota.java
 * Created at 10:13:16 PM on Oct 17, 2013
 */
package swing;

import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.Component;

import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JRadioButton;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import lib.DOMUtil;

import org.eclipse.wb.swing.FocusTraversalOnArray;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import etc.Conf;
import swing.tabcolmodel.AnggotaColumnModel;
import swing.tabmodel.AnggotaTableModel;
import bin.CSE_Client;

/**
 * @author cnx
 *
 */
public class JIFAnggota extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JMenu menuWindow;
	private JMenuItem menuItem;
	private String title;
	private JScrollPane scrollPane;
	private TableRowSorter<AnggotaTableModel> sorter;
	private JTable table;
	private JTextField tfNama;
	private JFormattedTextField ftfId;
	private JComboBox<String> cbDivisi;
	private JFormattedTextField ftfTglmasuk;
	private JComboBox<String> cbAccspok;
	private JComboBox<String> cbAccssuk;
	private JComboBox<String> cbAcchut;
	private JButton btnTambah;
	private JButton btnEdit;
	private JButton btnHapus;
	private JButton btnBatal;
	private JPanel panel_1;
	private JTextField tfCari;
	private JRadioButton rdbtnNoId;
	private JRadioButton rdbtnNama;
	private JRadioButton rdbtnTanggalMasuk;
	
	private static Object[][] tableData = new Object[][] {
			{
				1, 
				new String(), 
				new String(), 
				new String(), 
				new String(), 
				new String(), 
				new String(), 
				new String()
			}
			}; 
	
	private Object[][] cbDivisiData = new Object[][] {
		{
			new String(),
			new String()
		}
	};
	
	private Object[] cbAccspokData = new Object[] {new String()};
	
	private Object[] cbAcchutData = new Object[] {new String()};
	
	private Object[] cbAccssukData = new Object[] {new String()};
	
	private String selAccspok = new String();
	private String selAcchut = new String();
	private String selAccssuk = new String();
	/**
	 * 
	 */
	public JIFAnggota(String title, JMenu menuWindow) {
		this.title = title;
		this.menuWindow = menuWindow;
//		emptyTableData();
//		emptyComboBoxesData();
		initComponents();
	}
	
	public JIFAnggota() {
		this.title = new String();
		this.menuWindow = new JMenu();
//		emptyTableData();
//		emptyComboBoxesData();
		initComponents();
	}
	
	private void initComponents() {
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
		
		scrollPane = new JScrollPane();
		
		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		sorter = new TableRowSorter<AnggotaTableModel>(new AnggotaTableModel());
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setColumnModel(new AnggotaColumnModel());
		table.setModel(new AnggotaTableModel());
		table.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                	@Override
                    public void valueChanged(ListSelectionEvent event) {
                		setupInitFormState();
                		checkComboAcc();
                        int viewRow = table.getSelectedRow();
                        if (viewRow < 0) {
                            //Selection got filtered away.
                            ftfId.setText(new String());
                            tfNama.setText(new String());
                            cbDivisi.setSelectedIndex(0);
                            ftfTglmasuk.setText(new String());
                            cbAccspok.setSelectedIndex(0);
                            cbAcchut.setSelectedIndex(0);
                            cbAccssuk.setSelectedIndex(0);
                        } else {
                            int modelRow = table.convertRowIndexToModel(viewRow);
                            ftfId.setText(tableData[modelRow][1].toString());
                            tfNama.setText(tableData[modelRow][2].toString());
                            cbDivisi.setSelectedItem(getArrDivisiData(tableData[modelRow][3].toString()));
                            ftfTglmasuk.setText(tableData[modelRow][4].toString());
                            selAcchut = tableData[modelRow][5].toString();
                            cbAcchut.addItem(selAcchut);
                            cbAcchut.setSelectedItem(selAcchut);
                            cbAcchut.setEnabled(false);
                            selAccspok = tableData[modelRow][6].toString();
                            cbAccspok.addItem(selAccspok);
                            cbAccspok.setSelectedItem(selAccspok);
                            cbAccspok.setEnabled(false);
                            selAccssuk = tableData[modelRow][7].toString();
                            cbAccssuk.addItem(selAccssuk);
                            cbAccssuk.setSelectedItem(selAccssuk);
                            cbAccssuk.setEnabled(false);
                            btnEdit.setEnabled(true);
                            btnHapus.setEnabled(true);
                        }
                    }
                }
        );
		
		JLabel lblNoId = new JLabel("No. ID");
		
		JLabel lblNama = new JLabel("Nama");
		
		JLabel lblDivisi = new JLabel("Divisi");
		
		JLabel lblTanggalMasuk = new JLabel("Tanggal Masuk");
		
		JLabel lblAccSimpPokok = new JLabel("Acc. Simp. Pokok");
		
		JLabel lblAccSimpSukarela = new JLabel("Acc. Simp. Sukarela");
		
		JLabel lblAccHutangAnggota = new JLabel("Acc. Hutang Anggota");
		
		tfNama = new JTextField();
		tfNama.setEditable(false);
		tfNama.setColumns(30);
		
		cbDivisi = new JComboBox<String>();
		cbDivisi.setEnabled(false);
		cbDivisi.addItem(new String());
		cbDivisi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cbDivisiActionPerformed(evt);
			}
		});
		
		ftfTglmasuk = new JFormattedTextField(createFormatter("##-##-####"));
		ftfTglmasuk.setEditable(false);
		ftfTglmasuk.setHorizontalAlignment(SwingConstants.CENTER);
		ftfTglmasuk.setToolTipText("format: dd-mm-yyyy");
		ftfTglmasuk.setColumns(10);
		ftfTglmasuk.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				String strTgl = ftfTglmasuk.getText().substring(0, 2);
				String strBln = ftfTglmasuk.getText().substring(3, 5);
//				String strThn = ftfTglmasuk.getText().substring(6, 10);
				try {
					int tgl = NumberFormat.getInstance().parse(strTgl).intValue();
					int bln = NumberFormat.getInstance().parse(strBln).intValue();
					if( tgl>31 || tgl<0 || bln>12 || bln<0){
						JOptionPane.showMessageDialog(null, "Format input tanggal salah!");
						ftfTglmasuk.setValue("  -  -    ");
					}
				} catch (ParseException e) {
					
				}
			}
		});
		
		ftfId = new JFormattedTextField(createFormatter("#######"));
		ftfId.setEditable(false);
		ftfId.setColumns(7);
		
		cbAccspok = new JComboBox<String>();
		cbAccspok.addItem(new String());
		cbAccspok.setEnabled(false);
		
		cbAccssuk = new JComboBox<String>();
		cbAccssuk.addItem(new String());
		cbAccssuk.setEnabled(false);
		
		cbAcchut = new JComboBox<String>();
		cbAcchut.addItem(new String());
		cbAcchut.setEnabled(false);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		
		btnTambah = new JButton("Tambah");
		btnTambah.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnTambahActionPerformed(evt);
			}
		});
		
		btnEdit = new JButton("Edit");
		btnEdit.setEnabled(false);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnEditActionPerformed(evt);
			}
		});
		
		btnHapus = new JButton("Hapus");
		btnHapus.setEnabled(false);
		btnHapus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnHapusActionPerformed(evt);
			}
		});
		
		btnBatal = new JButton("Batal");
		btnBatal.setEnabled(false);
		btnBatal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnBatalActionPerformed(evt);
			}
		});
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNoId)
						.addComponent(lblAccSimpSukarela)
						.addComponent(lblAccHutangAnggota)
						.addComponent(lblAccSimpPokok)
						.addComponent(lblTanggalMasuk)
						.addComponent(lblDivisi)
						.addComponent(lblNama))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(ftfId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfNama, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbDivisi, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(ftfTglmasuk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbAccspok, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbAccssuk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbAcchut, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnTambah)
						.addComponent(btnEdit)
						.addComponent(btnHapus)
						.addComponent(btnBatal))
					.addPreferredGap(ComponentPlacement.RELATED, 81, Short.MAX_VALUE)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(separator, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnTambah)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnEdit)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnHapus)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnBatal))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNoId)
								.addComponent(ftfId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNama)
								.addComponent(tfNama, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblDivisi)
								.addComponent(cbDivisi, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblTanggalMasuk)
								.addComponent(ftfTglmasuk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAccSimpPokok)
								.addComponent(cbAccspok, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAccSimpSukarela)
								.addComponent(cbAccssuk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAccHutangAnggota)
								.addComponent(cbAcchut, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addGap(12))
		);
		gl_panel.linkSize(SwingConstants.HORIZONTAL, new Component[] {btnTambah, btnEdit, btnHapus, btnBatal});
		
		JLabel lblKeyword = new JLabel("Keyword");
		
		tfCari = new JTextField();
		tfCari.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Filter Berdasarkan", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(lblKeyword)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tfCari, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(4)
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblKeyword)
						.addComponent(tfCari, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		rdbtnNoId = new JRadioButton("No. ID");
		rdbtnNoId.setSelected(true);
		
		rdbtnNama = new JRadioButton("Nama");
		
		rdbtnTanggalMasuk = new JRadioButton("Tanggal Masuk");
		
		ButtonGroup btngroup = new ButtonGroup();
		btngroup.add(rdbtnNoId);
		btngroup.add(rdbtnNama);
		btngroup.add(rdbtnTanggalMasuk);
		
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnNoId)
						.addComponent(rdbtnNama)
						.addComponent(rdbtnTanggalMasuk))
					.addContainerGap(158, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(rdbtnNoId)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnNama)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnTanggalMasuk)
					.addContainerGap(64, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{ftfId, tfNama, cbDivisi, ftfTglmasuk, cbAccspok, cbAccssuk, cbAcchut, btnTambah, btnEdit, btnHapus, btnBatal, tfCari, rdbtnNoId, rdbtnNama, rdbtnTanggalMasuk}));
		getContentPane().setLayout(groupLayout);
		
		pack();
	}
	
	private void formInternalFrameOpened(InternalFrameEvent evt) {
		try {
			if(CSE_Client.getConnStatus() == CSE_Client.CONNECTED_TO_SERVER) {
				setupInitFormState();
				populateTableData();
				getCbDivisiData();
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
	
	private void btnTambahActionPerformed(ActionEvent evt) {
		if(btnTambah.getText().equals("Tambah")) {
			setupInitFormState();
			btnTambah.setText("Simpan");
			ftfId.setEditable(true);
			tfNama.setEditable(true);
			cbDivisi.setEnabled(true);
			ftfTglmasuk.setEditable(true);
			ftfId.requestFocus();
			btnBatal.setEnabled(true);
		} else {
			try {
				if(ftfId.getText().length() != 0 &&
						tfNama.getText().length() != 0 &&
						cbDivisi.getSelectedIndex() != 0 &&
						checkTanggal(ftfTglmasuk.getText()) &&
						cbAccspok.getSelectedIndex() != 0 &&
						cbAcchut.getSelectedIndex() != 0 &&
						cbAccssuk.getSelectedIndex() != 0
						) {
					String id = ftfId.getText();
					String name = tfNama.getText();
					String divisi_id = NumberFormat.getIntegerInstance().format(cbDivisi.getSelectedIndex());
					String tglmasukText = ftfTglmasuk.getText();
					String tglmasuk = tglmasukText.substring(0,2) + "-" + tglmasukText.substring(3,5) + "-" + tglmasukText.substring(6); 
					String accspok_id = cbAccspok.getSelectedItem().toString(); 
					String acchut_id = cbAcchut.getSelectedItem().toString(); 
					String accssuk_id = cbAccssuk.getSelectedItem().toString();

					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//					prepare request to server 
					DocumentBuilder parser = factory.newDocumentBuilder();
					String uri = new String("http://" + Conf.getAppSrvHost() + ":" + Conf.getAppSrvSocket() + Conf.getAppSrvURL()  +
						"/DB/Anggota/dbOperation.jsp" +
						";jsessionid=" + CSE_Client.getConnData().getJsessid()) +  "?" +
						"id=" + id + "&" +
						"name=" + name + "&" +
						"divisi_id=" + divisi_id + "&" +
						"tglmasuk=" + tglmasuk + "&" +
						"accspok_id=" + accspok_id + "&" +
						"acchut_id=" + acchut_id + "&" +
						"accssuk_id=" + accssuk_id + "&" +
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
					} else {
						JOptionPane.showMessageDialog(this, messageStr, messageVal, JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(this, "Input tidak lengkap!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (SAXException | IOException | ParserConfigurationException e) {
				JOptionPane.showMessageDialog(this, "Unable to contact server", "Error", JOptionPane.ERROR_MESSAGE);
			} finally {
				setupInitFormState();
				try {
					populateTableData();
				} catch (ParserConfigurationException | SAXException
						| IOException e) {
					JOptionPane.showMessageDialog(this, "Unable to contact server", "Error", JOptionPane.ERROR_MESSAGE);
				}
				refreshTableModel();
			}
		}
	}
	
	private void btnEditActionPerformed(ActionEvent evt) {
		if(btnEdit.getText().equals("Edit")) {
			btnTambah.setEnabled(false);
			btnHapus.setEnabled(false);
			btnEdit.setEnabled(true);
			btnEdit.setText("Simpan");
			ftfId.setEditable(false);
			tfNama.setEditable(true);
			cbDivisi.setEnabled(true);
			ftfTglmasuk.setEditable(true);
			cbAcchut.setEnabled(true);
			cbAccspok.setEnabled(true);
			cbAccssuk.setEnabled(true);
			ftfId.requestFocus();
			btnBatal.setEnabled(true);
		} else {
			try {
				if(ftfId.getText().length() != 0 &&
						tfNama.getText().length() != 0 &&
						cbDivisi.getSelectedIndex() != 0 &&
						checkTanggal(ftfTglmasuk.getText()) &&
						cbAccspok.getSelectedIndex() != 0 &&
						cbAcchut.getSelectedIndex() != 0 &&
						cbAccssuk.getSelectedIndex() != 0
						) {
					String id = ftfId.getText();
					String name = tfNama.getText();
					String divisi_id = NumberFormat.getIntegerInstance().format(cbDivisi.getSelectedIndex());
					String tglmasukText = ftfTglmasuk.getText();
					String tglmasuk = tglmasukText.substring(0,2) + "-" + tglmasukText.substring(3,5) + "-" + tglmasukText.substring(6); 
					String accspok_id = cbAccspok.getSelectedItem().toString(); 
					String acchut_id = cbAcchut.getSelectedItem().toString(); 
					String accssuk_id = cbAccssuk.getSelectedItem().toString();

					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//					prepare request to server 
					DocumentBuilder parser = factory.newDocumentBuilder();
					String uri = new String("http://" + Conf.getAppSrvHost() + ":" + Conf.getAppSrvSocket() + Conf.getAppSrvURL()  +
						"/DB/Anggota/dbOperation.jsp" +
						";jsessionid=" + CSE_Client.getConnData().getJsessid()) +  "?" +
						"id=" + id + "&" +
						"name=" + name + "&" +
						"divisi_id=" + divisi_id + "&" +
						"tglmasuk=" + tglmasuk + "&" +
						"accspok_id=" + accspok_id + "&" +
						"acchut_id=" + acchut_id + "&" +
						"accssuk_id=" + accssuk_id + "&" +
						"dbRequest=update";

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
					} else {
						JOptionPane.showMessageDialog(this, messageStr, messageVal, JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(this, "Input tidak lengkap!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (SAXException | IOException | ParserConfigurationException e) {
				JOptionPane.showMessageDialog(this, "Unable to contact server", "Error", JOptionPane.ERROR_MESSAGE);
			} finally {
				setupInitFormState();
				try {
					populateTableData();
				} catch (ParserConfigurationException | SAXException
						| IOException e) {
					JOptionPane.showMessageDialog(this, "Unable to contact server", "Error", JOptionPane.ERROR_MESSAGE);
				}
				refreshTableModel();
			}
		}
	}
	
	private void btnHapusActionPerformed(ActionEvent evt) {
		btnTambah.setEnabled(false);
		btnEdit.setEnabled(false);
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
				if(ftfId.getText().length() != 0 ) {
					String id = ftfId.getText();

					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//					prepare request to server 
					DocumentBuilder parser = factory.newDocumentBuilder();
					String uri = new String("http://" + Conf.getAppSrvHost() + ":" + Conf.getAppSrvSocket() + Conf.getAppSrvURL()  +
						"/DB/Anggota/dbOperation.jsp" +
						";jsessionid=" + CSE_Client.getConnData().getJsessid()) +  "?" +
						"id=" + id + "&" +
						"dbRequest=delete";

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
					} else {
						JOptionPane.showMessageDialog(this, messageStr, messageVal, JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(this, "Input tidak lengkap!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (SAXException | IOException | ParserConfigurationException e) {
				JOptionPane.showMessageDialog(this, "Unable to contact server", "Error", JOptionPane.ERROR_MESSAGE);
			} finally {
				setupInitFormState();
				try {
					populateTableData();
				} catch (ParserConfigurationException | SAXException
						| IOException e) {
					JOptionPane.showMessageDialog(this, "Unable to contact server", "Error", JOptionPane.ERROR_MESSAGE);
				}
				refreshTableModel();
			}
		} else {
		btnTambah.setEnabled(true);
		btnEdit.setEnabled(true);
		}
	}
	
	private boolean checkTanggal(String tglblnthn) {
		try {
			int tgl = NumberFormat.getInstance().parse(tglblnthn.substring(0, 2)).intValue();
			int bln = NumberFormat.getInstance().parse(tglblnthn.substring(3, 5)).intValue();
			if(tgl>=0 && tgl<=31 && bln>=0 && bln<=12) {
				return true;
			}
			else return false;
		} catch (ParseException e) {
			return false;
		}
	}
	
	private void btnBatalActionPerformed(ActionEvent evt) {
		setupInitFormState();
	}
	
	private void cbDivisiActionPerformed(ActionEvent evt) {
		cbAccspok.removeAllItems();
		cbAccspok.addItem(new String());
		cbAcchut.removeAllItems();
		cbAcchut.addItem(new String());
		cbAccssuk.removeAllItems();
		cbAccssuk.addItem(new String());
		if(cbDivisi.getSelectedIndex() == 0) {
			cbAccspok.setEnabled(false);
			cbAcchut.setEnabled(false);
			cbAccssuk.setEnabled(false);
		} else {
			try {
				getCbAccspokData();
				getCbAcchutData();
				getCbAccssukData();
				cbAccspok.setEnabled(true);
				cbAcchut.setEnabled(true);
				cbAccssuk.setEnabled(true);
			} catch(ParserConfigurationException | SAXException | IOException e) {
				e.printStackTrace();
			}
		}
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

	private void refreshTableModel() {
		try {
			table.setColumnModel(new AnggotaColumnModel());
			table.setModel(new AnggotaTableModel());
		} catch (NullPointerException | ArrayIndexOutOfBoundsException npe) {
			String message = "Unable to achieve data.\nPlease reconnect to server";
			String title = "error";
			JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
//			setupTableData();
//			setupComboBoxesData();
		}
	}
	
	private void setupInitFormState() {
//		buttons
		btnTambah.setEnabled(true);
		btnTambah.setText("Tambah");
		btnEdit.setEnabled(false);
		btnEdit.setText("Edit");
		btnHapus.setEnabled(false);
		btnHapus.setText("Hapus");
		btnBatal.setEnabled(false);
		
//		panel cari
		rdbtnNoId.setSelected(true);
		tfCari.setText(new String());
		
//		form input
		ftfId.setText(new String());
		ftfId.setEditable(false);
		tfNama.setText(new String());
		tfNama.setEditable(false);
//		cbDivisi.removeAllItems();
//		cbDivisi.addItem(new String());
		cbDivisi.setSelectedIndex(0);
		cbDivisi.setEnabled(false);
		ftfTglmasuk.setText(new String());
		ftfTglmasuk.setEditable(false);
		cbAccspok.removeAllItems();
		cbAccspok.addItem(new String());
		cbAccspok.setSelectedIndex(0);
		cbAccspok.setEnabled(false);
		cbAcchut.removeAllItems();
		cbAcchut.addItem(new String());
		cbAcchut.setSelectedIndex(0);
		cbAcchut.setEnabled(false);
		cbAccssuk.removeAllItems();
		cbAccssuk.addItem(new String());
		cbAccssuk.setSelectedIndex(0);
		cbAccssuk.setEnabled(false);
		
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
					new String()}
			};
	}
	
	private void emptyTableData(int i, int j) {
		tableData = new Object[i][j];
	}
	
	private void populateTableData() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = factory.newDocumentBuilder();
		String strUri = new String("http://" + Conf.getAppSrvHost() + ":" + Conf.getAppSrvSocket() + Conf.getAppSrvURL()  +
				"/DB/Anggota/selectDataAll.jsp;jsessionid=" + CSE_Client.getConnData().getJsessid());

		try {
			Document document = parser.parse(strUri);
			try {
				Element dbdata = document.getDocumentElement();
				NodeList datarows = dbdata.getElementsByTagName("datarow");
				
				if(datarows.getLength() > 0) {
					emptyTableData(datarows.getLength(), 8);
				}
				else {
					emptyTableData();
				}
				
				Element datarow;
				
				for(int i = 0; i < datarows.getLength(); i++) {
					datarow = (Element) datarows.item(i);
					tableData[i][0] = i+1;
					tableData[i][1] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "id"));
					tableData[i][2] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "name"));
					tableData[i][3] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "divisi_id"));
					tableData[i][4] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "tglmasuk"));
					tableData[i][5] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "accspok_id"));
					tableData[i][6] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "acchut_id"));
					tableData[i][7] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "accssuk_id"));
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
	
	private void getCbDivisiData() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = factory.newDocumentBuilder();
		String strUri = new String("http://" + Conf.getAppSrvHost() + ":" + Conf.getAppSrvSocket() + Conf.getAppSrvURL()  +
				"/DB/Anggota/selectDivisi.jsp;jsessionid=" + CSE_Client.getConnData().getJsessid());

		try {
			Document document = parser.parse(strUri);
			try {
				Element dbdata = document.getDocumentElement();
				NodeList datarows = dbdata.getElementsByTagName("datarow");
				
				cbDivisiData = new Object[datarows.getLength()][2];
				
				Element datarow;
				
				for(int i = 0; i < datarows.getLength(); i++) {
					datarow = (Element) datarows.item(i);
					cbDivisiData[i][0] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "id"));
					cbDivisiData[i][1] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "name"));
					cbDivisi.addItem(cbDivisiData[i][1].toString());
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
	
	private void getCbAccspokData() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = factory.newDocumentBuilder();
		String strUri = new String("http://" + Conf.getAppSrvHost() + ":" + Conf.getAppSrvSocket() + Conf.getAppSrvURL()  +
				"/DB/AccCode/selectAccspok.jsp;jsessionid=" + CSE_Client.getConnData().getJsessid() + "?divisi_id=" + cbDivisi.getSelectedIndex());

		try {
			Document document = parser.parse(strUri);
			try {
				Element dbdata = document.getDocumentElement();
				NodeList datarows = dbdata.getElementsByTagName("datarow");
				
				cbAccspokData = new Object[datarows.getLength()];
				
				Element datarow;
				
				for(int i = 0; i < datarows.getLength(); i++) {
					datarow = (Element) datarows.item(i);
					cbAccspokData[i] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "id"));
					cbAccspok.addItem(cbAccspokData[i].toString());
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
	
	private void getCbAcchutData() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = factory.newDocumentBuilder();
		String strUri = new String("http://" + Conf.getAppSrvHost() + ":" + Conf.getAppSrvSocket() + Conf.getAppSrvURL()  +
				"/DB/AccCode/selectAcchut.jsp;jsessionid=" + CSE_Client.getConnData().getJsessid() + "?divisi_id=" + cbDivisi.getSelectedIndex());

		try {
			Document document = parser.parse(strUri);
			try {
				Element dbdata = document.getDocumentElement();
				NodeList datarows = dbdata.getElementsByTagName("datarow");
				
				cbAcchutData = new Object[datarows.getLength()];
				
				Element datarow;
				
				for(int i = 0; i < datarows.getLength(); i++) {
					datarow = (Element) datarows.item(i);
					cbAcchutData[i] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "id"));
					cbAcchut.addItem(cbAcchutData[i].toString());
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
	
	private void getCbAccssukData() throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = factory.newDocumentBuilder();
		String strUri = new String("http://" + Conf.getAppSrvHost() + ":" + Conf.getAppSrvSocket() + Conf.getAppSrvURL()  +
				"/DB/AccCode/selectAccssuk.jsp;jsessionid=" + CSE_Client.getConnData().getJsessid() + "?divisi_id=" + cbDivisi.getSelectedIndex());

		try {
			Document document = parser.parse(strUri);
			try {
				Element dbdata = document.getDocumentElement();
				NodeList datarows = dbdata.getElementsByTagName("datarow");
				
				cbAccssukData = new Object[datarows.getLength()];
				
				Element datarow;
				
				for(int i = 0; i < datarows.getLength(); i++) {
					datarow = (Element) datarows.item(i);
					cbAccssukData[i] = DOMUtil.getSimpleElementText(DOMUtil.getFirstElement(datarow, "id"));
					cbAccssuk.addItem(cbAccssukData[i].toString());
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

	private String getArrDivisiData(String idx) {
		String divisiName = null;
		for(int i = 0; i < cbDivisiData.length; i++) {
			if(cbDivisiData[i][0].toString().equals(idx)) {
				divisiName = cbDivisiData[i][1].toString();
				break;
			}
		}
		return divisiName;
	}
	
	private void checkComboAcc() {
		if(!(selAcchut.equals(new String())) || !(selAccspok.equals(new String())) || !(selAccssuk.equals(new String()))) {
            cbAcchut.removeItem(selAcchut);
            cbAccspok.removeItem(selAccspok);
            cbAccssuk.removeItem(selAccssuk);
            selAcchut = new String();
            selAccspok = new String();
            selAccssuk = new String();
            btnEdit.setEnabled(false);
            btnHapus.setEnabled(false);
		}
	}
}
