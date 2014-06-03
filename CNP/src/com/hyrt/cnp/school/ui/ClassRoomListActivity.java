package com.hyrt.cnp.school.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.ClassRoom;
import com.hyrt.cnp.R;
import com.hyrt.cnp.school.adapter.ClassRoomImageAdapter;
import com.hyrt.cnp.school.request.ClassRoomListRequest;
import com.hyrt.cnp.school.requestListener.ClassRoomListRequestListener;
import com.hyrt.cnp.school.view.MyGridView;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

import java.util.ArrayList;

/**
 * Created by GYH on 14-1-10.
 */
public class ClassRoomListActivity extends BaseActivity {
    private MyGridView gridviewsmall, gridviewmun, gridviewmax, gridviewcustody, gridviewother;
//    private int[] imageResIdsmall, imageResIdmun, imageResIdmax, imageResIdcustody, imageResIdother;
//    private String[] Smalltext = {"小三班\n班主任：苏菲菲", "小三班\n班主任：苏菲菲"};
//    private String[] Muntext = {"付晓宁\n班主任：苏菲菲"};
//    private String[] Maxtext = {"付晓宁\n班主任：苏菲菲"};
//    private String[] Custodytext = {"付晓宁\n班主任：苏菲菲"};
//    private String[] Othertext = {"付晓宁\n班主任：苏菲菲"};
    private ClassRoomImageAdapter starTeacherAdaptersmall = null;
    private ClassRoomImageAdapter starTeacherAdaptermun = null;
    private ClassRoomImageAdapter starTeacherAdaptermax = null;
    private ClassRoomImageAdapter starTeacherAdaptercustody = null;
    private ClassRoomImageAdapter starTeacherAdapterother = null;
    private int mSid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);
        Intent intent = getIntent();
        mSid = intent.getIntExtra("sid", -1);
        initView();
    }

    @Override
    protected void initTitleview() {
        super.initTitleview();
        TextView textView = (TextView) viewTitleBar.findViewById(R.id.action_bar_title_text);
        textView.setText("班级设置");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (starTeacherAdaptersmall == null) {
            loadSendword();
        }
    }

    private void initView() {
        gridviewsmall = (MyGridView) findViewById(R.id.cnp_gridview_small);
        gridviewmun = (MyGridView) findViewById(R.id.cnp_gridview_mun);
        gridviewmax = (MyGridView) findViewById(R.id.cnp_gridview_max);
        gridviewcustody = (MyGridView) findViewById(R.id.cnp_gridview_custody);
        gridviewother = (MyGridView) findViewById(R.id.cnp_gridview_other);
//        imageResIdsmall = new int[]{R.drawable.classroom1, R.drawable.classroom2};
//        imageResIdmun = new int[]{R.drawable.classroom3};
//        imageResIdmax = new int[]{R.drawable.classroom2};
    }

    /**
     * 更新ui界面
     */
    public void initData(ClassRoom.Model model) {

        if (model == null) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.VISIBLE);
            TextView bottom_num = (TextView) findViewById(R.id.bottom_num);
            bottom_num.setText("暂无信息");
        } else {
            String[] resKeys = new String[]{"getImagepath", "getRenname","getRoomname"};
            int[] reses = new int[]{R.id.gridview_image, R.id.gridview_renname,R.id.gridview_classroomname};
            starTeacherAdapterother = new ClassRoomImageAdapter(this, getClassRoommodel(model, "5").getData(), R.layout.layout_item_gridview_image_classroomlist, resKeys, reses);
            starTeacherAdaptercustody = new ClassRoomImageAdapter(this, getClassRoommodel(model, "4").getData(), R.layout.layout_item_gridview_image_classroomlist, resKeys, reses);
            starTeacherAdaptersmall = new ClassRoomImageAdapter(this, getClassRoommodel(model, "3").getData(), R.layout.layout_item_gridview_image_classroomlist, resKeys, reses);
            starTeacherAdaptermun = new ClassRoomImageAdapter(this, getClassRoommodel(model, "2").getData(), R.layout.layout_item_gridview_image_classroomlist, resKeys, reses);
            starTeacherAdaptermax = new ClassRoomImageAdapter(this, getClassRoommodel(model, "1").getData(), R.layout.layout_item_gridview_image_classroomlist, resKeys, reses);
            gridviewsmall.setAdapter(starTeacherAdaptersmall);
            gridviewmun.setAdapter(starTeacherAdaptermun);
            gridviewmax.setAdapter(starTeacherAdaptermax);
            gridviewcustody.setAdapter(starTeacherAdaptercustody);
            gridviewother.setAdapter(starTeacherAdapterother);
        }
    }

    private void loadSendword() {
        ClassRoomListRequestListener sendwordRequestListener = new ClassRoomListRequestListener(this);
        ClassRoomListRequest classRoomListRequest = new ClassRoomListRequest(ClassRoom.Model.class, this, mSid);
        spiceManager.execute(classRoomListRequest, classRoomListRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }

    private ClassRoom.Model getClassRoommodel(ClassRoom.Model model, String grade) {
        ClassRoom.Model m = new ClassRoom.Model();
        m.setData(new ArrayList<ClassRoom>());

        for (int i = 0; i < model.getData().size(); i++) {
            if (model.getData().get(i).getGrade().equals(grade)) {
                m.getData().add(model.getData().get(i));
            }
        }

        return m;
    }
}
