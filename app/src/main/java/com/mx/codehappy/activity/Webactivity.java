package com.mx.codehappy.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.mx.codehappy.R;
import com.mx.codehappy.cache.CoderfunCache;
import com.mx.codehappy.utils.BrowserUtils;
import com.mx.codehappy.utils.ShareUtils;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class WebActivity extends AppCompatActivity implements SwipyRefreshLayout.OnRefreshListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.appbar)
    AppBarLayout mAppbar;
    @Bind(R.id.loadingview)
    FrameLayout mLoadingview;
    @Bind(R.id.webview)
    WebView mWebview;
    @Bind(R.id.swipyrefreshlayout)
    SwipyRefreshLayout mSwipyrefreshlayout;
    private String url;
    private String desc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webactivity);
        ButterKnife.bind(this);
        CoderfunCache.ISBACKFROMWEBORIMAGE=true;
        url=getIntent().getStringExtra("url");
        desc=getIntent().getStringExtra("desc");

        initToolBar();
        initWebView();
        initSwipeRefreshLayout();
    }

    private void initSwipeRefreshLayout() {

        mSwipyrefreshlayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mSwipyrefreshlayout.setOnRefreshListener(this);

    }

    private void initWebView() {
        mWebview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress==100){
                    mLoadingview.setVisibility(View.GONE);
                    url=mWebview.getUrl();
                }else{
                    mLoadingview.setVisibility(View.VISIBLE);
                }
            }
        });
        mWebview.setWebViewClient(new WebViewClient());
        mWebview.getSettings().setBuiltInZoomControls(true);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setDomStorageEnabled(true);
        mWebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebview.getSettings().setAppCacheEnabled(true);

        mWebview.loadUrl(url);
    }

    private void initToolBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        getSupportActionBar().setTitle(desc);
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        Observable.timer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mSwipyrefreshlayout.setRefreshing(false);
                    }
                });
        mWebview.loadUrl(url);
        mLoadingview.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_share:
                ShareUtils.shareText(this,mWebview.getTitle()+" "+mWebview.getUrl()+"来自[趣刻]APP");
                break;
            case R.id.action_refresh:
                mWebview.reload();
                break;
            case R.id.action_copy:
                BrowserUtils.copyToClipBoard(this,mWebview.getUrl());
                break;
            case R.id.action_open_in_browser:
                BrowserUtils.openInBrowser(this,mWebview.getUrl());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction()==KeyEvent.ACTION_DOWN){
            if(keyCode==KeyEvent.KEYCODE_BACK){
                if(mWebview.canGoBack()){
                    mWebview.goBack();
                }else{
                    finish();
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mWebview!=null){
            mWebview.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mWebview!=null){
            mWebview.onResume();
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mWebview!=null){
            ViewGroup parent = (ViewGroup) mWebview.getParent();
            if (parent != null) {
                parent.removeView(mWebview);
            }
            mWebview.removeAllViews();
            mWebview.destroy();
        }
    }
}
