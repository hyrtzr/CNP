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
import com.hyrt.cnp.dynamic.adapter.ListViewAdapter;
import com.hyrt.cnp.dynamic.adapter.MyForwardListAdapter;
import com.hyrt.cnp.dynamic.request.ItInfoRequest;
import com.hyrt.cnp.dynamic.requestListener.ItInfoRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zoe on 2014-04-06.
 */
public class MyForwardListActivity extends BaseActivity{

    private XListView listView;

    private String STATE;
    final private String REFRESH="refresh";
    final private String ONLOADMORE="onLoadMore";
    final private String HASDATA="hasdata";
    private String more="1";

    private MyForwardListAdapter mAdapter;

    private List<ItInfo> datas = new ArrayList<ItInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myitlist);
        findView();
        STATE=HASDATA;
        loadData();
    }

    public void loadData(){
        ItInfoRequestListener requestListener = new ItInfoRequestListener(this);
        requestListener.setListener(mItInfoListener);
        ItInfoRequest request = new ItInfoRequest(ItInfo.Model.class, this, more, 1);
        spiceManager.execute(request, request.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                requestListener.start());

    }

    ItInfoRequestListener.RequestListener mItInfoListener = new ItInfoRequestListener.RequestListener() {
        @Override
        public void onRequestSuccess(ItInfo.Model data) {
            if(data == null){
                LinearLayout linearLayout =(LinearLayout)findViewById(R.id.layout_bottom);
                linearLayout.setVisibility(View.VISIBLE);
                TextView bottom_num = (TextView)findViewById(R.id.bottom_num);
                bottom_num.setText("暂无信息");
            }else{
                more= data.getMore();
                if(STATE.equals(REFRESH)){//如果正在刷新就清空
                    datas.clear();
                }
                datas.addAll(data.getData());
                if(mAdapter == null){
                    String[] resKeys = new String[]{"getFromName", "getdContent",
                            "gettContent", "getPosttime3"};
                    int[] reses = new int[]{ R.id.tv_forward_name,
                            R.id.tv_forward_content, R.id.tv_forward_content2,
                            R.id.tv_forward_time};
                    mAdapter = new MyForwardListAdapter(MyForwardListActivity.this, datas, R.layout.forward_item, resKeys, reses);
                    listView.setAdapter(mAdapter);
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

    public void findView() {
        listView = (XListView) findViewById(R.id.myit_listview);
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                if (STATE.equals(HASDATA) || STATE.equals(ONLOADMORE)) {
                    AlertUtils.getInstance().showCenterToast(MyForwardListActivity.this, "正在加载,请稍后!");
//                    Toast.makeText(MyForwardListActivity.this, "正在加载,请稍后!", Toast.LENGTH_SHORT).show();
                } else {
                    STATE = REFRESH;
                    more = "1";
                    loadData();
                }
                listView.stopRefresh();
            }

            @Override
            public void onLoadMore() {
//                if (STATE.equals(HASDATA) || STATE.equals(REFRESH)) {
//                    Toast.makeText(MyForwardListActivity.this, "正在加载,请稍后!", Toast.LENGTH_SHORT).show();
//                } else {
//                    loadData();
//                }
                AlertUtils.getInstance().showCenterToast(MyForwardListActivity.this, "已经全部加载");
                listView.stopLoadMore();
            }
        });
    }
}
