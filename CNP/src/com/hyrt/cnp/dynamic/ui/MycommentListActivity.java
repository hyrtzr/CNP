package com.hyrt.cnp.dynamic.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.base.account.model.ItInfo;
import com.hyrt.cnp.base.account.utils.AlertUtils;
import com.hyrt.cnp.base.view.XListView;
import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.adapter.ListViewAdapter;
import com.hyrt.cnp.dynamic.adapter.MyCommnentListAdapter;
import com.hyrt.cnp.dynamic.request.ItInfoRequest;
import com.hyrt.cnp.dynamic.requestListener.ItInfoRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zoe 2014/4/5.
 */
public class MycommentListActivity extends BaseActivity{

    private XListView listView;

    private String STATE;
    final private String REFRESH="refresh";
    final private String ONLOADMORE="onLoadMore";
    final private String HASDATA="hasdata";
    private String more="1";

    private MyCommnentListAdapter mAdapter;

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
        ItInfoRequest request = new ItInfoRequest(ItInfo.Model.class, this, more, 2);
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
                    String[] resKeys = new String[]{"getFromName", "getPosttime3",
                            "getMsgTitle", "getMsgData"};
                    int[] reses = new int[]{R.id.tv_comment_name,
                            R.id.tv_comment_time, R.id.tv_comment_content,
                            R.id.tv_comment_content2};
                    mAdapter = new MyCommnentListAdapter(MycommentListActivity.this, datas, R.layout.comment_item, resKeys, reses);
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
                    AlertUtils.getInstance().showCenterToast(MycommentListActivity.this, "正在加载,请稍后!");
//                    Toast.makeText(MycommentListActivity.this, "正在加载,请稍后!", Toast.LENGTH_SHORT).show();
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
//                    Toast.makeText(MycommentListActivity.this, "正在加载,请稍后!", Toast.LENGTH_SHORT).show();
//                } else {
//                    loadData();
//                }
                AlertUtils.getInstance().showCenterToast(MycommentListActivity.this, "已经全部加载");
                listView.stopLoadMore();
            }
        });

    }
}
