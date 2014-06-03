package com.hyrt.cnp.dynamic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.base.account.CNPClient;
import com.hyrt.cnp.base.account.model.BabyInfo;
import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.base.account.utils.LogHelper;
import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.adapter.DynamicAdapter;
import com.hyrt.cnp.dynamic.request.BabayDynamicRequest;
import com.hyrt.cnp.dynamic.requestListener.MyDynamicRequestListener;
import com.hyrt.cnp.dynamic.ui.BabayIndexActivity;
import com.hyrt.cnp.dynamic.ui.HomeInteractiveActivity;
import com.hyrt.cnp.base.view.XListView;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

import net.oschina.app.AppContext;

import java.util.ArrayList;

/**
 * 全部动态
 * Created by GYH on 14-3-12.
 */
public class AlldynamicFragment extends Fragment {

    private XListView xListView;
    private String more = "1";
    private String moreType = "";
    private HomeInteractiveActivity activity;

    public String STATE;
    final public String REFRESH = "refresh";
    final public String ONLOADMORE = "onLoadMore";
    final private String HASDATA = "hasdata";

    private DynamicAdapter dynamicAdapter;
    private ArrayList<Dynamic> dynamics = new ArrayList<Dynamic>();

    private View rootview;

    private boolean isFirst = true;

    private static final String TAG = "AlldynamicFragment";

    public static final int RESULT_FOR_SEND_DYNAMIC = 105;

    public static AlldynamicFragment instantiation(int position) {
        AlldynamicFragment fragment = new AlldynamicFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        STATE = HASDATA;
        rootview = inflater.inflate(R.layout.fragment_alldynamic, container, false);
        dynamicAdapter = null;
        dynamics.clear();
        isFirst = false;
        initView(rootview);
        initData();
        if(dynamics.size()==0){
            loadData(false);
        }else{
            allDataUi();
        }
        return rootview;
    }


