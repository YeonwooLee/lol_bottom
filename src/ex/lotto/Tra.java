package ex.lotto;

import java.util.Arrays;
import java.util.Random;

public class Tra {
	private void generate() {
		//결과를 담을 배열
		int[] result = new int[6];
		//랜덤객체생성
		Random rand = new Random();
		for(int i=0;i<6;i++) {
			while (true){
				//시드변경
				long seed = System.currentTimeMillis();
				rand = new Random(seed);
				int tempnum= rand.nextInt(45)+1;
				boolean check= Arrays.asList(result).contains(tempnum);
				if (!check) {
					break;
				}
			}
			
		}

	public static void main(String[] args) {
		int[] a= {1,2,3,4,5};
		
		System.out.println(a[0]==2);
		
	}

}
