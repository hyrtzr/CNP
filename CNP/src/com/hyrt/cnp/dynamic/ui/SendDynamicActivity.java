package com.hyrt.cnp.dynamic.ui;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.internal.view.SupportMenuInflater;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.base.account.model.Photo;
import com.hyrt.cnp.base.account.request.BaseClassroomaddcommentRequest;
import com.hyrt.cnp.base.account.requestListener.BaseClassroomaddcommentRequestListener;
import com.hyrt.cnp.base.account.utils.AlertUtils;
import com.hyrt.cnp.base.account.utils.FileUtils;
import com.hyrt.cnp.base.account.utils.PhotoUpload;
import com.hyrt.cnp.base.account.utils.StringUtils;
import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.adapter.BrowGridAdapter;
import com.hyrt.cnp.dynamic.fragment.AlldynamicFragment;
import com.hyrt.cnp.dynamic.request.DynamicCommentRequest;
import com.hyrt.cnp.dynamic.request.DynamicaddcommentRequest;
import com.hyrt.cnp.dynamic.request.SendDynamicRequest;
import com.hyrt.cnp.dynamic.requestListener.DynamicaddcommentRequestListener;
import com.hyrt.cnp.dynamic.requestListener.DynamiccommentRequestListener;
import com.hyrt.cnp.dynamic.requestListener.SendDynamicRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;

import net.oschina.app.AppContext;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Zoe on 2014-04-09.
 */
public class SendDynamicActivity extends BaseActivity{

    private EditText etContent;
    private ImageView btnAddBrow;
    private ImageView btnAddPhoto;
    private ImageView btnAddAbout;
    private GridView gvBrows;
//    private MenuItem sendbtn;
    private LinearLayout layoutForwardSpan;
    private LinearLayout layoutAddPhoto;
    private ImageView photo;
    private TextView tvForwardContent;
    private RelativeLayout layoutInput;
    private ImageView ivForwardPhoto;
    private View btn_back;
    private TextView tv_send;
    private TextView tv_action_title;

    private boolean sendEnabled;

    private static final String TAG = "SendDynamicActivity";

    private Photo mPhoto;



    private boolean hideKeyboard = false;

    private BrowGridAdapter mBrowGridAdapter;

    private Map<String, Integer> brows = new HashMap<String, Integer>();
    private List<Object[]> browArray = new ArrayList<Object[]>();
    private Map<String, Integer> aboutArray = new HashMap<String, Integer>();
    private Uri faceFile;
    private PhotoUpload photoUpload;
    private Bitmap bitmap;
    private File targetFile;
    private int type = 0;

    private Dynamic mDynamic;
    private Comment mComment;

    public static final int FROM_ABOUT_FRIEND = 101;

