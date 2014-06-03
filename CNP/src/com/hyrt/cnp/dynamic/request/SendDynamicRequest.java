package com.hyrt.cnp.dynamic.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.SendDynamicService;

import java.io.File;

/**
 * Created by HY on 2014-04-10.
 */
public class SendDynamicRequest extends BaseRequest{

    @Inject
    SendDynamicService sendDynamicService;

    private String content;
    private File picUrl;
    private String toUid;
    private String toName;

    @Override
    public Context getContext() {
        return super.getContext();
    }

    public SendDynamicRequest(Context context, String content, File picUrl, String toUid, String toName) {
        super(BaseTest.class, context);
        this.content = content;
        this.picUrl = picUrl;
        this.toUid = toUid;
        this.toName = toName;
    }


    @Override
    public Base run() {
        return sendDynamicService.addDynamic(content, picUrl, toUid, toName);
    }

    public String createCacheKey() {
        return "sendDynamic"+System.currentTimeMillis();
    }

	@Override
	public int compareTo(Object another) {
		// TODO Auto-generated method stub
		return 0;
	}
}
