package com.hyrt.cnp.dynamic.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.internal.view.SupportMenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.R;
import com.hyrt.cnp.base.account.model.ClassRoomBabay;
import com.hyrt.cnp.base.account.request.BaseClassroomBabayRequest;
import com.hyrt.cnp.base.account.requestListener.BaseClassroomBabayRequestListener;
import com.hyrt.cnp.base.view.XListView;
import com.hyrt.cnp.dynamic.adapter.ListViewAdapter;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;

/**
 * Created by Zoe on 2014-04-09.
 */
public class AboutFriendActivity extends BaseActivity{

    private MenuItem sendbtn;
    private XListView listview;

    private String STATE;
    final private String REFRESH="refresh";
    final private String ONLOADMORE="onLoadMore";
    final private String HASDATA="hasdata";
//    private String more="1";

    private List<ClassRoomBabay> datas = new ArrayList<ClassRoomBabay>();
    private ListViewAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myitlist);
        findView();
        STATE=HASDATA;
        loadData();
        setListener();
    }

    public void loadData(){
        BaseClassroomBabayRequestListener requestListener = new BaseClassroomBabayRequestListener(this);
        requestListener.setListener(mRequestListener);
        BaseClassroomBabayRequest request = new BaseClassroomBabayRequest(ClassRoomBabay.Model.class, this, true);
        spiceManager.execute(request, request.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                requestListener.start());
    }

    public void setListener(){
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ClassRoomBabay crb = datas.get(i-1);
                Intent data = new Intent();
                data.putExtra("name", crb.getRenname());
                data.putExtra("uid", crb.getUser_id());
                setResult(SendDynamicActivity.FROM_ABOUT_FRIEND, data);
                finish();
            }
        });
    }

    private BaseClassroomBabayRequestListener.requestListener mRequestListener = new BaseClassroomBabayRequestListener.requestListener() {
        @Override
        public void onRequestSuccess(ClassRoomBabay.Model data) {
            if(data == null){
                LinearLayout linearLayout =(LinearLayout)findViewById(R.id.layout_bottom);
                linearLayout.setVisibility(View.VISIBLE);
                TextView bottom_num = (TextView)findViewById(R.id.bottom_num);
                bottom_num.setText("暂无信息");
            }else{
//                more= data.getMore();
                if(STATE.equals(REFRESH)){//如果正在刷新就清空
                    datas.clear();
                }
                datas.addAll(data.getData());
                if(mAdapter == null){
                    String[] resKeys = new String[]{"getLogopath", "getRenname"};
                    int[] reses = new int[]{R.id.iv_about_friend_face, R.id.tv_about_friend_name};
                    mAdapter = new ListViewAdapter(AboutFriendActivity.this, datas, R.layout.about_friend_item, resKeys, reses);
                    listview.setAdapter(mAdapter);
                }else{
                    mAdapter.notifyDataSetChanged();
                }
            }
            STATE="";//清空状态
        }

        @Override
        public void onRequestFailure(SpiceException e) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SupportMenuInflater mMenuInflater = new SupportMenuInflater(this);
        mMenuInflater.inflate(R.menu.senddy, menu);
        menu.findItem(R.id.senddy).
                setCheckable(false).
                setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        sendbtn = menu.findItem(R.id.senddy);
        sendbtn.setTitle("更新");
        sendbtn.setEnabled(true);
        return true;
    }

    public void findView(){
        listview = (XListView) findViewById(R.id.myit_listview);

        listview.setPullLoadEnable(true);
        listview.setPullRefreshEnable(true);
        listview.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                if (STATE.equals(HASDATA) || STATE.equals(ONLOADMORE)) {
                    Toast.makeText(AboutFriendActivity.this, "正在加载,请稍后!", Toast.LENGTH_SHORT).show();
                } else {
                    STATE = REFRESH;
//                    more = "1";
                    loadData();
                }
                listview.stopRefresh();
            }

            @Override
            public void onLoadMore() {
                /*if (STATE.equals(HASDATA) || STATE.equals(REFRESH)) {
                    Toast.makeText(AboutFriendActivity.this, "正在加载,请稍后!", Toast.LENGTH_SHORT).show();
                } else {
                    loadData();
                }
                */
                listview.stopLoadMore();
            }
        });
    }
}
