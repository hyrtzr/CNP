package com.hyrt.cnp.school.requestListener;

import android.app.Activity;
import android.content.Context;

import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.base.account.utils.AlertUtils;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by Zoe on 2014-05-20.
 */
public class NoticeAlterRequestListener extends BaseRequestListener{

    private RequestListener mListener;
    private Context context;
    private int type = 0;//0:增；1:改；2:删

    public NoticeAlterRequestListener(Activity context) {
        super(context);
        this.context = context;
        mListener = null;
    }

    @Override
    public BaseRequestListener start() {
        showIndeterminate("发布中...");
        return this;
    }

    public BaseRequestListener start(int type) {
        this.type = type;
        if(type == 0){
            showIndeterminate("发布中...");
        }else if(type == 1){
            showIndeterminate("修改中...");
        }else{
            showIndeterminate("删除中...");
        }

        return this;
    }

    @Override
    public void onRequestSuccess(Object o) {
        super.onRequestSuccess(o);
        if(mListener != null){
            if(o != null){
                BaseTest bt = (BaseTest) o;
                if("200".equals(bt.getCode())){
                    if(type == 0){
                        AlertUtils.getInstance().showCenterToast(context, "添加成功");
                    }else if (type == 1) {
                        AlertUtils.getInstance().showCenterToast(context, "修改成功");
                    }else{
                        AlertUtils.getInstance().showCenterToast(context, "删除成功");
                    }
                    mListener.onRequestSuccess(bt);
                }else{
                    mListener.onRequestFailure();
                    if(type == 0){
                        AlertUtils.getInstance().showCenterToast(context, "添加失败");
                    }else if (type == 1) {
                        AlertUtils.getInstance().showCenterToast(context, "修改失败");
                    }else{
                        AlertUtils.getInstance().showCenterToast(context, "删除失败");
                    }

                }
            }else{
                mListener.onRequestFailure();
                if(type == 0){
                    AlertUtils.getInstance().showCenterToast(context, "添加失败");
                }else if (type == 1) {
                    AlertUtils.getInstance().showCenterToast(context, "修改失败");
                }else{
                    AlertUtils.getInstance().showCenterToast(context, "删除失败");
                }
            }
        }
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
        if(mListener != null){
            mListener.onRequestFailure();
        }
        if(type == 0){
            AlertUtils.getInstance().showCenterToast(context, "添加失败");
        }else if (type == 1) {
            AlertUtils.getInstance().showCenterToast(context, "修改失败");
        }else{
            AlertUtils.getInstance().showCenterToast(context, "删除失败");
        }
    }

    public void setListener(RequestListener listener){
        this.mListener = listener;
    }

    public static interface RequestListener{
        public void onRequestSuccess(BaseTest data);
        public void onRequestFailure();
    }
}
