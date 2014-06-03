package com.hyrt.cnp.classroom.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Album;
import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.base.account.model.DynamicPhoto;
import com.hyrt.cnp.base.account.model.Photo;
import com.hyrt.cnp.R;
import com.hyrt.cnp.classroom.adapter.ClassRoomAdapter;
import com.hyrt.cnp.classroom.request.ClassroomPhotoListRequest;
import com.hyrt.cnp.classroom.requestListener.ClassroomPhotoListRequestListener;
import com.hyrt.cnp.dynamic.adapter.DynamicPhotoInfoAdapter;
import com.hyrt.cnp.dynamic.request.AddPhotoRequest;
import com.hyrt.cnp.dynamic.request.DynamicPhotoListRequest;
import com.hyrt.cnp.dynamic.requestListener.AddPhotoRequestListener;
import com.hyrt.cnp.dynamic.requestListener.DynamicPhotoListRequestListener;
import com.hyrt.cnp.dynamic.ui.DynamicPhotoInfoActivity;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;

import net.oschina.app.AppContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GYH on 14-1-17.
 */
public class ClassroomphotolistActivity extends BaseActivity{

    private GridView gridView;
    private ClassRoomAdapter classRoomAdapter;
    private Photo.Model model;
    private DynamicPhoto.Model DynamicModel;
    private String  Category;
    private TextView bottom_num;
    private ArrayList<String> imageurls = new ArrayList<String>();
    private ArrayList<String> commentNums = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroomphotolist_classroom);
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    /**
     * 更新ui界面
     * */
    public void updateUI(Photo.Model model){
        if(model==null){
            bottom_num.setText("暂无信息");
        }else{
            this.model=model;
            imageurls.clear();
            commentNums.clear();
            for(int i=0,j=model.getData().size(); i<j; i++){
                imageurls.add(model.getData().get(i).getImagepics());
                commentNums.add(model.getData().get(i).getCommentNum()+"");
            }
            String[] resKeys=new String[]{"getImagethpath","getTitle"};
            int[] reses=new int[]{R.id.gridview_image,R.id.gridview_name};
            classRoomAdapter = new ClassRoomAdapter(this,model.getData(),R.layout.layout_item_gridview_image1_classroom,resKeys,reses);
            gridView.setAdapter(classRoomAdapter);
            bottom_num.setText("共 "+model.getData().size()+" 张");
        }
    }
    private void initView(){
        bottom_num=(TextView)findViewById(R.id.bottom_num);
        gridView =(GridView)findViewById(R.id.cnp_gridview);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(AppContext.getInstance().mUserDetail != null
                        && AppContext.getInstance().mUserDetail.getGroupID() != 7){
                    showPop3(gridView, imageurls, commentNums, i, ClassroomphotolistActivity.this, mShowPop3Listener, true);
                }else{
                    showPop3(gridView, imageurls, commentNums, i, ClassroomphotolistActivity.this, mShowPop3Listener, false);
                }
            }
        });
    }

    showPop3Listener mShowPop3Listener = new showPop3Listener() {
        @Override
        public void onClick(int type, int position) {
            if(type == 1 || type == 2){
                Intent intent = new Intent();
                if(Category.equals("BabayIndexActivity")){
                    intent.setClass(ClassroomphotolistActivity.this,DynamicPhotoInfoActivity.class);
                    intent.putExtra("dynamicPhoto", DynamicModel.getData().get(position));
                    intent.putExtra("album", getIntent().getSerializableExtra("album"));
                }else{
                    intent.setClass(ClassroomphotolistActivity.this,ClassroomphotoinfoActivity.class);
                    intent.putExtra("vo",model.getData().get(position));
                    intent.putExtra("Category",Category);
                }
                if(type == 1){
                    intent.putExtra("etFocus", true);
                }
                startActivity(intent);
                popWin.dismiss();
            }else if(type == 3){
                AddPhotoRequestListener requestListener
                        = new AddPhotoRequestListener(ClassroomphotolistActivity.this);
                requestListener.setListener(new AddPhotoRequestListener.RequestListener() {
                    @Override
                    public void onRequestSuccess(Object o) {
                        loadData();
                    }

                    @Override
                    public void onRequestFailure(SpiceException e) {}
                });
                DynamicPhoto mPhoto = new DynamicPhoto();
                mPhoto.setPhotoID(model.getData().get(position).getPhotoID());
                AddPhotoRequest request = new AddPhotoRequest(
                        BaseTest.class, ClassroomphotolistActivity.this, mPhoto);
                spiceManager.execute(request, request.getcachekey(), 1,
                        requestListener.startDel());
            }
        }
    };

    private void loadData(){
        Intent intent = getIntent();
        Album album = (Album)intent.getSerializableExtra("vo");
        Category=intent.getStringExtra("Category");
        if(Category.equals("ClassroomIndexActivity")){
            titletext.setText("班级相册");
            ClassroomPhotoListRequestListener sendwordRequestListener = new ClassroomPhotoListRequestListener(this);
            ClassroomPhotoListRequest schoolRecipeRequest=new ClassroomPhotoListRequest(Photo.Model.class,this,album.getPaId());
            spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                    sendwordRequestListener.start());
        }else if(Category.equals("BabayIndexActivity")){
            titletext.setText("动感相册");
            DynamicPhotoListRequestListener requestListener = new DynamicPhotoListRequestListener(this);
            requestListener.setListener(mDynamicPhotoRequestListener);
            DynamicPhotoListRequest request = new DynamicPhotoListRequest(DynamicPhoto.Model.class,this,album.getPaId());
            spiceManager.execute(request, request.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                    requestListener.start());
        }



    }

    private DynamicPhotoListRequestListener.RequestListener mDynamicPhotoRequestListener
            = new DynamicPhotoListRequestListener.RequestListener() {
        @Override
        public void onRequestSuccess(DynamicPhoto.Model data) {
            DynamicModel = data;
            imageurls.clear();
            commentNums.clear();
            for(int i=0,j=data.getData().size(); i<j; i++){
                imageurls.add(data.getData().get(i).getImagepics());
                commentNums.add(data.getData().get(i).getCommentNum()+"");
            }
            String[] resKeys=new String[]{"getImagethpath","getTitle"};
            int[] reses=new int[]{R.id.gridview_image,R.id.gridview_name};
            classRoomAdapter = new ClassRoomAdapter(
                    ClassroomphotolistActivity.this,data.getData(),
                    R.layout.layout_item_gridview_image1_classroom,resKeys,reses);
            gridView.setAdapter(classRoomAdapter);
            bottom_num.setText("共 "+data.getData().size()+" 张");
        }

        @Override
        public void onRequestFailure(SpiceException e) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model=null;
        classRoomAdapter=null;
    }
}
