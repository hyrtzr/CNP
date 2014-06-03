package com.hyrt.cnp.dynamic.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.ItInfoService;

/**
 * Created by GYH on 2014/4/6.
 */
public class ItInfoRequest extends BaseRequest {

    @Inject
    private ItInfoService itInfoService;
    private String more;
    private int type = 0;//0:与我相关 1:转发 2:评论
    public ItInfoRequest(Class clazz, Context context,String more) {
        super(clazz, context);
        this.more=more;
    }

    public ItInfoRequest(Class clazz, Context context,String more, int type) {
        super(clazz, context);
        this.more=more;
        this.type = type;
    }

    @Override
    public Base run() {
            switch (type){
                case 0:
                    return itInfoService.getMyitinfoData(getRestTemplate(), more);
                case 1:
                    return itInfoService.getForwardData(getRestTemplate(), more);
                case 2:
                    return itInfoService.getCommentData(getRestTemplate(), more);
                default:
                    return itInfoService.getMyitinfoData(getRestTemplate(), more);
            }
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "myitinfo"+more+type;
    }
}
