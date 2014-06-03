package com.hyrt.cnp.classroom.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.ui.SendDynamicActivity;
import com.jingdong.app.pad.adapter.MySimpleAdapter;
import com.jingdong.common.frame.BaseActivity;

import net.oschina.app.AppContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GYH on 14-1-8.
 */
public class ClassRoomAdapter extends MySimpleAdapter {

    public ClassRoomAdapter(BaseActivity context, List data, int layoutId, String[] resKeys, int[] reses) {
        super(context, data, layoutId, resKeys, reses);

    }


}
