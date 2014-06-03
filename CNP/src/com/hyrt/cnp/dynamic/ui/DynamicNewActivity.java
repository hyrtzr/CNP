package com.hyrt.cnp.dynamic.ui;

import android.os.Bundle;
import android.support.v7.internal.view.SupportMenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hyrt.cnp.R;
import com.hyrt.cnp.dynamic.adapter.DynamicAddphotoAdapter;
import com.jingdong.common.frame.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GYH on 2014/3/28.
 */
public class DynamicNewActivity extends BaseActivity{
    private MenuItem sendbtn;

    private DynamicAddphotoAdapter dynamicAddphotoAdapter;

    private List<Integer> list= new ArrayList<Integer>();

    private GridView gridview_dynamic_addphoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamicnew);
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
        list.add(R.drawable.cnp_newphoto);
        dynamicAddphotoAdapter=new DynamicAddphotoAdapter(list,this);
        gridview_dynamic_addphoto = (GridView)findViewById(R.id.gridview_dynamic_addphoto);
        gridview_dynamic_addphoto.setAdapter(dynamicAddphotoAdapter);
        gridview_dynamic_addphoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==list.size()-1){
                }
            }
        });
    }
}
