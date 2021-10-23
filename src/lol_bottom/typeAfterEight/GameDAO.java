package lol_bottom.typeAfterEight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import lol_bottom.BottomDto;
import version_checker.VersionChecker;

public class GameDAO {
	private PreparedStatement pstmt;
	private Connection con;
	String champ;
	String champ2;
	String nowVersion =VersionChecker.getVer(); 
	public GameDAO(String champ) {
		this.champ=champ;
	}
	//한쪽팀 원딜만 아는 경우
	public ArrayList<GameDTO> listGames() throws ClassNotFoundException, SQLException{
		String url = "jdbc:oracle:thin:@192.168.219.101:1521/XE";
	
		String sql="SELECT LOL_BLUE.WIN AS BWIN, LOL_RED.WIN AS RWIN, LOL_BLUE.ADC AS BADC, LOL_RED.ADC AS RADC, LOL_BLUE.SUP AS BSUP, LOL_RED.SUP AS RSUP FROM LOL_BLUE INNER JOIN LOL_RED ON LOL_BLUE.GAMEID=LOL_RED.GAMEID INNER JOIN LOL_TIME_V ON LOL_RED.GAMEID=lol_time_v.gameid WHERE (lol_time_v.version='11.20' AND (lol_red.adc='"+this.champ+"' OR LOL_BLUE.ADC='"+this.champ+"'))";
		sql="SELECT LOL_BLUE.WIN AS BWIN, LOL_RED.WIN AS RWIN, LOL_BLUE.ADC AS BADC, LOL_RED.ADC AS RADC, LOL_BLUE.SUP AS BSUP, LOL_RED.SUP AS RSUP FROM LOL_BLUE INNER JOIN LOL_RED ON LOL_BLUE.GAMEID=LOL_RED.GAMEID INNER JOIN LOL_TIME_V ON LOL_RED.GAMEID=lol_time_v.gameid WHERE (lol_time_v.version='"+nowVersion+"' AND (lol_red.adc='"+this.champ+"' OR LOL_BLUE.ADC='"+this.champ+"'))";
		
		
		ArrayList<GameDTO> gameList = new ArrayList<GameDTO>();
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con=DriverManager.getConnection(url,"yanoos","dudn0915");
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next()) {	
			String bwin = rs.getString("BWIN");
			String badc = rs.getString("BADC");
			String bsup = rs.getString("BSUP");
			
			String rwin = rs.getString("RWIN");
			String radc = rs.getString("RADC");
			String rsup = rs.getString("RSUP");
			
			
			GameDTO dto = new GameDTO();
			dto.setBwin(bwin);
			dto.setBadc(badc);
			dto.setBsup(bsup);
			
			dto.setRwin(rwin);
			dto.setRadc(radc);
			dto.setRsup(rsup);
			gameList.add(dto);
		}
		rs.close();
		st.close();
		con.close();
		
