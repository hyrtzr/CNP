package com.hyrt.cnp.base.service;

import com.octo.android.robospice.JacksonSpringAndroidSpiceService;

/**
 * Created by yepeng on 14-1-12.
 */
public class MyService extends JacksonSpringAndroidSpiceService {


    @Override
    public int getThreadCount() {
        return 3;
    }
}
