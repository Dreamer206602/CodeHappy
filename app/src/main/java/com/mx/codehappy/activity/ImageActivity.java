package com.mx.codehappy.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mx.codehappy.R;
import com.mx.codehappy.utils.SaveImageUtils;
import com.mx.codehappy.utils.ShareUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.appbar)
    AppBarLayout mAppbar;
    @Bind(R.id.draweeview)
    SimpleDraweeView mDraweeview;
    private String url;
    private String desc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        url=getIntent().getStringExtra("url");
        desc=getIntent().getStringExtra("desc");


        initToolBar();
        initSimpleDraweeView();
    }

    private void initSimpleDraweeView() {
        GenericDraweeHierarchy hierarchy = mDraweeview.getHierarchy();
        hierarchy.setProgressBarImage(new ProgressBarDrawable());
        Uri uri=Uri.parse(url);
        mDraweeview.setImageURI(uri);
    }

    private void initToolBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        getSupportActionBar().setTitle("beautiful girls");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_share:
                ShareUtils.shareImage(this, SaveImageUtils.saveImage(url,desc,this));
                break;
            case R.id.action_save:
                SaveImageUtils.saveImage(url,desc,this);
                Toast.makeText(this,"图片已经保存了",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
