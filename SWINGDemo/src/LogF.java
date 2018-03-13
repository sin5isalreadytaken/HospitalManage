import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.Toolkit;
import java.awt.Window.Type;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Color;
import javax.swing.JPasswordField;



@SuppressWarnings("serial")
public class LogF extends JFrame {

	public static DBHelper createdbHelper = new DBHelper(1);
	public static DBHelper dbHelper;
	public ResultSet rs = null;
	public static String logName = "";
	public static String logPwd = "";
	private JPanel contentPane;
	private JTextField tfLogName;
	private static LogF logF;
	private JPasswordField tfPassWord;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					logF = new LogF(){};
					logF.setVisible(true);
					logF.addWindowListener(new WindowListener(){
						@Override
						public void windowClosed(WindowEvent arg0) {
							// TODO Auto-generated method stub
							//System.out.println("111");
							dbHelper.disConn();
						}

						@Override
						public void windowActivated(WindowEvent arg0) {
							// TODO Auto-generated method stub
							
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
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public LogF() throws MySQLIntegrityConstraintViolationException {
		createdbHelper.execute("create database IF NOT EXISTS schoolh DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci");
		createdbHelper.execute("use schoolh");
		dbHelper = new DBHelper();
		dbHelper.execute("create table if not exists office"
								+"(Ono int check(Ono > 0 and Ono <100),"
								+"Oname varchar(16) not null,"
								+"Otype varchar(16) not null,"
								+" primary key(Ono))"
								+" ENGINE=InnoDB DEFAULT CHARSET=utf8");
		dbHelper.execute("create table if not exists doctor"
								+"(Dno int check(Dno > 1000000000 and Dno <10000000000),"
								+"Dname varchar(16) not null,"
								+"Dsex varchar(2) check(Dsex in('男','女')),"
								+"Dage int check(Dage > 0 and Dage < 100),"
								+"Dtitle varchar(16) not null,"
								+"Ono int check(Ono > 0 and Ono < 100),"
								+"Dpwd int not null,"
								+"primary key(Dno),"
								+"foreign key(Ono)references office(Ono)on delete set null on update cascade)"
								+" ENGINE=InnoDB DEFAULT CHARSET=utf8");
		dbHelper.execute("create table if not exists nurse"
								+"(Nno int check(Nno > 1000000000 and Nno <10000000000),"
								+"Nname varchar(16) not null,"
								+"Nsex varchar(2) check(Nsex in('男','女')),"
								+"Nage int check(Nage > 0 and Nage < 100),"
								+"Ntitle char(16) not null,primary key(Nno))"
								+" ENGINE=InnoDB DEFAULT CHARSET=utf8");
		dbHelper.execute("create table if not exists sickroom"
								+"(Rno int check(Rno >= 1 and Rno <= 999),"
								+"Bno int check(Bno >= 1000 and Bno <= 9999),"
								+"Bstate int(1) check(Bstate >=0 and Bstate <= 1),"
								+"PriceperDay double not null,"
								+"primary key(Bno))"
								+" ENGINE=InnoDB DEFAULT CHARSET=utf8");
		dbHelper.execute("create table if not exists patient"
								+"(Pno int check(Pno >= 10000000 and Pno <= 99999999),"
								+"Pname varchar(16) not null,"
								+"Psex varchar(2) check(Psex in('男','女')),"
								+"Page int check(Page > 0 and Page < 100),"
								+"Pmh varchar(255) not null,"
								+"primary key(Pno))"
								+" ENGINE=InnoDB DEFAULT CHARSET=utf8");
		dbHelper.execute("create table if not exists medicine"
								+"(Mno int check(Mno > 0 and Mno < 100000000),"
								+"Mname varchar(32) not null,"
								+"Mnum int check(Mnum >= 0 and Mnum < 100000000),"
								+"Mprice double check(Mprice >= 0 and Mprice <100000000),"
								+"primary key(Mno))"
								+" ENGINE=InnoDB DEFAULT CHARSET=utf8");
		dbHelper.execute("create table if not exists register"
								+"(Pno int check(Pno >= 10000000 and Pno <= 99999999),"
								+"Ono int check(Ono > 0 and Ono < 100),"
								+"Time varchar(255) not null,"
								+"primary key(Pno,Ono))"
								+" ENGINE=InnoDB DEFAULT CHARSET=utf8");
		dbHelper.execute("create table if not exists prescription"
								+"(Pno int check(Pno >= 10000000 and Pno <= 99999999),"
								+"Mno int check(Mno > 0 and Mno < 100000000),"
								+"Mnum int check(Mnum > 0 and Mnum < 100000000),"
								+"id int(11) not null auto_increment,"
								+"primary key(id))"
								+" ENGINE=InnoDB DEFAULT CHARSET=utf8");
		dbHelper.execute("create table if not exists hoslist"
								+"(Dno int check(Dno > 1000000000 and Dno <10000000000),"
								+"Pno int check(Pno >= 10000000 and Pno <= 99999999),"
								+"Hospitalized int not null,"
								+"primary key(Pno))"
								+" ENGINE=InnoDB DEFAULT CHARSET=utf8");
		dbHelper.execute("create table if not exists hospitalize"
								+"(Pno int check(Pno >= 10000000 and Pno <= 99999999),"
								+"Pname varchar(16) not null,"
								+"Rno int check(Rno >= 1 and Rno <= 999),"
								+"Bno int check(Bno >= 1000 and Bno <= 9999),"
								+"Intime varchar(255) not null,"
								+"primary key(Pno))"
								+" ENGINE=InnoDB DEFAULT CHARSET=utf8");
		dbHelper.execute("create table if not exists user"
								+"(userid varchar(255) not null,"
								+"pwd varchar(255) not null,"
								+"usermax int not null,"
								+"usernum int not null,"
								+"primary key(userid))"
								+" ENGINE=InnoDB DEFAULT CHARSET=utf8");
		try {
			rs = dbHelper.getResultSet("select count(userid) from user where userid  = 'register'" );
			rs.next();
			if (rs.getInt(1) == 0){
				dbHelper.execute("INSERT INTO user VALUES('register','register','3','0')");
			}
			rs = dbHelper.getResultSet("select count(userid) from user where userid  = 'medicine'" );
			rs.next();
			if (rs.getInt(1) == 0){
				dbHelper.execute("INSERT INTO user VALUES('medicine','medicine','3','0')");
			}
			rs = dbHelper.getResultSet("select count(userid) from user where userid  = 'sickroom'" );
			rs.next();
			if (rs.getInt(1) == 0){
				dbHelper.execute("INSERT INTO user VALUES('sickroom','sickroom','3','0')");
			}
			dbHelper.execute("DROP TRIGGER IF EXISTS addhospitalize");
			dbHelper.execute("DROP TRIGGER IF EXISTS delhospitalize");
			dbHelper.execute("DROP TRIGGER IF EXISTS adddoctor");
			dbHelper.execute("DROP TRIGGER IF EXISTS deldoctor");
			dbHelper.execute("CREATE TRIGGER addhospitalize AFTER INSERT ON hospitalize FOR EACH ROW UPDATE sickroom SET Bstate = 1 WHERE Bno = new.Bno");
			dbHelper.execute("CREATE TRIGGER delhospitalize BEFORE DELETE ON hospitalize FOR EACH ROW UPDATE sickroom SET Bstate = 0 WHERE Bno = old.Bno");
			dbHelper.execute("CREATE TRIGGER adddoctor AFTER INSERT ON doctor FOR EACH ROW INSERT INTO user VALUES(new.Dno,new.Dpwd,'1','0')");
			dbHelper.execute("CREATE TRIGGER deldoctor BEFORE DELETE ON doctor FOR EACH ROW DELETE FROM user WHERE userid = old.Dno");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		setFont(new Font("Dialog", Font.BOLD, 12));
		setIconImage(Toolkit.getDefaultToolkit().getImage("Img\\icon.png"));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("\u6821\u533B\u9662\u7BA1\u7406\u7CFB\u7EDFV1.0");
		this.setUndecorated(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
		this.setLocationRelativeTo(null);
		setBounds(100, 100, 355, 283);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogName = new JLabel("\u7528\u6237\u540D\uFF1A");
		lblLogName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLogName.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblLogName.setBounds(38, 56, 72, 21);
		contentPane.add(lblLogName);
		
		tfLogName = new JTextField();
		tfLogName.setBounds(107, 58, 177, 21);
		contentPane.add(tfLogName);
		tfLogName.setColumns(10);
		
		JLabel lblPassWord = new JLabel("\u5BC6\u7801\uFF1A");
		lblPassWord.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassWord.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblPassWord.setBounds(38, 106, 72, 21);
		contentPane.add(lblPassWord);
		
		JButton btnLogOn = new JButton("\u767B\u9646");
		btnLogOn.setBackground(new Color(255, 255, 255));
		btnLogOn.setFont(new Font("Dialog", Font.BOLD, 12));
		btnLogOn.setBounds(107, 150, 118, 36);
		contentPane.add(btnLogOn);
		
		tfPassWord = new JPasswordField();
		tfPassWord.setBounds(107, 107, 177, 21);
		contentPane.add(tfPassWord);
		btnLogOn.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent arg0) {
						logName = tfLogName.getText();
						logPwd = new String(tfPassWord.getPassword());
						if(logName.equals("") || logPwd.equals("")){
							JOptionPane.showMessageDialog(null, "请输入账号密码", "Error", JOptionPane.INFORMATION_MESSAGE);
						}
						else{
							if(logName.equals("admin") && logPwd.equals("admin")){
								logF.dispose();
								new ManageF();
							}
							else if(logName.equals("register") && logPwd.equals("register")){
								rs = dbHelper.getResultSet("SELECT usermax,usernum FROM user WHERE userid = 'register'");
								int userMax = 0,userNum = 0;
								try{
									rs.next();
									userMax = rs.getInt(1);
									userNum = rs.getInt(2);
									if (userNum < userMax){
										dbHelper.execute("UPDATE user SET usernum='" + (userNum + 1) + "' WHERE userid = 'register'");
										logF.dispose();
										new RegisterF();
									}
									else{
										JOptionPane.showMessageDialog(null, "当前登陆人数已达上限", "Error", JOptionPane.INFORMATION_MESSAGE);
									}
								}catch(SQLException e){
									System.out.print(e);
								}
							}
							else if(logName.equals("medicine")&& logPwd.equals("medicine")){
								rs = dbHelper.getResultSet("SELECT usermax,usernum FROM user WHERE userid = 'medicine'");
								int userMax = 0,userNum = 0;
								try{
									rs.next();
									userMax = rs.getInt(1);
									userNum = rs.getInt(2);
									if (userNum < userMax){
										dbHelper.execute("UPDATE user SET usernum='" + (userNum + 1) + "' WHERE userid = 'medicine'");
										logF.dispose();
										new MedicineF();
									}
									else{
										JOptionPane.showMessageDialog(null, "当前登陆人数已达上限", "Error", JOptionPane.INFORMATION_MESSAGE);
									}
								}catch(SQLException e){
									System.out.print(e);
								}
							}
							else if(logName.equals("sickroom")&& logPwd.equals("sickroom")){
								rs = dbHelper.getResultSet("SELECT usermax,usernum FROM user WHERE userid = 'sickroom'");
								int userMax = 0,userNum = 0;
								try{
									rs.next();
									userMax = rs.getInt(1);
									userNum = rs.getInt(2);
									if (userNum < userMax){
										dbHelper.execute("UPDATE user SET usernum='" + (userNum + 1) + "' WHERE userid = 'sickroom'");
										logF.dispose();
										new SickroomF();
									}
									else{
										JOptionPane.showMessageDialog(null, "当前登陆人数已达上限", "Error", JOptionPane.INFORMATION_MESSAGE);
									}
								}catch(SQLException e){
									System.out.print(e);
								}
							}
							else{
								int findFlag = 0;
								rs = dbHelper.getResultSet("select Dno,Dpwd from doctor");
								try {
									while(rs.next()){
										if(logName.equals(rs.getString(1))&& logPwd.equals(rs.getString(2))){
											findFlag = 1;
											DBHelper tmpdbHelper = new DBHelper();
											ResultSet tmprs = tmpdbHelper.getResultSet("SELECT usermax,usernum FROM user WHERE userid = '" + logName +  "'");
											int userMax = 0,userNum = 0;
											try{
												tmprs.next();
												userMax = tmprs.getInt(1);
												userNum = tmprs.getInt(2);
												if (userNum < userMax){
													tmpdbHelper.execute("UPDATE user SET usernum='" + (userNum + 1) + "' WHERE userid = '" + logName +  "'");
													logF.dispose();
													new DoctorF(Integer.parseInt(logName));
													
												}
												else{
													JOptionPane.showMessageDialog(null, "当前登陆人数已达上限", "Error", JOptionPane.INFORMATION_MESSAGE);
												}
												tmpdbHelper.disConn();
											}catch(SQLException e){
												System.out.print(e);
											}
										}
									}
									if(findFlag == 0)
										JOptionPane.showMessageDialog(null, "请输入正确账户与密码", "Error", JOptionPane.INFORMATION_MESSAGE);
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}
						}
					}
				});
	}
}
