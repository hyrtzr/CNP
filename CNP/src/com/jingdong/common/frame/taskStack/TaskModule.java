package com.jingdong.common.frame.taskStack;

import net.oschina.app.AppContext;
import net.oschina.app.bean.Constants;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.jingdong.common.frame.BaseActivity;
import com.jingdong.common.frame.MyActivity;
import com.jingdong.common.utils.Log;

/**
 * 控制片段的显示流程
 * @author yepeng
 *
 */
public abstract class TaskModule {
	
	private static final String TAG = "TaskModule";
	private Bundle bundle;
	protected boolean inHistory = true;
	private boolean init = false;
	private boolean isNeedClearBackStack = false;
	private TaskModule prev;

	private boolean checkBackStack() {
		TaskModule localTaskModule = getPrev();
		if (localTaskModule != null && localTaskModule.isInHistory())
			return true;
		return false;
	}

	public void addAndCommit(int paramInt, MyActivity paramMyActivity) {
		addAndCommit(paramInt, paramMyActivity, null);
	}

	public void addAndCommit(int paramInt, MyActivity paramMyActivity,
			String paramString) {
		if (Log.D)
			Log.d(TAG, "addAndCommit -->> ");
		FragmentTransaction localFragmentTransaction = ((FragmentActivity) AppContext
				.getInstance().getBaseActivity()).getSupportFragmentManager()
				.beginTransaction();
		localFragmentTransaction.add(paramInt, paramMyActivity);
		boolean bool = checkBackStack();
		if (Log.D)
			Log.d(TAG, "checkBackStack:" + bool);
		if (bool) {
			if (Log.D)
				Log.d(TAG, "addToBackStack() -->> fragment:" + getPrev());
			localFragmentTransaction.addToBackStack(paramString);
		}
		localFragmentTransaction.commitAllowingStateLoss();
	}

	public void addAndCommit(MyActivity paramMyActivity) {
		addAndCommit(
				((BaseActivity) AppContext.getInstance().getBaseActivity())
						.getCurrentFragmentViewId(),
				paramMyActivity);
	}

	public void addAndCommit(MyActivity paramMyActivity, String paramString) {
		addAndCommit(
				((BaseActivity) AppContext.getInstance().getBaseActivity())
						.getCurrentFragmentViewId(),
				paramMyActivity, paramString);
	}

	public void beforeLeave(boolean paramBoolean) {
	}

	protected void doInit() {
	}

	protected void doShow() {
	}

	public Bundle getBundle() {
		if (this.bundle == null)
			bundle = new Bundle();
		return bundle;
	}

	public TaskModule getPrev() {
		return this.prev;
	}

	public void init() {
		doInit();
		this.init = true;
	}

	public boolean isInHistory() {
		return this.inHistory;
	}

	public boolean isInit() {
		return this.init;
	}

	public boolean isNeedClearBackStack() {
		return this.isNeedClearBackStack;
	}

	public boolean premise() {
		return true;
	}

	public void replaceAndCommit(int paramInt, MyActivity paramMyActivity) {
		replaceAndCommit(paramInt, paramMyActivity, null);
	}

	public void replaceAndCommit(int paramInt, MyActivity paramMyActivity,
			String paramString) {
		FragmentTransaction localFragmentTransaction = AppContext.getInstance()
				.getBaseActivity().getSupportFragmentManager()
				.beginTransaction();
		localFragmentTransaction.replace(paramInt, paramMyActivity);
		boolean bool = checkBackStack();
		if (Log.D)
			Log.d(TAG, "checkBackStack:" + bool);
		if (bool) {
			if (Log.D)
				Log.d(TAG, "addToBackStack() -->> fragment:" + getPrev());
			localFragmentTransaction.addToBackStack(Constants.BACK_STACK_TAG);
		}
		localFragmentTransaction.commitAllowingStateLoss();
	}

	public void replaceAndCommit(MyActivity paramMyActivity) {
		replaceAndCommit(AppContext.getInstance().getBaseActivity()
				.getCurrentFragmentViewId(), paramMyActivity);
	}

	public void replaceAndCommit(MyActivity paramMyActivity, String paramString) {
		replaceAndCommit(AppContext.getInstance().getBaseActivity()
				.getCurrentFragmentViewId(), paramMyActivity, paramString);
	}

	public void setBundle(Bundle paramBundle) {
		this.bundle = paramBundle;
	}

	public void setInHistory(boolean paramBoolean) {
		this.inHistory = paramBoolean;
	}

	public void setNeedClearBackStack(boolean paramBoolean) {
		this.isNeedClearBackStack = paramBoolean;
	}
	
	public void setPrev(TaskModule paramTaskModule) {
		this.prev = paramTaskModule;
	}

	public void show() {
		doShow();
	}
}