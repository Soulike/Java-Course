
public class Catalan {
	
	public static int answers = 0;
	
	//请实现go函数
	public static void go(Deque from, Deque to, Stack s) {
		
	}

	public static void main(String[] args) {
		Deque from = new Deque();
		Deque to = new Deque();
		Stack s = new Stack();
		
		for(int i=1;i<=7;i++) {
			from.addLast(i);
		}
		
		go(from, to, s);
		
		System.out.println(answers);
		

	}

}
