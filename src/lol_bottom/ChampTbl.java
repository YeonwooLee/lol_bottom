package lol_bottom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import version_checker.VersionChecker;


public class ChampTbl {
	String champ;
	String nowVersion =VersionChecker.getVer_Underbar();
	//lol_data	yanoos@//192.168.219.101:1521/XE
	public ChampTbl(String champ) {
		this.champ = champ;
	}
	
	public ArrayList<String> mkBtList() throws ClassNotFoundException, SQLException {
		String url = "jdbc:oracle:thin:@192.168.219.101:1521/XE";
		String sql="SELECT * FROM USER_TABLES WHERE TABLE_NAME LIKE "+this.champ;
		sql="SELECT * FROM USER_TABLES WHERE TABLE_NAME LIKE "+this.champ+" AND TABLE_NAME LIKE '%"+nowVersion+"%'";
		ArrayList<String> tblList = new ArrayList<String>();
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con=DriverManager.getConnection(url,"yanoos","dudn0915");
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next()) {	
			String tblName = rs.getString("TABLE_NAME");
			
			tblList.add(tblName);
		}
		rs.close();
		st.close();
		con.close();
		
		return tblList;
	}
	
	
	
}
