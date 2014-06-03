package com.hyrt.cnp.school.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.base.account.model.Base;
import com.hyrt.cnp.base.account.request.BaseUserVarRequest;
import com.hyrt.cnp.base.account.requestListener.BaseUserVarRequestListener;
import com.hyrt.cnp.base.view.BounceListView;
import com.hyrt.cnp.R;
import com.hyrt.cnp.school.adapter.SchoolCityListAdapter;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;

import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Zoe on 2014-04-04.
 */
public class SchoolCityListActivity extends BaseActivity{
    private TextView tvCancle, tv_school_city_list_prompt;
    private BounceListView mListView;
    private String position;

    private String[] provinces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_city_list);
        Intent intent = getIntent();
        position = intent.getStringExtra("position");
        findView();
        setListener();
        loadData();
    }

    private void loadData(){
        BaseUserVarRequestListener userVarRequestListener = new BaseUserVarRequestListener(this, "province");
        userVarRequestListener.setListener(mUserVarListener);

        BaseUserVarRequest userVarRequest = new BaseUserVarRequest(this, "province");
        spiceManager.execute(
                userVarRequest, userVarRequest.createCacheKey(),
                DurationInMillis.ONE_SECOND * 10,
                userVarRequestListener.start());
    }

    private BaseUserVarRequestListener.RequestListener mUserVarListener = new BaseUserVarRequestListener.RequestListener() {
        @Override
        public void onRequestSuccess(Map<String, String> data) {
            Collection<String> collection = data.values();
            Iterator it = collection.iterator();

            provinces = new String[data.size()];
            int i = 0;
            while(it.hasNext()){
                String text = (String) it.next();
                provinces[i] = text;
                i++;
            }

            SchoolCityListAdapter mAdapter = new SchoolCityListAdapter(SchoolCityListActivity.this, provinces, position);
            tv_school_city_list_prompt.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            mListView.setAdapter(mAdapter);
        }

        @Override
        public void onRequestFailure(SpiceException e) {
            tv_school_city_list_prompt.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        }
    };

    @Override
    protected void initTitleview() {
        actionBar.hide();
    }

    private void setListener(){
        tvCancle.setOnClickListener(tvCancleOnClickListener);
        mListView.setOnItemClickListener(mListViewOnItemClickListener);
    }

    private View.OnClickListener tvCancleOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    private AdapterView.OnItemClickListener mListViewOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            TextView tv = (TextView) view.findViewById(R.id.tv_school_city_list_item);

            if (i == 0) {
                Intent data = new Intent();
                String text = tv.getText().toString();
                text = text.substring(0, text.indexOf("G"));
                data.putExtra("provinceName", text);
                setResult(1, data);
                finish();
            }else if (i > 1) {
                Intent data = new Intent();
                data.putExtra("provinceName", tv.getText());
                data.putExtra("provinceId", (i-1)+"");
                setResult(1, data);
                finish();
            }

        }
    };

    private void findView(){
        tvCancle = (TextView) findViewById(R.id.tv_school_city_list_cancle);
        mListView = (BounceListView) findViewById(R.id.lv_school_city_list);
        tv_school_city_list_prompt = (TextView) findViewById(R.id.tv_school_city_list_prompt);
    }
}
