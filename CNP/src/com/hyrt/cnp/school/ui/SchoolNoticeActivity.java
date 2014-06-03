package com.hyrt.cnp.school.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.model.Notice;
import com.hyrt.cnp.base.account.utils.LogHelper;
import com.hyrt.cnp.R;
import com.hyrt.cnp.school.adapter.SchoolNoticeAdapter;
import com.hyrt.cnp.school.request.NotNeedLoginSchoolNoticeListRequest;
import com.hyrt.cnp.school.request.NoticeAlterRequest;
import com.hyrt.cnp.school.request.SchoolNoticeListRequest;
import com.hyrt.cnp.school.requestListener.NoticeAlterRequestListener;
import com.hyrt.cnp.school.requestListener.SchoolNoticeRequestListener;
import com.hyrt.cnp.base.view.XListView;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

import net.oschina.app.AppContext;

import java.util.ArrayList;

/**
 * Created by GYH on 14-1-9.
 */
public class SchoolNoticeActivity extends BaseActivity {

    private XListView noticelistview;
    private SchoolNoticeAdapter schoolNoticeAdapter = null;
    //    private Notice.Model models;
    private String data;
    private int mSid = -1;
    private Notice notice;
    private TextView schoolnotice_title, schoolnotice_time_name, schoolnotice_content, tv_change, tv_del;
    private LinearLayout noticefirst;

    private ArrayList<Notice> notices = new ArrayList<Notice>();
    private ArrayList<Notice> notices2 = new ArrayList<Notice>();
    private String STATE;
    final private String REFRESH = "refresh";
    final private String ONLOADMORE = "onLoadMore";
    final private String HASDATA = "hasdata";
    private String more = "1";

