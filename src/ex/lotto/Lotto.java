package ex.lotto;

import java.util.Arrays;
import java.util.Random;

public class Lotto {
	
	//3page 속성=객체변수 = 멤버 변수 =인스턴수 변수
	//로또번호 유지 할당 크기는 6
	public int[] lottoNumber = new int[6];
	//현재 배열에 설정된 숫자들이 유효한 로또번호인지를 나타냄
	public boolean isValid;
	
	//4page 생성자 오버로딩
	public Lotto(int[] arr) {
		//arr의 길이가 6이 아니면 lottoNumber를 -32으로 채움(이래야 isvalid에서 걸러지면서 range오류도 피함) 
		if (arr.length!=6) {
			for (int i=0; i<6;i++) {
				this.lottoNumber[i]=-32; //-32인 이유는 범위오류냐 중복오류냐 구분할때 -32인거 보고 판단
			}
		}else { //수 6개 들어왔으면 그대로 반영 (중복수는 isvalid에서 거르도록)
			for (int i=0; i<arr.length;i++) {
				this.lottoNumber[i]=arr[i];
			}
		}
	}
	public static void main(String[] args) {
		Lotto a = new Lotto("1,12,13,42,3,1");
	}
	public Lotto(String num) {
		//arr의 길이가 6이 아니면 lottoNumber를 0으로 채움(이래야 isvalid에서 걸러지면서 range오류도 피함)
		String[] numArr =num.split(",");
		
		if (numArr.length!=6) {
			for (int i=0; i<6;i++) {
				this.lottoNumber[i]=-32;
			}
		}else { //수 6개 들어왔으면 그대로 반영 (중복수는 isvalid에서 거르도록)
			for (int i=0; i<numArr.length;i++) {
				//지금 기입할 수
				String nowNumber = numArr[i];
				//nowNumber int로 형변환
				int toInt = Integer.parseInt(nowNumber);
				//속성 lottoNumber에 기입						
				this.lottoNumber[i]=toInt;
			}
		}
	}
	
	public boolean contain_check(int[] arr, int num) {
		for(int i=0;i<arr.length;i++) {
			if(arr[i]==num) {
				return true;
			}
		}
		return false;
		
	}
	//p4-2 boolean 번호유효성검사, 길이는 생성자에서 6으로 맞춰놨으니 여기선 중복체크만함
	//정상자료면  true, 아니면  false return하며, this.isValid에 반영
	public boolean verify() {
		//검사용 임시배열
		int[] newArr = new int[6];
		
		//this.lottonumber의 길이(길이는 무조건 6_생성자 참고)만큼 for loop, 
		for(int i=0;i<this.lottoNumber.length;i++) {
			//중복체크 check==true면 중복
			boolean check = contain_check(newArr,lottoNumber[i]);
			//lottonumber[i]가 newArr에 있으면 false 반환 
			if (check) {
				this.isValid=false;
				return false;
				
			//아니면 newArr에 lottonumber[i] 삽입
			}else {
				newArr[i]=lottoNumber[i];
			}
		}
		//여까지 왔으면 중복 없는것임
		this.isValid=true;
		return true;
	}
	
	//p4-3
	public void show() {
		if (this.isValid==true) {
			for (int i=0;i<this.lottoNumber.length;i++) {
				System.out.print(this.lottoNumber[i]);
				System.out.print(" ");
			}
			System.out.println("");
		}else {
			int[] temp = {-32,-32,-32,-32,-32,-32};
			if (this.lottoNumber== temp) {
				System.out.println("비정상적인 로또번호입니다(6자리수 아님).");
			}else {
				System.out.println("비정상적인 로또번호입니다(중복번호 존재).");
			}
		}
	}
	
	//4-4
	public int[] getNumbers() {
		if (this.isValid==true) {
			return this.lottoNumber;
		}else {
			return null;
		}
	}
	
	//4-5
	public void generate() {
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
				boolean check = contain_check(result,tempnum);
				if (!check) {
					result[i]=tempnum;
					break;
				}
			}
		}
		this.lottoNumber=result;
		this.verify();
	}
}
