package com.hyrt.cnp.school.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.SendWord;
import com.hyrt.cnp.base.account.utils.FaceUtils;
import com.hyrt.cnp.R;
import com.hyrt.cnp.school.request.SendwordRequest;
import com.hyrt.cnp.school.requestListener.SendWordRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

import java.lang.ref.WeakReference;

/**
 * Created by GYH on 14-1-8.
 * 园长寄语
 */
public class SendwordActivity extends BaseActivity {


    private TextView sendtexttitle;
    private TextView sendtextintr;
    private TextView sendtextname;
    private TextView sendtextpol;
    private TextView sendtextmsg;
    private WeakReference<ImageView> weakImageView;

    private int mSid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendword);
        Intent intent = getIntent();
        mSid = intent.getIntExtra("sid", mSid);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadSendword();
    }

    @Override
    protected void initTitleview() {
        super.initTitleview();
        TextView textView = (TextView) viewTitleBar.findViewById(R.id.action_bar_title_text);
        textView.setText("园长寄语");
    }

    private void initView() {
        sendtexttitle = (TextView) findViewById(R.id.sendword_text_title);
        sendtextintr = (TextView) findViewById(R.id.sendword_text_intro);
        sendtextname = (TextView) findViewById(R.id.sendword_text_rename);
        sendtextpol = (TextView) findViewById(R.id.sendword_text_pol);
        sendtextmsg = (TextView) findViewById(R.id.sendword_text_msg);

    }

    public void initData(SendWord.Model model) {
        sendtexttitle.setText(model.getData().getnName());
        sendtextname.setText(model.getData().getRenname());
        sendtextpol.setText(model.getData().getPolitical());
        sendtextintr.setText(model.getData().getIntro());
        sendtextmsg.setText(model.getData().getMessage());
        //setImage(model.getData().getUser_id());
        String facePath = FaceUtils.getAvatar(model.getData().getUser_id(), FaceUtils.FACE_BIG);
        ImageView imageView = (ImageView) findViewById(R.id.imageview);
        showDetailImage(facePath, imageView, false);
    }

    private void loadSendword() {
        SendWordRequestListener sendwordRequestListener = new SendWordRequestListener(this);
        SendwordRequest sendwordRequest = new SendwordRequest(SendWord.Model.class, this, mSid);
        spiceManager.execute(sendwordRequest, sendwordRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }


}
