package com.hyrt.cnp.school.requestListener;

import android.app.Activity;
import android.widget.Toast;

import com.hyrt.cnp.base.account.model.SchoolSearch;
import com.hyrt.cnp.base.account.requestListener.BaseRequestListener;
import com.hyrt.cnp.base.account.utils.Log;
import com.octo.android.robospice.persistence.exception.SpiceException;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created by Zoe on 2014-04-01.
 */
public class SchoolSearchRequestListener extends BaseRequestListener{

    public SchoolSearchRequestListener(Activity context) {
        super(context);
    }

    private RequestListener mListener;

    private static final String TAG = "SchoolSearchRequestListener";

    @Override
    public void onRequestFailure(SpiceException e) {
        super.onRequestFailure(e);
        if (mListener != null) {
            mListener.onRequestFailure(e);
        }
    }

    @Override
    public void onRequestSuccess(Object o) {
        super.onRequestSuccess(o);
        if (mListener != null) {
            if(o != null){
                mListener.onRequestSuccess(((SchoolSearch.Model) o).getData());
            }else{
                mListener.onRequestSuccess(null);
            }
        }
    }

    @Override
    public SchoolSearchRequestListener start() {
        showIndeterminate("加载中...");
        return this;
    }

    public void setListener(RequestListener listener){
        this.mListener = listener;
    }

    public static interface RequestListener{
        public void onRequestSuccess(List<SchoolSearch> datas);
        public void onRequestFailure(SpiceException e);

    }
}
