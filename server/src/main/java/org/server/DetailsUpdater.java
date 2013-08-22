package org.server;

public class DetailsUpdater implements Runnable {

	private int interval = 1000;
	private boolean rerun = true;
	
	public DetailsUpdater() {
        new Thread(this).start();
	}
	
	public void run() {
	    while(rerun)
	    {
	    	Server.updateDetailPanelsData();
	    	Server.pushDataToStorage();
	        try{
	            Thread.sleep(interval);
	        } catch(Exception e){
	        	e.printStackTrace();
	        }

	    }
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public boolean isRerun() {
		return rerun;
	}

	public void setRerun(boolean rerun) {
		this.rerun = rerun;
	}

}
