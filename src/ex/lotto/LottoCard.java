package ex.lotto;
import java.util.Scanner;

//5page
public class LottoCard {
	public Lotto[] lotto= new Lotto[5];
	public int n=0;
	//자동
	public void auto_set() {
		if (this.n>=5) {
			System.out.println("용지 한도 초과");
		}else {
			Lotto tempLotto = new Lotto("12345");
			tempLotto.generate();
			this.lotto[n]=tempLotto;
			this.n+=1;
		}
	}
	//수동
	public void manual_set(int[] arr) {
		
		if (this.n>=5) {
			System.out.println("용지 한도 초과");
		}else {
			Lotto temp =new Lotto(arr);
			temp.verify();
			this.lotto[n]=temp;
			this.n+=1;
		}
	}

}
