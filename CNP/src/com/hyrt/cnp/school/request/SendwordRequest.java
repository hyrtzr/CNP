package com.hyrt.cnp.school.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.request.NotNeedLoginBaseRequest;
import com.hyrt.cnp.base.account.service.SendwordService;

/**
 * Created by GYH on 14-1-8.
 */
public class SendwordRequest extends NotNeedLoginBaseRequest {

    @Inject
    private SendwordService sendwordService;
    private int sid = -1;

    public SendwordRequest(Class clazz, Context context) {
        super(clazz, context);
    }

    public SendwordRequest(Class clazz, Context context, int sid) {
        super(clazz, context);
        this.sid = sid;
    }

    @Override
    public Base run() {
        return sendwordService.getSendwordData(getRestTemplate(), sid);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "Sendword"+sid;
    }
}
