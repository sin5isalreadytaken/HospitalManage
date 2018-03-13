import java.io.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JToggleButton;
import javax.swing.JTabbedPane;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import java.awt.Font;
import java.awt.Toolkit;

public class ManageF extends JFrame {

	private JPanel contentPane;
	private DBHelper dbHelper = new DBHelper();
	private ResultSet rs = null;
	private JTabbedPane tabbedPane;
	private JPanel panel_Doc, panel_Nur, panel_Pat, panel_Med, panel_Sic, panel_Off, panel_Usr;
	private JTextField tfName_Doc, tfOption_Doc;
	private JTextField tfName_Nur, tfOption_Nur;
	private JTextField tfName_Pat, tfOption_Pat, tfPno_Pat;
	private JTextField tfName_Med, tfOption_Med, tfMno_Med, tfMnum_Med, tfPrice_Med;
	private JTextField tfOption_Sic, tfRno_Sic, tfBnum_Sic, tfPrice_Sic;
	private JTextField tfOption_Off, tfOno_Off, tfOname_Off;
	private JTextField tfQuery_Usr;
	private JTable tbDocs;
	private JTable tbNurs;
	private JTable tbPats;
	private JTable tbMeds;
	private JTable tbSics;
	private JTable tbOffs;
	private JTable tbUsrs;
	private JRadioButton rbMan_Doc, rbWomen_Doc;
	private JRadioButton rbMan_Nur, rbWomen_Nur;
	private JRadioButton rbMan_Pat, rbWomen_Pat;
	private JButton btnGenerate_Doc, btnEntry_Doc, btnQuery_Doc, btnRevise_Doc, btnLeave_Doc;
	private JButton btnGenerate_Nur, btnEntry_Nur, btnQuery_Nur, btnRevise_Nur, btnLeave_Nur;
	private JButton	btnGenerate_Pat, btnEntry_Pat, btnQuery_Pat, btnRevise_Pat, btnLeave_Pat;
	private JButton btnEntry_Med, btnQuery_Med, btnRevise_Med, btnLeave_Med;
	private JButton btnEntry_Sic, btnQuery_Sic, btnRevise_Sic, btnLeave_Sic;
	private JButton btnEntry_Off, btnQuery_Off, btnRevise_Off, btnLeave_Off;
	private JButton btnQuery_Usr, btnRevise_Usr;
	private JComboBox cbAge_Doc, cbTitle_Doc, cbOno_Doc, cbOption_Doc;
	private JComboBox cbAge_Nur, cbTitle_Nur, cbOption_Nur;
	private JComboBox cbAge_Pat, cbOption_Pat;
	private JComboBox cbOption_Med;
	private JComboBox cbOption_Sic;
	private JComboBox cbOption_Off, cbOtype_Off;
	private JLabel lblDno_Doc, lblPwd_Doc, lblDocCount_Doc, lblSelectedCount_Doc;
	private JLabel lblNno_Nur, lblNurCount_Nur, lblSelectedCount_Nur; 
	private JLabel lblMhState_Pat, lblPatCount_Pat, lblSelectedCount_Pat;
	private JLabel lblMedCount_Med, lblSelectedCount_Med;
	private JLabel lblSicCount_Sic, lblSelectedCount_Sic;
	private JLabel lblOffCount_Off, lblSelectedCount_Off;
	private ButtonGroup rbGroup_Doc;
	private ButtonGroup rbGroup_Nur;
	private ButtonGroup rbGroup_Pat;
	private boolean reviseFlag_Doc = false;
	private boolean reviseFlag_Nur = false;
	private boolean reviseFlag_Pat = false;
	private boolean reviseFlag_Med = false;
	private boolean reviseFlag_Sic = false;
	private boolean reviseFlag_Off = false;
	private boolean reviseFlag_Usr = false;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageF frame = new ManageF();
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
	public ManageF() {
		this.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				rs = dbHelper.getResultSet("SELECT usernum FROM user WHERE userid='admin'");
				int userNum = 0;
				try{
					rs.next();
					userNum = rs.getInt(1);
					userNum--;
					if (userNum < 0){
						userNum = 0;
					}
					dbHelper.execute("UPDATE user SET usernum = '"+userNum + "' WHERE userid = 'admin'");
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
		initWidgets();
	}
	
	public void initWidgets(){
		setIconImage(Toolkit.getDefaultToolkit().getImage("Img\\icon.png"));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("管理系统");
		//this.setUndecorated(true);
		//this.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
		//this.setLocationRelativeTo(null);
		setBounds(100, 100, 909, 380);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setVisible(true);
		
		tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setFont(new Font("SimSun", Font.PLAIN, 18));
		tabbedPane.setBounds(0, 0, 903, 351);
		contentPane.add(tabbedPane);
		
		initDocPanel();
		
		initNurPanel();
		
		initPatPanel();
		
		initMedPanel();
		
		initSicPanel();
		
		initOffPanel();
		
		initUsrPanel();
	}
	
	public void initUsrPanel(){
		panel_Usr = new JPanel();
		panel_Usr.setBackground(Color.WHITE);
		tabbedPane.addTab("\u7528\u6237\u7ba1\u7406", null, panel_Usr, null);
		panel_Usr.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(108, 10, 408, 326);
		panel_Usr.add(scrollPane);
		
		tbUsrs = new JTable(){
			public boolean isCellEditable(int row, int column){
				if(column >= 1 && reviseFlag_Usr){
					return true;
				}
				else{
					return false;
				}
			}
		};
		tbUsrs.setFillsViewportHeight(true);
		scrollPane.setViewportView(tbUsrs);
		
		btnQuery_Usr = new JButton("\u67E5\u8BE2");
		btnQuery_Usr.setBackground(Color.WHITE);
		btnQuery_Usr.setBounds(526, 39, 93, 23);
		panel_Usr.add(btnQuery_Usr);
		
		btnRevise_Usr = new JButton("\u4FEE\u6539");
		btnRevise_Usr.setBackground(Color.WHITE);
		btnRevise_Usr.setBounds(526, 72, 93, 23);
		panel_Usr.add(btnRevise_Usr);
		
		tfQuery_Usr = new JTextField();
		tfQuery_Usr.setBounds(526, 8, 93, 21);
		panel_Usr.add(tfQuery_Usr);
		tfQuery_Usr.setColumns(10);
		
		JButton btnReset = new JButton("\u91CD\u7F6E");
		btnReset.setBackground(Color.WHITE);
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbHelper.execute("UPDATE user SET usernum = '0'");
				refreshTbUsrs();
			}
		});
		btnReset.setBounds(526, 105, 93, 23);
		panel_Usr.add(btnReset);
		
		initUsrListeners();
		refreshTbUsrs();
	}
	
	
	public void initUsrListeners(){
		btnQuery_Usr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!tfQuery_Usr.getText().trim().equals("")){
					String queryStr = tfQuery_Usr.getText();
					tbUsrs.setRowSelectionInterval(0, tbUsrs.getRowCount() - 1);
					for(int i = 0; i < tbUsrs.getRowCount(); i++){
						if(!(tbUsrs.getValueAt(i, 1) + "").contains(queryStr)){
							tbUsrs.removeRowSelectionInterval(i, i);
						}
					}
				}
			}
		});
		btnRevise_Usr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] selectedRows = tbUsrs.getSelectedRows();
				if(selectedRows.length == 1 && btnRevise_Usr.getText().equals("修改")){
					reviseFlag_Usr = true;
					int selectedRow = tbUsrs.getSelectedRow();
					btnRevise_Usr.setText("保存");
					DefaultTableModel dtm = new DefaultTableModel(null, new String[]{"用户名", "密码", "最大在线人数", "当前在线人数"});
					dtm.addRow(new Object[]{tbUsrs.getValueAt(selectedRow, 0), 
							tbUsrs.getValueAt(selectedRow, 1), 
							tbUsrs.getValueAt(selectedRow, 2),
							tbUsrs.getValueAt(selectedRow, 3)});
					tbUsrs.setModel(dtm);
				}
				else{
					String selectedUsr = tbUsrs.getValueAt(0, 0) + "";
					String str1 = tbUsrs.getValueAt(0, 1) + "";
					String str2 = tbUsrs.getValueAt(0, 2) + "";
					dbHelper.execute("UPDATE user SET pwd='" + tbUsrs.getValueAt(0, 1) + "', " +
								"usermax='" + tbUsrs.getValueAt(0, 2) + "'," +
								"usernum='" + tbUsrs.getValueAt(0, 3) + "'" +
								" WHERE userid='" + selectedUsr + "'");
					btnRevise_Usr.setText("修改");
					reviseFlag_Usr = false;
					refreshTbUsrs();
				}
			}
		});
	}
	
	
	public void refreshTbUsrs(){
		rs = dbHelper.getResultSet("SELECT * FROM user");
		DefaultTableModel dtm = new DefaultTableModel(null, new String[]{"用户名", "用户密码", "最大在线人数", "当前在线人数"});
		try {
			while(rs.next()){
				dtm.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4)});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tbUsrs.setModel(dtm);
	}
	
	public void initOffPanel(){
		panel_Off = new JPanel();
		panel_Off.setBackground(Color.WHITE);
		tabbedPane.addTab("\u79d1\u5ba4\u7ba1\u7406", null, panel_Off, null);
		panel_Off.setLayout(null);
		
		JLabel label_4 = new JLabel("\u79D1\u5BA4\u53F7");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(21, 40, 70, 15);
		panel_Off.add(label_4);
		
		JLabel label_2_1 = new JLabel("\u79D1\u5BA4\u540D");
		label_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_2_1.setBounds(21, 119, 70, 15);
		panel_Off.add(label_2_1);
		
		btnEntry_Off = new JButton("\u5F55\u5165");
		btnEntry_Off.setBackground(Color.WHITE);
		btnEntry_Off.setBounds(21, 300, 182, 23);
		panel_Off.add(btnEntry_Off);
		
		btnQuery_Off = new JButton("\u67E5\u8BE2");
		btnQuery_Off.setBackground(Color.WHITE);
		btnQuery_Off.setBounds(478, 20, 93, 23);
		panel_Off.add(btnQuery_Off);
		
		btnRevise_Off = new JButton("\u4FEE\u6539");
		btnRevise_Off.setBackground(Color.WHITE);
		btnRevise_Off.setBounds(581, 20, 93, 23);
		panel_Off.add(btnRevise_Off);
		
		tfOption_Off = new JTextField();
		tfOption_Off.setColumns(10);
		tfOption_Off.setBounds(345, 21, 123, 21);
		panel_Off.add(tfOption_Off);
		
		cbOption_Off = new JComboBox();
		cbOption_Off.setBackground(Color.WHITE);
		cbOption_Off.setBounds(253, 21, 82, 21);
		panel_Off.add(cbOption_Off);
		
		JScrollPane scrollPane_Offs = new JScrollPane();
		scrollPane_Offs.setBounds(253, 53, 524, 245);
		panel_Off.add(scrollPane_Offs);
		
		tbOffs = new JTable(){
			public boolean isCellEditable(int rowIndex, int columnIndex) {
		        if(columnIndex >= 1 && reviseFlag_Off){
		        	return true;
		        }
		        else{
		        	return false;
		        }
		    }
		};
		tbOffs.setFillsViewportHeight(true);
		tbOffs.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane_Offs.setViewportView(tbOffs);
		
		btnLeave_Off = new JButton("\u5220\u9664");
		btnLeave_Off.setBackground(Color.WHITE);
		btnLeave_Off.setBounds(684, 20, 93, 23);
		panel_Off.add(btnLeave_Off);
		
		lblOffCount_Off = new JLabel("\u603B\u8BA1\uFF1A   0   \u9879");
		lblOffCount_Off.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOffCount_Off.setBounds(684, 308, 93, 15);
		panel_Off.add(lblOffCount_Off);
		
		lblSelectedCount_Off = new JLabel("");
		lblSelectedCount_Off.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSelectedCount_Off.setBounds(581, 308, 93, 15);
		lblSelectedCount_Off.setVisible(false);
		panel_Off.add(lblSelectedCount_Off);
		
		JLabel label_3_1 = new JLabel("\u79D1\u5BA4\u7C7B\u578B");
		label_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_3_1.setBounds(21, 202, 70, 15);
		panel_Off.add(label_3_1);
		
		tfOno_Off = new JTextField();
		tfOno_Off.setColumns(10);
		tfOno_Off.setBounds(109, 37, 94, 21);
		panel_Off.add(tfOno_Off);
		
		tfOname_Off = new JTextField();
		tfOname_Off.setBounds(109, 116, 94, 21);
		panel_Off.add(tfOname_Off);
		tfOname_Off.setColumns(10);
		
		cbOtype_Off = new JComboBox();
		cbOtype_Off.setBackground(Color.WHITE);
		cbOtype_Off.setBounds(109, 199, 94, 21);
		panel_Off.add(cbOtype_Off);
	
		initOffCbs();
		initOffListeners();
		refreshTbOffs();
	}
	
	public void initOffCbs(){
		cbOtype_Off.addItem("呼吸内科");
		cbOtype_Off.addItem("消化内科");
		cbOtype_Off.addItem("神经内科");
		cbOtype_Off.addItem("心血管内科");
		cbOtype_Off.addItem("内分泌科");
		cbOtype_Off.addItem("普通外科");
		cbOtype_Off.addItem("神经外科");
		cbOtype_Off.addItem("骨外科");
		cbOtype_Off.addItem("妇产科");
		cbOtype_Off.addItem("男科");
		cbOtype_Off.addItem("五官科");
		cbOtype_Off.addItem("皮肤科");
		cbOtype_Off.addItem("心理咨询科 ");
		cbOtype_Off.addItem("体检科");
		
		cbOption_Off.addItem("科室号");
		cbOption_Off.addItem("科室名");
		cbOption_Off.addItem("科室类型");
		cbOtype_Off.setSelectedIndex(-1);
		cbOption_Off.setSelectedIndex(-1);
	}
	
	public void initOffListeners(){
		btnEntry_Off.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!tfOno_Off.getText().trim().equals("") && !tfOname_Off.getText().trim().equals("") && cbOtype_Off.getSelectedIndex() != -1){
					if(!isDigit(tfOno_Off.getText())){
						JOptionPane.showMessageDialog(null, "请输入正确科室号", "Error", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					boolean findFlag = false;
					rs = dbHelper.getResultSet("SELECT Ono FROM office");
					try {
						while(rs.next()){
							if(rs.getString(1).equals(tfOno_Off.getText())){
								findFlag = true;
								break;
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if(findFlag){
						JOptionPane.showMessageDialog(null, "科室号已存在", "Error", JOptionPane.INFORMATION_MESSAGE);
					}
					else{
						dbHelper.execute("INSERT INTO office VALUES(" + tfOno_Off.getText() + ",' " + tfOname_Off.getText() + "', '"  +  cbOtype_Off.getSelectedItem() + "')");
						tfOno_Off.setText("");
						tfOname_Off.setText("");
						cbOtype_Off.setSelectedIndex(-1);
						refreshTbOffs();
					}
				}
			}
		});

		btnQuery_Off.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!tfOption_Off.getText().trim().equals("")){
					String queryStr = tfOption_Off.getText();
					int selectedCount = tbOffs.getRowCount();
					tbOffs.setRowSelectionInterval(0, tbOffs.getRowCount() - 1);
					switch(cbOption_Off.getSelectedIndex()){
					case -1:
						tbOffs.removeRowSelectionInterval(0, tbOffs.getRowCount() - 1);
						return;
					case 1://科室名
						for(int i = 0; i < tbOffs.getRowCount(); i++){
							if(!(tbOffs.getValueAt(i, 1) + "").contains(queryStr)){
								tbOffs.removeRowSelectionInterval(i, i);
								selectedCount--;
							}
						}
						break;
					case 0://科室号
						for(int i = 0; i < tbOffs.getRowCount(); i++){
							if(!(tbOffs.getValueAt(i, 0) + "").contains(queryStr)){
								tbOffs.removeRowSelectionInterval(i, i);
								selectedCount--;
							}
						}
						break;
					case 2://科室类型
						for(int i = 0; i < tbOffs.getRowCount(); i++){
							if(!(tbOffs.getValueAt(i, 2) + "").contains(queryStr)){
								tbOffs.removeRowSelectionInterval(i, i);
								selectedCount--;
							}
						}
						break;
					}
					lblSelectedCount_Off.setText("找到：   " + selectedCount + "   个");
					lblSelectedCount_Off.setVisible(true);
				}
			}
		});
		btnRevise_Off.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] selectedRows = tbOffs.getSelectedRows();
				if(selectedRows.length == 1 && btnRevise_Off.getText().equals("修改")){
					reviseFlag_Off = true;
					int selectedRow = tbOffs.getSelectedRow();
					btnRevise_Off.setText("保存");
					DefaultTableModel dtm = new DefaultTableModel(null, new String[]{"科室号", "科室名", "科室类型"});
					dtm.addRow(new Object[]{tbOffs.getValueAt(selectedRow, 0), 
							tbOffs.getValueAt(selectedRow, 1), 
							tbOffs.getValueAt(selectedRow, 2)});
					tbOffs.setModel(dtm);
				}
				else{
					String selectedOno = tbOffs.getValueAt(0, 0) + "";
					dbHelper.execute("UPDATE office SET Oname='" + tbOffs.getValueAt(0, 1) + "', " +
								"Otype='" + tbOffs.getValueAt(0, 2) + "'" +
								" WHERE Ono=" + selectedOno);
					btnRevise_Off.setText("修改");
					reviseFlag_Off = false;
					refreshTbOffs();
				}
			}
		});
		btnLeave_Off.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbHelper.execute("DELETE FROM office WHERE Ono=" + tbOffs.getValueAt(tbOffs.getSelectedRow(), 0));
				refreshTbOffs();
			}
		});
	}
	
	public void refreshTbOffs(){
		rs = dbHelper.getResultSet("SELECT * FROM office");
		DefaultTableModel dtm = new DefaultTableModel(null, new String[]{"科室号", "科室名", "科室类型"});
		try {
			while(rs.next()){
				dtm.addRow(new Object[]{rs.getInt(1), rs.getString(2), rs.getString(3)});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tbOffs.setModel(dtm);
		lblOffCount_Off.setText("总计：   " + dtm.getRowCount() + "   项");
	}
	
	public void initSicPanel(){
		
		panel_Med.add(tfPrice_Med);
		panel_Sic = new JPanel();
		panel_Sic.setBackground(Color.WHITE);
		tabbedPane.addTab("\u75C5\u5E8A\u7BA1\u7406", null, panel_Sic, null);
		panel_Sic.setLayout(null);
		
		JLabel label_4 = new JLabel("\u623F\u53F7");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(21, 40, 70, 15);
		panel_Sic.add(label_4);
		
		JLabel label_2_1 = new JLabel("\u5E8A\u6570");
		label_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_2_1.setBounds(21, 119, 70, 15);
		panel_Sic.add(label_2_1);
		
		btnEntry_Sic = new JButton("\u5F55\u5165");
		btnEntry_Sic.setBackground(Color.WHITE);
		btnEntry_Sic.setBounds(21, 300, 182, 23);
		panel_Sic.add(btnEntry_Sic);
		
		btnQuery_Sic = new JButton("\u67E5\u8BE2");
		btnQuery_Sic.setBackground(Color.WHITE);
		btnQuery_Sic.setBounds(478, 20, 93, 23);
		panel_Sic.add(btnQuery_Sic);
		
		btnRevise_Sic = new JButton("\u4FEE\u6539");
		btnRevise_Sic.setBackground(Color.WHITE);
		btnRevise_Sic.setBounds(581, 20, 93, 23);
		panel_Sic.add(btnRevise_Sic);
		
		tfOption_Sic = new JTextField();
		tfOption_Sic.setColumns(10);
		tfOption_Sic.setBounds(345, 21, 123, 21);
		panel_Sic.add(tfOption_Sic);
		
		cbOption_Sic = new JComboBox();
		cbOption_Sic.setBackground(Color.WHITE);
		cbOption_Sic.setBounds(253, 21, 82, 21);
		panel_Sic.add(cbOption_Sic);
		
		JScrollPane scrollPane_Sics = new JScrollPane();
		scrollPane_Sics.setBounds(253, 53, 524, 245);
		panel_Sic.add(scrollPane_Sics);
		
		tbSics = new JTable(){
			public boolean isCellEditable(int rowIndex, int columnIndex) {
		        if(rowIndex >= 1 && reviseFlag_Sic){
		        	return true;
		        }
		        else{
		        	return false;
		        }
		    }
		};
		tbSics.setFillsViewportHeight(true);
		tbSics.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane_Sics.setViewportView(tbSics);
		
		btnLeave_Sic = new JButton("\u5220\u9664");
		btnLeave_Sic.setBackground(Color.WHITE);
		btnLeave_Sic.setBounds(684, 20, 93, 23);
		panel_Sic.add(btnLeave_Sic);
		
		lblSicCount_Sic = new JLabel("");
		lblSicCount_Sic.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSicCount_Sic.setBounds(684, 308, 93, 15);
		panel_Sic.add(lblSicCount_Sic);
		
		lblSelectedCount_Sic = new JLabel("");
		lblSelectedCount_Sic.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSelectedCount_Sic.setBounds(581, 308, 93, 15);
		lblSelectedCount_Sic.setVisible(false);
		panel_Sic.add(lblSelectedCount_Sic);
		
		JLabel label_3_1 = new JLabel("\u5355\u4EF7");
		label_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_3_1.setBounds(21, 202, 70, 15);
		panel_Sic.add(label_3_1);
		
		tfRno_Sic = new JTextField();
		tfRno_Sic.setColumns(10);
		tfRno_Sic.setBounds(109, 37, 94, 21);
		panel_Sic.add(tfRno_Sic);
		
		tfBnum_Sic = new JTextField();
		tfBnum_Sic.setColumns(10);
		tfBnum_Sic.setBounds(109, 116, 94, 21);
		panel_Sic.add(tfBnum_Sic);
		
		tfPrice_Sic = new JTextField();
		tfPrice_Sic.setColumns(10);
		tfPrice_Sic.setBounds(109, 199, 94, 21);
		panel_Sic.add(tfPrice_Sic);
		
		
		initSicCbs();
		initSicListeners();
		refreshTbSics();
	}
	
	public void initSicCbs(){
		cbOption_Sic.addItem("房号");
		cbOption_Sic.addItem("床号");
		cbOption_Sic.setSelectedIndex(-1);
	}

	public void initSicListeners(){
		btnEntry_Sic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!tfRno_Sic.getText().trim().equals("") && !tfBnum_Sic.getText().trim().equals("") &&
						!tfPrice_Sic.getText().trim().equals("")){
					if(!isDigit(tfRno_Sic.getText()) || !isDigit(tfBnum_Sic.getText()) || !isDouble(tfPrice_Sic.getText())){
						JOptionPane.showMessageDialog(null, "请输入正确的信息", "Error", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					boolean findFlag = false;
					rs = dbHelper.getResultSet("SELECT Rno FROM sickroom");
					try {
						while(rs.next()){
							if(rs.getString(1).equals(tfRno_Sic.getText())){
								findFlag = true;
								break;
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if(findFlag){
						JOptionPane.showMessageDialog(null, "请先清除本房病床记录再重新创建", "Error", JOptionPane.INFORMATION_MESSAGE);
					}
					else{
						for(int i = 0; i < Integer.parseInt(tfBnum_Sic.getText()); i++){
							int tmpBno = Integer.parseInt(tfRno_Sic.getText()) * 100 + i + 1;
							dbHelper.execute("INSERT INTO sickroom VALUES('" + tfRno_Sic.getText() + "', " + tmpBno + ", "  +  "0" + ",' " + tfPrice_Sic.getText() + "')");

						}
						tfRno_Sic.setText("");
						tfBnum_Sic.setText("");
						tfPrice_Sic.setText("");
						refreshTbSics();
					}
				}
			}
		});

		btnQuery_Sic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!tfOption_Sic.getText().trim().equals("")){
					String queryStr = tfOption_Sic.getText();
					int selectedCount = tbSics.getRowCount();
					tbSics.setRowSelectionInterval(0, tbSics.getRowCount() - 1);
					switch(cbOption_Sic.getSelectedIndex()){
					case -1:
						tbSics.removeRowSelectionInterval(0, tbSics.getRowCount() - 1);
						return;
					case 1://床号  
						for(int i = 0; i < tbSics.getRowCount(); i++){
							if(!(tbSics.getValueAt(i, 1) + "").equals(queryStr)){
								tbSics.removeRowSelectionInterval(i, i);
								selectedCount--;
							}
						}
						break;
					case 0://房号
						for(int i = 0; i < tbSics.getRowCount(); i++){
							if(!(tbSics.getValueAt(i, 0) + "").contains(queryStr)){
								tbSics.removeRowSelectionInterval(i, i);
								selectedCount--;
							}
						}
						break;
					}
					lblSelectedCount_Sic.setText("找到：   " + selectedCount + "   个");
					lblSelectedCount_Sic.setVisible(true);
				}
			}
		});

		btnRevise_Sic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] selectedRows = tbSics.getSelectedRows();
				if(selectedRows.length == 1 && btnRevise_Sic.getText().equals("修改")){
					reviseFlag_Sic = true;
					int selectedRow = tbSics.getSelectedRow();
					btnRevise_Sic.setText("保存");
					DefaultTableModel dtm = new DefaultTableModel(null, new String[]{"房号", "床号", "状态", "单价"});
					dtm.addRow(new Object[]{tbSics.getValueAt(selectedRow, 0), 
							tbSics.getValueAt(selectedRow, 1), 
							tbSics.getValueAt(selectedRow, 2),
							tbSics.getValueAt(selectedRow, 3)});
					tbSics.setModel(dtm);
				}
				else{
					String selectedBno = tbSics.getValueAt(0, 0) + "";
					String selectedPname = tbSics.getValueAt(0, 1) + "";
					dbHelper.execute("UPDATE sickroom SET Bno='" + tbSics.getValueAt(0, 1) + "', " +
								"Bstate='" + tbSics.getValueAt(0, 2) + "', " +
								"Bprice='" + tbSics.getValueAt(0, 3) + "'" +
								" WHERE Bno=" + selectedBno);
					btnRevise_Sic.setText("修改");
					reviseFlag_Sic = false;
					refreshTbSics();
				}
			}
		});
		btnLeave_Sic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbHelper.execute("DELETE FROM sickroom WHERE Rno=" + tbSics.getValueAt(tbSics.getSelectedRow(), 0));
				refreshTbSics();
			}
		});
	}
	
	public void refreshTbSics(){
		rs = dbHelper.getResultSet("SELECT * FROM sickroom");
		DefaultTableModel dtm = new DefaultTableModel(null, new String[]{"房号", "床号", "状态", "单价"});
		try {
			while(rs.next()){
				dtm.addRow(new Object[]{rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDouble(4)});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tbSics.setModel(dtm);
		lblSicCount_Sic.setText("总计：   " + dtm.getRowCount() + "   项");
	}
	
	public void initMedPanel(){
		panel_Med = new JPanel();
		panel_Med.setBackground(Color.WHITE);
		tabbedPane.addTab("\u836F\u54C1\u7BA1\u7406", null, panel_Med, null);
		panel_Med.setLayout(null);
		
		tfName_Med = new JTextField();
		tfName_Med.setBounds(109, 103, 94, 21);
		panel_Med.add(tfName_Med);
		tfName_Med.setColumns(10);
		
		JLabel label_4_1 = new JLabel("\u7F16\u7801");
		label_4_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_4_1.setBounds(21, 41, 70, 15);
		panel_Med.add(label_4_1);
		
		JLabel label_1_1 = new JLabel("\u540D\u79F0");
		label_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1_1.setBounds(21, 106, 70, 15);
		panel_Med.add(label_1_1);
		
		JLabel label_2_1_1 = new JLabel("\u6570\u91CF");
		label_2_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_2_1_1.setBounds(21, 171, 70, 15);
		panel_Med.add(label_2_1_1);
		
		btnEntry_Med = new JButton("\u5F55\u5165");
		btnEntry_Med.setBackground(Color.WHITE);
		btnEntry_Med.setBounds(21, 300, 182, 23);
		panel_Med.add(btnEntry_Med);
		
		btnQuery_Med = new JButton("\u67E5\u8BE2");
		btnQuery_Med.setBackground(Color.WHITE);
		btnQuery_Med.setBounds(478, 20, 93, 23);
		panel_Med.add(btnQuery_Med);
		
		btnRevise_Med = new JButton("\u4FEE\u6539");
		btnRevise_Med.setBackground(Color.WHITE);
		btnRevise_Med.setBounds(581, 20, 93, 23);
		panel_Med.add(btnRevise_Med);
		
		tfOption_Med = new JTextField();
		tfOption_Med.setColumns(10);
		tfOption_Med.setBounds(345, 21, 123, 21);
		panel_Med.add(tfOption_Med);
		
		cbOption_Med = new JComboBox();
		cbOption_Med.setBackground(Color.WHITE);
		cbOption_Med.setBounds(253, 21, 82, 21);
		panel_Med.add(cbOption_Med);
		
		JScrollPane scrollPane_Meds = new JScrollPane();
		scrollPane_Meds.setBounds(253, 53, 524, 245);
		panel_Med.add(scrollPane_Meds);
		
		tbMeds = new JTable(){
			public boolean isCellEditable(int rowIndex, int columnIndex) {
		        if(rowIndex >= 1 && reviseFlag_Med){
		        	return true;
		        }
		        else{
		        	return false;
		        }
		    }
		};
		tbMeds.setFillsViewportHeight(true);
		tbMeds.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane_Meds.setViewportView(tbMeds);
		
		btnLeave_Med = new JButton("\u5220\u9664");
		btnLeave_Med.setBackground(Color.WHITE);
		btnLeave_Med.setBounds(684, 20, 93, 23);
		panel_Med.add(btnLeave_Med);
		
		lblMedCount_Med = new JLabel("\u603B\u8BA1\uFF1A   0   \u79CD");
		lblMedCount_Med.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMedCount_Med.setBounds(684, 308, 93, 15);
		panel_Med.add(lblMedCount_Med);
		
		lblSelectedCount_Med = new JLabel("");
		lblSelectedCount_Med.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSelectedCount_Med.setBounds(581, 308, 93, 15);
		lblSelectedCount_Med.setVisible(false);
		panel_Med.add(lblSelectedCount_Med);
		
		JLabel label_3_1_1 = new JLabel("\u5355\u4EF7");
		label_3_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_3_1_1.setBounds(21, 233, 70, 15);
		panel_Med.add(label_3_1_1);
		
		tfMno_Med = new JTextField();
		tfMno_Med.setColumns(10);
		tfMno_Med.setBounds(109, 38, 94, 21);
		panel_Med.add(tfMno_Med);
		
		tfMnum_Med = new JTextField();
		tfMnum_Med.setColumns(10);
		tfMnum_Med.setBounds(109, 168, 94, 21);
		panel_Med.add(tfMnum_Med);
		
		tfPrice_Med = new JTextField();
		tfPrice_Med.setColumns(10);
		tfPrice_Med.setBounds(109, 230, 94, 21);
		
		initMedCbs();
		initMedListeners();
		refreshTbMeds();
	}
	
	public void initMedCbs(){
		cbOption_Med.addItem("编号");
		cbOption_Med.addItem("名称");
		cbOption_Med.setSelectedIndex(-1);
	}
	
	public void initMedListeners(){
		btnEntry_Med.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!tfName_Med.getText().trim().equals("") && !tfMno_Med.getText().trim().equals("") &&
						!tfMnum_Med.getText().trim().equals("") && !tfPrice_Med.getText().trim().equals("")){
					if(!isDigit(tfMno_Med.getText()) || !isDigit(tfMnum_Med.getText()) || !isDouble(tfPrice_Med.getText())){
						JOptionPane.showMessageDialog(null, "请录入正确信息", "Error", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					boolean findFlag = false;
					rs = dbHelper.getResultSet("SELECT * FROM medicine");
					try {
						while(rs.next()){
							if(rs.getString(1).equals(tfMno_Med.getText()) || rs.getString(2).equals(tfName_Med.getText())){
								findFlag = true;
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if(findFlag){
						JOptionPane.showMessageDialog(null, "重复记录", "Error", JOptionPane.INFORMATION_MESSAGE);
					}
					else{
						double price = Double.parseDouble(tfPrice_Med.getText());
						dbHelper.execute("INSERT INTO medicine VALUES(" + tfMno_Med.getText() + ", '" + tfName_Med.getText() + "', " + tfMnum_Med.getText() + ", " + price + ")");
						tfName_Med.setText("");
						tfMno_Med.setText("");
						tfMnum_Med.setText("");
						tfPrice_Med.setText("");
						refreshTbMeds();
					}
				}
			}
		});

		btnQuery_Med.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!tfOption_Med.getText().trim().equals("")){
					String queryStr = tfOption_Med.getText();
					int selectedCount = tbMeds.getRowCount();
					tbMeds.setRowSelectionInterval(0, tbMeds.getRowCount() - 1);
					switch(cbOption_Med.getSelectedIndex()){
					case -1:
						tbMeds.removeRowSelectionInterval(0, tbMeds.getRowCount() - 1);
						return;
					case 1://名称
						for(int i = 0; i < tbMeds.getRowCount(); i++){
							if(!((String)tbMeds.getValueAt(i, 1)).contains(queryStr)){
								tbMeds.removeRowSelectionInterval(i, i);
								selectedCount--;
							}
						}
						break;
					case 0://编号
						for(int i = 0; i < tbMeds.getRowCount(); i++){
							if(!(tbMeds.getValueAt(i, 0) + "").contains(queryStr)){
								tbMeds.removeRowSelectionInterval(i, i);
								selectedCount--;
							}
						}
						break;
					}
					lblSelectedCount_Med.setText("找到：   " + selectedCount + "   个");
					lblSelectedCount_Med.setVisible(true);
				}
			}
		});
		btnRevise_Med.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] selectedRows = tbMeds.getSelectedRows();
				if(selectedRows.length == 1 && btnRevise_Med.getText().equals("修改")){
					reviseFlag_Med = true;
					int selectedRow = tbMeds.getSelectedRow();
					btnRevise_Med.setText("保存");
					DefaultTableModel dtm = new DefaultTableModel(null, new String[]{"编号", "名称", "数量", "单价"});
					dtm.addRow(new Object[]{tbMeds.getValueAt(selectedRow, 0), 
							tbMeds.getValueAt(selectedRow, 1), 
							tbMeds.getValueAt(selectedRow, 2),
							tbMeds.getValueAt(selectedRow, 3)});
					tbMeds.setModel(dtm);
				}
				else{
					String selectedMno = tbMeds.getValueAt(0, 0) + "";
					String selectedPname = tbMeds.getValueAt(0, 1) + "";
					dbHelper.execute("UPDATE medicine SET Mname='" + tbMeds.getValueAt(0, 1) + "', " +
								"Mnum='" + tbMeds.getValueAt(0, 2) + "', " +
								"Mprice='" + tbMeds.getValueAt(0, 3) + "'" +
								" WHERE Mno=" + selectedMno);
					btnRevise_Med.setText("修改");
					reviseFlag_Med = false;
					refreshTbMeds();
				}
			}
		});
		btnLeave_Med.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbHelper.execute("DELETE FROM medicine WHERE Mno=" + tbMeds.getValueAt(tbMeds.getSelectedRow(), 0));
				refreshTbMeds();
			}
		});
	}
	
	public void refreshTbMeds(){
		rs = dbHelper.getResultSet("SELECT * FROM medicine");
		DefaultTableModel dtm = new DefaultTableModel(null, new String[]{"编号", "名称", "数量", "单价"});
		try {
			while(rs.next()){
				dtm.addRow(new Object[]{rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getDouble(4)});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tbMeds.setModel(dtm);
		lblMedCount_Med.setText("总计：   " + dtm.getRowCount() + "   种");
	}
	
	public void initPatPanel(){
		panel_Pat = new JPanel();
		panel_Pat.setBackground(Color.WHITE);
		tabbedPane.addTab("\u75C5\u4EBA\u7BA1\u7406", null, panel_Pat, null);
		panel_Pat.setLayout(null);
		
		tfName_Pat = new JTextField();
		tfName_Pat.setBounds(109, 21, 94, 21);
		panel_Pat.add(tfName_Pat);
		tfName_Pat.setColumns(10);
		
		rbMan_Pat = new JRadioButton("\u7537");
		rbMan_Pat.setBackground(Color.WHITE);
		rbMan_Pat.setBounds(109, 123, 46, 23);
		panel_Pat.add(rbMan_Pat);
		
		rbWomen_Pat = new JRadioButton("\u5973");
		rbWomen_Pat.setBackground(Color.WHITE);
		rbWomen_Pat.setBounds(157, 123, 46, 23);
		panel_Pat.add(rbWomen_Pat);
		
		rbGroup_Pat = new ButtonGroup();
		rbGroup_Pat.add(rbMan_Pat);
		rbGroup_Pat.add(rbWomen_Pat);
		
		JLabel label = new JLabel("\u59D3\u540D");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(21, 24, 70, 15);
		panel_Pat.add(label);
		
		JLabel label_1 = new JLabel("\u5E74\u9F84");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(21, 74, 70, 15);
		panel_Pat.add(label_1);
		
		cbAge_Pat = new JComboBox();
		cbAge_Pat.setBackground(Color.WHITE);
		cbAge_Pat.setBounds(109, 71, 94, 21);
		panel_Pat.add(cbAge_Pat);
		
		JLabel label_2 = new JLabel("\u6027\u522B");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(21, 127, 70, 15);
		panel_Pat.add(label_2);
		
		btnEntry_Pat = new JButton("\u5F55\u5165");
		btnEntry_Pat.setBackground(Color.WHITE);
		btnEntry_Pat.setBounds(21, 300, 182, 23);
		panel_Pat.add(btnEntry_Pat);
		
		btnQuery_Pat = new JButton("\u67E5\u8BE2");
		btnQuery_Pat.setBackground(Color.WHITE);
		btnQuery_Pat.setBounds(478, 20, 93, 23);
		panel_Pat.add(btnQuery_Pat);
		
		tfOption_Pat = new JTextField();
		tfOption_Pat.setColumns(10);
		tfOption_Pat.setBounds(345, 21, 123, 21);
		panel_Pat.add(tfOption_Pat);
		
		cbOption_Pat = new JComboBox();
		cbOption_Pat.setBackground(Color.WHITE);
		cbOption_Pat.setBounds(253, 21, 82, 21);
		panel_Pat.add(cbOption_Pat);
		
		JScrollPane scrollPane_Pats = new JScrollPane();
		scrollPane_Pats.setBounds(253, 53, 524, 245);
		panel_Pat.add(scrollPane_Pats);
		
		tbPats = new JTable(){
			public boolean isCellEditable(int rowIndex, int columnIndex) {
		        if(rowIndex >= 1 && reviseFlag_Pat){
		        	return true;
		        }
		        else{
		        	return false;
		        }
		    }
		};
		tbPats.setFillsViewportHeight(true);
		tbPats.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane_Pats.setViewportView(tbPats);
		
		btnRevise_Pat = new JButton("\u4FEE\u6539");
		btnRevise_Pat.setBackground(Color.WHITE);
		btnRevise_Pat.setBounds(581, 20, 93, 23);
		panel_Pat.add(btnRevise_Pat);
		
		btnLeave_Pat = new JButton("\u5220\u9664");
		btnLeave_Pat.setBackground(Color.WHITE);
		btnLeave_Pat.setBounds(684, 20, 93, 23);
		panel_Pat.add(btnLeave_Pat);
		
		lblPatCount_Pat = new JLabel("");
		lblPatCount_Pat.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPatCount_Pat.setBounds(684, 308, 93, 15);
		panel_Pat.add(lblPatCount_Pat);
		
		lblSelectedCount_Pat = new JLabel("");
		lblSelectedCount_Pat.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSelectedCount_Pat.setBounds(581, 308, 93, 15);
		panel_Pat.add(lblSelectedCount_Pat);
		
		JLabel label_3 = new JLabel("\u5B66\u53F7");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(21, 181, 70, 15);
		panel_Pat.add(label_3);
		
		tfPno_Pat = new JTextField();
		tfPno_Pat.setColumns(10);
		tfPno_Pat.setBounds(109, 178, 94, 21);
		panel_Pat.add(tfPno_Pat);
		lblSelectedCount_Pat.setVisible(false);
		
		btnGenerate_Pat = new JButton("\u751F\u6210");
		btnGenerate_Pat.setBackground(Color.WHITE);
		btnGenerate_Pat.setBounds(21, 229, 70, 54);
		panel_Pat.add(btnGenerate_Pat);
		
		lblMhState_Pat = new JLabel("\u75C5\u4F8B\u5F85\u751F\u6210");
		lblMhState_Pat.setBackground(Color.LIGHT_GRAY);
		lblMhState_Pat.setBounds(109, 249, 94, 15);
		lblMhState_Pat.setOpaque(true);
		panel_Pat.add(lblMhState_Pat);
		
		initPatCbs();
		initPatListeners();
		refreshTbPats();
	}
	
	public void initPatCbs(){
		for(int i = 6; i <= 99; i++){
			cbAge_Pat.addItem(i);
		}
		cbOption_Pat.addItem("编号");
		cbOption_Pat.addItem("姓名");
		cbOption_Pat.addItem("性别");
		cbOption_Pat.addItem("年龄");
		cbOption_Pat.setSelectedIndex(-1);
		cbAge_Pat.setSelectedIndex(-1);
	}
	
	public void initPatListeners(){
		btnGenerate_Pat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!tfName_Pat.getText().trim().equals("") && cbAge_Pat.getSelectedIndex() != -1 && 
						!tfPno_Pat.getText().trim().equals("") && (rbMan_Pat.isSelected() ^ rbWomen_Pat.isSelected())){
					if(!isDigit(tfPno_Pat.getText())){
						JOptionPane.showMessageDialog(null, "请输入正确学号", "Error", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					boolean findFlag = false;
					rs = dbHelper.getResultSet("SELECT Pno From Patient");
					try {
						while(rs.next()){
							if(rs.getString(1).equals(tfPno_Pat.getText())){
								findFlag = true;
								break;
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if(findFlag){
						JOptionPane.showMessageDialog(null, "此记录曾被录入", "Error", JOptionPane.INFORMATION_MESSAGE);
					}
					else{
						String str = tfPno_Pat.getText() + ".txt";
						try{
							File path = new File("D:\\");
							File dir = new File(path,str);
							if(!dir.exists())
								dir.createNewFile();
							lblMhState_Pat.setText("D:\\" + dir.getName());
						}catch(Exception e){
							System.out.print(e);
						}
					}
					//生成文件,命名格式为d:\学号.txt  //*****************************
				}
			}
		});

		btnEntry_Pat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!lblMhState_Pat.getText().trim().equals("病历待生成")){
					String sex = "";
					if(rbMan_Pat.isSelected()){
						sex = "\u7537";
					}
					else{
						sex = "\u5973";
					}
					dbHelper.execute("INSERT INTO patient VALUES(" + tfPno_Pat.getText() + ", '" + tfName_Pat.getText() + "', '" + sex + "', " + cbAge_Pat.getSelectedItem() + ", '" + lblMhState_Pat.getText() + "')");
					tfName_Pat.setText("");
					cbAge_Pat.setSelectedIndex(-1);
					rbGroup_Pat.clearSelection();
					lblMhState_Pat.setText("病例待生成");
					refreshTbPats();
				}
			}
		});
		btnQuery_Pat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!tfOption_Pat.getText().trim().equals("")){
					String queryStr = tfOption_Pat.getText();
					int selectedCount = tbPats.getRowCount();
					tbPats.setRowSelectionInterval(0, tbPats.getRowCount() - 1);
					switch(cbOption_Pat.getSelectedIndex()){
					case -1:
						tbPats.removeRowSelectionInterval(0, tbPats.getRowCount() - 1);
						return;
					case 1://姓名
						for(int i = 0; i < tbPats.getRowCount(); i++){
							if(!((String)tbPats.getValueAt(i, 1)).contains(queryStr)){
								tbPats.removeRowSelectionInterval(i, i);
								selectedCount--;
							}
						}
						break;
					case 0://编号
						for(int i = 0; i < tbPats.getRowCount(); i++){
							if(!(tbPats.getValueAt(i, 0) + "").equals(queryStr)){
								tbPats.removeRowSelectionInterval(i, i);
								selectedCount--;
							}
						}
						break;
					case 2://性别
						for(int i = 0; i < tbPats.getRowCount(); i++){
							if(!tbPats.getValueAt(i, 2).equals("queryStr")){
								tbPats.removeRowSelectionInterval(i, i);
								selectedCount--;
							}
						}
						break;
					case 3://年龄
						if(isAgeRange(queryStr)){
							String[] strs = queryStr.split("-");
							int bottom = Integer.parseInt(strs[0]);
							int top = Integer.parseInt(strs[1]);
							if(isDigit(strs[0]) && isDigit(strs[1]) && bottom <= top){
								for(int i = 0; i < tbPats.getRowCount(); i++){
									int age = (int) tbPats.getValueAt(i, 3);
									if(age < bottom || age > top){
										tbPats.removeRowSelectionInterval(i, i);
										selectedCount--;
									}
								}
							}
						}
						else{
							tbPats.removeRowSelectionInterval(0, tbPats.getRowCount() - 1);
							return;
						}
						break;
					}
					lblSelectedCount_Pat.setText("找到：   " + selectedCount + "   个");
					lblSelectedCount_Pat.setVisible(true);
				}
			}
		});
		btnRevise_Pat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] selectedRows = tbPats.getSelectedRows();
				if(selectedRows.length == 1 && btnRevise_Pat.getText().equals("修改")){
					reviseFlag_Pat = true;
					int selectedRow = tbPats.getSelectedRow();
					btnRevise_Pat.setText("保存");
					DefaultTableModel dtm = new DefaultTableModel(null, new String[]{"编号", "姓名", "性别", "年龄"});
					dtm.addRow(new Object[]{tbPats.getValueAt(selectedRow, 0), 
							tbPats.getValueAt(selectedRow, 1), 
							tbPats.getValueAt(selectedRow, 2),
							tbPats.getValueAt(selectedRow, 3)});
					tbPats.setModel(dtm);
				}
				else{
					String selectedPno = tbPats.getValueAt(0, 0) + "";
					String selectedPname = tbPats.getValueAt(0, 1) + "";
					dbHelper.execute("UPDATE patient SET Page='" + tbPats.getValueAt(0, 3) + "', " +
								"Pname='" + tbPats.getValueAt(0, 1) + "', " +
								"Psex='" + tbPats.getValueAt(0, 2) + "'" +
								" WHERE Pno=" + selectedPno);
					btnRevise_Pat.setText("修改");
					reviseFlag_Pat = false;
					refreshTbPats();
				}
			}
		});
		btnLeave_Pat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbHelper.execute("DELETE FROM Patient WHERE Pno=" + tbPats.getValueAt(tbPats.getSelectedRow(), 0));
				refreshTbPats();
			}
		});
	}
	
	public void refreshTbPats(){
		rs = dbHelper.getResultSet("SELECT * FROM patient");
		DefaultTableModel dtm = new DefaultTableModel(null, new String[]{"编号", "姓名", "性别", "年龄"});
		try {
			while(rs.next()){
				dtm.addRow(new Object[]{rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5)});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tbPats.setModel(dtm);
		lblPatCount_Pat.setText("总计：   " + dtm.getRowCount() + "   人");
	}
	
	public void initNurPanel(){
		panel_Nur = new JPanel();
		panel_Nur.setBackground(Color.WHITE);
		tabbedPane.addTab("\u62A4\u58EB\u7BA1\u7406", null, panel_Nur, null);
		panel_Nur.setLayout(null);
		
		tfName_Nur = new JTextField();
		tfName_Nur.setBounds(109, 21, 94, 21);
		panel_Nur.add(tfName_Nur);
		tfName_Nur.setColumns(10);
		
		rbMan_Nur = new JRadioButton("\u7537");
		rbMan_Nur.setBackground(Color.WHITE);
		rbMan_Nur.setBounds(109, 123, 46, 23);
		panel_Nur.add(rbMan_Nur);
		
		rbWomen_Nur = new JRadioButton("\u5973");
		rbWomen_Nur.setBackground(Color.WHITE);
		rbWomen_Nur.setBounds(157, 123, 46, 23);
		panel_Nur.add(rbWomen_Nur);
		
		rbGroup_Nur = new ButtonGroup();
		rbGroup_Nur.add(rbMan_Nur);
		rbGroup_Nur.add(rbWomen_Nur);
		
		JLabel label = new JLabel("\u59D3\u540D");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(21, 24, 70, 15);
		panel_Nur.add(label);
		
		JLabel label_1 = new JLabel("\u5E74\u9F84");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(21, 74, 70, 15);
		panel_Nur.add(label_1);
		
		cbAge_Nur = new JComboBox();
		cbAge_Nur.setBackground(Color.WHITE);
		cbAge_Nur.setBounds(109, 71, 94, 21);
		panel_Nur.add(cbAge_Nur);
		
		JLabel label_2 = new JLabel("\u6027\u522B");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(21, 127, 70, 15);
		panel_Nur.add(label_2);
		
		JLabel label_3 = new JLabel("\u804C\u79F0");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(21, 178, 70, 15);
		panel_Nur.add(label_3);
		
		cbTitle_Nur = new JComboBox();
		cbTitle_Nur.setBackground(Color.WHITE);
		cbTitle_Nur.setBounds(109, 175, 94, 21);
		panel_Nur.add(cbTitle_Nur);
		
		btnEntry_Nur = new JButton("\u5165\u804C");
		btnEntry_Nur.setBackground(Color.WHITE);
		btnEntry_Nur.setBounds(21, 300, 182, 23);
		panel_Nur.add(btnEntry_Nur);
		
		btnGenerate_Nur = new JButton("\u751F\u6210");
		btnGenerate_Nur.setBackground(Color.WHITE);
		btnGenerate_Nur.setBounds(21, 229, 70, 54);
		panel_Nur.add(btnGenerate_Nur);
		
		lblNno_Nur = new JLabel("\u7F16\u53F7");
		lblNno_Nur.setBackground(Color.LIGHT_GRAY);
		lblNno_Nur.setBounds(109, 249, 94, 15);
		lblNno_Nur.setOpaque(true);
		panel_Nur.add(lblNno_Nur);
		
		btnQuery_Nur = new JButton("\u67E5\u8BE2");
		btnQuery_Nur.setBackground(Color.WHITE);
		btnQuery_Nur.setBounds(478, 20, 93, 23);
		panel_Nur.add(btnQuery_Nur);
		
		tfOption_Nur = new JTextField();
		tfOption_Nur.setColumns(10);
		tfOption_Nur.setBounds(345, 21, 123, 21);
		panel_Nur.add(tfOption_Nur);
		
		cbOption_Nur = new JComboBox();
		cbOption_Nur.setBackground(Color.WHITE);
		cbOption_Nur.setBounds(253, 21, 82, 21);
		panel_Nur.add(cbOption_Nur);
		
		JScrollPane scrollPane_Nurs = new JScrollPane();
		scrollPane_Nurs.setBounds(253, 53, 524, 245);
		panel_Nur.add(scrollPane_Nurs);
		
		tbNurs = new JTable(){
			public boolean isCellEditable(int rowIndex, int columnIndex) {
		        if(rowIndex == 0 && columnIndex >= 1 && reviseFlag_Nur){
		        	return true;
		        }
		        else{
		        	return false;
		        }
		    }
		};
		tbNurs.setFillsViewportHeight(true);
		tbNurs.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane_Nurs.setViewportView(tbNurs);
		
		btnRevise_Nur = new JButton("\u4FEE\u6539");
		btnRevise_Nur.setBackground(Color.WHITE);
		btnRevise_Nur.setBounds(581, 20, 93, 23);
		panel_Nur.add(btnRevise_Nur);
		
		btnLeave_Nur = new JButton("\u79BB\u804C");
		btnLeave_Nur.setBackground(Color.WHITE);
		btnLeave_Nur.setBounds(684, 20, 93, 23);
		panel_Nur.add(btnLeave_Nur);
		
		lblNurCount_Nur = new JLabel("");
		lblNurCount_Nur.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNurCount_Nur.setBounds(684, 308, 93, 15);
		panel_Nur.add(lblNurCount_Nur);
		
		lblSelectedCount_Nur = new JLabel("");
		lblSelectedCount_Nur.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSelectedCount_Nur.setBounds(581, 308, 93, 15);
		panel_Nur.add(lblSelectedCount_Nur);
		lblSelectedCount_Nur.setVisible(false);
		
		initNurCbs();
		initNurListeners();
		refreshTbNurs();
	}
	
	public void initNurCbs(){
		for(int i = 16; i <= 65; i++){
			cbAge_Nur.addItem(i);
		}
		cbTitle_Nur.addItem("初始护士");
		cbTitle_Nur.addItem("主管护士");
		cbTitle_Nur.addItem("副主任护士");
		cbTitle_Nur.addItem("主任护士");
		cbOption_Nur.addItem("编号");
		cbOption_Nur.addItem("姓名");
		cbOption_Nur.addItem("性别");
		cbOption_Nur.addItem("年龄");
		cbOption_Nur.addItem("职称");
		cbOption_Nur.setSelectedIndex(-1);
		cbAge_Nur.setSelectedIndex(-1);
		cbTitle_Nur.setSelectedIndex(-1);
	}
	
	public void initNurListeners(){
		btnGenerate_Nur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!tfName_Nur.getText().trim().equals("") && cbAge_Nur.getSelectedIndex() != -1 && 
						cbTitle_Nur.getSelectedIndex() != -1 && (rbMan_Nur.isSelected() ^ rbWomen_Nur.isSelected())){
					Date date = new Date();
					SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
					String time = format.format(date);
					rs = dbHelper.getResultSet("SELECT Nno FROM Nurse WHERE Nno>=" + time + "1000" + " ORDER BY Nno ASC");
					int lastNum = -1;
					String numStr = "";
					try {
						while(rs.next()){
							lastNum = rs.getInt(1);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if(lastNum == -1){
						numStr = "001";
					}
					else{
						lastNum = lastNum - lastNum/1000*1000 + 1;
						if(lastNum /10 >= 10){
							numStr = lastNum + "";
						}
						else if(lastNum / 10 >= 1){
							numStr = "0" + lastNum;
						}
						else{
							numStr = "00" + lastNum;
						}
					}
					lblNno_Nur.setText(time + "1" + numStr);
				}
			}
		});
		btnEntry_Nur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!lblNno_Nur.getText().trim().equals("")){
					String sex = "";
					if(rbMan_Nur.isSelected()){
						sex = "\u7537";
					}
					else{
						sex = "\u5973";
					}
					dbHelper.execute("INSERT INTO nurse VALUES(" + lblNno_Nur.getText() + ", '" + tfName_Nur.getText() + "', '" + sex + "', " + cbAge_Nur.getSelectedItem() + ", '" + cbTitle_Nur.getSelectedItem() + "')");
					tfName_Nur.setText("");
					cbAge_Nur.setSelectedIndex(-1);
					cbTitle_Nur.setSelectedIndex(-1);
					rbGroup_Nur.clearSelection();
					lblNno_Nur.setText("编号");
					refreshTbNurs();
				}
			}
		});
		btnQuery_Nur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!tfOption_Nur.getText().trim().equals("")){
					String queryStr = tfOption_Nur.getText();
					int selectedCount = tbNurs.getRowCount();
					tbNurs.setRowSelectionInterval(0, tbNurs.getRowCount() - 1);
					switch(cbOption_Nur.getSelectedIndex()){
					case -1:
						tbNurs.removeRowSelectionInterval(0, tbNurs.getRowCount() - 1);
						return;
					case 1://姓名
						for(int i = 0; i < tbNurs.getRowCount(); i++){
							if(!((String)tbNurs.getValueAt(i, 1)).contains(queryStr)){
								tbNurs.removeRowSelectionInterval(i, i);
								selectedCount--;
							}
						}
						break;
					case 0://编号
						for(int i = 0; i < tbNurs.getRowCount(); i++){
							if(!(tbNurs.getValueAt(i, 0) + "").equals(queryStr)){
								tbNurs.removeRowSelectionInterval(i, i);
								selectedCount--;
							}
						}
						break;
					case 2://性别
						for(int i = 0; i < tbNurs.getRowCount(); i++){
							if(!tbNurs.getValueAt(i, 2).equals("queryStr")){
								tbNurs.removeRowSelectionInterval(i, i);
								selectedCount--;
							}
						}
						break;
					case 3://年龄
						if(isAgeRange(queryStr)){
							String[] strs = queryStr.split("-");
							int bottom = Integer.parseInt(strs[0]);
							int top = Integer.parseInt(strs[1]);
							if(isDigit(strs[0]) && isDigit(strs[1]) && bottom <= top){
								for(int i = 0; i < tbNurs.getRowCount(); i++){
									int age = (int) tbNurs.getValueAt(i, 3);
									if(age < bottom || age > top){
										tbNurs.removeRowSelectionInterval(i, i);
										selectedCount--;
									}
								}
							}
						}
						else{
							tbNurs.removeRowSelectionInterval(0, tbNurs.getRowCount() - 1);
							return;
						}
						break;
					case 4://职称
						for(int i = 0; i < tbNurs.getRowCount(); i++){
							if(!(tbNurs.getValueAt(i, 4)).equals(queryStr)){
								tbNurs.removeRowSelectionInterval(i, i);
								selectedCount--;
							}
						}
						break;
					}
					lblSelectedCount_Nur.setText("找到：   " + selectedCount + "   个");
					lblSelectedCount_Nur.setVisible(true);
				}
			}
		});
		btnRevise_Nur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] selectedRows = tbNurs.getSelectedRows();
				if(selectedRows.length == 1 && btnRevise_Nur.getText().equals("修改")){
					reviseFlag_Nur = true;
					int selectedRow = tbNurs.getSelectedRow();
					btnRevise_Nur.setText("保存");
					DefaultTableModel dtm = new DefaultTableModel(null, new String[]{"编号", "姓名", "性别", "年龄", "职称"});
					dtm.addRow(new Object[]{tbNurs.getValueAt(selectedRow, 0), 
							tbNurs.getValueAt(selectedRow, 1), 
							tbNurs.getValueAt(selectedRow, 2),
							tbNurs.getValueAt(selectedRow, 3),
							tbNurs.getValueAt(selectedRow, 4)});
					tbNurs.setModel(dtm);
				}
				else{
					String selectedNno = tbNurs.getValueAt(0, 0) + "";
					String Ntitle = tbNurs.getValueAt(0, 4) + ""; //***********???????????***********
					try {
						Ntitle = new String((tbNurs.getValueAt(0, 4) + "").getBytes("gb2312"),"utf-8");
						dbHelper.execute("UPDATE Nurse SET Nage='" + tbNurs.getValueAt(0, 3) + "', " +
								"Ntitle='" + Ntitle + "', " +
								"Nname='" + tbNurs.getValueAt(0, 1) + "', " +
								"Nsex='" + tbNurs.getValueAt(0, 2) + "'" +
								" WHERE Nno=" + selectedNno);
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}
					btnRevise_Nur.setText("修改");
					reviseFlag_Nur = false;
					refreshTbNurs();
				}
			}
		});
		btnLeave_Nur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbHelper.execute("DELETE FROM nurse WHERE Nno=" + tbNurs.getValueAt(tbNurs.getSelectedRow(), 0));
				refreshTbNurs();
			}
		});
	}
	
	public void refreshTbNurs(){
		rs = dbHelper.getResultSet("SELECT * FROM nurse");
		DefaultTableModel dtm = new DefaultTableModel(null, new String[]{"编号", "姓名", "性别", "年龄", "职称"});
		try {
			while(rs.next()){
				dtm.addRow(new Object[]{rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5)});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tbNurs.setModel(dtm);
		lblNurCount_Nur.setText("总计：   " + dtm.getRowCount() + "   人");
	}
	
	public void initDocPanel(){
		panel_Doc = new JPanel();
		panel_Doc.setBackground(Color.WHITE);
		tabbedPane.addTab("\u533B\u751F\u7BA1\u7406", null, panel_Doc, null);
		panel_Doc.setLayout(null);
		
		tfName_Doc = new JTextField();
		tfName_Doc.setBounds(109, 21, 94, 21);
		panel_Doc.add(tfName_Doc);
		tfName_Doc.setColumns(10);
		
		rbMan_Doc = new JRadioButton("\u7537");
		rbMan_Doc.setBackground(Color.WHITE);
		rbMan_Doc.setBounds(109, 109, 46, 23);
		panel_Doc.add(rbMan_Doc);
		
		rbWomen_Doc = new JRadioButton("\u5973");
		rbWomen_Doc.setBackground(Color.WHITE);
		rbWomen_Doc.setBounds(157, 109, 46, 23);
		panel_Doc.add(rbWomen_Doc);
		
		rbGroup_Doc = new ButtonGroup();
		rbGroup_Doc.add(rbMan_Doc);
		rbGroup_Doc.add(rbWomen_Doc);
		
		cbOno_Doc = new JComboBox();
		cbOno_Doc.setBackground(Color.WHITE);
		cbOno_Doc.setBounds(109, 201, 94, 21);
		panel_Doc.add(cbOno_Doc);
		
		JLabel label = new JLabel("\u59D3\u540D");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(21, 24, 70, 15);
		panel_Doc.add(label);
		
		JLabel label_1 = new JLabel("\u5E74\u9F84");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(21, 65, 70, 15);
		panel_Doc.add(label_1);
		
		cbAge_Doc = new JComboBox();
		cbAge_Doc.setBackground(Color.WHITE);
		cbAge_Doc.setBounds(109, 62, 94, 21);
		panel_Doc.add(cbAge_Doc);
		
		JLabel label_2 = new JLabel("\u6027\u522B");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(21, 113, 70, 15);
		panel_Doc.add(label_2);
		
		JLabel label_3 = new JLabel("\u804C\u79F0");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(21, 158, 70, 15);
		panel_Doc.add(label_3);
		
		JLabel label_4 = new JLabel("\u79D1\u5BA4");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(21, 204, 70, 15);
		panel_Doc.add(label_4);
		
		cbTitle_Doc = new JComboBox();
		cbTitle_Doc.setBackground(Color.WHITE);
		cbTitle_Doc.setBounds(109, 155, 94, 21);
		panel_Doc.add(cbTitle_Doc);
		
		btnEntry_Doc = new JButton("\u5165\u804C");
		btnEntry_Doc.setBackground(Color.WHITE);
		btnEntry_Doc.setBounds(21, 308, 182, 23);
		panel_Doc.add(btnEntry_Doc);
		
		btnGenerate_Doc = new JButton("\u751F\u6210");
		btnGenerate_Doc.setBackground(Color.WHITE);
		btnGenerate_Doc.setBounds(21, 244, 70, 54);
		panel_Doc.add(btnGenerate_Doc);
		
		lblDno_Doc = new JLabel("\u7F16\u53F7");
		lblDno_Doc.setBackground(Color.LIGHT_GRAY);
		lblDno_Doc.setBounds(109, 253, 94, 15);
		lblDno_Doc.setOpaque(true);
		panel_Doc.add(lblDno_Doc);
		
		lblPwd_Doc = new JLabel("\u5BC6\u7801");
		lblPwd_Doc.setBackground(Color.LIGHT_GRAY);
		lblPwd_Doc.setBounds(109, 278, 94, 15);
		lblPwd_Doc.setOpaque(true);
		panel_Doc.add(lblPwd_Doc);
		
		btnQuery_Doc = new JButton("\u67E5\u8BE2");
		btnQuery_Doc.setBackground(Color.WHITE);
		btnQuery_Doc.setBounds(478, 20, 93, 23);
		panel_Doc.add(btnQuery_Doc);
		
		tfOption_Doc = new JTextField();
		tfOption_Doc.setColumns(10);
		tfOption_Doc.setBounds(345, 21, 123, 21);
		panel_Doc.add(tfOption_Doc);
		
		cbOption_Doc = new JComboBox();
		cbOption_Doc.setBackground(Color.WHITE);
		cbOption_Doc.setBounds(253, 21, 82, 21);
		panel_Doc.add(cbOption_Doc);
		
		JScrollPane scrollPane_Docs = new JScrollPane();
		scrollPane_Docs.setBounds(253, 53, 524, 245);
		panel_Doc.add(scrollPane_Docs);
		
		tbDocs = new JTable(){
			public boolean isCellEditable(int rowIndex, int columnIndex) {
		        if(rowIndex == 0 && columnIndex >= 1 && reviseFlag_Doc){
		        	return true;
		        }
		        else{
		        	return false;
		        }
		    }
		};
		tbDocs.setBackground(Color.WHITE);
		tbDocs.setFillsViewportHeight(true);
		tbDocs.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane_Docs.setViewportView(tbDocs);
		
		btnRevise_Doc = new JButton("\u4FEE\u6539");
		btnRevise_Doc.setBackground(Color.WHITE);
		btnRevise_Doc.setBounds(581, 20, 93, 23);
		panel_Doc.add(btnRevise_Doc);
		
		btnLeave_Doc = new JButton("\u79BB\u804C");
		btnLeave_Doc.setBackground(Color.WHITE);
		btnLeave_Doc.setBounds(684, 20, 93, 23);
		panel_Doc.add(btnLeave_Doc);
		
		lblDocCount_Doc = new JLabel("");
		lblDocCount_Doc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDocCount_Doc.setBounds(684, 308, 93, 15);
		panel_Doc.add(lblDocCount_Doc);
		
		lblSelectedCount_Doc = new JLabel("");
		lblSelectedCount_Doc.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSelectedCount_Doc.setBounds(581, 308, 93, 15);
		panel_Doc.add(lblSelectedCount_Doc);
		lblSelectedCount_Doc.setVisible(false);
		
		initDocCbs();
		initDocListeners();
		refreshTbDocs();
	}
	
	public void initDocCbs(){
		for(int i = 16; i <= 65; i++){
			cbAge_Doc.addItem(i);
		}
		cbTitle_Doc.addItem("初级医师");
		cbTitle_Doc.addItem("主管医师");
		cbTitle_Doc.addItem("副主任医师");
		cbTitle_Doc.addItem("主任医师");
		rs = dbHelper.getResultSet("SELECT Ono FROM office");
		try {
			while(rs.next()){
				cbOno_Doc.addItem(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cbOption_Doc.addItem("编号");
		cbOption_Doc.addItem("姓名");
		cbOption_Doc.addItem("性别");
		cbOption_Doc.addItem("年龄");
		cbOption_Doc.addItem("职称");
		cbOption_Doc.addItem("科室");
		cbOption_Doc.setSelectedIndex(-1);
		cbAge_Doc.setSelectedIndex(-1);
		cbTitle_Doc.setSelectedIndex(-1);
		cbOno_Doc.setSelectedIndex(-1);
	}
	
	public void initDocListeners(){
		btnGenerate_Doc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!tfName_Doc.getText().trim().equals("") && cbAge_Doc.getSelectedIndex() != -1 && cbTitle_Doc.getSelectedIndex() != -1 &&
						cbOno_Doc.getSelectedIndex() != -1 && (rbMan_Doc.isSelected() ^ rbWomen_Doc.isSelected())){
					Date date = new Date();
					SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
					String time = format.format(date);
					rs = dbHelper.getResultSet("SELECT Dno FROM doctor WHERE Dno>=" + time + "0000" + " ORDER BY Dno ASC");
					int lastNum = -1;
					String numStr = "";
					try {
						while(rs.next()){
							lastNum = rs.getInt(1);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if(lastNum == -1){
						numStr = "001";
					}
					else{
						lastNum = lastNum - lastNum/1000*1000 + 1;
						if(lastNum /10 >= 10){
							numStr = lastNum + "";
						}
						else if(lastNum / 10 >= 1){
							numStr = "0" + lastNum;
						}
						else{
							numStr = "00" + lastNum;
						}
					}
					lblDno_Doc.setText(time + "0" + numStr);
					lblPwd_Doc.setText(time + "0" + numStr);
				}
			}
		});
		btnEntry_Doc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!lblDno_Doc.getText().trim().equals("")){
					String sex = "";
					if(rbMan_Doc.isSelected()){
						sex = "\u7537";
					}
					else{
						sex = "\u5973";
					}
					dbHelper.execute("INSERT INTO doctor VALUES(" + lblDno_Doc.getText() + ", '" + 
							tfName_Doc.getText() + "', '" + sex + "', " + cbAge_Doc.getSelectedItem() + ",' " +
							cbTitle_Doc.getSelectedItem() + "',"+cbOno_Doc.getSelectedItem()+"," 
							+ lblPwd_Doc.getText() + ")");
					tfName_Doc.setText("");
					cbAge_Doc.setSelectedIndex(-1);
					cbTitle_Doc.setSelectedIndex(-1);
					cbOno_Doc.setSelectedIndex(-1);
					rbGroup_Doc.clearSelection();
					lblDno_Doc.setText("编号");
					lblPwd_Doc.setText("密码");
					refreshTbDocs();
				}
			}
		});
		btnQuery_Doc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!tfOption_Doc.getText().trim().equals("")){
					String queryStr = tfOption_Doc.getText();
					int selectedCount = tbDocs.getRowCount();
					tbDocs.setRowSelectionInterval(0, tbDocs.getRowCount() - 1);
					switch(cbOption_Doc.getSelectedIndex()){
					case -1:
						tbDocs.removeRowSelectionInterval(0, tbDocs.getRowCount() - 1);
						return;
					case 1://姓名
						for(int i = 0; i < tbDocs.getRowCount(); i++){
							if(!((String)tbDocs.getValueAt(i, 1)).contains(queryStr)){
								tbDocs.removeRowSelectionInterval(i, i);
								selectedCount--;
							}
						}
						break;
					case 0://编号
						for(int i = 0; i < tbDocs.getRowCount(); i++){
							if(!(tbDocs.getValueAt(i, 0) + "").equals(queryStr)){
								tbDocs.removeRowSelectionInterval(i, i);
								selectedCount--;
							}
						}
						break;
					case 5://科室
						for(int i = 0; i < tbDocs.getRowCount(); i++){
							if(!(tbDocs.getValueAt(i, 5) + "").equals(queryStr)){
								tbDocs.removeRowSelectionInterval(i, i);
								selectedCount--;
							}
						}
						break;
					case 2://性别
						for(int i = 0; i < tbDocs.getRowCount(); i++){
							if(!tbDocs.getValueAt(i, 2).equals("queryStr")){
								tbDocs.removeRowSelectionInterval(i, i);
								selectedCount--;
							}
						}
						break;
					case 3://年龄
						if(isAgeRange(queryStr)){
							String[] strs = queryStr.split("-");
							int bottom = Integer.parseInt(strs[0]);
							int top = Integer.parseInt(strs[1]);
							if(isDigit(strs[0]) && isDigit(strs[1]) && bottom <= top){
								for(int i = 0; i < tbDocs.getRowCount(); i++){
									int age = (int) tbDocs.getValueAt(i, 3);
									if(age < bottom || age > top){
										tbDocs.removeRowSelectionInterval(i, i);
										selectedCount--;
									}
								}
							}
						}
						else{
							tbDocs.removeRowSelectionInterval(0, tbDocs.getRowCount() - 1);
							return;
						}
						break;
					case 4://职称
						for(int i = 0; i < tbDocs.getRowCount(); i++){
							if(!(tbDocs.getValueAt(i, 4)).equals(queryStr)){
								tbDocs.removeRowSelectionInterval(i, i);
								selectedCount--;
							}
						}
						break;
					}
					lblSelectedCount_Doc.setText("找到：   " + selectedCount + "   个");
					lblSelectedCount_Doc.setVisible(true);
				}
			}
		});
		btnRevise_Doc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] selectedRows = tbDocs.getSelectedRows();
				if(selectedRows.length == 1 && btnRevise_Doc.getText().equals("修改")){
					reviseFlag_Doc = true;
					int selectedRow = tbDocs.getSelectedRow();
					btnRevise_Doc.setText("保存");
					DefaultTableModel dtm = new DefaultTableModel(null, new String[]{"编号", "姓名", "性别", "年龄", "职称", "科室", "密码"});
					dtm.addRow(new Object[]{tbDocs.getValueAt(selectedRow, 0), 
							tbDocs.getValueAt(selectedRow, 1), 
							tbDocs.getValueAt(selectedRow, 2),
							tbDocs.getValueAt(selectedRow, 3),
							tbDocs.getValueAt(selectedRow, 4),
							tbDocs.getValueAt(selectedRow, 5),
							tbDocs.getValueAt(selectedRow, 6)});
					tbDocs.setModel(dtm);
				}
				else{
					String selectedDno = tbDocs.getValueAt(0, 0) + "";
					String Dtitle = tbDocs.getValueAt(0, 4) + ""; //***********???????????***********
					String Dpwd = tbDocs.getValueAt(0, 6) + "";
					//System.out.println(Dtitle + Dpwd);
					try {
						Dtitle = new String((tbDocs.getValueAt(0, 4) + "").getBytes("gb2312"),"utf-8");
						dbHelper.execute("UPDATE doctor SET Dage='" + tbDocs.getValueAt(0, 3) + "', " +
								"Dtitle='" + Dtitle + "', " +
								"Ono='" + tbDocs.getValueAt(0, 5) + "', " +
								"Dpwd='" + Dpwd + "'" +
								" WHERE Dno=" + selectedDno);
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}
					btnRevise_Doc.setText("修改");
					reviseFlag_Doc = false;
					refreshTbDocs();
				}
			}
		});
		btnLeave_Doc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dbHelper.execute("DELETE FROM doctor WHERE Dno=" + tbDocs.getValueAt(tbDocs.getSelectedRow(), 0));
				refreshTbDocs();
			}
		});
	}
	
	public void refreshTbDocs(){
		rs = dbHelper.getResultSet("SELECT * FROM doctor");
		DefaultTableModel dtm = new DefaultTableModel(null, new String[]{"编号", "姓名", "性别", "年龄", "职称", "科室", "密码"});
		try {
			while(rs.next()){
				dtm.addRow(new Object[]{rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getString(7)});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tbDocs.setModel(dtm);
		lblDocCount_Doc.setText("总计：   " + dtm.getRowCount() + "   人");
	}
	
	public boolean isAgeRange(String str){
		if(!str.contains("-")){
			return false;
		}
		int minusCount = 0;
		int minusPlace = 0;
		for(int i = 0; i < str.length(); i++){
			if((str.charAt(i) < '0' || str.charAt(i) > '9') && str.charAt(i) != '-'){
				return false;
			}
			if(str.charAt(i) == '-'){
				minusCount++;
				minusPlace = i;
			}
		}
		if(minusCount != 1 || minusPlace == 0 || minusPlace == str.length() - 1){
			return false;
		}
		return true;
	}
	
	public boolean isDigit(String str){
		for(int i = 0; i < str.length(); i++){
			if(str.charAt(i) < '0' || str.charAt(i) > '9'){
				return false;
			}
		}
		return true;
	}
	
	public boolean isDouble(String str){
		int pos = -1;
		int pointCount = 0;
		for(int i = 0; i < str.length(); i++){
			if((str.charAt(i) < '0' || str.charAt(i) > '9') && str.charAt(i) != '.'){
				return false;
			}
			if(str.charAt(i) == '.'){
				pointCount++;
				pos = i;
			}
		}
		if(pointCount > 1 || pos == 0){
			return false;
		}
		return true;
	}
}
