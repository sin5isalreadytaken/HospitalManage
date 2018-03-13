import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.AbstractButton;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JEditorPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JToggleButton;
import java.awt.Toolkit;


public class DoctorF extends JFrame {

	private JPanel contentPane;
	private JTextField tfNum;
	private DBHelper dbHelper = new DBHelper();
	private ResultSet rs = null;
	private int Dno;
	private String Dname;
	private int Pno;
	private String mhFilePath;
	private String dirPath = "D:\\";
	private JList patientList;
	private JScrollPane scrollPane_list;
	private JLabel lblPno;
	private JTextArea taMh;
	private JTextField tfSearch;
	private JComboBox cbMedicine;
	private JComboBox cbQuantifier;
	private JTable tbMedicine;
	private JScrollPane scrollPane_M;
	private DefaultTableModel dtm;
	private JButton btnSearchMedicine;
	private JButton btnDeleteMedicine;
	private JButton btnAddMedicine;
	private JButton btnGo;
	private JButton btnRefresh;
	private JButton btnUpdateMh;
	private JToggleButton btnHospitalized;
	private JTextField tfUsage;
	private JEditorPane etNewMh;
	

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorF frame = new DoctorF(1, "1");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DoctorF(int Dno) {
		setIconImage(Toolkit.getDefaultToolkit().getImage("img\\icon.png"));
		this.Dno = Dno;
		this.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				rs = dbHelper.getResultSet("SELECT usernum FROM user WHERE userid='" + Dno + "'");
				int userNum = 0;
				try{
					rs.next();
					userNum = rs.getInt(1);
					userNum--;
					if (userNum < 0){
						userNum = 0;
					}
					dbHelper.execute("UPDATE user SET usernum = '"+userNum + "' WHERE userid = '" + Dno + "'");
				}catch(SQLException e){
					System.out.print(e);
				}
				dbHelper.disConn();
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		rs = dbHelper.getResultSet("SELECT Dname FROM doctor WHERE Dno='" + Dno + "'");
		String name = "";
		try {
			rs.next();
			name = rs.getString(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.Dname = name;
		this.setTitle(Dno + " " + Dname);
		
		initWidgets();
		initListeners();
		refreshPatientList();
		refreshMedicineList();		
		resetWidgets();
	}
	
	public void initWidgets(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 842, 558);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setVisible(true);
		
		btnGo = new JButton("\u770B\u75C5");
		btnGo.setBounds(10, 10, 93, 23);
		contentPane.add(btnGo);
		
		btnRefresh = new JButton("\u5237\u65B0");
		btnRefresh.setBounds(10, 259, 93, 23);
		contentPane.add(btnRefresh);
		
		lblPno = new JLabel();
		lblPno.setText("\u75C5\u53F7");
		lblPno.setBounds(127, 14, 66, 15);
		contentPane.add(lblPno);
		
		btnUpdateMh = new JButton("\u66F4\u65B0");
		btnUpdateMh.setBounds(366, 427, 66, 23);
		contentPane.add(btnUpdateMh);
		
		tfNum = new JTextField();
		tfNum.setBounds(127, 74, 156, 21);
		contentPane.add(tfNum);
		tfNum.setColumns(10);
		
		scrollPane_M = new JScrollPane();
		scrollPane_M.setBounds(127, 136, 305, 146);
		contentPane.add(scrollPane_M);
		
		btnAddMedicine = new JButton("\u6DFB\u52A0");
		btnAddMedicine.setBounds(367, 73, 66, 23);
		contentPane.add(btnAddMedicine);
		
		btnDeleteMedicine = new JButton("\u5220\u9664");
		btnDeleteMedicine.setBounds(367, 103, 66, 23);
		contentPane.add(btnDeleteMedicine);
		
		
		cbMedicine = new JComboBox();
		cbMedicine.setBounds(127, 43, 230, 21);
		contentPane.add(cbMedicine);
		
		scrollPane_list = new JScrollPane();
		scrollPane_list.setBounds(10, 43, 93, 206);
		contentPane.add(scrollPane_list);
		
		JScrollPane scrollPane_Mh = new JScrollPane();
		scrollPane_Mh.setBounds(442, 9, 374, 500);
		contentPane.add(scrollPane_Mh);
		
		taMh = new JTextArea();
		taMh.setEditable(false);
		scrollPane_Mh.setViewportView(taMh);
		
		JScrollPane scrollPane_newMh = new JScrollPane();
		scrollPane_newMh.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_newMh.setBounds(10, 292, 347, 217);
		contentPane.add(scrollPane_newMh);
		
		etNewMh = new JEditorPane();
		scrollPane_newMh.setViewportView(etNewMh);
		
		tfSearch = new JTextField();
		tfSearch.setBounds(367, 10, 66, 23);
		contentPane.add(tfSearch);
		tfSearch.setColumns(10);
		
		btnSearchMedicine = new JButton("\u67E5\u627E");
		btnSearchMedicine.setBounds(367, 42, 66, 23);
		contentPane.add(btnSearchMedicine);
		
		cbQuantifier = new JComboBox();
		cbQuantifier.setBounds(293, 74, 64, 21);
		contentPane.add(cbQuantifier);
		
		cbQuantifier.addItem("盒");
		cbQuantifier.addItem("粒");
		cbQuantifier.addItem("瓶");
		
		tfUsage = new JTextField();
		tfUsage.setBounds(127, 105, 230, 21);
		contentPane.add(tfUsage);
		tfUsage.setColumns(10);
		
		btnHospitalized = new JToggleButton("\u4F4F\u9662");
		btnHospitalized.setBounds(366, 394, 66, 23);
		contentPane.add(btnHospitalized);
	}
	
	public void initListeners(){
		btnGo.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						resetWidgets();
						if(patientList.isSelectionEmpty()){
							return;
						}
						Pno = Integer.parseInt((String)patientList.getSelectedValue());
						lblPno.setText(Pno + "");
						dbHelper.execute("DELETE FROM register WHERE Pno=" + Pno);
						rs = dbHelper.getResultSet("SELECT * FROM patient WHERE Pno=" + Pno);
						try {
							rs.next();
							mhFilePath = rs.getString(5);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						refreshPatientList();
						refreshMh();
					}
				});
		
