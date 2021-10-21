package lol_bottom;

public class BottomDto {
	public String Enemy_combi;
	public int whole;
	public int win;
	public int lose;
	public float win_rate;
	String adc;
	String sup;
	
	public String getEnemy_combi() {
		return Enemy_combi;
	}
	public int getWhole() {
		return whole;
	}
	public int getWin() {
		return win;
	}
	public int getLose() {
		return lose;
	}
	public float getWin_rate() {
		return win_rate;
	}
	public void setEnemy_combi(String enemy_combi) {
		Enemy_combi = enemy_combi;
		String[] temparr=enemy_combi.split("_");
		this.adc=temparr[0];
		this.sup=temparr[1];
		
		
	}
	public void setWhole(int whole) {
		this.whole = whole;
	}
	public void setWin(int win) {
		this.win = win;
	}
	public void setLose(int lose) {
		this.lose = lose;
	}
	public void setWin_rate(float win_rate) {
		this.win_rate = win_rate;
	}


}
