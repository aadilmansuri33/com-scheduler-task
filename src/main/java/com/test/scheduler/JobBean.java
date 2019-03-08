package com.test.scheduler;

/**
 * @author inexture
 *
 */
public class JobBean {

	String delay;

	/**
	 * @return
	 */
	public String getDelay() {
		return delay;
	}

	/**
	 * @param delay
	 */
	public void setDelay(String delay) {
		this.delay = delay;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "delay" + delay;
	}
}