    public static final int RESULT_FROM_SEND_NOTICE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolnotice);
        initView();
        if(data != null && data.equals("classroom")
                && AppContext.getInstance().mUserDetail != null
                && AppContext.getInstance().mUserDetail.getGroupID() != 7){
//            layoutAlter.setVisibility(View.VISIBLE);
        }
        STATE = HASDATA;
        loadData(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(menu != null && data != null && data.equals("classroom")
                && AppContext.getInstance().mUserDetail != null
                && AppContext.getInstance().mUserDetail.getGroupID() != 7){
            menu.add("发布")
                    .setIcon(R.drawable.ic_actionbar_upload)
                    .setShowAsAction(
                            MenuItem.SHOW_AS_ACTION_ALWAYS);
            return  true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("发布")) {
            Intent intent = new Intent();
            intent.setClass(this, SendNoticeActivity.class);
            startActivityForResult(intent, RESULT_FROM_SEND_NOTICE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_FROM_SEND_NOTICE){
            loadData(false);
        }
    }

    /**
     * 更新actionbar标题
     */
    @Override
    protected void initTitleview() {
        super.initTitleview();
        Intent intent = getIntent();
        data = intent.getStringExtra("data");
        mSid = intent.getIntExtra("sid", -1);
        if (data.equals("school")) {
            titletext.setText("通知公告");
        } else if (data.equals("classroom")) {
            titletext.setText("班级公告");
        }
    }

    private void loadData(boolean isMore) {
        if(!isMore){
            STATE = REFRESH;
        }
        if (data.equals("school")) {
            loadSchoolNoticeData(isMore);
        } else if (data.equals("classroom")) {
            loadClassroomNoticeData(isMore);
        }
    }

    /**
     * 初始化ui界面
     */

    private void initView() {
        noticefirst = (LinearLayout) findViewById(R.id.notice_first);
        schoolnotice_title = (TextView) findViewById(R.id.schoolnotice_title);
        schoolnotice_time_name = (TextView) findViewById(R.id.schoolnotice_time_name);
        schoolnotice_content = (TextView) findViewById(R.id.schoolnotice_content);
//        layoutAlter = (LinearLayout) findViewById(R.id.layout_alter);
        tv_change = (TextView) findViewById(R.id.tv_change);
        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(SchoolNoticeActivity.this, SendNoticeActivity.class);
                intent.putExtra("notice", notice);
                intent.putExtra("type", 1);
                startActivityForResult(intent, RESULT_FROM_SEND_NOTICE);
            }
        });
        tv_del = (TextView) findViewById(R.id.tv_del);
        tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoticeAlterRequestListener requestListener = new NoticeAlterRequestListener(SchoolNoticeActivity.this);
                requestListener.setListener(mNoticeDelRequestListener);
                NoticeAlterRequest  request = new NoticeAlterRequest(
                        SchoolNoticeActivity.this, notice.getAnnource_id());
                spiceManager.execute(
                        request, request.getcachekey(),
                        DurationInMillis.ONE_SECOND * 10,
                        requestListener.start(2));
            }
        });

        noticelistview = (XListView) findViewById(R.id.schoolnotice_listview);
        noticelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(SchoolNoticeActivity.this, SchoolNoticeInfoActivity.class);
                intent.putExtra("notice", notices.get(i));
                intent.putExtra("data", data);
                intent.putExtra("sid", mSid);
                startActivityForResult(intent, RESULT_FROM_SEND_NOTICE);
            }
        });
        noticelistview.setPullLoadEnable(true);
        noticelistview.setPullRefreshEnable(true);
        noticelistview.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                if (STATE.equals(HASDATA) || STATE.equals(ONLOADMORE)) {
                    Toast.makeText(SchoolNoticeActivity.this, "正在加载,请稍后!", Toast.LENGTH_SHORT).show();
                } else {
                    STATE = REFRESH;
                    more = "1";
                    loadData(false);
                }
                noticelistview.stopRefresh();
            }

            @Override
            public void onLoadMore() {
                if (STATE.equals(HASDATA) || STATE.equals(REFRESH)) {
                    Toast.makeText(SchoolNoticeActivity.this, "正在加载,请稍后!", Toast.LENGTH_SHORT).show();
                } else {
                    loadData(true);
                }
                noticelistview.stopLoadMore();
            }
        });


    }

    public NoticeAlterRequestListener.RequestListener mNoticeDelRequestListener
            = new NoticeAlterRequestListener.RequestListener() {
        @Override
        public void onRequestSuccess(BaseTest data) {
            loadData(false);
        }

        @Override
        public void onRequestFailure() {

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 学习公告请求方法
     */
    private void loadSchoolNoticeData(boolean isMore) {
        SchoolNoticeRequestListener schoolNoticelistRequestListener = new SchoolNoticeRequestListener(this);
        NotNeedLoginSchoolNoticeListRequest schoolNoticeListRequest = null;
        if(isMore){
            if(notices.size() > 0){
                more = notices.get(notices.size()-1).getAnnource_id()+"";
                schoolNoticeListRequest = new NotNeedLoginSchoolNoticeListRequest(
                        Notice.Model.class, this, "school", more, mSid);
            }
        }else{
            schoolNoticeListRequest = new NotNeedLoginSchoolNoticeListRequest(
                    Notice.Model.class, this, "school", "1", mSid);
        }
        if(schoolNoticeListRequest != null){
            spiceManager.execute(
                    schoolNoticeListRequest, schoolNoticeListRequest.getcachekey(),
                    DurationInMillis.ONE_SECOND * 10,
                    schoolNoticelistRequestListener.start());
        }
    }

    /**
     * 班级公告请求方法
     */

    private void loadClassroomNoticeData(boolean isMore) {
        SchoolNoticeRequestListener schoolNoticelistRequestListener = new SchoolNoticeRequestListener(this);

        SchoolNoticeListRequest schoolNoticeListRequest = null;
        if(isMore){
            if(notices.size() > 0){
                more = notices.get(notices.size()-1).getAnnource_id()+"";
                schoolNoticeListRequest = new SchoolNoticeListRequest(
                        Notice.Model.class, this, "classroom", more);
            }
        }else{
            schoolNoticeListRequest = new SchoolNoticeListRequest(
                    Notice.Model.class, this, "classroom", "1");
        }
        if(schoolNoticeListRequest != null){
            spiceManager.execute(
                    schoolNoticeListRequest, schoolNoticeListRequest.getcachekey(),
                    DurationInMillis.ONE_SECOND * 10,
                    schoolNoticelistRequestListener.start());
        }
    }

    /**
     * 监听更新ui界面数据
     */
    public void initData(Notice.Model model) {
        if (model == null && notices.size() == 0) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.VISIBLE);
            TextView bottom_num = (TextView) findViewById(R.id.bottom_num);
            bottom_num.setText("暂无信息");
            noticefirst.setVisibility(View.GONE);
        } else if (model == null) {
            Toast.makeText(SchoolNoticeActivity.this, "已经全部加载", Toast.LENGTH_SHORT).show();
        } else {
            noticefirst.setVisibility(View.VISIBLE);
            if (STATE.equals(REFRESH)) {//如果正在刷新就清空
                notices.clear();
            }
            notices.addAll(model.getData());
//            if(notices.size() <= 0){
//                notices.addAll(model.getData());
                notice = model.getData().get(0);
                schoolnotice_title.setText(notice.getTitle());

                schoolnotice_time_name.setText("发布人:" + notice.getRenname() +
                        "    发布时间:" + notice.getPosttime2());

                schoolnotice_content.setText(notice.getContent());
//            }


            noticefirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(SchoolNoticeActivity.this, SchoolNoticeInfoActivity.class);
                    intent.putExtra("notice", notice);
                    intent.putExtra("data", data);
                    intent.putExtra("sid", mSid);
                    startActivityForResult(intent, RESULT_FROM_SEND_NOTICE);
                }
            });
//            notices2.clear();
//            notices2.addAll(notices);
//            notices2.remove(0);//移除第一个
            if (schoolNoticeAdapter == null) {
                schoolNoticeAdapter = new SchoolNoticeAdapter(SchoolNoticeActivity.this, notices, data);
                noticelistview.setAdapter(schoolNoticeAdapter);
            } else {
                schoolNoticeAdapter.notifyDataSetChanged();
            }
        }
        STATE = "";//清空状态
    }
}
