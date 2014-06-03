package com.hyrt.cnp.classroom.request;

import android.content.Context;

import com.google.inject.Inject;
import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.request.BaseRequest;
import com.hyrt.cnp.base.account.service.ClassroomAlterService;

import java.io.File;

/**
 * Created by Zoe on 2014-05-19.
 */
public class ClassroomAlterRequest extends BaseRequest{

    private int type = 0;
    private File file;
    private String content;

    @Inject
    private ClassroomAlterService classroomAlterService;

//    public ClassroomAlterRequest(Class clazz, Context context) {
//        super(clazz, context);
//    }

    public ClassroomAlterRequest(Context context, File file) {
        super(BaseTest.class, context);
        this.file = file;
        this.type = 0;
    }

    public ClassroomAlterRequest(Context context, String content) {
        super(BaseTest.class, context);
        this.type = 1;
        this.content = content;
    }

    @Override
    public Base run() {
        switch (type){
            case 0:
                return classroomAlterService.alterClassrommBanner(file);
            case 1:
                return classroomAlterService.alertClassroomSignature(content);
        }
        return null;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getcachekey(){
        return "classroomalter"+System.currentTimeMillis();
    }
}