    public static final int TYPE_SEND = 0;
    public static final int TYPE_FORWARD = 1;
    public static final int TYPE_COMMENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_dynamic);
        Intent intent = getIntent();
        type = intent.getIntExtra("type", TYPE_SEND);
        findView();
        if(type == TYPE_FORWARD){
            layoutForwardSpan.setVisibility(View.VISIBLE);
            btnAddAbout.setVisibility(View.GONE);
            mDynamic = (Dynamic) intent.getSerializableExtra("dynamic");
            actionBar.setTitle("转发动态");
            setSendEnabled(true);

            if(mDynamic.getbPicAry().size()>0){
                ImageLoader.getInstance().displayImage(mDynamic.getsPicAry0(), ivForwardPhoto, AppContext.getInstance().mImageloaderoptions);
                ivForwardPhoto.setVisibility(View.VISIBLE);
            }else{
                ivForwardPhoto.setVisibility(View.GONE);
            }
            if(mDynamic.gettContent().length()>0 || mDynamic.getContent().length() > 0){
                LinearLayout layout_forward_content_span = (LinearLayout) findViewById(R.id.layout_forward_content_span);
                LinearLayout.LayoutParams mParams = (LinearLayout.LayoutParams) layout_forward_content_span.getLayoutParams();
                if(mParams == null){
                    mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }else{
                    mParams.width = LinearLayout.LayoutParams.FILL_PARENT;
                }

                if(mDynamic.gettContent().length()>0){
                    tvForwardContent.setText(StringUtils.getSpannableString(mDynamic.gettContent(), this));
                }else{
                    tvForwardContent.setText(StringUtils.getSpannableString(mDynamic.getContent(), this));
                }

                tvForwardContent.setVisibility(View.VISIBLE);
            }else{
                LinearLayout layout_forward_content_span = (LinearLayout) findViewById(R.id.layout_forward_content_span);
                LinearLayout.LayoutParams mParams = (LinearLayout.LayoutParams) layout_forward_content_span.getLayoutParams();
                if(mParams == null){
                    mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                }else{
                    mParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
                }
                tvForwardContent.setVisibility(View.GONE);
            }

        }else if(type == TYPE_COMMENT){
            btnAddAbout.setVisibility(View.GONE);
            layoutInput.getLayoutParams().height = getResources().getDimensionPixelOffset(R.dimen.send_dynamic_content_height_big);
            mDynamic = (Dynamic) intent.getSerializableExtra("dynamic");
            mComment = (Comment) intent.getSerializableExtra("comment");
            actionBar.setTitle("发评论");
        }else{
            btnAddAbout.setVisibility(View.VISIBLE);
            layoutAddPhoto.setVisibility(View.VISIBLE);
            actionBar.setTitle("发布动态");
        }
        setListener();
        loadData();
    }

    public void loadData(){
        Object[] brow1 = new Object[2];
        brow1[0] = "[img]哈哈[/img]";
        brow1[1] = R.drawable.laugh;
        brows.put("[img]哈哈[/img]", R.drawable.laugh);
        browArray.add(brow1);

        Object[] brow2 = new Object[2];
        brow2[0] = "[img]鄙视[/img]";
        brow2[1] = R.drawable.bs2_thumb;
        brows.put("[img]鄙视[/img]", R.drawable.bs2_thumb);
        browArray.add(brow2);

        Object[] brow3 = new Object[2];
        brow3[0] = "[img]闭嘴[/img]";
        brow3[1] = R.drawable.bz_thumb;
        brows.put("[img]闭嘴[/img]", R.drawable.bz_thumb);
        browArray.add(brow3);

        Object[] brow4 = new Object[2];
        brow4[0] = "[img]偷笑[/img]";
        brow4[1] = R.drawable.heia_thumb;
        brows.put("[img]偷笑[/img]", R.drawable.heia_thumb);
        browArray.add(brow4);

        Object[] brow5 = new Object[2];
        brow5[0] = "[img]吃惊[/img]";
        brow5[1] = R.drawable.cj_thumb;
        brows.put("[img]吃惊[/img]", R.drawable.cj_thumb);
        browArray.add(brow5);

        Object[] brow6 = new Object[2];
        brow6[0] = "[img]可怜[/img]";
        brow6[1] = R.drawable.kl_thumb;
        brows.put("[img]可怜[/img]", R.drawable.kl_thumb);
        browArray.add(brow6);

        Object[] brow7 = new Object[2];
        brow7[0] = "[img]懒得理你[/img]";
        brow7[1] = R.drawable.ldln_thumb;
        brows.put("[img]懒得理你[/img]", R.drawable.ldln_thumb);
        browArray.add(brow7);

        Object[] brow8 = new Object[2];
        brow8[0] = "[img]太开心[/img]";
        brow8[1] = R.drawable.mb_thumb;
        brows.put("[img]太开心[/img]", R.drawable.mb_thumb);
        browArray.add(brow8);

        Object[] brow9 = new Object[2];
        brow9[0] = "[img]爱你[/img]";
        brow9[1] = R.drawable.lovea_thumb;
        brows.put("[img]爱你[/img]", R.drawable.lovea_thumb);
        browArray.add(brow9);

        Object[] brow10 = new Object[2];
        brow10[0] = "[img]亲亲[/img]";
        brow10[1] = R.drawable.qq_thumb;
        brows.put("[img]亲亲[/img]", R.drawable.qq_thumb);
        browArray.add(brow10);

        Object[] brow11 = new Object[2];
        brow11[0] = "[img]泪[/img]";
        brow11[1] = R.drawable.sada_thumb;
        brows.put("[img]泪[/img]", R.drawable.sada_thumb);
        browArray.add(brow11);

        Object[] brow12 = new Object[2];
        brow12[0] = "[img]生病[/img]";
        brow12[1] = R.drawable.sb_thumb;
        brows.put("[img]生病[/img]", R.drawable.sb_thumb);
        browArray.add(brow12);

        Object[] brow13 = new Object[2];
        brow13[0] = "[img]害羞[/img]";
        brow13[1] = R.drawable.shamea_thumb;
        brows.put("[img]害羞[/img]", R.drawable.shamea_thumb);
        browArray.add(brow13);

        Object[] brow14 = new Object[2];
        brow14[0] = "[img]呵呵[/img]";
        brow14[1] = R.drawable.smilea_thumb;
        brows.put("[img]呵呵[/img]", R.drawable.smilea_thumb);
        browArray.add(brow14);

        Object[] brow15 = new Object[2];
        brow15[0] = "[img]嘻嘻[/img]";
        brow15[1] = R.drawable.tootha_thumb;
        brows.put("[img]嘻嘻[/img]", R.drawable.tootha_thumb);
        browArray.add(brow15);

        Object[] brow16 = new Object[2];
        brow16[0] = "[img]可爱[/img]";
        brow16[1] = R.drawable.tza_thumb;
        brows.put("[img]可爱[/img]", R.drawable.tza_thumb);
        browArray.add(brow16);

        Object[] brow17 = new Object[2];
        brow17[0] = "[img]挤眼[/img]";
        brow17[1] = R.drawable.zy_thumb;
        brows.put("[img]挤眼[/img]", R.drawable.zy_thumb);
        browArray.add(brow17);

        Object[] brow18 = new Object[2];
        brow18[0] = "[img]阴险[/img]";
        brow18[1] = R.drawable.yx_thumb;
        brows.put("[img]阴险[/img]", R.drawable.yx_thumb);
        browArray.add(brow18);

        Object[] brow19 = new Object[2];
        brow19[0] = "[img]右哼哼[/img]";
        brow19[1] = R.drawable.yhh_thumb;
        brows.put("[img]右哼哼[/img]", R.drawable.yhh_thumb);
        browArray.add(brow19);

        Object[] brow20 = new Object[2];
        brow20[0] = "[img]来[/img]";
        brow20[1] = R.drawable.come_thumb;
        brows.put("[img]来[/img]", R.drawable.come_thumb);
        browArray.add(brow20);

        mBrowGridAdapter = new BrowGridAdapter(this, browArray);
        gvBrows.setAdapter(mBrowGridAdapter);
    }

    private void setListener() {
        btnAddBrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(SendDynamicActivity.this.INPUT_METHOD_SERVICE);
                if(gvBrows.getVisibility() != View.VISIBLE){
                    if(imm.isActive()){
                        hideKeyboard = true;
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    gvBrows.setVisibility(View.VISIBLE);
                }else{
                    gvBrows.setVisibility(View.GONE);
                    if(hideKeyboard){
                        hideKeyboard = false;
                        imm.showSoftInput(view,InputMethodManager.RESULT_SHOWN);
                    }
                }
            }
        });

        btnAddAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(SendDynamicActivity.this, AboutFriendActivity.class);
                startActivityForResult(intent, FROM_ABOUT_FRIEND);
            }
        });

        gvBrows.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 20){
                    String text = etContent.getText().toString();
                    if(text.length()>0){
                        int selectionStart = etContent.getSelectionStart();
                        String tempStr = text.substring(0, selectionStart);
                        if(tempStr.substring(tempStr.length()-1, tempStr.length()).equals("]")){
                            int start = tempStr.lastIndexOf("[img]");
                            etContent.getEditableText().delete(start, selectionStart);
                        }else if(tempStr.length() >= 2 && tempStr.substring(tempStr.length()-2, tempStr.length()).equals("//")) {
                            int start = tempStr.lastIndexOf("@");
                            String delName = tempStr.substring(start+1, tempStr.lastIndexOf("//")-1);
                            etContent.getEditableText().delete(start, selectionStart);
                        } else {
                            etContent.getEditableText().delete(tempStr.length() - 1, selectionStart);
                        }

                    }
                }else{
                    Object[] objs = browArray.get(i);
                    SpannableString spannable = new SpannableString(objs[0]+"");
                    Drawable drawable = getResources().getDrawable((Integer) objs[1]);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
                    spannable.setSpan(
                            span,
                            0,
                            objs[0].toString().length(),
                            Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                    etContent.append(spannable);
                    etContent.setSelection(etContent.getText().length());
                }
            }
        });

        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if(charSequence.length()>0 || type == TYPE_FORWARD){
                    setSendEnabled(true);
                }else{
                    setSendEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                faceFile = Uri.fromFile(FileUtils.createFile("cnp", "face_cover.png"));
                photoUpload = new PhotoUpload(SendDynamicActivity.this, faceFile);
                photoUpload.choiceItem();
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                faceFile = null;
                targetFile = null;
                photo.setVisibility(View.GONE);
                TextView textview = (TextView) findViewById(R.id.tv_add_photo_text);
                textview.setVisibility(View.VISIBLE);
                btnAddPhoto.setVisibility(View.VISIBLE);
            }
        });

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
                    if(type == TYPE_COMMENT){
                        addComment();
                    }else if(type == TYPE_FORWARD){
                        addForward();
                    }else{
                        sendDynamic();
                    }
                }
            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        if (etContent.getText().length() > 0 || type == TYPE_FORWARD) {
            setSendEnabled(true);
        }else{
            setSendEnabled(false);
        }
        if(type == TYPE_FORWARD){
            tv_action_title.setText("转发动态");
        }else if(type == TYPE_COMMENT){
            tv_action_title.setText("发评论");
        }else{
            tv_action_title.setText("发布动态");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == FROM_ABOUT_FRIEND) {
            if(data != null){
                String name = data.getStringExtra("name");
                int uid = data.getIntExtra("uid", -1);
                etContent.append(Html.fromHtml("<font color='#6ecbd9'>@" + name + "</font>//"));
                aboutArray.put(name, uid);
            }
        }else if (requestCode == PhotoUpload.PHOTO_ZOOM && data != null && data.getParcelableExtra("data") != null) {
            //保存剪切好的图片
            bitmap = data.getParcelableExtra("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            targetFile = FileUtils.writeFile(baos.toByteArray(), "cnp", "dynamic_upload_photo.png");

            BitmapDrawable bd = new BitmapDrawable(bitmap);
            if(Build.VERSION.SDK_INT >= 16){
                photo.setBackground(bd);
            }else{
                photo.setBackgroundDrawable(bd);
            }
            photo.setVisibility(View.VISIBLE);
            TextView textview = (TextView) findViewById(R.id.tv_add_photo_text);
            textview.setVisibility(View.GONE);
            btnAddPhoto.setVisibility(View.GONE);
        }else if(resultCode == AppContext.getInstance().RESULT_FOR_PHONE_ALBUM){
            ArrayList<String> checkeds = data.getStringArrayListExtra("checkeds");
            if(checkeds != null && checkeds.size() > 0){
                ImageLoader.getInstance().displayImage("file:///"+checkeds.get(0), photo, AppContext.getInstance().mImageloaderoptions);
                photo.setVisibility(View.VISIBLE);
                TextView textview = (TextView) findViewById(R.id.tv_add_photo_text);
                textview.setVisibility(View.GONE);
                btnAddPhoto.setVisibility(View.GONE);
                targetFile = new File("", checkeds.get(0));
            }
        }
    }

    @Override
    protected void initTitleview() {
        actionBar.hide();
    }

    public void addComment(){
        Comment comment=new Comment();
        if(mDynamic == null){
            android.util.Log.i("tag", "mComment:"+mComment);
            comment.set_id(mComment.get_id()+"");
            comment.setInfoid2(mComment.getInfoID());
//            if(mComment.getInfoTitle().equals("")){
//                comment.setInfoTitle("null");
//            }else{
//                comment.setInfoTitle(mComment.getInfoTitle());
//            }
            comment.setInfoUserId(mComment.getInfoUserId()+"");

            comment.setInfoNurseryId(mComment.getNursery_id()+"");
            comment.setInfoClassroomId(mComment.getInfoClassroomId()+"");

            comment.setUrl(mComment.getUrl());
            comment.setRecontent(mComment.getContent());
            comment.setReuserId(mComment.getUserid()+"");
            comment.setReusername(mComment.getUsername());
            if(mComment.getInfoTitle() != null && mComment.getInfoTitle().trim().length() > 0){
                comment.setInfoTitle(mComment.getInfoTitle());
            }/*else{
                comment.setInfoTitle(mComment.getContent());
            }*/
        }else{
            comment.set_id(mDynamic.get_id()+"");
            comment.setInfoid2(mDynamic.get_id());
//            if(mDynamic.getTitle().equals("")){
//                comment.setInfoTitle("null");
//            }else{
//                comment.setInfoTitle(mDynamic.getTitle());
//            }
            comment.setInfoUserId(mDynamic.getUserId()+"");
            comment.setInfoNurseryId(mDynamic.getNueseryId()+"");
            comment.setInfoClassroomId(mDynamic.getClassRoomId()+"");

            comment.setUrl("null");
            comment.setRecontent(mDynamic.getdContent());
            comment.setReuserId(mDynamic.getUserId()+"");
            comment.setReusername(mDynamic.getUserName());

            if(mDynamic.getIsTran() == 2){
                comment.setInfoTitle(mDynamic.getdContent());
            }else{
                comment.setInfoTitle(mDynamic.getContent());
            }
            /*if(mDynamic.getdContent() != null
                    && !mDynamic.getdContent().equals("")
                    && !mDynamic.getdContent().equals("null")){
                comment.setInfoTitle(mDynamic.getdContent());
            }else if(mDynamic != null && mDynamic.getTitle() != null && mDynamic.getTitle().trim().length() > 0){
                comment.setInfoTitle(mDynamic.getTitle());
            }*//*else{
                comment.setInfoTitle(mDynamic.getContent());
            }*/
        }


        comment.setSiteid("50");
        comment.setLstatus("Y");
        comment.setContent(etContent.getText().toString());
        if(mComment != null){
            comment.setSiteid(mComment.getSiteid());
            comment.setRedate(mComment.getCreatdate());
            comment.setReply("1");
        }else{
            comment.setReply("0");
        }



        DynamicaddcommentRequestListener sendwordRequestListener = new DynamicaddcommentRequestListener(this);
        sendwordRequestListener.setListener(mAddCommentRequestListener);
        DynamicaddcommentRequest schoolRecipeRequest= null;
        if(mComment == null){
            schoolRecipeRequest = new DynamicaddcommentRequest(Comment.Model3.class,this,comment, 0);
        }else{
            schoolRecipeRequest = new DynamicaddcommentRequest(Comment.Model3.class,this,comment, 1);
        }
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }

    /**
     * 获取@的人列表
     * @param text
     */
    public Map<String, Integer> findAboutName(String text){
        Map<String, Integer> selectedAboutArray = new HashMap<String, Integer>();
        String tempStr = text;
        while(tempStr.length() >0 && tempStr.lastIndexOf("@") >= 0 && tempStr.lastIndexOf("//") >= 0){
            int aboutStart = tempStr.lastIndexOf("@");
            int aboutEnd = tempStr.lastIndexOf("//");
            if(aboutStart < aboutEnd){
                android.util.Log.i("tag", "aboutStart:"+ aboutStart+" aboutEnd:"+aboutEnd);
                String str = tempStr.substring(aboutStart+1, aboutEnd);
                if(aboutArray.containsKey(str)){
                    selectedAboutArray.put(str, aboutArray.get(str));
                }
                android.util.Log.i("tag", "str:"+ str);
            }
            tempStr = tempStr.substring(0, aboutStart);

        }
        Iterator<Map.Entry<String, Integer>> it = selectedAboutArray.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, Integer> et = it.next();
            android.util.Log.i("tag", "id:"+ et.getValue()+" name:"+et.getKey());
        }
        return selectedAboutArray;
    }

    private DynamicaddcommentRequestListener.requestListener mAddCommentRequestListener
            = new DynamicaddcommentRequestListener.requestListener() {
        @Override
        public void onRequestSuccess(Object data) {
            AlertUtils.getInstance().showCenterToast(SendDynamicActivity.this, "发送成功");
//            Toast toast = Toast.makeText(SendDynamicActivity.this, "发送成功", 0);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            toast.show();
            setResult(AlldynamicFragment.RESULT_FOR_SEND_DYNAMIC);
            finish();
        }

        @Override
        public void onRequestFailure(SpiceException e) {
//            Toast toast = Toast.makeText(SendDynamicActivity.this, "发送失败", 0);
//            toast.setGravity(Gravity.CENTER, 0, 0);
//            toast.show();
            AlertUtils.getInstance().showCenterToast(SendDynamicActivity.this, "发送失败");
        }
    };

    public void addForward(){
        Dynamic data=new Dynamic();
        data.set_id(mDynamic.get_id());
        data.setContent(etContent.getText().toString());
        DynamiccommentRequestListener sendwordRequestListener = new DynamiccommentRequestListener(this);
        sendwordRequestListener.setListener(mForwardRequestListener);
        DynamicCommentRequest schoolRecipeRequest=new DynamicCommentRequest(Dynamic.Model3.class,this,data);
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }

    private DynamiccommentRequestListener.RequestListener mForwardRequestListener = new DynamiccommentRequestListener.RequestListener() {
        @Override
        public void onRequestSuccess(Object data) {
            AlertUtils.getInstance().showCenterToast(SendDynamicActivity.this, "发送成功");
//            Toast.makeText(SendDynamicActivity.this, "发送成功", 0).show();
            setResult(AlldynamicFragment.RESULT_FOR_SEND_DYNAMIC);
            finish();
        }

        @Override
        public void onRequestFailure(SpiceException e) {
            AlertUtils.getInstance().showCenterToast(SendDynamicActivity.this, "发送失败");
//            Toast.makeText(SendDynamicActivity.this, "发送失败", 0).show();
        }
    };

    public void sendDynamic(){
        String toUid = "";
        String toName = "";
        Map<String, Integer> selectedAboutArray = findAboutName(etContent.getText().toString());
        if(selectedAboutArray.size() > 0){
            Iterator<Map.Entry<String, Integer>> it = selectedAboutArray.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String, Integer> et = it.next();
                toUid += et.getValue()+",";
                toName += et.getKey()+",";
            }
            toUid.substring(0, toUid.length()-1);
            toName.substring(0, toName.length()-1);
        }


        android.util.Log.i("tag", "targetFile:"+targetFile+" toUid:"+toUid+" toName:"+toName);
        SendDynamicRequest request = new SendDynamicRequest(this, etContent.getText().toString(), targetFile, toUid, toName);
        SendDynamicRequestListener requestListener = new SendDynamicRequestListener(this);
        requestListener.setListener(mSendDynamicRequestListener);
        spiceManager.execute(request, request.createCacheKey(), 1, requestListener.start());
    }

    private SendDynamicRequestListener.requestListener mSendDynamicRequestListener = new SendDynamicRequestListener.requestListener() {
        @Override
        public void onRequestSuccess(Object o) {
            AlertUtils.getInstance().showCenterToast(SendDynamicActivity.this, "发送成功");
//            Toast.makeText(SendDynamicActivity.this, "发送成功", 0).show();
            setResult(AlldynamicFragment.RESULT_FOR_SEND_DYNAMIC);
            finish();
        }

        @Override
        public void onRequestFailure(SpiceException e) {
            AlertUtils.getInstance().showCenterToast(SendDynamicActivity.this, "发送失败");
//            Toast.makeText(SendDynamicActivity.this, "发送失败", 0).show();
        }
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
        etContent = (EditText) findViewById(R.id.et_send_dynamic_content);
        btnAddBrow = (ImageView) findViewById(R.id.btn_add_brow);
        btnAddPhoto = (ImageView) findViewById(R.id.btn_add_photo);
        gvBrows = (GridView) findViewById(R.id.gv_brows);
        btnAddAbout = (ImageView) findViewById(R.id.btn_add_about);
        layoutForwardSpan = (LinearLayout)findViewById(R.id.layout_forward_span);
        layoutAddPhoto = (LinearLayout) findViewById(R.id.layout_add_photo);
        photo = (ImageView) findViewById(R.id.iv_photo1);
        tvForwardContent = (TextView) findViewById(R.id.tv_forward_content);
        layoutInput = (RelativeLayout) findViewById(R.id.layout_input);
        ivForwardPhoto = (ImageView) findViewById(R.id.iv_forward_photo);
        btn_back = findViewById(R.id.btn_back);
        tv_send = (TextView) findViewById(R.id.tv_send);
        tv_action_title = (TextView) findViewById(R.id.tv_action_title);
    }
}
