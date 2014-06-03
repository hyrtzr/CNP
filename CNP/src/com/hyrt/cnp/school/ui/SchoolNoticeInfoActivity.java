package com.hyrt.cnp.school.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.model.Notice;
import com.hyrt.cnp.base.account.utils.PhotoUpload;
import com.hyrt.cnp.R;
import com.hyrt.cnp.school.request.NotNeedLoginNoticeInfoRequest;
import com.hyrt.cnp.school.request.NoticeAlterRequest;
import com.hyrt.cnp.school.request.NoticeInfoRequest;
import com.hyrt.cnp.school.requestListener.NoticeAlterRequestListener;
import com.hyrt.cnp.school.requestListener.NoticeInfoRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

import net.oschina.app.AppContext;

/**
 * Created by GYH on 14-1-9.
 */
public class SchoolNoticeInfoActivity extends BaseActivity {

    private TextView Noticetitle;
    private TextView Noticetime;
    private TextView Noticecontext;
    private String data;
    private Notice notice;
    private Dialog mPhotoSelctDialog;
    private ImageView btn_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolnoticeinfo);
        initView();
        LoadData();
    }

    @Override
    protected void initTitleview() {
        super.initTitleview();
        Intent intent = getIntent();
        data = intent.getStringExtra("data");
        if (data.equals("school")) {
            titletext.setText("通知公告");
        } else if (data.equals("classroom")) {
            titletext.setText("班级公告");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(menu != null && data != null && data.equals("classroom")
                && AppContext.getInstance().mUserDetail != null
                && AppContext.getInstance().mUserDetail.getGroupID() != 7){
            menu.add("删除")
                    .setIcon(R.drawable.ic_dynamic_del)
                    .setShowAsAction(
                            MenuItem.SHOW_AS_ACTION_ALWAYS);
            return  true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("删除")) {
//            if(mPhotoSelctDialog == null){
//                mPhotoSelctDialog = new Dialog(this, R.style.MyDialog);
//                mPhotoSelctDialog.setContentView(R.layout.layout_notice_dialog);
//                mPhotoSelctDialog.getWindow().setLayout(
//                        LinearLayout.LayoutParams.MATCH_PARENT,
//                        LinearLayout.LayoutParams.MATCH_PARENT);
//                LinearLayout layout_dialog_parent = (LinearLayout) mPhotoSelctDialog.findViewById(R.id.layout_dialog_parent);
//                TextView tv_change_notice = (TextView) mPhotoSelctDialog.findViewById(R.id.tv_change_notice);
//                TextView tv_del_notice = (TextView) mPhotoSelctDialog.findViewById(R.id.tv_del_notice);
//                TextView tv_cancle_dialog = (TextView) mPhotoSelctDialog.findViewById(R.id.tv_cancle_dialog);
//
//                View.OnClickListener mLayoutOnClickListener = new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if(view.getId() == R.id.tv_change_notice){
//                            Intent intent = new Intent();
//                            intent.setClass(SchoolNoticeInfoActivity.this, SendNoticeActivity.class);
//                            intent.putExtra("notice", notice);
//                            intent.putExtra("type", 1);
//                            startActivityForResult(intent, SchoolNoticeActivity.RESULT_FROM_SEND_NOTICE);
//                        }else if(view.getId() == R.id.tv_del_notice){
                            NoticeAlterRequestListener requestListener = new NoticeAlterRequestListener(SchoolNoticeInfoActivity.this);
                            requestListener.setListener(mNoticeDelRequestListener);
                            NoticeAlterRequest request = new NoticeAlterRequest(
                                    SchoolNoticeInfoActivity.this, notice.getAnnource_id());
                            spiceManager.execute(
                                    request, request.getcachekey(),
                                    DurationInMillis.ONE_SECOND * 10,
                                    requestListener.start(2));
//                        }
//                        mPhotoSelctDialog.dismiss();
//                    }
//                };
//                layout_dialog_parent.setOnClickListener(mLayoutOnClickListener);
//                tv_change_notice.setOnClickListener(mLayoutOnClickListener);
//                tv_del_notice.setOnClickListener(mLayoutOnClickListener);
//                tv_cancle_dialog.setOnClickListener(mLayoutOnClickListener);
//            }
//            mPhotoSelctDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public NoticeAlterRequestListener.RequestListener mNoticeDelRequestListener
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == SchoolNoticeActivity.RESULT_FROM_SEND_NOTICE){
            LoadData();
        }
    }

    private void initView() {
        Noticetitle = (TextView) findViewById(R.id.schoolnotice_title);
        Noticetime = (TextView) findViewById(R.id.schoolnotice_time_name);
        Noticecontext = (TextView) findViewById(R.id.notice_context);
        btn_edit = (ImageView) findViewById(R.id.btn_edit);
        if(data != null && data.equals("classroom")
                && AppContext.getInstance().mUserDetail != null
                && AppContext.getInstance().mUserDetail.getGroupID() != 7){
            btn_edit.setVisibility(View.VISIBLE);
            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(SchoolNoticeInfoActivity.this, SendNoticeActivity.class);
                    intent.putExtra("notice", notice);
                    intent.putExtra("type", 1);
                    startActivityForResult(intent, SchoolNoticeActivity.RESULT_FROM_SEND_NOTICE);
                }
            });
        }

    }


    private void LoadData(){
        Notice notice = (Notice) getIntent().getSerializableExtra("notice");
        int sid = getIntent().getIntExtra("sid", -1);
        NoticeInfoRequestListener schoolNoticelistRequestListener = new NoticeInfoRequestListener(this);
        if(sid != -1 && data.equals("school")){
            NotNeedLoginNoticeInfoRequest schoolNoticeListRequest = new NotNeedLoginNoticeInfoRequest(
                    Notice.Model.class, this, data, sid, notice.getAnnource_id()+"");
            spiceManager.execute(schoolNoticeListRequest, schoolNoticeListRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                    schoolNoticelistRequestListener.start());
        }else{
            NoticeInfoRequest schoolNoticeListRequest = new NoticeInfoRequest(
                    Notice.Model.class, this, data, notice.getAnnource_id()+"");
            spiceManager.execute(schoolNoticeListRequest, schoolNoticeListRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                    schoolNoticelistRequestListener.start());
        }

    }

    @Override
    public void finish() {
        setResult(SchoolNoticeActivity.RESULT_FROM_SEND_NOTICE);
        super.finish();
    }

    public void UpDataUI(Notice.Model2 model2){
        notice = model2.getData();
        Noticetitle.setText(notice.getTitle());
        Noticetime.setText("发布人：" + notice.getRenname() + " 发布时间：" + notice.getPosttime2());
        Noticecontext.setText(notice.getContent());
    }
}
