package lol_bottom;

public class Trash {
	int a=0;
	
	void toto() {
		a=3;
	}
	
	public static void main(String[] args) {
		Trash aa = new Trash();
		aa.toto();
		System.out.println(aa.a);
		
		Trash bb= new Trash();
		System.out.println(bb.a);

	}

}
