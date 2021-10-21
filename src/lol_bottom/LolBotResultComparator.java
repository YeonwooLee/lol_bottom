package lol_bottom;

import java.util.ArrayList;
import java.util.Comparator;

public class LolBotResultComparator implements Comparator <String>{

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public int compare(String a, String b) {
		// TODO Auto-generated method stub
		String[] arrA=a.split(",");
		String[] arrB=b.split(",");
		
		float winrateA;
		float winrateB;
		
		winrateA = Float.parseFloat(arrA[4]);
		winrateB = Float.parseFloat(arrB[4]);
		
		
		if(winrateA>winrateB) {
			return -1;
		}else if(winrateA<winrateB) {
			return 1;
		}
		return 0;
	}

}
