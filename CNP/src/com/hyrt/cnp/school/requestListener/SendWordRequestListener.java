package com.hyrt.cnp.school.requestListener;

import android.app.Activity;

import com.hyrt.cnp.base.account.model.SendWord;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.R;
import com.hyrt.cnp.school.ui.SendwordActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * 其他人员个人资料监听
 * Created by GYH on 14-1-14.
 */
public class SendWordRequestListener extends BaseRequestListener {
    /**
     * @param context
     */
    public SendWordRequestListener(Activity context) {
        super(context);
    }

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
        showMessage(R.string.nodata_title, R.string.nodata_content);
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
        if(data != null){
            SendwordActivity activity = (SendwordActivity)context.get();
            SendWord.Model result= (SendWord.Model)data;
            activity.initData(result);
        }else{
            showMessage(R.string.nodata_title, R.string.nodata_content);
        }
    }

    @Override
    public SendWordRequestListener start() {
        showIndeterminate(R.string.personinfo_pg);
        return this;
    }
}