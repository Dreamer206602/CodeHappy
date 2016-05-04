package com.mx.codehappy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mx.codehappy.R;
import com.mx.codehappy.adapter.MyPagerAdapter;
import com.mx.codehappy.fragment.DiscoverFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.appbar)
    AppBarLayout mAppbar;
    @Bind(R.id.vp)
    ViewPager mVp;
    @Bind(R.id.main_content)
    CoordinatorLayout mMainContent;
    private String[] mTitles;
    private ArrayList<Fragment>mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initFragment();
        initViewPager();
        initTabLayout();

    }




    private void initData() {
        mTitles=getResources().getStringArray(R.array.titles);
        setSupportActionBar(mToolbar);

    }

    private void initFragment() {
        mFragments=new ArrayList<>();
        for (String title:mTitles){
                mFragments.add(DiscoverFragment.getInstance(title));
        }
    }
    private void initViewPager() {

        mVp.setOffscreenPageLimit(3);
        mVp.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),mFragments,mTitles));
    }
    private void initTabLayout() {
        mTabLayout.setupWithViewPager(mVp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_read:
                Intent intent=new Intent(this,ReadActivity.class);
                startActivity(intent);
                break;
            case R.id.action_about_me:
                Intent intent_about_me=new Intent(this,WebActivity.class);
                intent_about_me.putExtra("url","http://www.hugeterry.cn/about");
                startActivity(intent_about_me);
                break;
            case R.id.action_about:
                Intent intent_about=new Intent(this,AboutActivity.class);
                startActivity(intent_about);
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
