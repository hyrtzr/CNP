package com.hyrt.cnp.dynamic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.base.account.model.BabyInfo;
import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.adapter.DynamicAdapter;
import com.hyrt.cnp.dynamic.request.BabaywordRequest;
import com.hyrt.cnp.dynamic.requestListener.BabaywordRequestListener;
import com.hyrt.cnp.base.view.XListView;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

import java.util.ArrayList;

/**
 * Created by GYH on 14-1-21.
 * 童言稚语
 */
public class BabayWordActivity extends BaseActivity{

    private DynamicAdapter dynamicAdapter;
    private XListView listView;
    private BabyInfo classRoomBabay;
    private String STATE;
    final private String REFRESH="refresh";
    final private String ONLOADMORE="onLoadMore";
    final private String HASDATA="hasdata";
    private String more="1";
    private String moreType = "";

    private ArrayList<Dynamic> dynamics=new ArrayList<Dynamic>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babayword);
        initView();
        initData();
        STATE=HASDATA;
        loadData(false);
    }

    private void initView(){
        listView = (XListView)findViewById(R.id.dynamic_listview);
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                if(STATE.equals(HASDATA)||STATE.equals(ONLOADMORE)){
                    Toast.makeText(BabayWordActivity.this,"正在加载,请稍后!",Toast.LENGTH_SHORT).show();
                }else {
                    STATE=REFRESH;
                    more="1";
//                    Toast.makeText(BabayIndexActivity.this,"正在刷新,请稍后!",Toast.LENGTH_SHORT).show();
                    loadData(false);
                }
                listView.stopRefresh();
            }

            @Override
            public void onLoadMore() {
                if(STATE.equals(HASDATA)||STATE.equals(REFRESH)){
                    Toast.makeText(BabayWordActivity.this,"正在加载,请稍后!",Toast.LENGTH_SHORT).show();
                }else {
                    loadData(true);
//                    Toast.makeText(BabayIndexActivity.this,"onLoadMore",Toast.LENGTH_SHORT).show();
                }
                listView.stopLoadMore();
            }
        });
    }

    private void initData(){
        Intent intent=getIntent();
        classRoomBabay = (BabyInfo)intent.getSerializableExtra("vo");
    }

    /**
     *
     * 更新ui界面
     * */

    public void updateUI(Dynamic.Model model){
        if(model==null&&dynamics.size()==0){
            LinearLayout linearLayout =(LinearLayout)findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.VISIBLE);
            TextView bottom_num = (TextView)findViewById(R.id.bottom_num);
            bottom_num.setText("暂无信息");
        }else if(model==null){
            Toast.makeText(BabayWordActivity.this, "已经全部加载", Toast.LENGTH_SHORT).show();
        }else{
            moreType = model.getMore();
            if(STATE.equals(REFRESH)){//如果正在刷新就清空
                dynamics.clear();
            }
            dynamics.addAll(model.getData());
            if(dynamicAdapter==null){
                String[] resKeys=new String[]{"getUserphoto","getUserName",
                        "getPosttime3","getContent2",
                        "getsPicAry0","getsPicAry1",
                        "getsPicAry2","getPosttime2","getTransmit2","getReview2","gettContent"};
                int[] reses=new int[]{R.id.dynamic_Avatar,R.id.dynamic_name,
                        R.id.dynamic_time,R.id.dynamic_context,
                        R.id.dynamic_image1,R.id.dynamic_image2,
                        R.id.dynamic_image3,R.id.dynamic_time2,R.id.dynamic_zf_num,R.id.dynamic_pl_num,R.id.dynamic_dcontext};
                dynamicAdapter = new DynamicAdapter(this,dynamics,R.layout.layout_item_dynamic,resKeys,reses);
                listView.setAdapter(dynamicAdapter);
            }else{
                dynamicAdapter.notifyDataSetChanged();
            }

        }
        STATE="";//清空状态
    }


    private void loadData(boolean isMore){
        BabaywordRequestListener sendwordRequestListener = new BabaywordRequestListener(this);
        BabaywordRequest schoolRecipeRequest = null;
        if(isMore){
            if(dynamics.size() > 0){
                more = dynamics.get(dynamics.size()-1).getPosttime();
                schoolRecipeRequest=new BabaywordRequest(Dynamic.Model.class,this,classRoomBabay.getUser_id()+"",more);
            }
        }else{
            schoolRecipeRequest=new BabaywordRequest(Dynamic.Model.class,this,classRoomBabay.getUser_id()+"","1");
        }
        if(schoolRecipeRequest != null){
            spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                    sendwordRequestListener.start());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0&&resultCode==1){
                STATE=REFRESH;
                more="1";
                loadData(false);
        }
    }
}
