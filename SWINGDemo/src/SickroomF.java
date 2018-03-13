import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import java.beans.VetoableChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.Toolkit;

public class SickroomF extends JFrame {

	private JPanel contentPane;
	private int selectedRno, selectedBno;
	private int Pno;
	private String Pname;
	private DBHelper dbHelper = new DBHelper();
	private ResultSet rs = null;
	private JButton btnGet, btnRefresh, btnQuery, btnOut;
	private JList patientList, listRno, listBno;
	private JTextField tfQuery;
	private JTable tbHospitalized;
	private JScrollPane scrollPane_Pno, scrollPane_Rno, scrollPane_Bno, scrollPane_detailed;
	private JLabel lblState;
	private JLabel lblTotalCoast;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SickroomF frame = new SickroomF();
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
	public SickroomF() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("Img\\icon.png"));
		this.setTitle("住院管理");
		this.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				rs = dbHelper.getResultSet("SELECT usernum FROM user WHERE userid='sickroom'");
				int userNum = 0;
				try{
					rs.next();
					userNum = rs.getInt(1);
					userNum--;
					if (userNum < 0){
						userNum = 0;
					}
					dbHelper.execute("UPDATE user SET usernum = '"+userNum + "' WHERE userid = 'sickroom'");
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
		this.setVisible(true);
		
		initWidgets();
		refreshPatientList();
		refreshRoomList();
		refreshBedList();
		refreshHospitalizedDetails();
		initListeners();
	}
	
	public void initListeners(){
		listRno.addListSelectionListener(
				new ListSelectionListener(){
					public void valueChanged(ListSelectionEvent arg0) {
						refreshBedList();
					}
				});
		listBno.addListSelectionListener(
				new ListSelectionListener(){
					public void valueChanged(ListSelectionEvent e) {
						if(listBno.getSelectedIndex() != -1){
							selectedBno = (int)listBno.getSelectedValue();
							rs = dbHelper.getResultSet("SELECT * FROM sickroom WHERE Rno=" + selectedRno + " AND Bno=" + selectedBno);
							try {
								rs.next();
								if(rs.getInt(3) == 0){
									lblState.setBackground(Color.GREEN);
								}
								else{
									lblState.setBackground(Color.RED);
								}
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
					}
				});
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshPatientList();
			}
		});
		btnGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(lblState.getBackground() == Color.GREEN){
					Date date = new Date();
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time = format.format(date);
					if (patientList.isSelectionEmpty()){
						return;
					}
					Pno = Integer.parseInt((String) patientList.getSelectedValue());
					DBHelper tmpHelper = new DBHelper();
					ResultSet rs = tmpHelper.getResultSet("SELECT * FROM hoslist WHERE Pno=" + Pno);
					try {
						rs.next();
						Pname = rs.getString(2);
						if(Pname == "null"){
							refreshPatientList();
							refreshHospitalizedDetails();
							return;
						}		
						tmpHelper.disConn();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					dbHelper.execute("INSERT INTO hospitalize VALUES(" + Pno + ",'" + 
							Pname + "'," + selectedRno + "," + selectedBno + ",'" + time + "')");
					dbHelper.execute("DELETE FROM hoslist WHERE Pno=" + Pno);
					refreshPatientList();
					refreshHospitalizedDetails();
				}
			}
		});
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!tfQuery.getText().trim().equals("")){
					String strQuery = tfQuery.getText();
					//是数字
					if(isDigit(strQuery)){
						for(int i = 0; i < tbHospitalized.getRowCount(); i++){
							if((int)tbHospitalized.getValueAt(i, 0) == Integer.parseInt(strQuery)){
								tbHospitalized.setRowSelectionInterval(i, i);
								break;
							}
						}
					}
					//是病房病床号
					else if(isRnoBno(strQuery)){
						String[] Nos = strQuery.split(" ");
						for(int i = 0; i < tbHospitalized.getRowCount(); i++){
							if((int)tbHospitalized.getValueAt(i, 2) == Integer.parseInt(Nos[0]) && (int)tbHospitalized.getValueAt(i, 3) == Integer.parseInt(Nos[1])){
								tbHospitalized.setRowSelectionInterval(i, i);
								break;
							}
						}
					}
					//是病人名字
					else{
						for(int i = 0; i < tbHospitalized.getRowCount(); i++){
							if(((String)tbHospitalized.getValueAt(i, 1)).contains(strQuery)){
								tbHospitalized.setRowSelectionInterval(i, i);
								break;
							}
						}
					}
				}
			}
		});
		tbHospitalized.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent arg0) {
				int row = tbHospitalized.getSelectedRow();
				if(row != -1){
					long day = 0;
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						Date beginDate = format.parse((String)tbHospitalized.getValueAt(tbHospitalized.getSelectedRow(), 4));
						Date endDate = new Date();
						day = (endDate.getTime() - beginDate.getTime())/(24*60*60*1000);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					int Bno = (int) tbHospitalized.getValueAt(tbHospitalized.getSelectedRow(), 3);
					double pricePerday = 0;
					rs = dbHelper.getResultSet("SELECT PriceperDay FROM sickroom WHERE Bno="+Bno);
					try {
						rs.next();
						pricePerday = rs.getDouble(1);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					double price = day*pricePerday;
					lblTotalCoast.setText(price + "");
				}
			}
		});
		btnOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tbHospitalized.getSelectedRow() == -1){
					return;
				}
				int selectedPno = (int)tbHospitalized.getValueAt(tbHospitalized.getSelectedRow(), 0);
				dbHelper.execute("DELETE FROM hospitalize WHERE Pno=" + selectedPno);
				refreshHospitalizedDetails();
			}
		});
	}
	
	
	public void refreshBedList(){
		if(listRno.getSelectedIndex() != -1){
			selectedRno = (int)listRno.getSelectedValue();
			rs = dbHelper.getResultSet("SELECT * FROM sickroom WHERE Rno=" + selectedRno);
			DefaultListModel dlm = new DefaultListModel();
			try {
				while(rs.next()){
					dlm.addElement(rs.getInt(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			listBno.setModel(dlm);
		}
	}
	
	public void refreshRoomList(){
		rs = dbHelper.getResultSet("SELECT * FROM sickroom");
		DefaultListModel dlm = new DefaultListModel();
		try{
			while(rs.next()){
				boolean hasAdded = false;
				for(int i = 0; i < dlm.getSize(); i++){
					if(((int)dlm.get(i)) == rs.getInt(1)){
						hasAdded = true;
						break;
					}
				}
				if(!hasAdded){
					dlm.addElement(rs.getInt(1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		listRno.setModel(dlm);
	}
	
	public void refreshPatientList(){
		rs = dbHelper.getResultSet("SELECT * FROM hoslist WHERE Hospitalized=1");
		DefaultListModel dlm = new DefaultListModel();
		try {
			while(rs.next()){
				dlm.addElement(rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		patientList.setModel(dlm);
	}
	
	public void refreshHospitalizedDetails(){
		rs = dbHelper.getResultSet("SELECT * FROM hospitalize");
		DefaultTableModel dtm = new DefaultTableModel(null, new Object[]{"病号", "姓名",  "病房", "病床", "入住时间"});
		try {
			while(rs.next()){
				dtm.addRow(new Object[]{rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5)});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tbHospitalized.setModel(dtm);
	}
	
	public void initWidgets(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 671, 339);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane_Rno = new JScrollPane();
		scrollPane_Rno.setBounds(113, 38, 74, 219);
		contentPane.add(scrollPane_Rno);
		
		listRno = new JList();
		scrollPane_Rno.setViewportView(listRno);
		
		scrollPane_Bno = new JScrollPane();
		scrollPane_Bno.setBounds(197, 38, 74, 219);
		contentPane.add(scrollPane_Bno);
		
		listBno = new JList();
		scrollPane_Bno.setViewportView(listBno);
		
		JLabel lblNewLabel = new JLabel("\u75C5\u623F\u53F7");
		lblNewLabel.setBounds(113, 10, 54, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u75C5\u5E8A\u53F7");
		lblNewLabel_1.setBounds(197, 10, 47, 15);
		contentPane.add(lblNewLabel_1);
		
		btnGet = new JButton("\u5206\u914D");
		btnGet.setBounds(10, 267, 261, 23);
		contentPane.add(btnGet);
		
		scrollPane_Pno = new JScrollPane();
		scrollPane_Pno.setBounds(10, 38, 93, 219);
		contentPane.add(scrollPane_Pno);
		
		patientList = new JList();
		scrollPane_Pno.setViewportView(patientList);
		
		btnRefresh = new JButton("\u5237\u65B0");
		btnRefresh.setBounds(10, 6, 93, 23);
		contentPane.add(btnRefresh);
		
		scrollPane_detailed = new JScrollPane();
		scrollPane_detailed.setBounds(305, 10, 340, 247);
		contentPane.add(scrollPane_detailed);
		
		DefaultTableModel dtm = new DefaultTableModel(null, new Object[]{"病人", "病房", "病床", "入住时间"});
		tbHospitalized = new JTable(dtm);
		tbHospitalized.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_detailed.setViewportView(tbHospitalized);
		
		tfQuery = new JTextField();
		tfQuery.setBounds(305, 268, 92, 21);
		contentPane.add(tfQuery);
		tfQuery.setColumns(10);
		
		btnQuery = new JButton("\u67E5\u8BE2");
		btnQuery.setBounds(407, 267, 74, 23);
		contentPane.add(btnQuery);
		
		btnOut = new JButton("\u51FA\u9662");
		btnOut.setBounds(571, 267, 74, 23);
		contentPane.add(btnOut);
		
		lblState = new JLabel("");
		lblState.setOpaque(true);
		lblState.setBackground(Color.WHITE);
		lblState.setBounds(254, 10, 17, 15);
		contentPane.add(lblState);
		
		lblTotalCoast = new JLabel("");
		lblTotalCoast.setBounds(507, 271, 54, 15);
		contentPane.add(lblTotalCoast);
	}
	
	public boolean isDigit(String str){
		for(int i = 0; i < str.length(); i++){
			if(str.charAt(i) < '0' || str.charAt(i) > '9'){
				return false;
			}
		}
		return true;
	}
	
	public boolean isRnoBno(String str){
		if(!str.contains(" ")){
			return false;
		}
		for(int i = 0; i < str.length(); i++){
			if(str.charAt(i) < '0' || str.charAt(i) > '9' || str.charAt(i) != ' '){
				return false;
			}
		}
		return true;
	}
}
