package version_checker;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class VersionChecker {

	public static String getVer() {
		String line = null;
		String result =null;
		try {
			
			BufferedReader br = new BufferedReader(new FileReader("C:/Users/yeonw/eclipse-workspace/lol_bottom/src/version_checker/nowVer.txt"));
			while(true) {
				line = br.readLine();
				if(line ==null) break;
				System.out.println("현재버전:"+line);
				result = line;
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
		
	}
	
	public static String getVer_Underbar() {
		String line = null;
		String result =null;
		String result_underbar;
		try {
			
			BufferedReader br = new BufferedReader(new FileReader("C:/Users/yeonw/eclipse-workspace/lol_bottom/src/version_checker/nowVer.txt"));
			while(true) {
				line = br.readLine();
				if(line ==null) break;
				System.out.println("현재버전:"+line);
				result = line;
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] temparr = result.split("\\.");

		result_underbar = temparr[0]+"_"+temparr[1];
		return result_underbar;
		
		
	}
	public static void main(String[] args) {
		String a = VersionChecker.getVer_Underbar();
		System.out.println(a);
	}
	

}
