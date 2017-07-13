package rusky.husky;

public class RepeatedThread {
	
	private boolean running;
	private Runnable task;
	public long interval;
	
	
	public RepeatedThread(Runnable task, long _interval){
		this.task = task;
		this.interval = _interval;
	}
	
	public boolean cancel(){
		return running || (running = false);
	}
	
	public void run(){
		running = true;
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(running){
					task.run();
					try {
						Thread.sleep(interval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
