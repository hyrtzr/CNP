package com.jingdong.common.frame;

import java.util.Map;
import com.jingdong.common.http.HttpGroup;
import com.jingdong.common.http.HttpGroup.OnEndListener;
import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

/**
 * 提供基本操作的接口
 * 
 * @author yepeng
 * 
 */
public interface IMyActivity {

	

	/**
	 * 页面销毁时执行此回调方法
	 * 
	 * @param paramIDestroyListener
	 */
	public void addDestroyListener(IDestroyListener paramIDestroyListener);

	/**
	 * 获取发送消息队列的handler
	 * 
	 * @return
	 */
	public Handler getHandler();

	/**
	 * 获取默认的http请求池
	 * 
	 * @return
	 */
	public HttpGroup getHttpGroupaAsynPool();

	/**
	 * 获取定制的http请求池
	 * 
	 * @param type
	 *            请求类型
	 * @return
	 */
	public HttpGroup getHttpGroupaAsynPool(int type);

	/**
	 * 获取当前的活动
	 * 
	 * @return
	 */
	public ActionBarActivity getThisActivity();

	/**
	 * 给handler推送消息
	 * 
	 * @param paramRunnable
	 */
	public void post(Runnable paramRunnable);

	/**
	 * 给handler延时推送消息
	 * 
	 * @param paramRunnable
	 *            延时时间
	 */
	public void post(Runnable paramRunnable, int paramInt);

	/**
	 * 实例化布局
	 */
	public View inflate(int layoutResourceId);

    /**
     * 刷新数据
     */
    public void refresh();

	/**
	 * 
	 * @param functionId
	 *            请求地址
	 * @param paramMap
	 *            请求参数
	 * @param dataClass
	 *            返回数据类型
	 * @param onEndListener
	 *            请求完毕回调接口
	 */
	public void execute(String functionId, Map<String, Object> paramMap,
			Class dataClass, OnEndListener onEndListener);
	
	public void executeImage(String imageUrl,OnEndListener onEndListener);

}