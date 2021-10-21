package ex.lotto;

public class Lotto2 {
   private int lotto_len=6; //이렇게 해두는것은 좋음 
   int[] lottoNumber = new int[lotto_len];
   
   boolean isValid = true; //굳이 초기화해둘 필요 없어보임
   //왜냐면 내가 모르는 코드의 빈틈으로 
   
   //생성자 오버로딩해야되는데 하나만 있음
   public Lotto2(int[] lottoNumber) {
      this.lottoNumber = lottoNumber;
   }
   
   

   public boolean verify(int[] lottoNumber) {
      int i;
      for(i=0;i<lottoNumber.length-1;i++) {
         if(lottoNumber[i]==lottoNumber[i+1])
            isValid=false;
         if(lottoNumber[i]>45)
            isValid=false;
      }
      if(lottoNumber[i+1]>45)
         isValid=false;
         
      return isValid;   
   }
   
   void show(int[] lottoNumber) {
      if(verify(lottoNumber))
         System.out.println(lottoNumber);
      System.out.println("유효하지 않습니다.");
      
   }
   
   int[] getNumbers(int[] lottoNumber) {
      if(verify(lottoNumber))
         return lottoNumber;
      return null;
   }
   
   void generate() {
      //모르겠다ㅏㅏ아아
      int rand;
      do {
         rand = (int)(Math.random()*45+1);
      }while();
   }
}