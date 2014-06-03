package com.hyrt.cnp.school.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.School;
import com.hyrt.cnp.base.account.utils.UITextUtils;
import com.hyrt.cnp.R;
import com.hyrt.cnp.school.request.SchoolinfoRequest;
import com.hyrt.cnp.school.requestListener.SchoolinfoRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

/**
 * Created by GYH on 14-1-13.
 * 园所介绍
 */
public class SchoolInfoActivity extends BaseActivity {

    private TextView schoolintro;
//    private ImageView schoolmap;
    private int mSid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolinfo);
        Intent intent = getIntent();
        mSid = intent.getIntExtra("sid", -1);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadSendword();
    }

    private void initView() {
        schoolintro = (TextView) findViewById(R.id.schoolintro);
//        schoolmap = (ImageView) findViewById(R.id.schoolmap);
    }

    public void initData(School.Model2 model2) {
        UITextUtils.setTextWithSelection(schoolintro, model2.getData().getIntro());
    }

    private void loadSendword() {
        SchoolinfoRequestListener sendwordRequestListener = new SchoolinfoRequestListener(this);
        SchoolinfoRequest schoolinfoRequest = new SchoolinfoRequest(School.Model2.class, this, mSid);
        spiceManager.execute(schoolinfoRequest, schoolinfoRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }
}
