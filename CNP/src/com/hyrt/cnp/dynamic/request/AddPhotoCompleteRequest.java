package com.hyrt.cnp.dynamic.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.AddPhotoCompleteService;

import java.util.List;

/**
 * Created by Zoe on 2014-04-24.
 */
public class AddPhotoCompleteRequest extends BaseRequest{

    @Inject
    private AddPhotoCompleteService addPhotoCompleteService;


    private Context context;
    private String paid;
    private String photoname;
    private List<String> msgs;

    public AddPhotoCompleteRequest(Class clazz, Context context, String paid, String photoname, List<String> msgs) {
        super(clazz, context);
        this.context = context;
        this.paid = paid;
        this.photoname = photoname;
        this.msgs = msgs;
    }

    @Override
    public Base run() {
        return addPhotoCompleteService.addDynamic(paid, photoname, msgs);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "addphotoComplete"+System.currentTimeMillis();
    }
}
