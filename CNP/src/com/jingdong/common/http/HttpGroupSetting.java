package com.jingdong.common.http;

public class HttpGroupSetting {

	public static final int PRIORITY_FILE = 500;
	public static final int PRIORITY_IMAGE = 5000;
	public static final int PRIORITY_JSON = 1000;
	public static final int TYPE_FILE = 500;
	public static final int TYPE_IMAGE = 5000;
	public static final int TYPE_JSON = 1000;
	private int priority;
	private int type;


	public int getPriority() {
		return priority;
	}

	public int getType() {
		return type;
	}

	public void setPriority(int i) {
		priority = i;
	}

	public void setType(int i) {
		type = i;
		if (priority != 0) {
			return;
		} else {
			switch (i) {
			case PRIORITY_IMAGE:
				setPriority(PRIORITY_IMAGE);
				break;
			case PRIORITY_JSON:
				setPriority(PRIORITY_JSON);
				break;
			}
		}
	}

}