		btnRefresh.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						refreshPatientList();
					}
		});
		
		
		btnSearchMedicine.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						if(!tfSearch.getText().trim().equals("")){
							for(int i = 0; i < cbMedicine.getItemCount(); i++){
								if(((String)cbMedicine.getItemAt(i)).contains(tfSearch.getText())){
									cbMedicine.setSelectedIndex(i);
									break;
								}
							}
						}
					}			
				});
		
		btnDeleteMedicine.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						if(tbMedicine.getSelectedRow() != -1){
							dtm.removeRow(tbMedicine.getSelectedRow());
						}
					}
				});
		
		btnAddMedicine.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						if(!tfNum.getText().trim().equals("") && !tfUsage.getText().trim().equals("")){
							String medicineStr = (String)cbMedicine.getSelectedItem();
							int tfnum = Integer.parseInt(tfNum.getText());
							for (int i = 0; i < dtm.getRowCount() ;i++){
								if (medicineStr.equals(dtm.getValueAt(i, 0))){
									tfnum += Integer.parseInt((String) dtm.getValueAt(i, 1));
								}
							}
							String mno[] = medicineStr.split("_");
							String Mno = mno[0];
							rs = dbHelper.getResultSet("SELECT Mnum FROM prescription WHERE Mno = '" + Mno + "'");
							try {
								while(rs.next()){
									tfnum += rs.getInt(1);
								}
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							rs = dbHelper.getResultSet("SELECT Mnum FROM medicine WHERE Mno = '" + Mno + "'");
							try {
								rs.next();
								if (tfnum > rs.getInt(1)){
									JOptionPane.showMessageDialog(null, "药品数量不足", "Error", JOptionPane.INFORMATION_MESSAGE);
									return;
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
							dtm.addRow(new Object[]{medicineStr, tfNum.getText(), (String)cbQuantifier.getSelectedItem(), tfUsage.getText()});
						}
					}
				});
		
		
		
		btnUpdateMh.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						if(!lblPno.getText().equals("病号")){
							File mhFile;
							if(mhFilePath == ""){
								mhFile = new File(dirPath + lblPno.getText() + ".txt");
								try {
									mhFile.createNewFile();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							else{
								mhFile = new File(mhFilePath);
							}
							Date date = new Date();
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String time = format.format(date);
							try {
								FileWriter fw = new FileWriter(mhFilePath, true);
								BufferedWriter bw = new BufferedWriter(fw);
								bw.write(time + "\r\n");
								bw.write("医师： " + Dname + "\t " + "医师编号： " + Dno + "\r\n");
								bw.write("诊断结果：" + "\r\n");
								bw.write(etNewMh.getText() + "\r\n");
								bw.write("药方：" + "\r\n");
								if(tbMedicine.getRowCount() == 0){
									bw.write("\r\n");
								}
								else{
									for(int i = 0; i < tbMedicine.getRowCount(); i++){
										bw.write((String)tbMedicine.getValueAt(i, 0) + "\t" +
													(String)tbMedicine.getValueAt(i, 1) + "\t" +
														(String)tbMedicine.getValueAt(i, 2) + "\t" +
															(String)tbMedicine.getValueAt(i, 3) + "\r\n");
									}
								}
								bw.write("是否住院： ");
								if(btnHospitalized.isSelected()){
									bw.write("是" + "\r\n");
									rs = dbHelper.getResultSet("SELECT Pno FROM hospitalize");
									try {
										while(rs.next()){
											if(rs.getString(1).equals(Pno + "")){
												JOptionPane.showMessageDialog(null, "病人已入院", "Error", JOptionPane.INFORMATION_MESSAGE);
												return;
											}
										}
									} catch (SQLException e) {
										e.printStackTrace();
									}
									rs = dbHelper.getResultSet("SELECT Pno FROM hoslist");
									try {
										while(rs.next()){
											if(rs.getString(1).equals(Pno + "")){
												return;
											}
										}
									} catch (SQLException e) {
										e.printStackTrace();
									}
									dbHelper.execute("INSERT INTO hoslist (Dno,Pno,Hospitalized) VALUES(" + Dno + ", " + Pno + ", 1)");
								}
								else{
									bw.write("否" + "\r\n");
									//dbHelper.execute("INSERT INTO hoslist (Dno,Pno,Hospitalized) VALUES(" + Dno + "," + Pno + ",0)");
								}
					            bw.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
							for(int i = 0; i < tbMedicine.getRowCount(); i++){
								String tmpMedicine = (String)tbMedicine.getValueAt(i, 0);
								String[] strs = tmpMedicine.split("_");
								dbHelper.execute("INSERT INTO prescription SET Pno = " + Pno + ", Mno = " + strs[0] + ", Mnum = " + tbMedicine.getValueAt(i, 1));
							}
							refreshPatientList();
							refreshMh();
							unableWidgets();
						}
					}
				});
		
	}
	
	public void resetWidgets(){
		taMh.setText("");
		dtm = new DefaultTableModel(null, new String[]{"药名", "数量", "量级", "用法"});
		tbMedicine = new JTable(dtm);
		tbMedicine.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		scrollPane_M.setViewportView(tbMedicine);
		mhFilePath = "";
		cbMedicine.setSelectedIndex(-1);
		tfNum.setText("");
		cbQuantifier.setSelectedIndex(-1);
		tfUsage.setText("");
		tfSearch.setText("");
		etNewMh.removeAll();
		
		lblPno.setEnabled(true);
		cbMedicine.setEnabled(true);
		tfNum.setEnabled(true);
		cbQuantifier.setEnabled(true);
		tfUsage.setEnabled(true);
		tfSearch.setEnabled(true);
		btnSearchMedicine.setEnabled(true);
		btnAddMedicine.setEnabled(true);
		btnDeleteMedicine.setEnabled(true);
		tbMedicine.setEnabled(true);
		etNewMh.setEnabled(true);
		btnUpdateMh.setEnabled(true);
	}
	
	public void unableWidgets(){
		lblPno.setEnabled(false);
		cbMedicine.setEnabled(false);
		tfNum.setEnabled(false);
		cbQuantifier.setEnabled(false);
		tfUsage.setEnabled(false);
		tfSearch.setEnabled(false);
		btnSearchMedicine.setEnabled(false);
		btnAddMedicine.setEnabled(false);
		btnDeleteMedicine.setEnabled(false);
		tbMedicine.setEnabled(false);
		etNewMh.setEnabled(false);
		btnUpdateMh.setEnabled(false);		
	}
	
	public void getMhFilePath(){
		rs = dbHelper.getResultSet("SELECT * FROM patient WHERE Pno=" + Pno);
		try {
			rs.next();
			mhFilePath = rs.getString(5);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void refreshMedicineList(){
		rs = dbHelper.getResultSet("SELECT * FROM medicine");
		String Mno, Mname;
		cbMedicine.removeAllItems();
		try {
			while(rs.next()){
				Mno = rs.getString(1);
				Mname = rs.getString(2);
				cbMedicine.addItem(Mno + "_" + Mname);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void refreshMh(){
		getMhFilePath();
		if(mhFilePath.equals("")){
			taMh.append("暂无本院病历信息" + "\r\n");
		}
		else{
			try {
				BufferedReader br = new BufferedReader(new FileReader(mhFilePath));
				String str = "";
				while((str = br.readLine()) != null){
					taMh.append(str + "\r\n");
				}
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void refreshPatientList(){
		DefaultListModel dlm = new DefaultListModel();
		int Ono = 1;
		rs = dbHelper.getResultSet("SELECT Ono FROM doctor WHERE Dno = "+Dno);
		try {
			rs.next();
			Ono = rs.getInt(1);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		rs = dbHelper.getResultSet("SELECT * FROM register WHERE Ono=" + Ono + " ORDER BY time");
		try {
			while(rs.next()){
				dlm.addElement(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		patientList = new JList();
		patientList.setModel(dlm);
		scrollPane_list.setViewportView(patientList);
	}
}
