package com.hyrt.cnp.dynamic.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.base.account.model.ItInfo;
import com.hyrt.cnp.base.account.utils.AlertUtils;
import com.hyrt.cnp.base.view.XListView;
import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.adapter.MyItInfoAdapter;
import com.hyrt.cnp.dynamic.request.ItInfoRequest;
import com.hyrt.cnp.dynamic.requestListener.ItInfoRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

import java.util.ArrayList;

/**
 * Created by GYH on 2014/4/5. change
 * @我的
 */
public class MyItListActivity extends BaseActivity{

    private String STATE;
    final private String REFRESH="refresh";
    final private String ONLOADMORE="onLoadMore";
    final private String HASDATA="hasdata";
    private String more="1";
    private XListView listView;
    private ArrayList<ItInfo> itInfos =new ArrayList<ItInfo>();
    private MyItInfoAdapter myItInfoAdapter=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myitlist);
        initView();
        STATE=HASDATA;
        loadData();
    }

    private void initView(){
        listView = (XListView)findViewById(R.id.myit_listview);
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                if(STATE.equals(HASDATA)||STATE.equals(ONLOADMORE)){
                    AlertUtils.getInstance().showCenterToast(MyItListActivity.this, "正在加载,请稍后!");
//                    Toast.makeText(MyItListActivity.this, "正在加载,请稍后!", Toast.LENGTH_SHORT).show();
                }else {
                    STATE=REFRESH;
                    more="1";
                    loadData();
                }
                listView.stopRefresh();
            }

            @Override
            public void onLoadMore() {
                AlertUtils.getInstance().showCenterToast(MyItListActivity.this, "已经全部加载");
//                if(STATE.equals(HASDATA)||STATE.equals(REFRESH)){
//                    AlertUtils.getInstance().showCenterToast(MyItListActivity.this, "正在加载,请稍后!");
////                    Toast.makeText(MyItListActivity.this,"正在加载,请稍后!",Toast.LENGTH_SHORT).show();
//                }else {
//                    loadData();
//                }
                listView.stopLoadMore();
            }
        });
    }

    public void loadData(){
        ItInfoRequestListener sendwordRequestListener = new ItInfoRequestListener(this);
        ItInfoRequest schoolRecipeRequest=new ItInfoRequest(
                ItInfo.Model.class,this,more);
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }

    /**
     *
     * 更新ui界面
     * */

    public void updateUI(ItInfo.Model model){

        if(model==null&& itInfos.size()==0){
            LinearLayout linearLayout =(LinearLayout)findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.VISIBLE);
            TextView bottom_num = (TextView)findViewById(R.id.bottom_num);
            bottom_num.setText("暂无信息");
        }else if(model==null){
            AlertUtils.getInstance().showCenterToast(MyItListActivity.this, "已经全部加载");
        }else{
            more=model.getMore();
            if(STATE.equals(REFRESH)){//如果正在刷新就清空
                itInfos.clear();
            }
            itInfos.addAll(model.getData());
            if(myItInfoAdapter==null){
                String[] resKeys=new String[]{"getUserphoto","getFromName",
                        "getPosttime3","getMsgTitle","getPosttime2","getMsgData"};
                int[] reses=new int[]{R.id.dynamic_Avatar,R.id.dynamic_name,
                        R.id.dynamic_time,R.id.dynamic_context,
                        R.id.dynamic_time2,R.id.dynamic_dcontext};
                myItInfoAdapter = new MyItInfoAdapter(this, itInfos,R.layout.layout_item_dynamic,resKeys,reses);
                listView.setAdapter(myItInfoAdapter);
            }else{
                myItInfoAdapter.notifyDataSetChanged();
            }
        }
        STATE="";//清空状态
    }
}
