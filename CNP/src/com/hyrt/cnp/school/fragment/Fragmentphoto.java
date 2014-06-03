package com.hyrt.cnp.school.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Photo;
import com.hyrt.cnp.R;
import com.hyrt.cnp.school.adapter.PhotoImageAdapter;
import com.hyrt.cnp.school.request.SchoolPhotoListRequest;
import com.hyrt.cnp.school.requestListener.SchoolPhotoListRequestListener;
import com.hyrt.cnp.school.ui.SchoolPhotoActivity;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

import java.util.ArrayList;

/**
 * Created by GYH on 14-1-14.
 * 活动剪影
 */
public  class Fragmentphoto extends Fragment {
    private GridView gridview;
    private Context context;
    private PopupWindow popWin;
    private PhotoImageAdapter photoImageAdapter=null;
    private SchoolPhotoActivity activity;
    private int id;
    private Photo.Model model;
    private String pkind;
    public int mSid = -1;
    private ArrayList<String> imageurls=new ArrayList<String>();
    private View rootView;


    public Fragmentphoto (int id, int sid){
        this.context=getActivity();
        this.id=id;
        this.mSid = sid;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_fragment_schoolphoto, container, false);
        gridview = (GridView)rootView.findViewById(R.id.cnp_gridview);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                ((BaseActivity)getActivity()).showPop(gridview,((Photo)(photoImageAdapter.getItem(i))).getImagepics());
                ((BaseActivity)getActivity()).showPop2(gridview,imageurls,i,getActivity());
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(photoImageAdapter==null){
            loadphotoysfc();
        }else{
            gridview.setAdapter(photoImageAdapter);
        }
    }

    private void loadphotoysfc() {
        pkind=(id+1)+"";
        activity = (SchoolPhotoActivity)getActivity();
        SchoolPhotoListRequestListener sendwordRequestListener
                = new SchoolPhotoListRequestListener(getActivity(),id);
        SchoolPhotoListRequest schoolPhotoListRequest =new SchoolPhotoListRequest(Photo.Model.class, activity,pkind, mSid);
        activity.spiceManager.execute(schoolPhotoListRequest,schoolPhotoListRequest.getcachekey(),
                DurationInMillis.ONE_SECOND * 10,
                sendwordRequestListener.start());
    }

    public void initData(Photo.Model model){
        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.layout_bottom);
        TextView bottom_num = (TextView) rootView.findViewById(R.id.bottom_num);
        if(model == null){
            if (model == null || model.getData().size() == 0) {
                linearLayout.setVisibility(View.VISIBLE);
                bottom_num.setText("暂无信息");
            }
            return;
        }

        linearLayout.setVisibility(View.GONE);
        bottom_num.setText("");
        this.model=model;
        String[] resKeys=new String[]{"getImagethpath","getTitle"};
        int[] reses=new int[]{R.id.gridview_image,R.id.gridview_name};
        photoImageAdapter=new PhotoImageAdapter(activity,model.getData(),R.layout.layout_item_gridview_image,resKeys,reses);
        gridview.setAdapter(photoImageAdapter);
        for(int i=0;i<model.getData().size();i++){
            imageurls.add(model.getData().get(i).getImagepics());
        }
    }
}
