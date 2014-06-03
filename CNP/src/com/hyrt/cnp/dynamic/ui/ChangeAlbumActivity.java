package com.hyrt.cnp.dynamic.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.base.account.model.Album;
import com.hyrt.cnp.base.view.XListView;
import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.adapter.ChangeAlbumAdapter;
import com.hyrt.cnp.dynamic.adapter.ListViewAdapter;
import com.hyrt.cnp.dynamic.request.MyAlbumRequest;
import com.hyrt.cnp.dynamic.requestListener.MyAlbumRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zoe on 2014-04-11.
 */
public class ChangeAlbumActivity extends BaseActivity{

    private ListView listview;

    private List<Album> datas = new ArrayList<Album>();
    private ChangeAlbumAdapter mAdapter;

    private String STATE;
    final private String REFRESH="refresh";
    final private String ONLOADMORE="onLoadMore";
    final private String HASDATA="hasdata";
    private String more="1";

    public static final int RESULT_FOR_ADD_ALBUM = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);
        findView();
        if(mAdapter == null) {
            mAdapter = new ChangeAlbumAdapter(datas, ChangeAlbumActivity.this);
            listview.setAdapter(mAdapter);
        }
        initImageLoader();
        STATE=HASDATA;
        loadData();
        setListener();
    }

    private void loadData() {
        MyAlbumRequestListener requestListener = new MyAlbumRequestListener(this);
        requestListener.setListener(mAlbumRequestListener);
        MyAlbumRequest request = new MyAlbumRequest(Album.Model.class, this);
        spiceManager.execute(request, request.getcachekey(), 1,
                requestListener.start());
    }

    public void setListener(){
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent data = new Intent();
                if(i == 0){
                    ArrayList<String> albumNames = new ArrayList<String>();
                    for(int j=0; j<datas.size(); j++){
                        albumNames.add(datas.get(j).getAlbumName());
                    }
                    data.putStringArrayListExtra("albumNames", albumNames);
                    data.setClass(ChangeAlbumActivity.this, AddAlbumActivity.class);
                    startActivityForResult(data, RESULT_FOR_ADD_ALBUM);
                }else{
                    data.putExtra("album", datas.get(i-1));
                    setResult(AddPhotoDynamicActivity.RESULT_FOR_CHANGE_ALBUM, data);
                    finish();
                }
            }
        });
    }

    public void initImageLoader(){
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
    }

    private MyAlbumRequestListener.RequestListener mAlbumRequestListener = new MyAlbumRequestListener.RequestListener() {
        @Override
        public void onRequestSuccess(Object data) {
            if(data == null){
                LinearLayout linearLayout =(LinearLayout)findViewById(R.id.layout_bottom);
                linearLayout.setVisibility(View.VISIBLE);
                TextView bottom_num = (TextView)findViewById(R.id.bottom_num);
                bottom_num.setText("暂无信息");
            }else{
//                more= data.getMore();
                if(STATE.equals(REFRESH)){//如果正在刷新就清空
                    datas.clear();
                }
                datas.addAll(((Album.Model)data).getData());
                if(mAdapter == null){
                    mAdapter = new ChangeAlbumAdapter(datas, ChangeAlbumActivity.this);
                    listview.setAdapter(mAdapter);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_FOR_ADD_ALBUM){
            datas.clear();
            loadData();
        }
    }

    private void findView() {
        listview = (ListView) findViewById(R.id.myit_listview);
       /* listview.setDividerHeight(1);
//        Drawable drawable = getResources().getDrawable(android.R.color.darker_gray);
//        listview.setDivider(drawable);

        listview.setPullLoadEnable(true);
        listview.setPullRefreshEnable(true);
        listview.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                if (STATE.equals(HASDATA) || STATE.equals(ONLOADMORE)) {
                    Toast.makeText(ChangeAlbumActivity.this, "正在加载,请稍后!", Toast.LENGTH_SHORT).show();
                } else {
                    STATE = REFRESH;
                    more = "1";
                    loadData();
                }
                listview.stopRefresh();
            }

            @Override
            public void onLoadMore() {
                *//*if (STATE.equals(HASDATA) || STATE.equals(REFRESH)) {
                    Toast.makeText(ChangeAlbumActivity.this, "正在加载,请稍后!", Toast.LENGTH_SHORT).show();
                } else {
                    loadData();
                }*//*
                listview.stopLoadMore();
            }
        });*/
    }
}
