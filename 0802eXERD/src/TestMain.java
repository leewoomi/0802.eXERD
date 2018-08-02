
public class TestMain {

	public static void main(String[] args) {

		T obj1 = T.getInstance();
		T obj2 = T.getInstance();
		
		
		System.out.println(System.identityHashCode(obj1));
		System.out.println(System.identityHashCode(obj2));
		
	
		int a = 10;
		int b = 10;
		System.out.println(System.identityHashCode(a));
		System.out.println(System.identityHashCode(b));
	}

}

