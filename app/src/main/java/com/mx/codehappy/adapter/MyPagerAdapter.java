package com.mx.codehappy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by boobooL on 2016/5/3 0003
 * Created 邮箱 ：boobooMX@163.com
 */
public class MyPagerAdapter  extends FragmentPagerAdapter {
    private FragmentManager mManager;
    private ArrayList<Fragment>mFragments=new ArrayList<>();
    private String[] mTitles;

    public MyPagerAdapter(FragmentManager fm,  ArrayList<Fragment> fragments, String[] titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }



    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
