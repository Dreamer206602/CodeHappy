package com.mx.codehappy.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mx.codehappy.R;
import com.mx.codehappy.adapter.ReadAdapter;
import com.mx.codehappy.constant.CoderfunKey;
import com.mx.codehappy.model.DataResults;
import com.mx.codehappy.model.Results;
import com.mx.codehappy.retorfit.CoderfunSingle;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

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
public class ReadFragment extends Fragment implements SwipyRefreshLayout.OnRefreshListener {

    public static final String TITLE = "title";
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.swipyrefreshlayout)
    SwipyRefreshLayout mSwipeRefreshLayout;
    private String mTitle;
    private static  int read_num= CoderfunKey.READ_NUM;
    public static  int NOW_PAGE_READ=1;
    private ReadAdapter mReadAdapter;


    public static ReadFragment getInstance(String title) {
        ReadFragment readFragment = new ReadFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        readFragment.setArguments(bundle);
        return readFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getArguments().getString(TITLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_read, container, false);
        ButterKnife.bind(this, view);

        initRecyclerView();
        initSwipeRefreshLayout();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(true);
    }

    private void loadData(boolean isTap) {
        if(isTap){
            NOW_PAGE_READ=1;
        }
        getDataResults(mTitle,read_num,NOW_PAGE_READ,isTap);
    }

    private void getDataResults(String type, int read_num, int nowPageRead, final boolean isTap) {
        CoderfunSingle.getInstance().getDataResults(type,read_num,nowPageRead)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DataResults>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DataResults dataResults) {

                        if(dataResults.isError()){
                            Snackbar.make(mRecyclerView, "啊擦，服务器出问题啦", Snackbar.LENGTH_SHORT).show();
                        }else{
                            if(isTap){
                                clearAdapterResults();
                            }
                            dealWithDataInRecyclerView(dataResults.getResults());
                        }

                    }
                });

    }

    private void dealWithDataInRecyclerView(List<Results> results) {
        mReadAdapter.getResults().addAll(results);
        mReadAdapter.notifyDataSetChanged();
        NOW_PAGE_READ++;
    }

    private void clearAdapterResults() {
        mReadAdapter.getResults().clear();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mReadAdapter=new ReadAdapter(getContext(),null);
        mRecyclerView.setAdapter(mReadAdapter);

    }

    private void initSwipeRefreshLayout() {

        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        //mSwipeRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
        mSwipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {

        Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
                );
        loadData(direction==SwipyRefreshLayoutDirection.TOP?true:false);
    }
}
