package ex.hw;
import java.util.Scanner;

import ex.lotto.*;

public class LottoSim {
	public static int[] userin() {
		//6-2 각 번호가 유요한 번호인지 확인후 처리=무한루프
		while(true) {
			Scanner sc = new Scanner(System.in);
			System.out.print("번호:");
			String numbers = sc.nextLine();
			String[] numberToArr = numbers.split(",");
			
			int[] result_arr = new int[6];
			for(int i=0;i<numberToArr.length;i++) {
				//개별 숫자1~45 범위 넘어가는건 고려 안하겠음
				result_arr[i]=Integer.parseInt(numberToArr[i]);
			}
			Lotto temp = new Lotto(result_arr);
			boolean check = temp.verify(); 
			if(check) {
				System.out.print("입력된 번호>>> ");
				for(int j=0;j<numberToArr.length;j++) {
					System.out.print(numberToArr[j]+" ");
				}
				System.out.println("");
				return temp.lottoNumber;
			}else {
				System.out.println("잘못된 번호입니다 다시 입력해주세요");
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//자동5개
		System.out.println("자동 게임의 번호입니다");
		LottoCard a = new LottoCard();
		for (int i=0;i<5;i++) {
			System.out.print(String.valueOf(i+1)+"번째 게임 번호:");
			a.auto_set();
			a.lotto[i].show();
		}
		
		//수동3개
		LottoCard b = new LottoCard();
		for(int i=0;i<3;i++) {
			System.out.println("\n로또번호를 입력해주세요 각 숫자는 컴마로 구분해주세요 ex 1,2,3,4,5,6 ("
					+String.valueOf(i+1)+"번째 게임입니다.)");
			int[] tempIntArr=userin();
			if (tempIntArr!=null) {
				b.manual_set(tempIntArr);
			}
		}
		System.out.println("\n수동 게임의 번호입니다");
		for(int i=0;i<3;i++) {
			System.out.print(String.valueOf(i+1)+"번째 게임 번호:");
			b.lotto[i].show();
		}
	}

}
