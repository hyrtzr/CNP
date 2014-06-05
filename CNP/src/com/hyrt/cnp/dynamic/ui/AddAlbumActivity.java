package com.hyrt.cnp.dynamic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.internal.view.SupportMenuInflater;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Album;
import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.base.account.utils.AlertUtils;
import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.fragment.MyAblumsFragment;
import com.hyrt.cnp.dynamic.request.MyAddAblumRequest;
import com.hyrt.cnp.dynamic.request.MyAlbumRequest;
import com.hyrt.cnp.dynamic.requestListener.MyAddAblumRequestListener;
import com.hyrt.cnp.dynamic.requestListener.MyAlbumRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zoe on 2014-04-11.
 */
public class AddAlbumActivity extends BaseActivity{

    private EditText etDescribe;
    private EditText etName;
//    private MenuItem sendbtn;
    private boolean sendEnabled;
    private View btn_back;
    private TextView tv_send;
    private TextView tv_action_title;

    private ArrayList<String> albumNames;

    private Album mAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_album);
        mAlbum = (Album) getIntent().getSerializableExtra("album");
        albumNames = getIntent().getStringArrayListExtra("albumNames");
        if(mAlbum != null && albumNames != null){
            albumNames.remove(mAlbum.getAlbumName());
        }
        findView();
        tv_action_title.setText(getString(R.string.album_list));
        if(mAlbum != null){
            if(mAlbum.getSimpleAlbumDesc() != null && !mAlbum.getSimpleAlbumDesc().equals("null")){
                etDescribe.setText(mAlbum.getSimpleAlbumDesc());
            }

            etName.setText(mAlbum.getAlbumName());
            setSendEnabled(true);
        }
        setListener();
    }

    @Override
    protected void initTitleview() {
        super.initTitleview();
        actionBar.hide();
    }

    private void setListener() {
        etDescribe.addTextChangedListener(mEditTextChangeListener);
        etName.addTextChangedListener(mEditTextChangeListener);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sendEnabled){
                    /*if(albumNames != null && albumNames.contains(etName.getText().toString())){
                        AlertUtils.getInstance().showCenterToast(AddAlbumActivity.this, "相册名重复");
                        return;
                    }*/
                    if(mAlbum == null){
                        MyAddAblumRequestListener requestListener = new MyAddAblumRequestListener(AddAlbumActivity.this);
                        requestListener.setListener( new MyAddAblumRequestListener.RequestListener() {
                            @Override
                            public void onRequestSuccess(Object data) {
                                setResult(ChangeAlbumActivity.RESULT_FOR_ADD_ALBUM);
                                finish();
                            }

                            @Override
                            public void onRequestFailure(SpiceException e) {}
                        });
                        MyAddAblumRequest request = new MyAddAblumRequest(
                                Comment.Model3.class, AddAlbumActivity.this,
                                etName.getText().toString(),
                                etDescribe.getText().toString());
                        spiceManager.execute(request, request.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                                requestListener.start());
                    }else{
                        MyAlbumRequestListener requestListener = new MyAlbumRequestListener(AddAlbumActivity.this);
                        requestListener.setListener(new MyAlbumRequestListener.RequestListener() {
                            @Override
                            public void onRequestSuccess(Object data) {
                                Intent mData = new Intent();
                                mAlbum.setAlbumName(etName.getText().toString());
                                mAlbum.setAlbumDesc(etDescribe.getText().toString());
                                mData.putExtra("album", mAlbum);
                                setResult(MyAblumsFragment.RESULT_FOR_ADD_ALBUM, mData);
                                finish();
                            }

                            @Override
                            public void onRequestFailure(SpiceException e) {}
                        });
                        MyAlbumRequest request = new MyAlbumRequest(
                                BaseTest.class, AddAlbumActivity.this, mAlbum.getPaId()+"",
                                etName.getText().toString(),
                                etDescribe.getText().toString());
                        spiceManager.execute(request, request.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                                requestListener.start());
                    }
                }
            }
        });
    }

    TextWatcher mEditTextChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) { }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if(etName.getText().length() > 0 && etDescribe.getText().length() > 0){
                setSendEnabled(true);
            }else{
                setSendEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) { }
    };

    private void setSendEnabled(boolean enable){
        if(enable){
            sendEnabled = true;
            tv_send.setTextColor(getResources().getColor(android.R.color.white));
        }else{
            sendEnabled = false;
            tv_send.setTextColor(getResources().getColor(R.color.sendbtn_enable_color));
        }
    }

    private void findView() {
        etName = (EditText) findViewById(R.id.et_album_name);
        etName.requestFocus();
        etDescribe = (EditText) findViewById(R.id.et_album_describe);
        btn_back = findViewById(R.id.btn_back);
        tv_send = (TextView) findViewById(R.id.tv_send);
        tv_action_title = (TextView) findViewById(R.id.tv_action_title);
    }
}
