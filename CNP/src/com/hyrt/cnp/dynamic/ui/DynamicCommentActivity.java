package com.hyrt.cnp.dynamic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.internal.view.SupportMenuInflater;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.base.account.model.ItInfo;
import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.request.DynamicCommentRequest;
import com.hyrt.cnp.dynamic.request.DynamicaddcommentRequest;
import com.hyrt.cnp.dynamic.requestListener.DynamicaddcommentRequestListener;
import com.hyrt.cnp.dynamic.requestListener.DynamiccommentRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

/**
 * Created by GYH on 14-1-21.
 */
public class DynamicCommentActivity extends BaseActivity{

    private TextView textcon;
    private EditText editcon;
    private Dynamic dynamic;
    private ItInfo itInfo;
    private String Category;
    private MenuItem sendbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamiccomment);
        initView();
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SupportMenuInflater mMenuInflater = new SupportMenuInflater(this);
        mMenuInflater.inflate(R.menu.senddy, menu);
        menu.findItem(R.id.senddy).
                setCheckable(false).
                setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        sendbtn = menu.findItem(R.id.senddy);
        return true;
    }

    private void initView(){
        textcon=(TextView)findViewById(R.id.text_context);
        editcon=(EditText)findViewById(R.id.edit_context);
        editcon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateEnablement();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getTitle().equals("发送")){
            if(!editcon.getText().toString().equals("")){
                if(Category.equals("pl")){
                    addcomment();
                }else if("zf".equals(Category)){
                    setzfData();
                }else{
                    hfComment();
                }
            }else{
                Toast.makeText(DynamicCommentActivity.this,"输入不能为空！",Toast.LENGTH_SHORT).show();
            }

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateEnablement();
    }

    private void initData(){
        Intent intent = getIntent();
        Category=intent.getStringExtra("Category");
        if("hf".equals(Category)){
            itInfo = (ItInfo) intent.getSerializableExtra("vo");
            textcon.setText(itInfo.getMsgTitle());
        }else{
            dynamic=(Dynamic)intent.getSerializableExtra("vo");
            textcon.setText(dynamic.getContent2().toString());
        }

        if(Category.equals("pl") || Category.equals("hf")){
            titletext.setText("评论动态");
        }else{
            titletext.setText("转发动态");
        }
    }

    private void setzfData(){
        Dynamic data=new Dynamic();
        data.set_id(dynamic.get_id());
        data.setContent(editcon.getText().toString());
        DynamiccommentRequestListener sendwordRequestListener = new DynamiccommentRequestListener(this);
        DynamicCommentRequest schoolRecipeRequest=new DynamicCommentRequest(Dynamic.Model3.class,this,data);
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }

    private void addcomment(){
        Comment comment=new Comment();
        comment.set_id(dynamic.get_id()+"");
        comment.setInfoid2(dynamic.get_id());
        if(dynamic.getTitle().equals("")){
            comment.setInfoTitle("null");
        }else{
            comment.setInfoTitle(dynamic.getTitle());
        }
        comment.setInfoUserId(dynamic.gettUserId()+"");
        comment.setInfoNurseryId(dynamic.getNueseryId()+"");
        comment.setInfoClassroomId(dynamic.getClassRoomId()+"");
        comment.setSiteid("50");
        comment.setUrl("null");
        comment.setLstatus("Y");
        comment.setContent(editcon.getText().toString());
        comment.setReply("0");
        comment.setRecontent("");
        comment.setReuserId("");
        comment.setReusername("");
        comment.setRedate("");
        DynamicaddcommentRequestListener sendwordRequestListener = new DynamicaddcommentRequestListener(this);
        DynamicaddcommentRequest schoolRecipeRequest=
                new DynamicaddcommentRequest(Comment.Model3.class,this,comment, 0);
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }

    public void hfComment(){
        Comment comment = new Comment();
//        comment.set_id(itInfo.getFromUid());
    }

    public void showSuccess(){
        Toast.makeText(DynamicCommentActivity.this,"评论成功！",Toast.LENGTH_SHORT).show();
        editcon.setText("");
        setResult(1);
        finish();
    }
    private void updateEnablement() {
        if (sendbtn != null){
            sendbtn.setEnabled(commentEnabled());
        }
    }
    private boolean commentEnabled() {
        return !TextUtils.isEmpty(editcon.getText());
    }

}
