package com.hyrt.cnp.school.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.model.PositionInfo;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.PositionInfoService;

/**
 * Created by Zoe on 2014-04-05.
 */
public class PositionInfoRequest extends BaseRequest{

    private double lng;
    private double lat;

    @Inject
    private PositionInfoService mPositionInfoService;

    private PositionInfo positionInfo;

    public PositionInfoRequest(Class clazz, Context context, double lat, double lng) {
        super(clazz, context);
        this.lng = lng;
        this.lat = lat;
    }

    @Override
    public Base run() {
        return mPositionInfoService.getPositionInfo(getRestTemplate(), lat, lng);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
    public String getcachekey(){
        return "PositionInfo:";
    }
}
