package com.hyrt.cnp.dynamic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.base.account.model.Album;
import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.base.account.model.DynamicPhoto;
import com.hyrt.cnp.base.account.model.ItInfo;
import com.hyrt.cnp.base.account.model.Photo;
import com.hyrt.cnp.base.view.Mylistview;
import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.adapter.DynamicPhotoInfoAdapter;
import com.hyrt.cnp.dynamic.adapter.ListViewAdapter;
import com.hyrt.cnp.dynamic.request.CommetListRequest;
import com.hyrt.cnp.dynamic.request.DynamicaddcommentRequest;
import com.hyrt.cnp.dynamic.request.ItInfoRequest;
import com.hyrt.cnp.dynamic.requestListener.CommentListRequestListener;
import com.hyrt.cnp.dynamic.requestListener.DynamicaddcommentRequestListener;
import com.hyrt.cnp.dynamic.requestListener.ItInfoRequestListener;
import com.jingdong.app.pad.adapter.MySimpleAdapter;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;

import java.util.ArrayList;

/**
 * Created by Zoe on 2014-04-12.
 */
public class DynamicPhotoInfoActivity extends BaseActivity{

    private ImageView imgphoto;
    private TextView albumname;
    private TextView photoname;
    private EditText editcommit;
    private TextView btnset;
    private Mylistview listView;
    private DynamicPhoto photo;
    private Album mAlbum;
    private DynamicPhotoInfoAdapter classRoomAdapter;
    private ArrayList<Comment> comments = new ArrayList<Comment>();//评论列表
    private int type;
    private boolean etFocus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroomphotoinfo_classroom);
        initView();
        initData();
        LoadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(etFocus){
            editcommit.requestFocus();
            etFocus = false;
        }
    }

    private void initView(){
        imgphoto=(ImageView)findViewById(R.id.img_photo);
        albumname=(TextView)findViewById(R.id.text_albumname);
        photoname=(TextView)findViewById(R.id.text_photoname);
        editcommit=(EditText)findViewById(R.id.edit_commit);
        btnset=(TextView)findViewById(R.id.btn_set);
        listView = (Mylistview)findViewById(R.id.commit_listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        btnset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editcommit.getText().toString().equals("")){
                    addcomment();
                }else{
                    Toast.makeText(DynamicPhotoInfoActivity.this, "评论不能为空！", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void initData(){
        Intent intent = getIntent();
        photo=(DynamicPhoto)intent.getSerializableExtra("dynamicPhoto");
        etFocus = intent.getBooleanExtra("etFocus", false);
        mAlbum = (Album) intent.getSerializableExtra("album");
        type = intent.getIntExtra("type", 0);
//        if(photo.getIntroduce() != null && photo.getIntroduce().length() > 0){
            photoname.setText(Html.fromHtml("照片名称：<font color='#6ecbd9'>"+photo.getIntroduce()+"</font>"));
//        }else{
//            photoname.setText(Html.fromHtml("照片名称：<font color='#6ecbd9'>"+photo.getTitle()+"</font>"));
//        }

            titletext.setText("动感相册");
            albumname.setText(Html.fromHtml("专辑名称：<font color='#6ecbd9'>"+mAlbum.getAlbumName()+"</font>"));
        showDetailImage(photo.getImagepics(),imgphoto,false);
    }

    private void LoadData(){
        CommentListRequestListener sendwordRequestListener = new CommentListRequestListener(this);
        sendwordRequestListener.setListener(mCommentRequestListener);
        CommetListRequest schoolRecipeRequest=
                new CommetListRequest(Comment.Model.class,this,photo.getPhotoID()+"","52");
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), 1000,
                sendwordRequestListener.start());
    }

    private CommentListRequestListener.RequestListener mCommentRequestListener = new CommentListRequestListener.RequestListener() {
        @Override
        public void onRequestSuccess(Comment.Model data) {
            updateUI(data);
        }

        @Override
        public void onRequestFailure(SpiceException e) {

        }
    };


    public void updateUI(Comment.Model model){
        if(model == null){
            return;
        }
        comments.clear();
        comments.addAll(model.getData());
        if(classRoomAdapter == null){
//            String[] resKeys=new String[]{"getphotoImage","getUsername","getCreatdate2","getContent"};
//            int[] reses=new int[]{R.id.comment_photo,R.id.text_name,R.id.text_time,R.id.text_con};
            classRoomAdapter = new DynamicPhotoInfoAdapter(this,comments);
            listView.setAdapter(classRoomAdapter);
        }else{
            classRoomAdapter.notifyDataSetChanged();
        }

    }

    public void ShowSuccess(){
        Toast toast = Toast.makeText(DynamicPhotoInfoActivity.this, "添加评论成功", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
//        Toast.makeText(DynamicPhotoInfoActivity.this,"添加评论成功",Toast.LENGTH_SHORT).show();
        editcommit.setText("");
        LoadData();//刷新
        //隐藏键盘
        ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                DynamicPhotoInfoActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void addcomment(){
        Comment comment=new Comment();
        comment.set_id(photo.getUserID()+"");
        comment.setInfoID(photo.getPhotoID()+"");
        comment.setInfoid2(photo.getPhotoID()+"");
        comment.setInfoTitle(photo.getTitle());
        comment.setInfoUserId(photo.getUserID()+"");
        comment.setInfoNurseryId(photo.getNurseryID()+"");
        comment.setInfoClassroomId(photo.getClassroomID()+"");
        comment.setSiteid("52");
        comment.setUrl("null");
        comment.setLstatus("Y");
        comment.setContent(editcommit.getText().toString());
        comment.setReply("0");
        comment.setRecontent("");
        comment.setReuserId("");
        comment.setReusername("");
        comment.setRedate("");
        DynamicaddcommentRequestListener sendwordRequestListener = new DynamicaddcommentRequestListener(this);
        sendwordRequestListener.setListener(mAddCommentRequestListener);
        DynamicaddcommentRequest schoolRecipeRequest=
                new DynamicaddcommentRequest(Comment.Model3.class,this,comment, 0);
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }

    private DynamicaddcommentRequestListener.requestListener mAddCommentRequestListener
            = new DynamicaddcommentRequestListener.requestListener() {
        @Override
        public void onRequestSuccess(Object data) {
            ShowSuccess();
        }

        @Override
        public void onRequestFailure(SpiceException e) {}
    };

}
