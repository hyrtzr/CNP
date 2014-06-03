package com.jingdong.common.thread;

import android.os.Process;

import com.jingdong.common.utils.IPriority;
import com.jingdong.common.utils.Log;
import com.jingdong.common.utils.PriorityCollection;

import java.util.Collection;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Vector;

/**
 * 管理调配线程的线程池子
 * 
 * @author yepeng
 * 
 */
public class ThreadPool {

	protected boolean hasIdleThread;
	protected int initPoolSize;
	protected boolean initialized;
	protected int maxPoolSize;
	// ui线程是否繁忙的标志为
	private static boolean isUiThreadBusy = false;
	// 优先级任务
	protected PriorityQueue<IPriority> queue = new PriorityQueue<IPriority>();
	// 线程容器
	protected Vector<PooledThread> effectiveThread = new Vector<PooledThread>();
	// 线程tid集合
	protected Vector<Integer> effectiveThreadIds = new Vector<Integer>();

	private static ThreadPool sFirstPool = new ThreadPool(
			Integer.parseInt("2"), Integer.parseInt("1"));
	private static ThreadPool sSecondPool = new ThreadPool(
			Integer.parseInt("4"), Integer.parseInt("1"));
	public static ThreadPool sThirdPool = new ThreadPool(Integer.parseInt("4"),
			Integer.parseInt("1"));

	static {
		sThirdPool.init();
		sSecondPool.init();
		sFirstPool.init();
	}

	public static ThreadPool getFirstThreadPool() {
		return sFirstPool;
	}

	public static ThreadPool getSecondThreadPool() {
		return sSecondPool;
	}

	public static ThreadPool getThirdThreadPool() {
		return sThirdPool;
	}

	public ThreadPool(int maxPoolSize, int initPoolSize) {
		this.maxPoolSize = maxPoolSize;
		this.initPoolSize = initPoolSize;
	}

	public void addThreadId(int paramInt) {
		if (Log.D)
			Log.d("ThreadPool", "currentThread id:"
					+ Thread.currentThread().getId()
					+ "- addThreadId() tId -->> " + paramInt);
		this.effectiveThreadIds.add(Integer.valueOf(paramInt));
	}

	/**
	 * 线程池醒来后，通知线程池子线程队列启动
	 */
	public static void notifyUIThreadNotBusy() {
		sFirstPool.queueNotify();
		sSecondPool.queueNotify();
		sThirdPool.queueNotify();
	}

	/**
	 * 获取当前ui线程是否繁忙
	 * 
	 * @return
	 */
	public static boolean isUiThreadBusy() {
		if (Log.D)
			Log.d("ThreadPool", "currentThread id:"
					+ Thread.currentThread().getId()
					+ "- isUiThreadBusy()-->> " + isUiThreadBusy);
		return isUiThreadBusy;
	}

	/**
	 * 设置线程池内线程的优先级
	 * 
	 * @param priority
	 *            线程优先级
	 */
	public static void setAllThreadPoolPriority(int priority) {
		sFirstPool.setAllThreadPriority(priority);
		sSecondPool.setAllThreadPriority(priority);
		sThirdPool.setAllThreadPriority(priority);
	}

