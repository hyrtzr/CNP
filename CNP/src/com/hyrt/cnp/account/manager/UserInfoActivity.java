package com.hyrt.cnp.account.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hyrt.cnp.R;
import com.hyrt.cnp.account.adapter.UserInfoAdapter;
import com.hyrt.cnp.account.request.UserInfoUpdateRequest;
import com.hyrt.cnp.account.request.UserVarRequest;
import com.hyrt.cnp.account.requestListener.UserInfoUpdateRequestListener;
import com.hyrt.cnp.account.requestListener.UserVarRequestListener;
import com.hyrt.cnp.base.account.model.BaseTest;
import com.hyrt.cnp.base.account.model.UserDetail;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

/**
 * TODO  部分字段不明确，需要后期增加，还有字典
 */
public class UserInfoActivity extends BaseActivity {

    private StringBuilder renname = new StringBuilder();
    private StringBuilder birthday = new StringBuilder();
    private StringBuilder className = new StringBuilder();
    private StringBuilder nurseryName = new StringBuilder();
    private StringBuilder sex = new StringBuilder();
    private StringBuilder national = new StringBuilder();
    private StringBuilder bloodType = new StringBuilder();
    private StringBuilder introl = new StringBuilder();

    private boolean mybabayinfo = true;

    private AlertDialog.Builder dialognationality=null;
    private AlertDialog.Builder dialogethnic=null;
    private AlertDialog.Builder dialogbloodType=null;
    private String[] notionality=null;
    private String[] ethnic=null;
    private String[] bloodtype=null;
    private int notionalityid=0;
    private int ethnicid=0;
    private int bloodtypeid=0;
    private List<Map<String, String>> values;
    private UserInfoAdapter simpleAdapter;
    private int num=5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);
        initListData();
        initView();
        if(mybabayinfo)
        getUserVar();
    }

    /**
     * 初始化list的数据
     * */
    private void initListData(){
        UserDetail.UserDetailModel userDetailModel =
                (UserDetail.UserDetailModel) getIntent().getSerializableExtra("vo");
        mybabayinfo = getIntent().getBooleanExtra("mybabayinfo", true);
        values = new ArrayList<Map<String, String>>();
        Map<String, String> item1 = new HashMap<String, String>();
        item1.put("title", "宝宝姓名");
        item1.put("content", userDetailModel.getData().getRenname());
        renname.append(userDetailModel.getData().getRenname());
        values.add(item1);
        Map<String, String> item2 = new HashMap<String, String>();
        item2.put("title", "出生年月");
        birthday.append(userDetailModel.getData().getBirthday());
        item2.put("content", userDetailModel.getData().getBirthday());
        values.add(item2);
        Map<String, String> item3 = new HashMap<String, String>();
        item3.put("title", "所在班级");
        item3.put("content", userDetailModel.getData().getNurseryName());
        values.add(item3);
        Map<String, String> item4 = new HashMap<String, String>();
        item4.put("title", "所在幼儿园");
        item4.put("content", userDetailModel.getData().getNurseryName());
        values.add(item4);
        Map<String, String> item5 = new HashMap<String, String>();
        item5.put("title", "性别");
        String strSex = "";
        String mSex = userDetailModel.getData().getSex();
        if(mSex != null && mSex.trim().length() > 0){
            if(mSex.toUpperCase().equals("M")){
                strSex = "男";
            }else{
                strSex = "女";
            }
        }
        sex.append(mSex);
        item5.put("content", strSex);
        values.add(item5);
        Map<String, String> item9 = new HashMap<String, String>();
        item9.put("title", "个性签名");
        item9.put("content", userDetailModel.getData().getIntro());
        values.add(item9);
        introl.append(userDetailModel.getData().getIntro());
        Map<String, String> item6 = new HashMap<String, String>();
        item6.put("title", "国籍");
        item6.put("content", userDetailModel.getData().getNationality());
        values.add(item6);
        Map<String, String> item7 = new HashMap<String, String>();
        item7.put("title", "血型");
        item7.put("content", userDetailModel.getData().getBloodType());
        values.add(item7);
        Map<String, String> item8 = new HashMap<String, String>();
        item8.put("title", "民族");
        item8.put("content", userDetailModel.getData().getEthnic());
        values.add(item8);
        if(mybabayinfo){
            notionalityid=Integer.valueOf(userDetailModel.getData().getNationality());//国籍赋值
            bloodtypeid=Integer.valueOf(userDetailModel.getData().getBloodType());
            ethnicid=Integer.valueOf(userDetailModel.getData().getEthnic());
        }
        simpleAdapter=new UserInfoAdapter(this, values, R.layout.user_info_item2, new String[]{"title", "content"}
                , new int[]{R.id.title, R.id.content}, mybabayinfo) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                final LinearLayout linearLayout = (LinearLayout) super.getView(position, convertView, parent);
                if (linearLayout != null) {
                    final TextView editText = (TextView) linearLayout.findViewById(R.id.content);
                    editText.setOnClickListener(null);
                    switch (position){
                        case 5:
                            editText.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    final EditText mEditText = new EditText(UserInfoActivity.this);
                                    mEditText.setText(introl+"");
                                    AlertDialog.Builder mBuilder =
                                            new AlertDialog.Builder(UserInfoActivity.this)
                                            .setTitle("个性签名")
                                            .setIcon(R.drawable.app)
                                            .setView(mEditText)
                                            .setPositiveButton("修改", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    introl = new StringBuilder(mEditText.getText().toString());
                                                    UpdataAdapter();
                                                }
                                            })
                                            .setNegativeButton("取消", null);
                                    mBuilder.show();
                                }
                            });
                            break;
                        case 6:
                            editText.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    num=position;
                                    if(dialognationality!=null&&mybabayinfo){
                                        dialognationality.show();
                                    }
                                }
                            });
                            break;
                        case 7:
                            editText.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    num=position;
                                    if(dialognationality!=null&&mybabayinfo){
                                        dialogbloodType.show();
                                    }
                                }
                            });
                            break;
                        case 8:
                            editText.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    num=position;
                                    if(dialognationality!=null&&mybabayinfo){
                                        dialogethnic.show();
                                    }
                                }
                            });
                            break;
                    }

                }
                return linearLayout;
            }
        };
    }

    /**
     * 初始化界面视图
     * */

    private void initView(){
        TextView prompt = (TextView) findViewById(R.id.prompt);

        if(mybabayinfo){
            prompt.setText(Html.fromHtml("提示：点击修改资料，<font color='red'>&nbsp\"*\"&nbsp</font>为不可修改"));
            prompt.setVisibility(View.VISIBLE);
            titletext.setText("用户资料");
        }else{
            prompt.setVisibility(View.GONE);
            titletext.setText("宝宝资料");
        }
        ((ListView) findViewById(R.id.user_info_listview)).setAdapter(simpleAdapter);
        if (mybabayinfo) {
            findViewById(R.id.userinfo_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initData();
                }
            });
        } else {
            findViewById(R.id.userinfo_btn).setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            initData();
        }
        return super.onKeyUp(keyCode, event);
    }

    /**
     * 提交更新数据
     */
    private void initData() {
        UserInfoUpdateRequest userInfoUpdateRequest = new UserInfoUpdateRequest(this, renname.toString(),
                birthday.toString(), sex.toString(), notionalityid+"", bloodtypeid+"",ethnicid+"", introl+"");
        UserInfoUpdateRequestListener userPwdRequestListener = new UserInfoUpdateRequestListener(this);
        spiceManager.execute(userInfoUpdateRequest, userInfoUpdateRequest.createCacheKey(),
                DurationInMillis.ONE_SECOND, userPwdRequestListener.start());
    }

    public void updateUi(BaseTest data){
        finish();
    }

    /**
     * 获取基本信息
     * */

    private void getUserVar(){
        UserVarRequest usernationality = new UserVarRequest(this,"nationality");
        UserVarRequestListener usernationalityListener = new UserVarRequestListener(this,"nationality");
        spiceManager.execute(usernationality, usernationality.createCacheKey(),
                DurationInMillis.ONE_SECOND, usernationalityListener.start());


    }
    /**
     * 设置提示窗口国籍
     * */
    public void SetVar_nationality(Map<String,String> map){
        notionality=MaptoStr(map);
        dialognationality = getAlertDialog(notionality,"国籍",notionalityid);
        UserVarRequest userbloodType = new UserVarRequest(this,"bloodType");
        UserVarRequestListener userbloodTypeListener = new UserVarRequestListener(this,"bloodType");
        spiceManager.execute(userbloodType, userbloodType.createCacheKey(),
                DurationInMillis.ONE_SECOND, userbloodTypeListener.start());
    }
    /**
     * 设置提示窗口民族
     * */
    public void SetVar_ethnic(Map<String,String> map){
        ethnic=MaptoStr(map);
        dialogethnic = getAlertDialog(ethnic,"民族",ethnicid);
        UpdataAdapter();
    }
    /**
     * 设置提示窗口血型
     * */
    public void SetVar_bloodType(Map<String,String> map){
        bloodtype=MaptoStr(map);
        dialogbloodType = getAlertDialog(bloodtype,"血型",bloodtypeid);
        UserVarRequest userethnic = new UserVarRequest(this,"ethnic");
        UserVarRequestListener userethnicListener = new UserVarRequestListener(this,"ethnic");
        spiceManager.execute(userethnic, userethnic.createCacheKey(),
                DurationInMillis.ONE_SECOND, userethnicListener.start());
    }

    /**
     * 将map数据解析成数组
     * */
    private String[] MaptoStr(Map<String,String> map){
        String str="";
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            if(str.equals("")){
                str=entry.getValue();
            }else{
                str=str+"_"+entry.getValue();
            }
        }
        String[] strs=str.split("_");
        return strs;
    }

    /**
     * 创建提示框
     * */

    private AlertDialog.Builder getAlertDialog(String[] strs,String str,int i){
        return new AlertDialog.Builder(this)
                .setTitle(str)
                .setIcon(R.drawable.app)
                .setSingleChoiceItems(strs, i,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                if (num == 5) {//国籍
                                    notionalityid = which;
                                } else if (num == 6) {//血型
                                    bloodtypeid = which;
                                } else {//民族
                                    ethnicid = which;
                                }
                                UpdataAdapter();
                            }
                        }
                )
                .setNegativeButton("取消", null);
    }

    /**
     * 更新ui
     * */

    private void UpdataAdapter(){
        values.remove(5);
        values.remove(5);
        values.remove(5);
        values.remove(5);
        Map<String, String> item5 = new HashMap<String, String>();
        item5.put("title", "个性签名");
        item5.put("content", introl+"");
        values.add(5, item5);
        Map<String, String> item6 = new HashMap<String, String>();
        item6.put("title", "国籍");
        item6.put("content", notionality[notionalityid]);
        values.add(6,item6);
        Map<String, String> item7 = new HashMap<String, String>();
        item7.put("title", "血型");
        item7.put("content", bloodtype[bloodtypeid]);
        values.add(7,item7);
        Map<String, String> item8 = new HashMap<String, String>();
        item8.put("title", "民族");
        item8.put("content",ethnic[ethnicid]);
        values.add(8,item8);
        simpleAdapter.notifyDataSetChanged();
    }
}
