package com.mx.codehappy.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mx.codehappy.R;
import com.mx.codehappy.adapter.MyPagerAdapter;
import com.mx.codehappy.cache.CoderfunCache;
import com.mx.codehappy.fragment.ReadFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReadActivity extends AppCompatActivity {


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
    private ArrayList<Fragment> mFragments;
    private final String[] mTitles = {"Android", "iOS", "前端", "拓展资源"};
    private int numToSetCurrentItem=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        ButterKnife.bind(this);

        CoderfunCache.ISBACKFROMWEBORIMAGE=true;
        numToSetCurrentItem=getIntent().getIntExtra("numToSetCurrentItem",0);
        initToolBar();
        initFragments();
        initViewPager();
        initTabLayout();

        mVp.setCurrentItem(numToSetCurrentItem);
    }

    private void initTabLayout() {

        mTabLayout.setupWithViewPager(mVp);
    }

    private void initViewPager() {

        mVp.setOffscreenPageLimit(4);
        mVp.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),mFragments,mTitles));
    }

    private void initFragments() {

        mFragments=new ArrayList<>();
        for(String title:mTitles){
            mFragments.add(ReadFragment.getInstance(title));
        }

    }

    private void initToolBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        getSupportActionBar().setTitle("分类阅读");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