	/**
	 * 如果ui线程繁忙的话则等待->每次请求数据的时候都调用此方法
	 */
	public static void sleepForUiThreadBusy() {
            boolean sleeped = false;
			while (isUiThreadBusy()) {
                sleeped = true;
				if (Log.D)
					Log.d("ThreadPool", "currentThread id:"
							+ Thread.currentThread().getId()
							+ "- sleepForUiThreadBusy()-->> "+500.0D * Math.random());

				try {
					Thread.sleep(Math.round(500.0D * Math.random()));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
            if(sleeped)
                notifyUIThreadNotBusy();
	}

	/**
	 * 设置当前ui线程是否繁忙--》页面滚动时设置为繁忙；页面停止滚动是设置为不繁忙
	 * 
	 * @param isBusy
	 */
	public static void setUiThreadBusy(boolean isBusy) {
		if (Log.D)
			Log.d("ThreadPool", "currentThread id:"
					+ Thread.currentThread().getId()
					+ "- setUiThreadBusy()-->> " + isBusy);
		isUiThreadBusy = isBusy;
		if (!isUiThreadBusy)
			setAllThreadPoolPriority(Process.THREAD_PRIORITY_DISPLAY);
		else
			setAllThreadPoolPriority(Process.THREAD_PRIORITY_BACKGROUND);
	}

	/**
	 * 迭代设置池子内线程的优先级
	 * 
	 * @param priority
	 */
	public void setAllThreadPriority(int priority) {
		synchronized (this.effectiveThreadIds) {
			Iterator<Integer> localIterator = this.effectiveThreadIds
					.iterator();
			while (localIterator.hasNext()) {
				Integer localInteger = (Integer) localIterator.next();
				if (Log.D)
					Log.d("ThreadPool", "currentThread id:"
							+ Thread.currentThread().getId()
							+ "- setAllThreadPriority() tId=" + localInteger
							+ ",priority=" + priority + " -->> ");
				android.os.Process.setThreadPriority(localInteger.intValue(),
						priority);
			}
		}
	}

	/**
	 * 弹出第一个优先级任务
	 * 
	 * @return
	 */
	private IPriority pollTasks() {
		synchronized (queue) {
			IPriority localIPriority = this.queue.poll();
			return localIPriority;
		}
	}

	/**
	 * 获取空闲的线程
	 * 
	 * @return
	 */
	public PooledThread getIdleThread() {
		while (true) {
			Iterator<PooledThread> iterator = effectiveThread.iterator();
			while (iterator.hasNext()) {
				PooledThread pooledthread = (PooledThread) iterator.next();
				if (!pooledthread.isRunning()) {
					return pooledthread;
				}
			}
			if (getPoolSize() < maxPoolSize) {
				PooledThread pooledthread = new PooledThread(this);
				pooledthread.start();
				effectiveThread.add(pooledthread);
				return pooledthread;
			}
			if (waitForIdleThread()) {
				continue;
			}
		}
	}

	/**
	 * 获取线程池的大小
	 * 
	 * @return
	 */
	public int getPoolSize() {
		return this.effectiveThread.size();
	}

	/**
	 * 初始化线程池内的线程，并且监听队列内时候有任务，随时通知给子线程
	 */
	public void init() {
		this.initialized = true;
		for (int i = 0;; ++i) {
			if (i >= this.initPoolSize) {
				new Thread(new Runnable() {

					public void run() {
						android.os.Process
								.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);
						if (Log.D)
							Log.d("ThreadPool", "currentThread id:"
									+ Thread.currentThread().getId()
									+ "- Manager Thread run()-->> on runing...");
						do {
							if (Log.D)
								Log.d("ThreadPool",
										"currentThread id:"
												+ Thread.currentThread()
														.getId()
												+ "- Manager Thread run()-->> on while (true)...");
							Collection collection = (Collection) pollTasks();
							if (collection != null) {
								PooledThread pooledThread = getIdleThread();
								if (Log.D)
									Log.d("ThreadPool",
											"currentThread id:"
													+ Thread.currentThread()
															.getId()
													+ "- Manager Thread run()-->> idleThread.startTasks()");
								pooledThread.putTasks(collection);
								pooledThread.startTasks();
							} else {
								try {
									synchronized (queue) {
										if (Log.D)
											Log.d("ThreadPool",
													"currentThread id:"
															+ Thread.currentThread()
																	.getId()
															+ "- Manager Thread run()-->> wait()");
										queue.wait();
									}
								} catch (InterruptedException localInterruptedException) {
									if (Log.D)
										Log.d("ThreadPool",
												"currentThread id:"
														+ Thread.currentThread()
																.getId()
														+ "- Manager Thread run()-->> "
														+ localInterruptedException
																.getMessage());
									localInterruptedException.printStackTrace();
								}
							}
						} while (true);
					}
				}).start();
				return;
			} else {
				PooledThread localPooledThread = new PooledThread(this);
				localPooledThread.start();
				this.effectiveThread.add(localPooledThread);
			}
		}
	}

	/**
	 * 通知线程池有空余线程
	 */
	protected void notifyForIdleThread() {
		synchronized (this) {
			this.hasIdleThread = true;
			this.notify();
		}
	}

	/**
	 * 向队列内增加任务
	 * 
	 * @param paramRunnable
	 * @param paramInt
	 */
	public void offerTask(Runnable paramRunnable, int paramInt) {
		PriorityCollection localPriorityCollection = new PriorityCollection(
				paramInt);
		localPriorityCollection.add(paramRunnable);
		offerTasks(localPriorityCollection);
	}

	/**
	 * 提供任务优先队列
	 * 
	 * @param paramIPriority
	 */
	public void offerTasks(IPriority paramIPriority) {
		synchronized (queue) {
			queue.offer(paramIPriority);
			queueNotify();
		}
	}

	/**
	 * 唤醒任务队列上等待的线程池子
	 */
	public void queueNotify() {
		synchronized (this.queue) {
			this.queue.notify();
		}
	}

	/**
	 * 把线程池扩展到最大
	 * 
	 * @param paramInt
	 */
	public void setMaxPoolSize(int paramInt) {
		this.maxPoolSize = paramInt;
		if (paramInt >= getPoolSize())
			return;
		setPoolSize(paramInt);
	}

	/**
	 * 设置线程池的大小
	 * 
	 * @param paramInt
	 */
	public void setPoolSize(int paramInt) {
		if (!this.initialized) {
			this.initPoolSize = paramInt;
		} else {
			if (paramInt > getPoolSize()) {
				for (int i = getPoolSize(); (i < paramInt)
						&& (i < this.maxPoolSize); ++i) {
					PooledThread localPooledThread = new PooledThread(this);
					localPooledThread.start();
					this.effectiveThread.add(localPooledThread);
				}
			}
			while (paramInt != getPoolSize()) {
				((PooledThread) this.effectiveThread.remove(0)).killSync();
                this.effectiveThreadIds.remove(0);
			}
		}
	}

	/**
	 * 等待获取空闲的线程
	 * 
	 * @return
	 */
	protected boolean waitForIdleThread() {
		this.hasIdleThread = false;
		while (true) {
			if (!hasIdleThread) {
				if (this.getPoolSize() >= maxPoolSize) {
					try {
						synchronized (this) {
							this.wait();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}else {
					return true;
				}
			} else {
                return true;
			}
		}

	}
}