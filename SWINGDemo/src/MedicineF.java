import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Toolkit;

public class MedicineF extends JFrame {

	private DBHelper dbHelper = new DBHelper();
	private ResultSet rs = null;
	private JPanel contentPane;
	private JScrollPane scrollPane_patientList, scrollPane_Medicines;
	private JButton btnGet, btnRefresh, btnComplete;
	private JLabel lblTotalPrice;
	private JList patientList;
	private JTextArea taMedicines;
	private double totalPrice = 0;
	private int selectedPno = -1;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MedicineF frame = new MedicineF();
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
	public MedicineF() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("Img\\icon.png"));
		this.setTitle("取药系统");
		this.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				rs = dbHelper.getResultSet("SELECT usernum FROM user WHERE userid='medicine'");
				int userNum = 0;
				try{
					rs.next();
					userNum = rs.getInt(1);
					userNum--;
					if (userNum < 0){
						userNum = 0;
					}
					dbHelper.execute("UPDATE user SET usernum = '"+userNum + "' WHERE userid = 'medicine'");
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
		initListeners();
		refreshPatientList();
	}
	
	public void initWidgets(){
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 342);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnGet = new JButton("\u53D6\u836F");
		btnGet.setBounds(10, 10, 93, 23);
		contentPane.add(btnGet);
		
		btnRefresh = new JButton("\u5237\u65B0");
		btnRefresh.setBounds(10, 270, 93, 23);
		contentPane.add(btnRefresh);
		
		scrollPane_patientList = new JScrollPane();
		scrollPane_patientList.setBounds(10, 45, 93, 215);
		contentPane.add(scrollPane_patientList);
		
		scrollPane_Medicines = new JScrollPane();
		scrollPane_Medicines.setBounds(147, 45, 277, 248);
		contentPane.add(scrollPane_Medicines);
		
		taMedicines = new JTextArea();
		taMedicines.setEditable(false);
		scrollPane_Medicines.setViewportView(taMedicines);
		
		JLabel lblCount = new JLabel("\u603B\u8BA1\uFF1A");
		lblCount.setBounds(147, 10, 55, 23);
		contentPane.add(lblCount);
		
		lblTotalPrice = new JLabel("\u82B1\u8D39");
		lblTotalPrice.setBounds(212, 10, 76, 23);
		contentPane.add(lblTotalPrice);
		
		btnComplete = new JButton("\u53D6\u836F\u5B8C\u6210");
		btnComplete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnComplete.setBounds(331, 10, 93, 23);
		contentPane.add(btnComplete);
	}
	
	public void initListeners(){
		btnRefresh.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						refreshPatientList();
					}
				});
		btnGet.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						if(patientList.getSelectedIndex()== -1){
							return;
						}
						selectedPno = (int)patientList.getSelectedValue();
						if(selectedPno != -1){
							totalPrice = 0;
							taMedicines.setText("");
							taMedicines.append(selectedPno + "\r\n");
							rs = dbHelper.getResultSet("SELECT * FROM prescription WHERE Pno=" + selectedPno);
							try {
								while(rs.next()){
										DBHelper tmpHelper = new DBHelper();
										ResultSet tmpRs = null;
										tmpRs = tmpHelper.getResultSet("SELECT * FROM medicine");
										String medicineName = "";
										double medicineSinglePrice = 0;
										while(tmpRs.next()){
											if(tmpRs.getInt(1) == rs.getInt(2)){
												medicineName = tmpRs.getString(2);
												medicineSinglePrice = tmpRs.getDouble(4);
												tmpHelper.disConn();
												break;
											}
										}
										taMedicines.append(medicineName + "\t" + rs.getInt(3) + "\r\n");
										totalPrice += rs.getInt(3) * medicineSinglePrice;
								}
								lblTotalPrice.setText(totalPrice + " 元");
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
					}
				});
		btnComplete.addActionListener(
				new ActionListener(){
					//完成后要复位一些数据
					public void actionPerformed(ActionEvent arg0) {
						if(selectedPno != -1){
							taMedicines.setText("");
							lblTotalPrice.setText("");
							totalPrice = 0;
							rs = dbHelper.getResultSet("SELECT Mno,Mnum FROM prescription WHERE Pno = "+selectedPno);
							try {
								while(rs.next()){
									int mno = rs.getInt(1);
									int mnum = rs.getInt(2);
									DBHelper tmpdb = new DBHelper();
									ResultSet tmprs = tmpdb.getResultSet("SELECT Mnum FROM medicine WHERE Mno = " + mno);
									tmprs.next();
									mnum = tmprs.getInt(1) - mnum;
									tmpdb.execute("UPDATE medicine SET Mnum = " + mnum + " WHERE Mno = " + mno);
									tmpdb.disConn();
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
							dbHelper.execute("DELETE FROM prescription WHERE Pno=" + selectedPno);
							selectedPno = -1;
							refreshPatientList();
						}
					}
				});
	}
	
	public void refreshPatientList(){
		rs = dbHelper.getResultSet("SELECT * FROM prescription");
		DefaultListModel dlm = new DefaultListModel();
		try {
			boolean sameFlag = false;
			while(rs.next()){
				for(int i = 0; i < dlm.getSize(); i++){
					if((int)dlm.get(i) == rs.getInt(1)){
						sameFlag = true;
						break;
					}
				}
				if(!sameFlag){
					dlm.addElement(rs.getInt(1));
				}
			}
			patientList = new JList(dlm);
			scrollPane_patientList.setViewportView(patientList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
