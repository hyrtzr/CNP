package com.jingdong.common.thread;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import android.os.Process;

/**
 * 线程池内的线程
 * @author yepeng
 *
 */
public class PooledThread extends Thread {

	protected boolean killed = false;
	private ThreadPool pool;
	protected boolean running = false;
	protected List<Runnable> tasks = new ArrayList<Runnable>();

	public PooledThread(ThreadPool pool) {
		this.pool = pool;
	}

	/**
	 * 返回是否在运行标志位
	 * @return
	 */
	public boolean isRunning() {
		return this.running;
	}

	/**
	 * 清除掉当前线程
	 */
	public void kill() {
		if (!this.running)
			interrupt();
		else
			this.killed = true;
	}

	/**
	 * 物理上清除当前线程
	 */
	public void killSync(){
		kill();
		while (true) {
			if (!isAlive())
				return;
			try {
				sleep(5L);
			} catch (InterruptedException localInterruptedException) {
			}
		}
    }

	/**
	 * 弹出第一个任务
	 * 
	 * @return
	 */
	protected Runnable popTask() {
		if (this.tasks.size() > 0)
			return (Runnable) this.tasks.remove(0);
		else
			return null;
	}

	/**
	 * 加入任务集合
	 * 
	 * @param tasks
	 */
	public void putTasks(Collection tasks) {
		this.tasks.addAll(tasks);
	}

	/**
	 * 开始唤醒线程，执行任务
	 */
	public void startTasks() {
		synchronized (this) {
			this.running = true;
			notify();
		}

	}

	/**
	 * 执行任务集合
	 */
	public void run() {
		Process.setThreadPriority(Process.THREAD_PRIORITY_DISPLAY);
		this.pool.addThreadId(Process.myTid());
		while (true) {
			if (killed)
				break;
			if (running && tasks.size() != 0) {
				Runnable runnable = popTask();
				if (runnable != null) {
					runnable.run();
				}
			} else {
				try {
					synchronized (this) {
						running = false;
						pool.notifyForIdleThread();
						wait();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}