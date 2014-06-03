package com.hyrt.cnp.school.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.NoticeAlterService;

/**
 * Created by Zoe on 2014-05-20.
 */
public class NoticeAlterRequest extends BaseRequest{

    private int type = 0;//0:增；1:改；2:删
    private int id;
    private String title;
    private String content;
    private Context context;

    @Inject
    private NoticeAlterService noticeAlterService;

    public NoticeAlterRequest(Context context, String title, String content) {
        super(BaseTest.class, context);
        this.title = title;
        this.content = content;
        this.type = 0;
        this.context = context;
    }

    public NoticeAlterRequest(Context context, int id, String title, String content) {
        super(BaseTest.class, context);
        this.id = id;
        this.title = title;
        this.content = content;
        this.type = 1;
        this.context = context;
    }

    public NoticeAlterRequest(Context context, int id) {
        super(BaseTest.class, context);
        this.id = id;
        this.type = 2;
        this.context = context;
    }

    @Override
    public Base run() {
        switch (type){
            case 0:
                return noticeAlterService.addNotice(title, content);
            case 1:
                return noticeAlterService.changeNotice(id, title, content);
            case 2:
                return noticeAlterService.delNotice(id);
        }
        return null;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
    public String getcachekey(){
        return "NoticeAlertRequest"+type+System.currentTimeMillis();
    }
}
