package com.jingdong.common.frame;

/**
 * 活动销毁时，回调的接口方法类，所有引用活动类的使用者需实现此接口，
 * 以便完成引用计数的清零操作
 * @author yepeng
 *
 */
public abstract interface IDestroyListener
{
  public abstract void onDestroy();
}