		return gameList;

		
	}
	
	//한쪽팀 서폿만 아는 경우
	public ArrayList<GameDTO> listGames2() throws ClassNotFoundException, SQLException{
		String url = "jdbc:oracle:thin:@192.168.219.101:1521/XE";
		String sql="SELECT LOL_BLUE.WIN AS BWIN, LOL_RED.WIN AS RWIN, LOL_BLUE.ADC AS BADC, LOL_RED.ADC AS RADC, LOL_BLUE.SUP AS BSUP, LOL_RED.SUP AS RSUP FROM LOL_BLUE INNER JOIN LOL_RED ON LOL_BLUE.GAMEID=LOL_RED.GAMEID INNER JOIN LOL_TIME_V ON LOL_RED.GAMEID=lol_time_v.gameid WHERE (lol_time_v.version='11.20' AND (lol_red.sup='"+this.champ+"' OR LOL_BLUE.SUP='"+this.champ+"'))";
		sql="SELECT LOL_BLUE.WIN AS BWIN, LOL_RED.WIN AS RWIN, LOL_BLUE.ADC AS BADC, LOL_RED.ADC AS RADC, LOL_BLUE.SUP AS BSUP, LOL_RED.SUP AS RSUP FROM LOL_BLUE INNER JOIN LOL_RED ON LOL_BLUE.GAMEID=LOL_RED.GAMEID INNER JOIN LOL_TIME_V ON LOL_RED.GAMEID=lol_time_v.gameid WHERE (lol_time_v.version='"+nowVersion+"' AND (lol_red.sup='"+this.champ+"' OR LOL_BLUE.SUP='"+this.champ+"'))";
		
		ArrayList<GameDTO> gameList = new ArrayList<GameDTO>();
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con=DriverManager.getConnection(url,"yanoos","dudn0915");
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next()) {	
			String bwin = rs.getString("BWIN");
			String badc = rs.getString("BADC");
			String bsup = rs.getString("BSUP");
			
			String rwin = rs.getString("RWIN");
			String radc = rs.getString("RADC");
			String rsup = rs.getString("RSUP");
			
			
			GameDTO dto = new GameDTO();
			dto.setBwin(bwin);
			dto.setBadc(badc);
			dto.setBsup(bsup);
			
			dto.setRwin(rwin);
			dto.setRadc(radc);
			dto.setRsup(rsup);
			gameList.add(dto);
		}
		rs.close();
		st.close();
		con.close();
		
		return gameList;

		
	}
	
	//한쪽팀 둘 다 아는 경우
	public ArrayList<GameDTO> listGames3() throws ClassNotFoundException, SQLException{
		String url = "jdbc:oracle:thin:@192.168.219.101:1521/XE";
		String sql="SELECT LOL_BLUE.WIN AS BWIN, LOL_RED.WIN AS RWIN, LOL_BLUE.ADC AS BADC, LOL_RED.ADC AS RADC, LOL_BLUE.SUP AS BSUP, LOL_RED.SUP AS RSUP FROM LOL_BLUE INNER JOIN LOL_RED ON LOL_BLUE.GAMEID=LOL_RED.GAMEID INNER JOIN LOL_TIME_V ON LOL_RED.GAMEID=lol_time_v.gameid WHERE (lol_time_v.version='11.20' AND (lol_red.adc='"+this.champ+"' and lol_red.sup='"+this.champ2+"') OR (LOL_BLUE.adc='"+this.champ+"' and lol_blue.sup='"+this.champ2+"'))";
		sql="SELECT LOL_BLUE.WIN AS BWIN, LOL_RED.WIN AS RWIN, LOL_BLUE.ADC AS BADC, LOL_RED.ADC AS RADC, LOL_BLUE.SUP AS BSUP, LOL_RED.SUP AS RSUP FROM LOL_BLUE INNER JOIN LOL_RED ON LOL_BLUE.GAMEID=LOL_RED.GAMEID INNER JOIN LOL_TIME_V ON LOL_RED.GAMEID=lol_time_v.gameid WHERE (lol_time_v.version='"+nowVersion+"' AND (lol_red.adc='"+this.champ+"' and lol_red.sup='"+this.champ2+"') OR (LOL_BLUE.adc='"+this.champ+"' and lol_blue.sup='"+this.champ2+"'))";
		ArrayList<GameDTO> gameList = new ArrayList<GameDTO>();
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con=DriverManager.getConnection(url,"yanoos","dudn0915");
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next()) {	
			String bwin = rs.getString("BWIN");
			String badc = rs.getString("BADC");
			String bsup = rs.getString("BSUP");
			
			String rwin = rs.getString("RWIN");
			String radc = rs.getString("RADC");
			String rsup = rs.getString("RSUP");
			
			
			GameDTO dto = new GameDTO();
			dto.setBwin(bwin);
			dto.setBadc(badc);
			dto.setBsup(bsup);
			
			dto.setRwin(rwin);
			dto.setRadc(radc);
			dto.setRsup(rsup);
			gameList.add(dto);
		}
		rs.close();
		st.close();
		con.close();
		
		return gameList;	
	}
	
	//양팀 원딜 아는 경우
	public ArrayList<GameDTO> listGames_bothAdc() throws ClassNotFoundException, SQLException{
		String url = "jdbc:oracle:thin:@192.168.219.101:1521/XE";
		
		String sql="SELECT LOL_BLUE.WIN AS BWIN, \r\n" + 
				"        LOL_BLUE.ADC AS BADC,\r\n" + 
				"        LOL_BLUE.SUP AS BSUP, \r\n" + 
				"        LOL_RED.WIN AS RWIN, \r\n" + 
				"        LOL_RED.ADC AS RADC, \r\n" + 
				"        LOL_RED.SUP AS RSUP \r\n" + 
				"            FROM LOL_BLUE INNER JOIN LOL_RED ON LOL_BLUE.GAMEID=LOL_RED.GAMEID \r\n" + 
				"            INNER JOIN LOL_TIME_V ON LOL_RED.GAMEID=lol_time_v.gameid \r\n" + 
				"            WHERE \r\n" + 
				"            (lol_time_v.version='11.20' \r\n" + 
				"            AND (lol_red.adc='"+this.champ+"' and lol_blue.adc='"+this.champ2+"') \r\n" + 
				"                    OR (LOL_BLUE.adc='"+this.champ+"' and lol_red.adc='"+this.champ2+"'))";
		ArrayList<GameDTO> gameList = new ArrayList<GameDTO>();
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con=DriverManager.getConnection(url,"yanoos","dudn0915");
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next()) {	
			String bwin = rs.getString("BWIN");
			String badc = rs.getString("BADC");
			String bsup = rs.getString("BSUP");
			
			String rwin = rs.getString("RWIN");
			String radc = rs.getString("RADC");
			String rsup = rs.getString("RSUP");
			
			
			GameDTO dto = new GameDTO();
			dto.setBwin(bwin);
			dto.setBadc(badc);
			dto.setBsup(bsup);
			
			dto.setRwin(rwin);
			dto.setRadc(radc);
			dto.setRsup(rsup);
			gameList.add(dto);
		}
		rs.close();
		st.close();
		con.close();
		
		return gameList;	
	}
}
