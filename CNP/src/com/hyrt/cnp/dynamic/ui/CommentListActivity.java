package com.hyrt.cnp.dynamic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.adapter.CommentListAdapter;
import com.hyrt.cnp.dynamic.fragment.AlldynamicFragment;
import com.hyrt.cnp.dynamic.fragment.CommentListFragment;
import com.hyrt.cnp.dynamic.fragment.ForwardListFragment;
import com.jingdong.common.frame.BaseActivity;

import java.util.ArrayList;

/**
 * Created by GYH on 14-2-24.
 */
public class CommentListActivity extends BaseActivity{

    private TextView tv_transmit_num;
    private TextView tv_review_num;
    private ListView xListView;

    private  Dynamic dynamic;

    private ArrayList<Comment> comments =new ArrayList<Comment>();
    private CommentListAdapter dynamicAdapter;
    private boolean isComment;

    CommentListFragment mCommentListFragment;
    ForwardListFragment mForwardListFragment;

    private int forwardCount;
    private int commentCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentlist);
        initView();
        setListener();
        initDataFragment();
    }

    private void initDataFragment(){
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        if(mCommentListFragment == null){
            mCommentListFragment = new CommentListFragment(dynamic);
            mFragmentTransaction.add(R.id.fragment, mCommentListFragment);
        }else{
            mFragmentTransaction.show(mCommentListFragment);
        }
        if(mForwardListFragment != null){
            mFragmentTransaction.hide(mForwardListFragment);
        }
        mFragmentTransaction.commit();
        isComment = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("评论")
                .setIcon(R.drawable.editbtn)
                .setShowAsAction(
                        MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("评论")){


            Intent intent = new Intent();
            intent.setClass(this, SendDynamicActivity.class);
            intent.putExtra("dynamic", dynamic);
            if(isComment){
                intent.putExtra("type", SendDynamicActivity.TYPE_COMMENT);
            }else{
                intent.putExtra("type", SendDynamicActivity.TYPE_FORWARD);
            }
            startActivityForResult(intent, AlldynamicFragment.RESULT_FOR_SEND_DYNAMIC);


            /*Intent intent = new Intent();
            intent.setClass(CommentListActivity.this, DynamicCommentActivity.class);
            intent.putExtra("Category", "pl");
            intent.putExtra("vo", dynamic);
            startActivityForResult(intent, 0);*/
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == AlldynamicFragment.RESULT_FOR_SEND_DYNAMIC){
            if(isComment){
//                commentCount++;
//                tv_review_num.setText(commentCount+"评论");
                if(mCommentListFragment != null){
                    mCommentListFragment.loadData(false);
                }
            }else{
//                forwardCount++;
//                tv_transmit_num.setText(forwardCount+"转发");
                if(mForwardListFragment != null){
                    mForwardListFragment.loadData(false);
                }
            }
        }
    }

    private void initView(){
        dynamic = (Dynamic)getIntent().getSerializableExtra("vo");
//        xListView=(ListView)findViewById(R.id.commentlist_listview);
        tv_transmit_num = (TextView) findViewById(R.id.tv_transmit_num);
        tv_review_num = (TextView) findViewById(R.id.tv_review_num);

        forwardCount = dynamic.getTransmit();
        commentCount = dynamic.getReview();
        tv_transmit_num.setText(dynamic.getTransmit()+"转发");
        tv_review_num.setText(dynamic.getReview()+"评论");

    }

    private void setListener(){
        tv_transmit_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                if(mForwardListFragment == null){
                    mForwardListFragment = new ForwardListFragment(dynamic);
                    mFragmentTransaction.add(R.id.fragment, mForwardListFragment);
                }else{
                    mFragmentTransaction.show(mForwardListFragment);
                }
                if(mCommentListFragment != null){
                    mFragmentTransaction.hide(mCommentListFragment);
                }
                mFragmentTransaction.commit();
                tv_transmit_num.setTextColor(getResources().getColor(android.R.color.black));
                tv_review_num.setTextColor(getResources().getColor(android.R.color.darker_gray));
                isComment = false;
            }
        });

        tv_review_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
                if(mCommentListFragment == null){
                    mCommentListFragment = new CommentListFragment(dynamic);
                    mFragmentTransaction.add(R.id.fragment, mCommentListFragment);
                }else{
                    mFragmentTransaction.show(mCommentListFragment);
                }
                if(mForwardListFragment != null){
                    mFragmentTransaction.hide(mForwardListFragment);
                }
                mFragmentTransaction.commit();
                tv_transmit_num.setTextColor(getResources().getColor(android.R.color.darker_gray));
                tv_review_num.setTextColor(getResources().getColor(android.R.color.black));
                isComment = true;
            }
        });
    }

    public void onLoad(int dataSize, int type){
        switch (type){
            case 0:
                tv_transmit_num.setText(dataSize+"转发");
                break;
            case 1:
                tv_review_num.setText(dataSize+"评论");
                break;
        }
    }

    @Override
    public void finish() {
        setResult(105);
        super.finish();
    }

    /* private void initData(){
        CommentListRequestListener sendwordRequestListener = new CommentListRequestListener(this);
        CommetListRequest schoolRecipeRequest=new CommetListRequest(
                Comment.Model.class,this,dynamic.get_id(),"50");
        spiceManager.execute(schoolRecipeRequest, schoolRecipeRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }*/

    /**
     * 跟新ui
     * */
   /* public void UpDataUI(Comment.Model model){
        if(model==null&&comments.size()==0){
            LinearLayout linearLayout =(LinearLayout)findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.VISIBLE);
            TextView bottom_num = (TextView)findViewById(R.id.bottom_num);
            bottom_num.setText("暂无信息");
        }else{
            comments.clear();
            comments.addAll(model.getData());
            if(dynamicAdapter==null){
                String[] resKeys=new String[]{"getphotoImage","getUsername","getCreatdate2"};
                int[] reses=new int[]{R.id.comment_photo,R.id.comment_name,R.id.comment_time};
                dynamicAdapter = new CommentListAdapter(this, comments,R.layout.layout_commentitem,resKeys,reses, dynamic, comments);
                xListView.setAdapter(dynamicAdapter);
            }else{
                dynamicAdapter.notifyDataSetChanged();
            }
            LinearLayout linearLayout =(LinearLayout)findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.GONE);
        }
    }*/
}
