package com.hyrt.cnp.school.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.StarBabay;
import com.hyrt.cnp.R;
import com.hyrt.cnp.school.adapter.StarBabayImageAdapter;
import com.hyrt.cnp.school.request.StarBabayRequest;
import com.hyrt.cnp.school.requestListener.StarBabayRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

import java.util.ArrayList;

/**
 * Created by GYH on 14-1-10.
 */
public class StarBabayActivity extends BaseActivity {

    private GridView gridview;
    private StarBabayImageAdapter starTeacherImageAdapter;
    private int mSid = -1;
    private ArrayList<String> paths = new ArrayList<String>();
    private ArrayList<String> names = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starteacher);
        Intent intent = getIntent();
        mSid = intent.getIntExtra("sid", mSid);
        initView();
    }

    @Override
    protected void initTitleview() {
        super.initTitleview();
        TextView textView = (TextView) viewTitleBar.findViewById(R.id.action_bar_title_text);
        textView.setText("明星宝宝");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (starTeacherImageAdapter == null) {
            loadStarteacehr();
        }
    }

    private void initView() {
        gridview = (GridView) findViewById(R.id.cnp_gridview);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String path = ((StarBabay) starTeacherImageAdapter.getItem(i)).getImagepath();
//                showPop(gridview, path);
                showPop4(gridview, paths, names, i, StarBabayActivity.this);
//                Intent intent = new Intent();
//                intent.putExtra("imageurl",((StarBabay) starTeacherImageAdapter.getItem(i)).getImagepath());
//                intent.setClass(StarBabayActivity.this, ImageShowpop.class);
//                startActivity(intent);
            }
        });
    }

    public void initData(StarBabay.Model model) {
        if (model == null) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.VISIBLE);
            TextView bottom_num = (TextView) findViewById(R.id.bottom_num);
            bottom_num.setText("暂无信息");
        } else {
            paths.clear();
            names.clear();
            for(int i=0, j=model.getData().size(); i<j;i++){
                paths.add(model.getData().get(i).getImagepath());
                names.add(model.getData().get(i).getRenname());
            }
            String[] resKeys = new String[]{"getImagepath", "getRenname"};
            int[] reses = new int[]{R.id.gridview_image, R.id.gridview_name};
            starTeacherImageAdapter = new StarBabayImageAdapter
                    (this, model.getData(), R.layout.layout_item_gridview_image, resKeys, reses);
            gridview.setAdapter(starTeacherImageAdapter);
        }
    }

    private void loadStarteacehr() {
        StarBabayRequestListener sendwordRequestListener = new StarBabayRequestListener(this);
        StarBabayRequest starBabayRequest = new StarBabayRequest(StarBabay.Model.class, this, mSid);
        spiceManager.execute(starBabayRequest, starBabayRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }


}
