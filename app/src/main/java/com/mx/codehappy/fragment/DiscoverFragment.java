package com.mx.codehappy.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mx.codehappy.R;
import com.mx.codehappy.adapter.GirlyAdapter;
import com.mx.codehappy.adapter.PartAdapter;
import com.mx.codehappy.adapter.RealAdapter;
import com.mx.codehappy.constant.CoderfunKey;
import com.mx.codehappy.model.DataResults;
import com.mx.codehappy.model.Results;
import com.mx.codehappy.retorfit.CoderfunSingle;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends Fragment implements SwipyRefreshLayout.OnRefreshListener {


    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipyrefreshlayout)
    SwipyRefreshLayout mSwipyrefreshlayout;
    private String mTitle;
    public static final String ARG_TITLE = "title";
    public static  int FRESH_GANHUO_TIME=4;
    public static  int fi_num= CoderfunKey.FI_NUM;
    public static  int gh_num=CoderfunKey.GH_NUM;
    public static  int mz_num=CoderfunKey.MZ_NUM;
    public static  int NOM_PAGE_FI=1;
    public static  int NOM_PAGE_GH=1;
    public static  int NOM_PAGE_MZ=1;

    private List<Results>part_list=new ArrayList<>();
    private List<Results>ganhuo_list;
    private List<List<Results>>ganhuo_real_list=new ArrayList<>();
    private List<Results>girly_list=new ArrayList<>();
    
    private GirlyAdapter mGirlyAdapter;
    private RealAdapter mRealAdapter;
    private PartAdapter mPartAdapter;


    public static DiscoverFragment getInstance(String title) {

        DiscoverFragment discoverFragment = new DiscoverFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        discoverFragment.setArguments(bundle);
        return discoverFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mTitle = bundle.getString(ARG_TITLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        ButterKnife.bind(this, view);

        initRecyclerView();
        initSwipeRefreshLayout();
        
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
  

        //TODO 先不要从数据库获取
        loadData(true);


    }

    private void loadData(boolean isTop) {
        switch (mTitle){
            case "首页":
                if(isTop){
                    NOM_PAGE_FI=1;
                }
                getDataResults("all",fi_num,NOM_PAGE_FI,isTop);
                break;
            case "干货":
                FRESH_GANHUO_TIME=0;
                ganhuo_real_list.clear();
                getDataResults("Android",gh_num,NOM_PAGE_GH,isTop);
                getDataResults("iOS",gh_num,NOM_PAGE_GH,isTop);
                getDataResults("前端",gh_num,NOM_PAGE_GH,isTop);
                getDataResults("拓展资源",gh_num,NOM_PAGE_GH,isTop);
                break;
            case "妹纸":
                if(isTop){
                    NOM_PAGE_MZ=1;
                }
                getDataResults("福利",mz_num,NOM_PAGE_MZ,isTop);
                break;
        }
    }

   

    private void initRecyclerView() {
        switch (mTitle){
            case "首页":
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
                mPartAdapter=new PartAdapter(getActivity(),null);
                mRecyclerView.setAdapter(mPartAdapter);
                break;
            case "干货":
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
                mRealAdapter=new RealAdapter(getActivity(),null);
                mRecyclerView.setAdapter(mRealAdapter);
                break;
            case "妹纸":
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                mGirlyAdapter=new GirlyAdapter(getActivity(),null);
                mRecyclerView.setAdapter(mGirlyAdapter);
                break;
        }

    }

    private void initSwipeRefreshLayout() {
        
        mSwipyrefreshlayout.setColorSchemeColors(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mSwipyrefreshlayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
        mSwipyrefreshlayout.setOnRefreshListener(this);

    }


    private void getDataResults(String type, int number, int page, boolean isTop) {

        CoderfunSingle.getInstance().getDataResults(type,number,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DataResults>() {
                    @Override
                    public void onCompleted() {
                        Log.i("frag","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("frag", "onError: " + e.getMessage(), e);
                        Snackbar.make(mRecyclerView,"网络不顺畅嘞,更新不了数据啦",Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(DataResults dataResults) {
                        if(dataResults.isError()){
                            Snackbar.make(mRecyclerView,"啊擦，服务器出问题啦",Snackbar.LENGTH_SHORT).show();
                        }else{
                            if(mTitle.equals("干货")){
                                ganhuo_list=new ArrayList<Results>();
                                ganhuo_list.addAll(dataResults.getResults());
                                ganhuo_real_list.add(ganhuo_list);
                                FRESH_GANHUO_TIME++;
                            }
                        }
                       //TODO 保存到数据库
                        
                        dealWithDataInRecyclerView(dataResults.getResults(),ganhuo_real_list,dataResults.getResults());
                        
                    }
                });
        

    }

    private void dealWithDataInRecyclerView(List<Results> part_list, List<List<Results>> ganhuo_real_list, List<Results> girly_list) {
        switch (mTitle){
            case "首页":
                mPartAdapter.getResults().addAll(part_list);
                mPartAdapter.notifyDataSetChanged();
                NOM_PAGE_FI++;
                break;
            case "干货":
                if(FRESH_GANHUO_TIME==4){
                    mRealAdapter.getRealResults().clear();;
                    mRealAdapter.getRealResults().addAll(ganhuo_real_list);
                    mRealAdapter.notifyDataSetChanged();
                    ganhuo_real_list.clear();
                }
                break;
            case "妹纸":
                mGirlyAdapter.getResults().addAll(girly_list);
                mGirlyAdapter.notifyDataSetChanged();
                NOM_PAGE_MZ++;
                break;
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mSwipyrefreshlayout.setRefreshing(false);
                    }
                });
        loadData(direction==SwipyRefreshLayoutDirection.TOP?true:false);
        
        
    }
}
