package io.github.xnovo3000.sjll.data;

public class Flag {
	
	private boolean flag;
	
	public Flag() {
		this.flag = false;
	}
	
	public void set() {
		flag = true;
	}
	
	public boolean isSet() {
		return flag;
	}

}
