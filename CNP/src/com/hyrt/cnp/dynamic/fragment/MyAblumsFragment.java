package com.hyrt.cnp.dynamic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.base.account.model.Album;
import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.view.BounceListView;
import com.hyrt.cnp.base.view.XListView;
import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.adapter.MyAblumAdapter;
import com.hyrt.cnp.dynamic.request.MyAlbumRequest;
import com.hyrt.cnp.dynamic.requestListener.MyAlbumRequestListener;
import com.hyrt.cnp.dynamic.ui.AddAlbumActivity;
import com.hyrt.cnp.dynamic.ui.DynamicPhotoListActivity;
import com.hyrt.cnp.dynamic.ui.HomeInteractiveActivity;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GYH on 14-3-13.
 */
public class MyAblumsFragment extends Fragment{

    private View rootView;

    private XListView listview;
    private MyAblumAdapter listViewAdapter;
    private String Category;
//    private Album.Model model;
    private List<Album> albums = new ArrayList<Album>();
    private HomeInteractiveActivity activity;

    public String STATE;
    final public String REFRESH = "refresh";
    final public String ONLOADMORE = "onLoadMore";
    final private String HASDATA = "hasdata";
    private String more = "1";
    private String moreType = "";

    public static final int RESULT_FOR_ADD_ALBUM = 103;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("tag", "onCreateView");
        STATE = HASDATA;
        rootView=inflater.inflate(R.layout.fragment_myablums,container,false);
        initView();
        loadData(false);
        return rootView;
    }

    private void initView(){
        listview=(XListView)rootView.findViewById(R.id.cnp_listview_album);
        listview.setPullLoadEnable(true);
        listview.setPullRefreshEnable(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent();
//                intent.setClass(ClassroomAlbumActivity.this,ClassroomphotolistActivity.class);
//                intent.putExtra("vo",model.getData().get(i));
//                intent.putExtra("Category",Category);
//                startActivity(intent);

//                Toast.makeText(getActivity(), "aaa", 0).show();
//                if(view.getId() == R.id.btn_change_album){
//                    Toast.makeText(getActivity(), "bbb", 0).show();
//                }
            }
        });

        listview.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                if (STATE.equals(HASDATA) || STATE.equals(ONLOADMORE)) {

                }else{
                    STATE = REFRESH;
                    more = "1";
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

    public void loadData(boolean isMore){
        Log.i("tag", "loadData isMore:"+isMore);
        activity = (HomeInteractiveActivity) getActivity();
        MyAlbumRequestListener sendwordRequestListener = new MyAlbumRequestListener(activity);
        MyAlbumRequest schoolRecipeRequest = null;
        if(isMore){
            if(albums.size()>0){
                more = albums.get(albums.size() - 1).getPaId()+"";
                schoolRecipeRequest=new MyAlbumRequest(more, Album.Model.class,activity);
            }
        }else{
            STATE = REFRESH;
            schoolRecipeRequest=new MyAlbumRequest("1", Album.Model.class,activity);
        }
        if(schoolRecipeRequest != null){
            activity.spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                    sendwordRequestListener.start());
        }

    }

    /**
     * 更新ui界面
     * */

    public void updateUI(Album.Model model){
        Log.i("tag", "updateUI");
        if (model == null && this.albums.size() == 0) {
            LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.VISIBLE);
            TextView bottom_num = (TextView) rootView.findViewById(R.id.bottom_num);
            bottom_num.setText("暂无信息");
        } else if (model == null) {
            Toast.makeText(activity, "已经全部加载", Toast.LENGTH_SHORT).show();
        }else{
            moreType = model.getMore();
            if (STATE == null || STATE.equals(REFRESH)) {//如果正在刷新就清空
                this.albums.clear();
            }
            this.albums.addAll(model.getData());
//            if(listViewAdapter == null){
                String[] resKeys=new String[]{"getAlbumName","getAlbumDesc","getPosttime2"};
                int[] reses=new int[]{R.id.item_album_title,R.id.tv_photo_describe,R.id.tv_photo_time};
                listViewAdapter = new MyAblumAdapter(activity,this.albums,R.layout.dynamic_album_item,resKeys,reses);
                listViewAdapter.setListener(mAblumAdapter);
                if(listview != null){
                    listview.setAdapter(listViewAdapter);
                }
//            }else{
//                listViewAdapter.notifyDataSetChanged();
//            }

        }
        STATE = "";//清空状态
    }

    private MyAblumAdapter.OnItemClickListener mAblumAdapter
            = new MyAblumAdapter.OnItemClickListener() {
        @Override
        public void onClick(int type, int position) {
            Album data = albums.get(position);
            if(type == 0){
                Intent intent = new Intent();
                intent.setClass(getActivity(), AddAlbumActivity.class);
                intent.putExtra("album", data);
                ArrayList<String> albumNames = new ArrayList<String>();
                for(int i=0; i<albums.size(); i++){
                    albumNames.add(albums.get(i).getAlbumName());
                }
                intent.putStringArrayListExtra("albumNames", albumNames);
                getActivity().startActivityForResult(intent, RESULT_FOR_ADD_ALBUM);
            }else if(type == 1){
                MyAlbumRequestListener requestListener = new MyAlbumRequestListener(getActivity());
                requestListener.setListener(mAlbumRequestListener);
                android.util.Log.i("tag", "paid:"+data.getPaId());
                MyAlbumRequest request = new MyAlbumRequest(BaseTest.class, getActivity(), data.getPaId()+"");
                ((BaseActivity)getActivity()).spiceManager.execute(
                        request, request.getcachekey(), 1,
                        requestListener.start());
            }else{
                Intent intent = new Intent();
                intent.setClass(getActivity(), DynamicPhotoListActivity.class);
                ArrayList<String> albumNames = new ArrayList<String>();
                for(int i=0; i<albums.size(); i++){
                    albumNames.add(albums.get(i).getAlbumName());
                }
                intent.putStringArrayListExtra("albumNames", albumNames);
                intent.putExtra("album", data);
                startActivityForResult(intent, RESULT_FOR_ADD_ALBUM);
            }

        }
    };

    MyAlbumRequestListener.RequestListener mAlbumRequestListener = new MyAlbumRequestListener.RequestListener() {
        @Override
        public void onRequestSuccess(Object data) {
            BaseTest bt = (BaseTest) data;
            android.util.Log.i("tag", "bt:"+bt.getCode()+":"+bt.getMsg());
            loadData(false);
        }

        @Override
        public void onRequestFailure(SpiceException e) {

        }
    };
}
