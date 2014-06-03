package com.jingdong.common.frame.taskStack;

import android.support.v4.app.FragmentManager;

import com.jingdong.common.frame.BaseActivity;
import com.jingdong.common.frame.MyActivity;
import com.jingdong.common.utils.Log;
import net.oschina.app.AppContext;
import net.oschina.app.bean.Constants;

/**
 * fragment跳转工具类
 * @author yepeng
 *
 */
public class ApplicationManager
{
  private static final String TAG = "ApplicationManager";
  private static final BackStackManager backStackManager = BackStackManager.getInstance();
  private static final BaseActivity mainActivity = AppContext.getInstance().getBaseActivity();

  public static void back()
  {
    if (Log.D)
      Log.d(TAG, "back() -->>");
    MyActivity.remove(mainActivity.getCurrentMyActivity());
    backStackManager.pop();
    if (Log.D)
         Log.d(TAG, "back() -->> backStackManager.size()" + backStackManager.size());
    mainActivity.getSupportFragmentManager().popBackStack();
    //返回到首页,隐藏up图标
    if(backStackManager.isLast()){
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }else{

    }
  }

  public static void go(TaskModule paramTaskModule)
  {
    if (Log.D)
      Log.d(TAG, "go() -->> taskModule:" + paramTaskModule);
    paramTaskModule.setPrev(backStackManager.getCurrent());
    if (!paramTaskModule.premise())
        return;
    backStackManager.setCurrent(paramTaskModule);
    if (paramTaskModule.isNeedClearBackStack())
    {
      paramTaskModule.setPrev(null);
      clearBackStack();
    }
    paramTaskModule.init();
    paramTaskModule.show();
  }

  public static void clearBackStack()
  {
    backStackManager.clearHistory();
    if (Log.D)
      Log.d(TAG, "clearBackStack backStackManager.size() -->> " + backStackManager.size());
      mainActivity.getSupportFragmentManager().popBackStack(Constants.BACK_STACK_TAG,FragmentManager.POP_BACK_STACK_INCLUSIVE);
      System.gc();
  }

}
