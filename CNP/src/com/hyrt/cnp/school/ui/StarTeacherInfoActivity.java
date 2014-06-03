package com.hyrt.cnp.school.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Teacher;
import com.hyrt.cnp.base.account.utils.FaceUtils;
import com.hyrt.cnp.base.account.utils.UITextUtils;
import com.hyrt.cnp.R;
import com.hyrt.cnp.school.request.StarTeacherInfoRequest;
import com.hyrt.cnp.school.requestListener.StarTeacherInfoRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

/**
 * Created by GYH on 14-1-10.
 */
public class StarTeacherInfoActivity extends BaseActivity {

    private TextView sendtexttitle;
    private TextView sendtextintr;
    private TextView sendtextname;
    private TextView sendtextpol;
    private TextView sendtextmsg;
    private Teacher teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starteacherinfo);
        initView();
        loadSendword();
    }

    private void initView() {
        sendtexttitle = (TextView) findViewById(R.id.sendword_text_title);
        sendtextintr = (TextView) findViewById(R.id.sendword_text_intro);
        sendtextname = (TextView) findViewById(R.id.sendword_text_rename);
        sendtextpol = (TextView) findViewById(R.id.sendword_text_pol);
        sendtextmsg = (TextView) findViewById(R.id.sendword_text_msg);
        teacher = (Teacher) getIntent().getSerializableExtra("vo");

    }

    public void updateUI(Teacher.Model2 model) {
        UITextUtils.setTextWithSelection(sendtexttitle, model.getData().getRenname());
        if (!model.getData().getSex().equals("")) {
            if (model.getData().getSex().equals("M")) {
                sendtextname.setText("男");
            } else if (model.getData().getSex().equals("W")) {
                sendtextname.setText("女");
            }

        }
        UITextUtils.setTextWithSelection(sendtextpol, model.getData().getRoomName());
        UITextUtils.setTextWithSelection(sendtextintr, model.getData().getNurseryName());
        UITextUtils.setTextWithSelection(sendtextmsg, model.getData().getIntro());
        showDetailImage(FaceUtils.getAvatar(model.getData().getUser_id(), FaceUtils.FACE_BIG), (ImageView) findViewById(R.id.starteacherimageview), false);

    }

    private void loadSendword() {
        StarTeacherInfoRequestListener sendwordRequestListener = new StarTeacherInfoRequestListener(this);
        StarTeacherInfoRequest starTeacherInfoRequest = new StarTeacherInfoRequest(Teacher.Model2.class, this, teacher.getUser_id());
        spiceManager.execute(starTeacherInfoRequest, starTeacherInfoRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }


}
