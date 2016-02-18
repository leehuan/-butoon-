package cn.wuyun.safe.bean;

public class BlackNumberBean {
	public String blacknumber;
	public int mode;

	public BlackNumberBean(String blacknumber, int mode) {
		this.blacknumber = blacknumber;
		this.mode = mode;
	
	}

	@Override
	public String toString() {
		return "BlackNumberBean [blacknumber=" + blacknumber + ", mode=" + mode
				+ "]";
	}

}
