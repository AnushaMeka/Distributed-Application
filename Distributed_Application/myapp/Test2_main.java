package myapp;

public class Test2_main {

		public static void main(String [] args) {
			Runnable r1 = new Test2(1);
			Runnable r2 = new Test2(2);
			for(int i = 0; i < 20; i++) {
				Thread thread1 = new Thread(r1);
				Thread thread2 = new Thread(r2);
				thread1.start();
				thread2.start();
			}
		}
}