    /**
     * 初始化布局
     */
    private void initView(View view) {
        xListView = (XListView) view.findViewById(R.id.dynamic_listview);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(new XListView.IXListViewListener() {
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
                xListView.stopRefresh();
            }

            @Override
            public void onLoadMore() {
                if (STATE.equals(HASDATA) || STATE.equals(REFRESH)) {
//                    Toast.makeText(BabayIndexActivity.this,"正在加载,请稍后!",Toast.LENGTH_SHORT).show();
                } else {
                    loadData(true);
//                    Toast.makeText(BabayIndexActivity.this,"onLoadMore",Toast.LENGTH_SHORT).show();
                }
                xListView.stopLoadMore();
            }
        });
    }

    public void loadData(boolean isMore) {
        activity = (HomeInteractiveActivity) getActivity();
        MyDynamicRequestListener sendwordRequestListener = new MyDynamicRequestListener(activity);
        BabayDynamicRequest schoolRecipeRequest = null;
        if(isMore){
            if(dynamics.size() > 0){
                if(moreType.toLowerCase().equals("posttime")){
                    more = dynamics.get(dynamics.size()-1).getPosttime();
                }else{
                    more = dynamics.get(dynamics.size()-1).get_id();
                }
                schoolRecipeRequest = new BabayDynamicRequest(
                        Dynamic.Model.class, activity, "", more, true);
            }


        }else{
            STATE = REFRESH;
            if(dynamics.size() > 1){
                String startPosttime = dynamics.get(0).getPosttime();
                String endPosttime = dynamics.get(dynamics.size()-1).getPosttime();
                schoolRecipeRequest = new BabayDynamicRequest(
                        Dynamic.Model.class, activity, "", "1",
                        startPosttime, endPosttime, true);
            }else{
                schoolRecipeRequest = new BabayDynamicRequest(
                        Dynamic.Model.class, activity, "", "1", true);
            }
        }

        if(schoolRecipeRequest != null){
            activity.spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), 1,
                    sendwordRequestListener.start());
        }
    }

    public void upUiData(Dynamic.Model model) {
        if (model == null && dynamics.size() == 0) {
            LinearLayout linearLayout = (LinearLayout) rootview.findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.VISIBLE);
            TextView bottom_num = (TextView) rootview.findViewById(R.id.bottom_num);
            bottom_num.setText("暂无信息");
        } else if (model == null) {
            Toast.makeText(activity, "已经全部加载", Toast.LENGTH_SHORT).show();
        } else {
            moreType = model.getMore();
            if (STATE == null || STATE.equals(REFRESH)) {//如果正在刷新就清空
                dynamics.clear();
            }
            dynamics.addAll(model.getData());
            if (dynamicAdapter == null) {
                String[] resKeys = new String[]{"getUserphoto", "getUserName", "getType",
                        "getPosttime3",  "getPosttime2", "getTransmit2", "getReview2"};
                int[] reses = new int[]{R.id.dynamic_Avatar, R.id.dynamic_name, R.id.dynamic_type,
                        R.id.dynamic_time,  R.id.dynamic_time2, R.id.dynamic_zf_num, R.id.dynamic_pl_num};
                dynamicAdapter = new DynamicAdapter(activity, dynamics, R.layout.layout_item_dynamic, resKeys, reses);
                dynamicAdapter.setCallback(mDynamicAdapterCallback);
                if(xListView != null){
                    xListView.setAdapter(dynamicAdapter);
                }
            } else {
                dynamicAdapter.notifyDataSetChanged();
            }
        }
        STATE = "";//清空状态
    }

    private DynamicAdapter.DynamicAdapterCallback mDynamicAdapterCallback = new DynamicAdapter.DynamicAdapterCallback() {
        @Override
        public void onFaceClick(int position) {
            int userId = dynamics.get(position).getUserId();
            CNPClient cnpClient = new CNPClient();
            cnpClient.configureRequest();
            if(AppContext.getInstance().uuid != -1){
                if(AppContext.getInstance().uuid == userId){
                    activity.showTitle(2);
                    activity.homeViewpager.setCurrentItem(2);
                }else{
                    BabyInfo mBabyInfo = new BabyInfo();
                    mBabyInfo.setUser_id(userId);
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), BabayIndexActivity.class);
                    intent.putExtra("needLoad", true);
                    intent.putExtra("vo",mBabyInfo);
                    startActivity(intent);
                }
            }

        }

        @Override
        public void onPhotoClick(int position, int PhotoPosition) {
            ((BaseActivity)getActivity()).showPop2(rootview, dynamics.get(position).getbPicAry2(), PhotoPosition, getActivity());
        }

        @Override
        public void onAboutClick(int position) {
            int userId = dynamics.get(position).gettUserId();
            if(AppContext.getInstance().uuid != -1){
                if(AppContext.getInstance().uuid == userId){
                    activity.showTitle(2);
                    activity.homeViewpager.setCurrentItem(2);
                }else{
                    BabyInfo mBabyInfo = new BabyInfo();
                    mBabyInfo.setUser_id(userId);
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), BabayIndexActivity.class);
                    intent.putExtra("needLoad", true);
                    intent.putExtra("vo",mBabyInfo);
                    startActivity(intent);
                }
            }
        }
    };

    /**
     * 将已有的数据放到listview中
     * */
    private void allDataUi(){
        String[] resKeys = new String[]{ "getUserName",
                "getPosttime3", "getContent2",
                "getsPicAry0", "getsPicAry1",
                "getsPicAry2", "getPosttime2", "getTransmit2", "getReview2", "gettContent"};
        int[] reses = new int[]{ R.id.dynamic_name,
                R.id.dynamic_time, R.id.dynamic_context,
                R.id.dynamic_image1, R.id.dynamic_image2,
                R.id.dynamic_image3, R.id.dynamic_time2, R.id.dynamic_zf_num, R.id.dynamic_pl_num, R.id.dynamic_dcontext};
        dynamicAdapter = new DynamicAdapter(activity, dynamics, R.layout.layout_item_dynamic, resKeys, reses);
        xListView.setAdapter(dynamicAdapter);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null)
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
    }
}
