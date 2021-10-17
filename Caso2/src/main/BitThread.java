package main;

public class BitThread extends Thread {
	
	private Monitor m;
	
	public BitThread(Monitor m) {
		this.m = m;
	}
	
	public void run() {
		while(true) {
			try {
				if ( m.getFinished() ) {
					break;
				}
				m.updateReferences();
				sleep(20);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
