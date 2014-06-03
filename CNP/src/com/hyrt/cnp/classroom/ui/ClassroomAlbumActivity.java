package com.hyrt.cnp.classroom.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Album;
import com.hyrt.cnp.base.account.model.BabyInfo;
import com.hyrt.cnp.R;
import com.hyrt.cnp.classroom.adapter.ClassRoomAdapter;
import com.hyrt.cnp.classroom.request.ClassroomAlbumRequest;
import com.hyrt.cnp.classroom.requestListener.ClassroomAlbumRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

/**
 * Created by GYH on 14-1-16.
 * 班级专辑
 */
public class ClassroomAlbumActivity extends BaseActivity{

    private ListView listview;
    private ClassRoomAdapter classRoomAdapter;
    private String Category;
    private Album.Model model;
    private BabyInfo classRoomBabay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroomalbum);
        initView();
        Intent intent=getIntent();
        Category=intent.getStringExtra("Category");
        if(Category.equals("ClassroomIndexActivity")){
            titletext.setText("班级相册");
            loadData();
        }else if(Category.equals("BabayIndexActivity")){
            titletext.setText("动感相册");
            classRoomBabay=(BabyInfo)intent.getSerializableExtra("vo");
            loadDatababayindex();
        }

    }

    /**
     * 更新ui界面
     * */

    public void updateUI(Album.Model model){
        if (model==null){
            LinearLayout linearLayout =(LinearLayout)findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.VISIBLE);
            TextView bottom_num = (TextView)findViewById(R.id.bottom_num);
            bottom_num.setText("暂无信息");
        }else{
            this.model=model;
            String[] resKeys=new String[]{"getImagepath","getAlbumName","getAlbumDesc","getPosttime2"};
            int[] reses=new int[]{R.id.item_album_image,R.id.item_album_title,R.id.item_album_con,R.id.item_album_time};
            classRoomAdapter = new ClassRoomAdapter(this,model.getData(),R.layout.layout_item_album,resKeys,reses);
            listview.setAdapter(classRoomAdapter);
        }

    }

    private void initView(){
        listview=(ListView)findViewById(R.id.cnp_listview_album);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(ClassroomAlbumActivity.this,ClassroomphotolistActivity.class);
                intent.putExtra("vo",model.getData().get(i));
                intent.putExtra("Category",Category);
                intent.putExtra("album", model.getData().get(i));
                startActivity(intent);
            }
        });
    }

    private void loadData(){
        ClassroomAlbumRequestListener sendwordRequestListener = new ClassroomAlbumRequestListener(this);
        ClassroomAlbumRequest schoolRecipeRequest=new ClassroomAlbumRequest(Album.Model.class,this,Category);
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }
    private void loadDatababayindex(){
        ClassroomAlbumRequestListener sendwordRequestListener = new ClassroomAlbumRequestListener(this);
        ClassroomAlbumRequest schoolRecipeRequest=new ClassroomAlbumRequest(Album.Model.class,this,Category,classRoomBabay.getUser_id()+"");
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }
}
