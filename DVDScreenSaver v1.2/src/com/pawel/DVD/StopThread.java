package com.pawel.DVD;

public class StopThread {
	
	private Thread xMoveLogo;
	private Thread yMoveLogo;
	private MoveLogoPanel mlp;
	private MoveLogoThreads mlt;

	public StopThread(Thread xMoveLogo, Thread yMoveLogo, MoveLogoPanel mlp, MoveLogoThreads mlt) {
		this.xMoveLogo = xMoveLogo;
		this.yMoveLogo = yMoveLogo;
		this.mlp = mlp;
		this.mlt = mlt;

	}
	
	void stop() {
		//stop() method used instead of interrupt() to stop the threads as the latter causes errors, 
		//so the code below is kind of a workaround
		mlt.getThreadX().stop();
		mlt.getThreadY().stop();				
		//sets the two threads to null so they can be ran again after stopping
		mlt.setThreadX(null);
		mlt.setThreadY(null);
		mlt.setLogoMoveSpeed(50);
		//sets the coords to values way beyond the window to make it "disappear" again
		mlp.setY(10000); 
		mlp.setX(10000);
	}
}
