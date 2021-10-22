package lol_bottom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import version_checker.VersionChecker;
	
public class ChampRow {
	String champ;
	String nowVersion =VersionChecker.getVer_Underbar();
	private String tableName;
	//lol_data	yanoos@//192.168.219.101:1521/XE
	public ChampRow(String champ,String tableName) {
		this.champ = champ;
		this.tableName = tableName;
	}
	public ArrayList<BottomDto> mkBtList() throws ClassNotFoundException, SQLException {
		String url = "jdbc:oracle:thin:@192.168.219.101:1521/XE";
		String sql="select * from "+this.tableName+" WHERE ENEMY_COMBI LIKE "+this.champ;
		sql="select * from "+this.tableName+" WHERE ENEMY_COMBI LIKE "+this.champ+" AND ENEMY_COMBI LIKE '%"+nowVersion+"%'";
		
		ArrayList<BottomDto> btList = new ArrayList<BottomDto>();
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con=DriverManager.getConnection(url,"yanoos","dudn0915");
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next()) {	
			String enemy_combi = rs.getString("ENEMY_COMBI");
			int whole = rs.getInt("WHOLE");
			int win = rs.getInt("WIN");
			int lose = rs.getInt("LOSE");
			float win_rate = rs.getFloat("WIN_RATE");
			
			BottomDto dto = new BottomDto();
			dto.setEnemy_combi(enemy_combi);
			dto.setWhole(whole);
			dto.setWin(win);
			dto.setLose(lose);
			dto.setWin_rate(win_rate);
			btList.add(dto);
		}
		rs.close();
		st.close();
		con.close();
		
		return btList;
	}
}
