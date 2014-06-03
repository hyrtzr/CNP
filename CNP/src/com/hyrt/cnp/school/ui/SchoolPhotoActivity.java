package com.hyrt.cnp.school.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.widget.TextView;

import com.hyrt.cnp.R;
import com.hyrt.cnp.school.fragment.Fragmentphoto;
import com.jingdong.common.frame.BaseActivity;

import java.util.ArrayList;

/**
 * Created by GYH on 14-1-10.
 */

public class SchoolPhotoActivity extends BaseActivity implements ActionBar.TabListener {

    private String[] str = new String[]{"园所风采", "活动剪影", "宝宝作品"};

    private AppSectionsPagerAdapter mAppSectionsPagerAdapter = null;

    private static int mSid = -1;

    private ViewPager mViewPager;
    public static ArrayList<Fragmentphoto> pages = new ArrayList<Fragmentphoto>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolphoto);

        Intent intent = getIntent();
        mSid = intent.getIntExtra("sid", -1);

        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < 3; i++) {
            ActionBar.Tab tab = actionBar.newTab();
            tab.setText(str[i]);
            tab.setTabListener(this);
            actionBar.addTab(tab);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void initTitleview() {
        super.initTitleview();
        TextView textView = (TextView) viewTitleBar.findViewById(R.id.action_bar_title_text);
        textView.setText("活动剪影");
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }


    private static class AppSectionsPagerAdapter extends FragmentPagerAdapter {


        public String[] textphoto = {"校内活动", "示范园", "校内活动", "示范园"};
        public int[] imageResId1 = new int[]{R.drawable.schoolphoto1, R.drawable.schoolphoto2
                , R.drawable.schoolphoto3, R.drawable.schoolphoto4};
        public int[] imageResId2 = new int[]{R.drawable.schoolphoto2, R.drawable.schoolphoto3
                , R.drawable.schoolphoto3, R.drawable.schoolphoto4};
        public int[] imageResId3 = new int[]{R.drawable.schoolphoto1, R.drawable.schoolphoto2
                , R.drawable.schoolphoto1, R.drawable.schoolphoto2};

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragmentphoto page = null;
            if (pages.size() > position) {
                page = pages.get(position);
            } else {
                page = new Fragmentphoto(position, mSid);
                pages.add(page);
            }
            page.mSid = mSid;
            return page;
        }

        @Override
        public int getCount() {
            return 3;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }

    @Override
    public void finish() {
        super.finish();
        pages = new ArrayList<Fragmentphoto>();
    }
}
