package com.hyrt.cnp.school.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Recipe;
import com.hyrt.cnp.R;
import com.hyrt.cnp.school.request.SchoolRecipeRequest;
import com.hyrt.cnp.school.requestListener.SchoolRecipeRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GYH on 14-1-9.
 */
public class SchoolRecipeActivity extends BaseActivity {

    private ListView noticelistview;
    private SimpleAdapter adapter = null;
    private Recipe.Model model = null;
    private int mSid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolrecipe);
        initView();
        Intent intent = getIntent();
        mSid = intent.getIntExtra("sid", -1);
    }

    @Override
    protected void initTitleview() {
        super.initTitleview();
        TextView textView = (TextView) viewTitleBar.findViewById(R.id.action_bar_title_text);
        textView.setText("每周食谱");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter == null) {
            loadSendword();
        }
    }

    /**
     * 界面初始化方法
     */
    private void initView() {
        noticelistview = (ListView) findViewById(R.id.schoolnotice_listview);
        noticelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(SchoolRecipeActivity.this, SchoolRepiceInfoActivity.class);
                intent.putExtra("vo", model.getData().get(i));
                startActivity(intent);
            }
        });
    }

    /**
     * 监听请求返回更新UI界面的方法
     */
    public void updateUI(Recipe.Model model) {
        if (model == null) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.VISIBLE);
            TextView bottom_num = (TextView) findViewById(R.id.bottom_num);
            bottom_num.setText("暂无信息");
            noticelistview.setVisibility(View.GONE);
        } else {
            noticelistview.setVisibility(View.VISIBLE);
            this.model = model;
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

            for (int i = 0; i < model.getData().size(); i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("title", model.getData().get(i).getStarttime() + "-" + model.getData().get(i).getEndtime() + "食谱");
                map.put("info", model.getData().get(i).getPosttime2());
                list.add(map);
            }
            adapter = new SimpleAdapter(this, list,
                    R.layout.layout_item_text, new String[]{"title", "info"},
                    new int[]{R.id.item_title, R.id.item_time});
            noticelistview.setAdapter(adapter);
        }
    }

    /**
     * 每周食谱请求方法
     */
    private void loadSendword() {
        SchoolRecipeRequestListener sendwordRequestListener = new SchoolRecipeRequestListener(this);
        SchoolRecipeRequest schoolRecipeRequest = new SchoolRecipeRequest(Recipe.Model.class, this, mSid);
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }
}
