package com.hyrt.cnp.school.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.model.Notice;
import com.hyrt.cnp.base.account.utils.StringUtils;
import com.hyrt.cnp.R;
import com.hyrt.cnp.school.request.NoticeAlterRequest;
import com.hyrt.cnp.school.requestListener.NoticeAlterRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

import net.oschina.app.AppContext;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Zoe on 2014-05-20.
 */
public class SendNoticeActivity extends BaseActivity{
    TextView tv_cancle, tv_send;
    EditText et_notice_title, et_notice_content;

    private Notice mNotice;
    private int type = 0;//0:发布公告;1.修改公告
    private boolean sendEnabled;//发送是否可用

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notice);
        findView();
        setListener();

        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);
        if(type == 0){
            setSendEnabled(false);
        }else{
            mNotice = (Notice) intent.getSerializableExtra("notice");
            et_notice_title.setText(mNotice.getTitle());
            et_notice_title.setSelection(mNotice.getTitle().length());
            et_notice_content.setText(mNotice.getContent());
            setSendEnabled(true);
        }
    }

    /**
     *
     * @param isAdd（true:添加；false:修改）
     */
    private void changeNotice(boolean isAdd){
        NoticeAlterRequestListener requestListener = new NoticeAlterRequestListener(this);
        requestListener.setListener(mRequestListener);
        NoticeAlterRequest request = null;
        if(isAdd){
            request = new NoticeAlterRequest(this,
                    et_notice_title.getText().toString(),
                    et_notice_content.getText().toString());
        }else{
            request = new NoticeAlterRequest(this, mNotice.getAnnource_id(),
                    et_notice_title.getText().toString(),
                    et_notice_content.getText().toString());
        }
        spiceManager.execute(
                request, request.getcachekey(),
                DurationInMillis.ONE_SECOND * 10,
                requestListener.start(type));
    }

    private NoticeAlterRequestListener.RequestListener mRequestListener
            = new NoticeAlterRequestListener.RequestListener() {
        @Override
        public void onRequestSuccess(BaseTest data) {
            setResult(SchoolNoticeActivity.RESULT_FROM_SEND_NOTICE);
            finish();
        }

        @Override
        public void onRequestFailure() {

        }
    };

    @Override
    protected void initTitleview() {
        super.initTitleview();
        actionBar.hide();
    }

    private void setSendEnabled(boolean enable){
        if(enable){
            sendEnabled = true;
            tv_send.setTextColor(getResources().getColor(android.R.color.white));
        }else{
            sendEnabled = false;
            tv_send.setTextColor(getResources().getColor(R.color.sendbtn_enable_color));
        }
    }

    private void findView(){
        tv_cancle = (TextView) findViewById(R.id.tv_cancle);
        tv_send = (TextView) findViewById(R.id.tv_send);
        et_notice_title = (EditText) findViewById(R.id.et_notice_title);
        et_notice_content = (EditText) findViewById(R.id.et_notice_content);
    }

    public void setListener(){
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sendEnabled){
                    if(type == 0){
                        changeNotice(true);
                    }else{
                        changeNotice(false);
                    }
                }
            }
        });

        et_notice_title.addTextChangedListener(mTextWatcher);
        et_notice_content.addTextChangedListener(mTextWatcher);
    }

    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if(et_notice_title.getText().length() > 0
                    && et_notice_content.getText().length() > 0){
                setSendEnabled(true);
            }else{
                setSendEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
