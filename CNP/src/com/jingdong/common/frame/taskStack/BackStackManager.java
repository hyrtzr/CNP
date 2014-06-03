package com.jingdong.common.frame.taskStack;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理taskModule的数据结构
 * @author yepeng
 *
 */
public class BackStackManager {

	private static BackStackManager mBackStackManager;
	private TaskModule current;
	private List<TaskModule> history = new ArrayList<TaskModule>();

	public static BackStackManager getInstance() {
		if (mBackStackManager == null)
			mBackStackManager = new BackStackManager();
		return mBackStackManager;
	}

	public boolean isLast() {
		return (isMLast());
	}

	private boolean isMLast() {
		return this.history.size() < 1;
	}

	public void clearHistory() {
		this.history.clear();
	}

	public TaskModule getCurrent() {
		return this.current;
	}

	public TaskModule pop() {
		if (prev() == null)
			return null;
		this.current = (TaskModule) this.history.remove(-1
				+ this.history.size());
		return this.current;
	}

	public TaskModule prev() {
		if (isLast())
			return null;
		return (TaskModule) this.history.get(-1 + this.history.size());
	}

	public void push(TaskModule paramTaskModule) {
		this.history.add(paramTaskModule);
	}

	public void setCurrent(TaskModule paramTaskModule) {
		tryPushPrev();
		this.current = paramTaskModule;
	}

	public int size() {
		return this.history.size();
	}

	public void tryPushPrev() {
		if ((this.current != null) && (this.current.isInHistory()))
			push(this.current);
	}
}