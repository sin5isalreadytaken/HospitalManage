import java.sql.*;

public class DBHelper {
	private static String str = "jdbc:mysql://192.168.26.113:3306";
	public static final String user = "root";
	public static final String pwd = "o0o0o0o";
	
	public static final String url =str + "/schoolh?characterEncoding=UTF-8";
	private Connection conn = null;
	private Statement st = null;
	private ResultSet rs = null;
	
	public DBHelper(){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DBHelper(int x){
		//加载驱动,指定连接类型
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn(x);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void conn(int x){
		try {
			str += "?characterEncoding=UTF-8";
			conn = DriverManager.getConnection(str, user, pwd);
			st = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void conn(){
		try {
			conn = DriverManager.getConnection(url, user, pwd);
			st = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void disConn(){
		try {
			conn.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void execute(String sql){
		try {
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet getResultSet(String sql){
		try {
			rs = st.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
}
