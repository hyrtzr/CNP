package com.hyrt.cnp.account.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.oschina.app.AppContext;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hyrt.cnp.R;
import com.hyrt.cnp.account.request.UserPwdRequest;
import com.hyrt.cnp.account.requestListener.UserPwdRequestListener;
import com.hyrt.cnp.base.account.model.UserDetail;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

public class UserPasswordActivity extends BaseActivity{

    private StringBuilder oldPassword = new StringBuilder();
    private StringBuilder newPassword = new StringBuilder();
    private StringBuilder renewPassword = new StringBuilder();
    private UserDetail.UserDetailModel userDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_password);
        AppContext.getInstance().activities.add(this);
        userDetail = ( UserDetail.UserDetailModel)getIntent().getSerializableExtra("vo");
        List<Map<String,StringBuilder>> values = new ArrayList<Map<String,StringBuilder>>();
        Map<String,StringBuilder> item1 = new HashMap<String, StringBuilder>();
        item1.put("title",new StringBuilder("当前账号"));
        item1.put("content",new StringBuilder(userDetail.getData().getRenname()));
        values.add(item1);
        Map<String,StringBuilder> item2 = new HashMap<String, StringBuilder>();
        item2.put("title",new StringBuilder("当前密码"));
        item2.put("content",oldPassword);
        values.add(item2);
        Map<String,StringBuilder> item3 = new HashMap<String, StringBuilder>();
        item3.put("title",new StringBuilder("新密码"));
        item3.put("content",newPassword);
        values.add(item3);
        Map<String,StringBuilder> item4 = new HashMap<String, StringBuilder>();
        item4.put("title",new StringBuilder("确认密码"));
        item4.put("content",renewPassword);
        values.add(item4);
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this,values,R.layout.user_info_item,new String[]{"title","content"},new int[]{R.id.title,R.id.content}){

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                final LinearLayout linearLayout = (LinearLayout) super.getView(position, convertView, parent);
                if(linearLayout != null){
                    EditText editText = (EditText) linearLayout.findViewById(R.id.content);
                    if(position == 0){
                        TextView textView =  (TextView)linearLayout.findViewById(R.id.title);
                        textView.setTextColor(getResources().getColor(android.R.color.black));
                        editText.setEnabled(false);

                    }else{
                        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                EditText editText = (EditText) linearLayout.findViewById(R.id.content);
                                editText.addTextChangedListener(new TextWatcher(){

                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        switch (position){
                                            case 1:
                                                oldPassword.delete(0,oldPassword.length());
                                                oldPassword.append(s.toString());
                                                break;
                                            case 2:
                                                newPassword.delete(0, newPassword.length());
                                                newPassword.append(s.toString());
                                                break;
                                            case 3:
                                                renewPassword.delete(0, renewPassword.length());
                                                renewPassword.append(s.toString());
                                                break;
                                        }
                                    }
                                });
                            }
                        });
                    }

                }
                return linearLayout;
            }
        };
        ((ListView)findViewById(R.id.user_info_listview)).setAdapter(simpleAdapter);
        findViewById(R.id.password_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_ENTER){
            initData();
        }
        return super.onKeyUp(keyCode, event);
    }

    /**
     * 获取数据
     */
    private void initData(){
        UserPwdRequest userPwdRequest = new UserPwdRequest(this,oldPassword.toString(),
                newPassword.toString(),renewPassword.toString());
        UserPwdRequestListener userPwdRequestListener = new UserPwdRequestListener(this);
        spiceManager.execute(userPwdRequest, userPwdRequest.createCacheKey(),
                DurationInMillis.ONE_SECOND, userPwdRequestListener.start());
    }
}
