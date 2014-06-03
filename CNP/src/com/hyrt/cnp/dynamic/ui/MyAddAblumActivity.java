package com.hyrt.cnp.dynamic.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.request.MyAddAblumRequest;
import com.hyrt.cnp.dynamic.requestListener.MyAddAblumRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

/**
 * Created by GYH on 2014/4/6.
 */
public class MyAddAblumActivity extends BaseActivity{

    private EditText ablum_title;
    private EditText ablum_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaddablum);
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("确定").
                setTitle("确定").
        setCheckable(false).
                setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getTitle().equals("确定")&&isEmptry()){
            loadData();
        }

        return super.onOptionsItemSelected(item);
    }


    private void initView(){
        ablum_title=(EditText)findViewById(R.id.ablum_title);
        ablum_text=(EditText)findViewById(R.id.ablum_text);
    }

    private boolean isEmptry(){
        boolean is = true;

        if(ablum_title.getText().toString().equals("")){
            is=false;
            Toast.makeText(this,"相册名称不能为空！",Toast.LENGTH_SHORT).show();
        }else if(ablum_text.getText().toString().equals("")){
            is=false;
            Toast.makeText(this,"相册描述不能为空！",Toast.LENGTH_SHORT).show();
        }
        return is;
    }

    private void loadData(){
        MyAddAblumRequestListener myAddAblumRequestListener = new MyAddAblumRequestListener(this);
        MyAddAblumRequest myAddAblumRequest = new MyAddAblumRequest(Comment.Model3.class,this,
                ablum_title.getText().toString(),
                ablum_text.getText().toString());
        spiceManager.execute(myAddAblumRequest,myAddAblumRequestListener, DurationInMillis.ONE_SECOND * 10,
                myAddAblumRequestListener.start());
    }

    public void BackAfterSucc(){
        this.finish();
    }
}
