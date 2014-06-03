package com.hyrt.cnp.school.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Teacher;
import com.hyrt.cnp.R;
import com.hyrt.cnp.school.adapter.StarTeacherImageAdapter;
import com.hyrt.cnp.school.request.StarTeacherRequest;
import com.hyrt.cnp.school.requestListener.StarTeacherRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

/**
 * Created by GYH on 14-1-10.
 */
public class StarTeacherActivity extends BaseActivity {

    private GridView gridview;

    private StarTeacherImageAdapter starTeacherImageAdapter;

    private int mSid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starteacher);
        Intent intent = getIntent();
        mSid = intent.getIntExtra("sid", -1);
        android.util.Log.i("tag", "StartTeacher mSid:"+mSid);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (starTeacherImageAdapter == null) {
            loadStarteacehr();
        }
    }

    private void initView() {
        gridview = (GridView) findViewById(R.id.cnp_gridview);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(StarTeacherActivity.this, StarTeacherInfoActivity.class);
                intent.putExtra("vo", (Teacher) (starTeacherImageAdapter.getItem(i)));
                startActivity(intent);
            }
        });
    }

    /**
     * 更新ui界面
     */
    public void initData(Teacher.Model model) {
        if (model == null) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.VISIBLE);
            TextView bottom_num = (TextView) findViewById(R.id.bottom_num);
            bottom_num.setText("暂无信息");
        } else {
            String[] resKeys = new String[]{"getImagepath", "getRenname"};
            int[] reses = new int[]{R.id.gridview_image, R.id.gridview_name};
            starTeacherImageAdapter = new StarTeacherImageAdapter
                    (this, model.getData(), R.layout.layout_item_gridview_image, resKeys, reses);
            gridview.setAdapter(starTeacherImageAdapter);
        }
    }

    private void loadStarteacehr() {
        StarTeacherRequestListener sendwordRequestListener = new StarTeacherRequestListener(this);
        StarTeacherRequest starTeacherRequest = new StarTeacherRequest(Teacher.Model.class, this, mSid);
        spiceManager.execute(starTeacherRequest, starTeacherRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }
}
