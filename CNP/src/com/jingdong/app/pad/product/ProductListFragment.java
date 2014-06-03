package com.jingdong.app.pad.product;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.jingdong.app.pad.adapter.MySimpleAdapter;
import com.jingdong.app.pad.utils.NextPageLoader;
import com.jingdong.common.frame.MyActivity;
import com.jingdong.common.frame.taskStack.ApplicationManager;
import com.jingdong.common.frame.taskStack.TaskModule;
import com.jingdong.common.http.HttpGroup;
import com.jingdong.common.http.HttpResponse;

import net.oschina.app.bean.Constants;
import net.oschina.app.bean.URLs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductListFragment extends MyActivity
        implements AdapterView.OnItemClickListener {

    private ListView listView;
    private NextPageLoader nextPageLoader;
    private boolean loading;

    private void getWareList(Map<String, Object> paramMap) {
       /* nextPageLoader = new NextPageLoader(this, listView, URLs.PRO_LIST, paramMap,
                BuildProjList.class) {

            *//**
             * 获取列表集合
             *//*
            @Override
            protected ArrayList toList(HttpResponse paramHttpResponse) {
                return (ArrayList) ((BuildProjList) paramHttpResponse
                        .getResultObject()).getBuildProjInfo();
            }

            *//**
             * 加载异常时，调用次方法
             *//*
            @Override
            protected void showError() {
                // 发现异常，重新加载
                this.showThisPage();
            }

            *//**
             * 获取基本的适配器
             *//*
            @Override
            protected MySimpleAdapter createAdapter(MyActivity paramMyActivity,
                                                    AdapterView paramAdapterView, ArrayList paramArrayList) {
                return new MySimpleAdapter(paramMyActivity, paramArrayList,
                        123, new String[]{"getCBprojcontent",
                        "getCRemark"}, new int[]{android.R.id.text1,
                        android.R.id.icon1});
            }
        };
        nextPageLoader.showThisPage();*/
    }

    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
        /*BuildProjContent localProduct = (BuildProjContent) this.nextPageLoader.getItemAt(paramInt);
        if (localProduct == null)
            return;
        ProductDetailFragment.ProductDetailFragmentTM localProductDetailFragmentTM = new ProductDetailFragment.ProductDetailFragmentTM();
        Bundle localBundle = new Bundle();
        localBundle.putSerializable("BuildProjContent", localProduct);
        localProductDetailFragmentTM.setBundle(localBundle);
        ApplicationManager.go(localProductDetailFragmentTM);*/
    }

    public void onStop() {
        super.onStop();
    }

    protected View realCreateViewMethod(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup) {
      /*  View localView = inflate(R.layout.list_widget);
        this.listView = ((ListView) localView.findViewById(R.id.list_widget));
        this.listView.setOnItemClickListener(this);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(Constants.PRO_NAME, getArguments().getString(Constants.PRO_NAME));
        paramMap.put(Constants.PRO_LEVEL, getArguments().getString(Constants.PRO_LEVEL));
        getWareList(paramMap);
        return localView;*/
        return null;
    }

    @Override
    public synchronized void refresh() {
       /* if (!loading) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put(Constants.PRO_NAME, getArguments().getString(Constants.PRO_NAME));
            paramMap.put(Constants.PRO_LEVEL, getArguments().getString(Constants.PRO_LEVEL));
            loading = true;
            execute(URLs.PRO_LIST, paramMap, BuildProjList.class, new HttpGroup.OnEndListener() {
                @Override
                public void onEnd(HttpResponse httpresponse) {
                    loading = false;
                    final List list =  (ArrayList) ((BuildProjList) httpresponse
                            .getResultObject()).getBuildProjInfo();
                    if(nextPageLoader != null) {
                        nextPageLoader.modifyData(new NextPageLoader.ModifyDataRunnable(){

                            @Override
                            public void modifyData(ArrayList<Object> paramArrayList) {
                                paramArrayList.clear();
                                paramArrayList.add(list);
                            }
                        });
                    }
                }
            });

        }*/
    }

    public static class ProductListTM extends TaskModule {
        private ProductListFragment productListTM;

        protected void doInit() {
            this.productListTM = new ProductListFragment();
            this.productListTM.setArguments(getBundle());
        }

        protected void doShow() {
            replaceAndCommit(this.productListTM);
        }
    }

    @Override
    public void onDestroy() {
        listView = null;
        nextPageLoader = null;
        super.onDestroy();
    }
}