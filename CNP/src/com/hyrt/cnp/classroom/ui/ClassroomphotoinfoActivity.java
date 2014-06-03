package com.hyrt.cnp.classroom.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.base.account.model.Photo;
import com.hyrt.cnp.base.view.MyXListView;
import com.hyrt.cnp.base.view.XListView;
import com.hyrt.cnp.R;
import com.hyrt.cnp.classroom.adapter.ClassRoomAdapter;
import com.hyrt.cnp.classroom.adapter.ClassRoomCommentAdapter;
import com.hyrt.cnp.classroom.request.ClassroomaddcommentRequest;
import com.hyrt.cnp.classroom.request.ClassroomcommentRequest;
import com.hyrt.cnp.classroom.requestListener.ClassroomaddcommentRequestListener;
import com.hyrt.cnp.classroom.requestListener.ClassroomcommentRequestListener;
import com.hyrt.cnp.base.view.Mylistview;
import com.jingdong.common.frame.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.octo.android.robospice.persistence.DurationInMillis;

import net.oschina.app.AppContext;

import java.util.ArrayList;

/**
 * Created by GYH on 14-1-22.
 */
public class ClassroomphotoinfoActivity extends BaseActivity{

    private ImageView imgphoto;
    private TextView albumname;
    private TextView photoname;
    private EditText editcommit;
    private TextView btnset;
    private Mylistview listView;
    private Photo photo;
    private ClassRoomCommentAdapter classRoomAdapter;
    private boolean etFocus = false;
    private ArrayList<Comment> comments = new ArrayList<Comment>();

    public String STATE;
    final public String REFRESH = "refresh";
    final private String ONLOADMORE = "onLoadMore";
    final private String HASDATA = "hasdata";

    private String more = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroomphotoinfo_classroom);
        STATE = HASDATA;
        initView();
        initData();
        LoadData(false);
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
      /*  listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                if (STATE.equals(HASDATA) || STATE.equals(ONLOADMORE)) {
//                    Toast.makeText(BabayIndexActivity.this, "正在加载,请稍后!", Toast.LENGTH_SHORT).show();
                } else {
                    STATE = REFRESH;
                    more = "1";
//                    Toast.makeText(BabayIndexActivity.this,"正在刷新,请稍后!",Toast.LENGTH_SHORT).show();
                    LoadData(false);
                }
                listView.stopRefresh();
            }

            @Override
            public void onLoadMore() {
                if (STATE.equals(HASDATA) || STATE.equals(REFRESH)) {
//                    Toast.makeText(BabayIndexActivity.this,"正在加载,请稍后!",Toast.LENGTH_SHORT).show();
                } else {
                    LoadData(true);
//                    Toast.makeText(BabayIndexActivity.this,"onLoadMore",Toast.LENGTH_SHORT).show();
                }
                listView.stopLoadMore();
            }
        });*/


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
                    Toast.makeText(ClassroomphotoinfoActivity.this,"评论不能为空！",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    //TODO 专辑名称是什么
    private void initData(){
        Intent intent = getIntent();
        etFocus = intent.getBooleanExtra("etFocus", false);
        photo=(Photo)intent.getSerializableExtra("vo");
        photoname.setText("照片名称："+photo.getTitle());
        String Category=intent.getStringExtra("Category");
        if(Category.equals("ClassroomIndexActivity")){
            titletext.setText("班级相册");
            albumname.setText("专辑名称：班级专辑");
        }else if(Category.equals("BabayIndexActivity")){
            titletext.setText("动感相册");
            albumname.setText("专辑名称：个人专辑");
        }
        ImageLoader.getInstance().displayImage(photo.getImagepics(),imgphoto, AppContext.getInstance().mImageloaderoptions);
//        showDetailImage(photo.getImagepics(), imgphoto, false);
    }

    private void LoadData(boolean isMore){
        ClassroomcommentRequestListener sendwordRequestListener = new ClassroomcommentRequestListener(this);
        ClassroomcommentRequest schoolRecipeRequest = null;
        if(isMore){
            if(comments.size() > 0){
                more = comments.get(comments.size()-1).getCreatdate();
                schoolRecipeRequest = new ClassroomcommentRequest(Comment.Model.class,this,photo.getPhotoID()+"","15", more);
            }
        }else{
            STATE = REFRESH;
            schoolRecipeRequest = new ClassroomcommentRequest(Comment.Model.class,this,photo.getPhotoID()+"","15");
        }
        if(schoolRecipeRequest != null){
            spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), 1000,
                    sendwordRequestListener.start());
        }
    }

    public void updateUI(Comment.Model model){
        if (model == null && comments.size() == 0) {
//            LinearLayout linearLayout = (LinearLayout) rootview.findViewById(R.id.layout_bottom);
//            linearLayout.setVisibility(View.VISIBLE);
//            TextView bottom_num = (TextView) rootview.findViewById(R.id.bottom_num);
//            bottom_num.setText("暂无信息");
        } else if (model == null) {
            Toast.makeText(this, "已经全部加载", Toast.LENGTH_SHORT).show();
        }else{
            if (STATE == null || STATE.equals(REFRESH)) {//如果正在刷新就清空
                comments.clear();
            }
            comments.addAll(model.getData());
            android.util.Log.i("tag", "comments:"+comments.size()+" "+comments.get(0).getContent());
            if(classRoomAdapter == null){
                String[] resKeys=new String[]{"getphotoImage","getUsername","getCreatdate2","getContent"};
                int[] reses=new int[]{R.id.comment_photo,R.id.text_name,R.id.text_time,R.id.text_con};
                classRoomAdapter = new ClassRoomCommentAdapter(this,comments,R.layout.layout_item_comment_classroom,resKeys,reses);
                listView.setAdapter(classRoomAdapter);
            }else{
                classRoomAdapter.notifyDataSetChanged();
            }
        }
        STATE = "";//清空状态
    }

    public void ShowSuccess(){
        Toast toast = Toast.makeText(ClassroomphotoinfoActivity.this, "添加评论成功", 0);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
//        Toast.makeText(ClassroomphotoinfoActivity.this,"添加评论成功",Toast.LENGTH_SHORT).show();
        editcommit.setText("");
        LoadData(false);//刷新
        //隐藏键盘
        ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                ClassroomphotoinfoActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    private void addcomment(){
        Comment comment=new Comment();
        comment.set_id(photo.getUserID()+"");
        comment.setInfoID(photo.getPhotoID()+"");
        comment.setInfoTitle(photo.getTitle());
        comment.setInfoUserId(photo.getUserID()+"");
        comment.setInfoNurseryId(photo.getNurseryID()+"");
        comment.setInfoClassroomId(photo.getClassroomID()+"");
        comment.setSiteid("15");
        comment.setUrl(photo.getImagepics());
        comment.setLstatus("Y");
        comment.setContent(editcommit.getText().toString());
        comment.setReply("0");
        comment.setRecontent("");
        comment.setReuserId("");
        comment.setReusername("");
        comment.setRedate("");
        ClassroomaddcommentRequestListener sendwordRequestListener = new ClassroomaddcommentRequestListener(this);
        ClassroomaddcommentRequest schoolRecipeRequest=
                new ClassroomaddcommentRequest(Comment.Model3.class,this,comment);
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }
}
