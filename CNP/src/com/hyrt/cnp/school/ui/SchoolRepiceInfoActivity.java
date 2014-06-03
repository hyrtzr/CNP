package com.hyrt.cnp.school.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hyrt.cnp.base.account.model.Recipe;
import com.hyrt.cnp.base.account.model.RecipeInfo;
import com.hyrt.cnp.base.account.utils.StringUtils;
import com.hyrt.cnp.R;
import com.hyrt.cnp.school.adapter.RepiceInfoAdapter;
import com.hyrt.cnp.school.request.SchoolRecipeInfoRequest;
import com.hyrt.cnp.school.requestListener.SchoolRecipeInfoRequestListener;
import com.jingdong.common.frame.BaseActivity;
import com.octo.android.robospice.persistence.DurationInMillis;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by GYH on 14-1-10.
 */

public class SchoolRepiceInfoActivity extends BaseActivity implements ActionBar.TabListener {

    private static int num = 0;
    private AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repiceinfo);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadSendword();
    }

    private void initView() {
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

    }

    public void initData(RecipeInfo.Model2 model) {
        if (model == null) {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_bottom);
            linearLayout.setVisibility(View.VISIBLE);
            TextView bottom_num = (TextView) findViewById(R.id.bottom_num);
            bottom_num.setText("暂无信息");
        } else {
            actionBar.removeAllTabs();
            num = model.getData().size();
            for (int i = 0; i < model.getData().size(); i++) {
                ActionBar.Tab tab = actionBar.newTab();
                try {
                    String week = StringUtils.getWeekOfDate(model.getData().get(i).getRecipeDate2());
//                    if("星期六".equals(week) || "星期日".equals(week)){
//                        continue;
//                    }
                    tab.setText(week);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tab.setTabListener(this);
                actionBar.addTab(tab);
            }
            mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager(), model);
            mViewPager.setAdapter(mAppSectionsPagerAdapter);
        }

    }

    private void loadSendword() {
        Intent intent = getIntent();
        Recipe recipe = (Recipe) intent.getSerializableExtra("vo");
        SchoolRecipeInfoRequestListener sendwordRequestListener = new SchoolRecipeInfoRequestListener(this);
        SchoolRecipeInfoRequest schoolRecipeInfoRequest = new SchoolRecipeInfoRequest(RecipeInfo.Model2.class, this, recipe);
        spiceManager.execute(schoolRecipeInfoRequest, schoolRecipeInfoRequest.getcachekey(), DurationInMillis.ONE_SECOND * 10, sendwordRequestListener.start());
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());;
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }


    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {
        private RecipeInfo.Model2 model;
        private ArrayList<Fragmentrecipe> pages = new ArrayList<Fragmentrecipe>();

        public AppSectionsPagerAdapter(FragmentManager fm, RecipeInfo.Model2 model) {
            super(fm);
            this.model = model;
        }

        @Override
        public Fragment getItem(int position) {
            Fragmentrecipe page = null;
            if (pages.size() > position) {
                page = pages.get(position);
                if (page != null) {
                    return page;
                }
            }
            while (position>=pages.size()) {
                pages.add(null);
            }
            page = new Fragmentrecipe(model.getData().get(position));
            pages.set(position, page);
            return page;
        }

        @Override
        public int getCount() {
            return num;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }


    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy
     * text.
     */
    public static class Fragmentrecipe extends Fragment {
        ArrayList<String> foot = new ArrayList<String>();
        private String foottime;
        String[] footstime = new String[]{"早餐", "早餐配料", "加餐", "午餐", "午餐配料", "午点", "晚餐", "晚餐配料", "日营养量", "负责人"};

        public Fragmentrecipe(RecipeInfo model) {
            foot.add(model.getBreakfast());
            foot.add(model.getB_ingredients());
            foot.add(model.getAddfood());
            foot.add(model.getLunch());
            foot.add(model.getL_ingredients());
            foot.add(model.getLunchsnacks());
            foot.add(model.getDinner());
            foot.add(model.getD_ingredients());
            foot.add(model.getLevel());
            foot.add(model.getFooder());
            foottime = model.getRecipeDate2();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.layout_repiceinfo, container,
                    false);
            TextView textView = (TextView) rootView.findViewById(R.id.foottimetext);
            textView.setText(foottime);
            ListView listView = (ListView) rootView.findViewById
                    (R.id.schoolnotice_listview);
            RepiceInfoAdapter repiceInfoAdapter = new RepiceInfoAdapter(this.getActivity
                    (), footstime, foot);
            listView.setAdapter(repiceInfoAdapter);
            return rootView;
        }
    }
}
