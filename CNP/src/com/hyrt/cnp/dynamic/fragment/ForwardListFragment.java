package com.hyrt.cnp.dynamic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.base.view.XListView;
import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.adapter.ForwardListAdapter;
import com.hyrt.cnp.dynamic.request.BabayDynamicRequest;
import com.hyrt.cnp.dynamic.requestListener.MyDynamicRequestListener;
import com.hyrt.cnp.dynamic.ui.CommentListActivity;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.exception.SpiceException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zoe on 2014-04-22.
 */
public class ForwardListFragment extends Fragment{

    private View parentView;
    private XListView listview;
    private Dynamic dynamic;
    private List<Dynamic> datas = new ArrayList<Dynamic>();
    private ForwardListAdapter dynamicAdapter;

    public String STATE;
    final public String REFRESH = "refresh";
    final private String ONLOADMORE = "onLoadMore";
    final private String HASDATA = "hasdata";
    private String more = "1";

    public ForwardListFragment(Dynamic dynamic) {
        this.dynamic = dynamic;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_list, null);
        findView();
        loadData(false);
        return parentView;
    }

    public void loadData(boolean isMore){
        MyDynamicRequestListener sendwordRequestListener = new MyDynamicRequestListener(getActivity());
        sendwordRequestListener.setListener(mForwardReuqestListener);
        BabayDynamicRequest schoolRecipeRequest = null;
        if(isMore){
            if(datas.size() > 0){
                more = datas.get(datas.size()-1).getPosttime();
                schoolRecipeRequest = new BabayDynamicRequest(
                        Dynamic.Model.class, (BaseActivity)getActivity(), "", dynamic.get_id(), more);
            }
        }else{
            STATE = REFRESH;
            schoolRecipeRequest = new BabayDynamicRequest(
                    Dynamic.Model.class, (BaseActivity)getActivity(), "", dynamic.get_id(), "1");
        }
        if(schoolRecipeRequest != null){
            ((BaseActivity)getActivity()).spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), 1,
                    sendwordRequestListener.start());
        }
    }

    private MyDynamicRequestListener.RequestListener mForwardReuqestListener = new MyDynamicRequestListener.RequestListener() {
        @Override
        public void onRequestSuccess(Dynamic.Model data) {
            UpDataUI(data);
        }

        @Override
        public void onRequestFailure(SpiceException e) {
            UpDataUI(null);
        }
    };

    public void UpDataUI(Dynamic.Model model){
        if (model == null && datas.size() == 0) {
            LinearLayout linearLayout = (LinearLayout) parentView.findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.VISIBLE);
            TextView bottom_num = (TextView) parentView.findViewById(R.id.bottom_num);
            bottom_num.setText("暂无信息");
        } else if (model == null) {
            Toast.makeText(getActivity(), "已经全部加载", Toast.LENGTH_SHORT).show();
        }else{
            if (STATE == null || STATE.equals(REFRESH)) {//如果正在刷新就清空
                datas.clear();
            }
            datas.addAll(model.getData());
            ((CommentListActivity)getActivity()).onLoad(datas.size(), 0);
            if(dynamicAdapter==null){
                String[] resKeys=new String[]{"getUserName","getPosttime3"};
                int[] reses=new int[]{R.id.tv_forward_name, R.id.tv_forward_time};
                dynamicAdapter = new ForwardListAdapter(
                        (BaseActivity)getActivity(),
                        datas,
                        R.layout.forward_item,
                        resKeys,reses);
                listview.setAdapter(dynamicAdapter);
            }else{
                dynamicAdapter.notifyDataSetChanged();
            }
            LinearLayout linearLayout =(LinearLayout)parentView.findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.GONE);
        }
        STATE = "";//清空状态
    }

    private void findView(){
        listview = (XListView) parentView.findViewById(R.id.listview);
        listview.setPullLoadEnable(true);
        listview.setPullRefreshEnable(true);
        listview.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                if (STATE.equals(HASDATA) || STATE.equals(ONLOADMORE)) {
//                    Toast.makeText(BabayIndexActivity.this, "正在加载,请稍后!", Toast.LENGTH_SHORT).show();
                } else {
                    STATE = REFRESH;
                    more = "1";
//                    Toast.makeText(BabayIndexActivity.this,"正在刷新,请稍后!",Toast.LENGTH_SHORT).show();
                    loadData(false);
                }
                listview.stopRefresh();
            }

            @Override
            public void onLoadMore() {
                if (STATE.equals(HASDATA) || STATE.equals(REFRESH)) {
//                    Toast.makeText(BabayIndexActivity.this,"正在加载,请稍后!",Toast.LENGTH_SHORT).show();
                } else {
                    loadData(true);
//                    Toast.makeText(BabayIndexActivity.this,"onLoadMore",Toast.LENGTH_SHORT).show();
                }
                listview.stopLoadMore();
            }
        });
    }
}
