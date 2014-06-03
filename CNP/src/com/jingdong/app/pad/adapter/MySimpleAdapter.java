package com.jingdong.app.pad.adapter;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;

import com.jingdong.common.frame.BaseActivity;
import com.jingdong.common.frame.IDestroyListener;
import com.jingdong.common.frame.MyActivity;
import com.jingdong.common.utils.ImageUtil;
import com.jingdong.common.utils.Log;
import com.jingdong.common.utils.cache.GlobalImageCache;

import java.util.List;

/**
 * 全局基本适配器的扩展
 * @author yepeng
 *
 */
public class MySimpleAdapter extends SimpleBeanAdapter
  implements IDestroyListener, MyActivity.PauseListener, MyActivity.ResumeListener, MyActivity.StopListener{
  
  private boolean isFinishing;
  
  /**
   * 
   * @param paramMyActivity 当前活动
   * @param data 列表数据
   * @param resourceId 列表条目资源id
   * @param resKeys 数据对应id集合
   * @param reses 数据key
   */
  public MySimpleAdapter(MyActivity paramMyActivity, List data, int resourceId, String[] resKeys,
                         int[] reses)
  {
    super(paramMyActivity, data, resourceId, resKeys, reses);
    paramMyActivity.addDestroyListener(this);
    paramMyActivity.addPauseListener(this);
    paramMyActivity.addResumeListener(this);
  }

    public MySimpleAdapter(BaseActivity paramMyActivity, List data, int resourceId, String[] resKeys,
                           int[] reses)
    {
        super(paramMyActivity, data, resourceId, resKeys, reses);

    }

  public void gc()
  {
    super.gc();
  }

  public View getView(int position, View paramView, ViewGroup paramViewGroup)
  {
    if (Log.D)
      Log.d(MySimpleAdapter.class.getName(), "position = " + position + " convertView = " + paramView + " -->> ");
    View localView = super.getView(position, paramView, paramViewGroup);
    if (Log.D)
      Log.d(MySimpleAdapter.class.getName(), "position = " + position + " view = " + localView + " -->> ");
    return localView;
  }

  public void onDestroy()
  {
    this.isFinishing = true;
    gc();
  }

  public void onPause()
  {
  }

  public void onResume()
  {
    notifyDataSetChanged();
  }

  public void onStop()
  {
  }

  /**
   * 图片回调类
   * @author yepeng
   *
   */
  public static abstract interface ImageProcessor
  {
	/**
	 * 图片流获取到后，对其进行处理的回调接口
	 * @param paramInputWay
	 * @param paramBitmapDigest
	 * @return
	 */
    public abstract Bitmap create(ImageUtil.InputWay paramInputWay, GlobalImageCache.BitmapDigest paramBitmapDigest);

    /**
     * 显示图片的回调接口
     * @param paramSubViewHolder
     * @param paramImageState
     */
    public abstract void show(SimpleBeanAdapter.SubViewHolder paramSubViewHolder, GlobalImageCache.ImageState paramImageState);
  }
}