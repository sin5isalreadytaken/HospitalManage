import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.SwingConstants;

public class RegisterF extends JFrame {

	private static DBHelper dbHelper = new DBHelper();
	private ResultSet rs = null;
	private JPanel contentPane;
	private JTextField tfPno;
	private JTextField tfSearchOno;
	private JTable tbRegisterQueue, tbOnos;
	private JButton btnRegister, btnSearchOno;
	private JComboBox cbOno;
	private JButton btnRefresh;

	/**
	 * Create the frame.
	 */
	public RegisterF() {
		this.setTitle("挂号系统");
		this.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				rs = dbHelper.getResultSet("SELECT usernum FROM user WHERE userid='register'");
				int userNum = 0;
				try{
					rs.next();
					userNum = rs.getInt(1);
					userNum--;
					if (userNum < 0){
						userNum = 0;
					}
					dbHelper.execute("UPDATE user SET usernum = '"+userNum + "' WHERE userid = 'register'");
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
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Img\\icon.png"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 579, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setVisible(true);
		
		tfPno = new JTextField();
		tfPno.setBounds(10, 10, 93, 21);
		contentPane.add(tfPno);
		tfPno.setColumns(10);
		
		btnRegister = new JButton("\u6302\u53F7");
		btnRegister.setBackground(Color.WHITE);
		btnRegister.setBounds(179, 9, 66, 23);
		contentPane.add(btnRegister);
		
		tfSearchOno = new JTextField();
		tfSearchOno.setBounds(328, 10, 149, 21);
		contentPane.add(tfSearchOno);
		tfSearchOno.setColumns(10);
		
		btnSearchOno = new JButton("\u67E5\u8BE2");
		btnSearchOno.setBackground(Color.WHITE);
		btnSearchOno.setBounds(487, 9, 66, 23);
		contentPane.add(btnSearchOno);
		
		cbOno = new JComboBox();
		cbOno.setBackground(Color.WHITE);
		cbOno.setBounds(113, 10, 56, 21);
		contentPane.add(cbOno);
		
		JScrollPane scrollPane_RQ = new JScrollPane();
		scrollPane_RQ.setBounds(10, 41, 281, 210);
		contentPane.add(scrollPane_RQ);
		
		tbRegisterQueue = new JTable(){
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		tbRegisterQueue.getTableHeader().setReorderingAllowed(false);
		tbRegisterQueue.setFillsViewportHeight(true);
		scrollPane_RQ.setViewportView(tbRegisterQueue);
		
		JScrollPane scrollPane_Ono = new JScrollPane();
		scrollPane_Ono.setBounds(328, 41, 225, 210);
		contentPane.add(scrollPane_Ono);
		
		tbOnos = new JTable(){
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		tbOnos.getTableHeader().setReorderingAllowed(false);
		tbOnos.setFillsViewportHeight(true);
		tbOnos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane_Ono.setViewportView(tbOnos);
		
		btnRefresh = new JButton("R");
		btnRefresh.setFont(new Font("SimSun", Font.BOLD, 8));
		btnRefresh.setBackground(Color.WHITE);
		btnRefresh.setBounds(255, 9, 36, 23);
		contentPane.add(btnRefresh);
		
		refreshRegisterQueue();
		refreshOnos();
		initListeners();
	}
	
	public void initListeners(){
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshRegisterQueue();
			}
		});	
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!tfPno.getText().trim().equals("") && cbOno.getSelectedIndex() != -1){
					rs = dbHelper.getResultSet("SELECT * FROM patient");
					boolean findFlag = false;
					try {
						while(rs.next()){
							if(rs.getString(1).equals(tfPno.getText())){
								findFlag = true;
								break;
							}
						}
						if(findFlag){
							Date date = new Date();
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String time = format.format(date);
							dbHelper.execute("INSERT INTO register VALUES ( " + tfPno.getText() + "," + cbOno.getSelectedItem() + ", '"+ time + "')");
							refreshRegisterQueue();
						}
						else{
							JOptionPane.showMessageDialog(null, "查无此人", "Error", JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}	
				}
			}
		});
		tbOnos.addMouseListener(
				new MouseListener(){
					@Override
					public void mouseClicked(MouseEvent e) {
						int row =((JTable)e.getSource()).rowAtPoint(e.getPoint()); //获得行位置 
						int targetOno = (int)tbOnos.getValueAt(row, 1);
						for(int i = 0; i < cbOno.getItemCount(); i++){
							if((int)cbOno.getItemAt(i) == targetOno){
								cbOno.setSelectedIndex(i);
								break;
							}
						}
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

				});
		btnSearchOno.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						if(!tfSearchOno.getText().trim().equals("")){
							if(isDigit(tfSearchOno.getText())){
								//是数字
								for(int i = 0; i < tbOnos.getRowCount(); i++){
									if((int)tbOnos.getValueAt(i, 1) == Integer.parseInt(tfSearchOno.getText())){
										tbOnos.setRowSelectionInterval(i, i);
									}
								}
							}
							else{
								//是关键字
								for(int i = 0; i < tbOnos.getRowCount(); i++){
									if(((String)tbOnos.getValueAt(i, 0)).contains(tfSearchOno.getText())){
										tbOnos.setRowSelectionInterval(i, i);
									}
								}
							}
						}
					}
				});
	}
	
	public boolean isDigit(String str){
		for(int i = 0; i < str.length(); i++){
			if(str.charAt(i) < '0' || str.charAt(i) > '9'){
				return false;
			}
		}
		return true;
	}

	
	public void refreshOnos(){
		rs = dbHelper.getResultSet("SELECT * FROM office");
		DefaultTableModel dtm = new DefaultTableModel(null, new String[]{"科室类型", "科室号"});
		try {
			while(rs.next()){
				dtm.addRow(new Object[]{rs.getString(3), rs.getInt(1)});
				cbOno.addItem(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tbOnos.setModel(dtm);	
	}
	
	public void refreshRegisterQueue(){
		rs = dbHelper.getResultSet("SELECT * FROM register");
		DefaultTableModel dtm = new DefaultTableModel(null, new String[]{"学号", "科室号", "挂号时间"});
		try {
			while(rs.next()){
				dtm.addRow(new Object[]{rs.getInt(1), rs.getInt(2), rs.getString(3)});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tbRegisterQueue.setModel(dtm);
	}
}
