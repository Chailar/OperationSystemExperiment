package OperationSystem;
public class PerThread extends Thread {

	private static int[] chopstick = { 1, 1, 1, 1, 1 };
	private int i;

	public PerThread(int i) {
		this.i = i;
	}

	@Override
	public void run() {

		synchronized (chopstick) {  
		eat(this.getName());

		think(this.getName());
		}

	}

	private void think(String name) {
		chopstick[i] = 1;
		chopstick[(i + 1) % 5] = 1;
		System.out.println(i+"号哲学家"+"正在思考问题...");
		
	}

	private void eat(String string) {

			while (true) {

				if (chopstick[i] != 0) {
					chopstick[i]--;
					System.out.println(i+"号哲学家" + " 获得左手的筷子.");
					break;
				}

			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			while (true) {
				if (chopstick[(i + 1) % 5] != 0) {
					chopstick[(i + 1) % 5]--;
					System.out.println(i+"号哲学家" 
                    + "获得右手的筷子.");
					break;
				}

			}
		System.out.println(i+"号哲学家" + " 正在吃饭...");
	}
	
	
	
    public static void executePerThread() {

		
        for (int i = 0; i < 5; i++) {
			
        	Thread t = new PerThread(i);

            t.start();  
		}		
	}
    public static void main(String[] args) {
        executePerThread();
    }
}