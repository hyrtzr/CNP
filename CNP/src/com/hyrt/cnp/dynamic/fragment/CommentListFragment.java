package com.hyrt.cnp.dynamic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyrt.cnp.base.account.model.Comment;
import com.hyrt.cnp.base.account.model.Dynamic;
import com.hyrt.cnp.base.view.XListView;
import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.adapter.CommentListAdapter;
import com.hyrt.cnp.dynamic.request.CommetListRequest;
import com.hyrt.cnp.dynamic.requestListener.CommentListRequestListener;
import com.hyrt.cnp.dynamic.ui.CommentListActivity;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;

import java.util.ArrayList;

/**
 * Created by Zoe on 2014-04-22.
 */
public class CommentListFragment extends Fragment{

    private View parentView;
    private XListView listview;
    private Dynamic dynamic;
    private ArrayList<Comment> comments =new ArrayList<Comment>();
    private CommentListAdapter dynamicAdapter;

    public String STATE;
    final public String REFRESH = "refresh";
    final private String ONLOADMORE = "onLoadMore";
    final private String HASDATA = "hasdata";
    private String more = "1";

    public CommentListFragment(Dynamic dynamic) {
        this.dynamic = dynamic;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        STATE = HASDATA;
        parentView = inflater.inflate(R.layout.fragment_list, null);
        findView();
        loadData(false);
        return parentView;
    }

    public void loadData(boolean isMore){
        CommentListRequestListener sendwordRequestListener = new CommentListRequestListener(getActivity());
        sendwordRequestListener.setListener(mCommentListener);
        CommetListRequest schoolRecipeRequest = null;
        if(isMore){
            if(comments.size() > 0){
                more = comments.get(comments.size()-1).getCreatdate();
                schoolRecipeRequest=new CommetListRequest(
                        Comment.Model.class,getActivity(),dynamic.get_id(),"50", more);
            }
        }else{
            STATE = REFRESH;
            schoolRecipeRequest=new CommetListRequest(
                    Comment.Model.class,getActivity(),dynamic.get_id(),"50");
        }

        if(schoolRecipeRequest != null){
            ((BaseActivity)getActivity()).spiceManager.execute(
                    schoolRecipeRequest,
                    schoolRecipeRequest.getcachekey(),
                    DurationInMillis.ONE_SECOND * 10,
                    sendwordRequestListener.start());
        }

    }

    private CommentListRequestListener.RequestListener mCommentListener = new CommentListRequestListener.RequestListener() {
        @Override
        public void onRequestSuccess(Comment.Model data) {
            UpDataUI(data);
        }

        @Override
        public void onRequestFailure(SpiceException e) {
            UpDataUI(null);
        }
    };

    public void UpDataUI(Comment.Model model){
        if (model == null && comments.size() == 0) {
            LinearLayout linearLayout = (LinearLayout) parentView.findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.VISIBLE);
            TextView bottom_num = (TextView) parentView.findViewById(R.id.bottom_num);
            bottom_num.setText("暂无信息");
        } else if (model == null) {
            Toast.makeText(getActivity(), "已经全部加载", Toast.LENGTH_SHORT).show();
        }else{
            if (STATE == null || STATE.equals(REFRESH)) {//如果正在刷新就清空
                comments.clear();
            }
            comments.addAll(model.getData());
            ((CommentListActivity)getActivity()).onLoad(comments.size(), 1);
            if(dynamicAdapter==null){
                String[] resKeys=new String[]{"getUsername","getCreatdate2"};
                int[] reses=new int[]{R.id.comment_name,R.id.comment_time};
                dynamicAdapter = new CommentListAdapter(
                        (BaseActivity)getActivity(),
                        comments,
                        R.layout.layout_commentitem,
                        resKeys,reses, dynamic, comments);
                listview.setAdapter(dynamicAdapter);
            }else{
                dynamicAdapter.notifyDataSetChanged();
            }
            LinearLayout linearLayout =(LinearLayout)parentView.findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.GONE);
        }
        STATE = "";//清空状态
    }

    private void findView(){
        listview = (XListView) parentView.findViewById(R.id.listview);
        listview.setPullLoadEnable(true);
        listview.setPullRefreshEnable(true);
        listview.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                if (STATE.equals(HASDATA) || STATE.equals(ONLOADMORE)) {
//                    Toast.makeText(BabayIndexActivity.this, "正在加载,请稍后!", Toast.LENGTH_SHORT).show();
                } else {
                    STATE = REFRESH;
                    more = "1";
//                    Toast.makeText(BabayIndexActivity.this,"正在刷新,请稍后!",Toast.LENGTH_SHORT).show();
                    loadData(false);
                }
                listview.stopRefresh();
            }

            @Override
            public void onLoadMore() {
                if (STATE.equals(HASDATA) || STATE.equals(REFRESH)) {
//                    Toast.makeText(BabayIndexActivity.this,"正在加载,请稍后!",Toast.LENGTH_SHORT).show();
                } else {
                    loadData(true);
//                    Toast.makeText(BabayIndexActivity.this,"onLoadMore",Toast.LENGTH_SHORT).show();
                }
                listview.stopLoadMore();
            }
        });
    }
}
