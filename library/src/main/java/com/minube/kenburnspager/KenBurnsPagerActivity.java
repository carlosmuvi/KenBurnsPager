package com.minube.kenburnspager;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.StringRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.minube.kenburnspager.listener.AppBarStateChangeListener;
import com.minube.kenburnspager.pageradapter.KenBurnsPagerAdapter;
import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

public abstract class KenBurnsPagerActivity extends AppCompatActivity {

    private KenBurnsView headerImageView;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private CoordinatorLayout coordinatorLayout;
    private AppBarLayout appBarLayout;
    private KenBurnsPagerAdapter kenBurnsPagerAdapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ken_burns_pager);
        bindViews();

        initAppbarLayout();
        initToolbar();
        initViewPager();
        initTabLayout();
        initCollapsingToolbar();
    }

    private void initAppbarLayout() {
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override public void onStateChanged(AppBarLayout appBarLayout, State state) {
                KenBurnsPagerActivity.this.onCollapsingStateChanged(state);
            }
        });
    }

    protected abstract void onCollapsingStateChanged(AppBarStateChangeListener.State state);

    protected abstract String getHeaderTitle();

    //VIEW SETUP METHODS

    private void bindViews() {
        headerImageView = (KenBurnsView) findViewById(R.id.header_image);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getHeaderTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initCollapsingToolbar() {
        collapsingToolbarLayout.setTitleEnabled(true);
    }

    private void initTabLayout() {
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initViewPager() {
        kenBurnsPagerAdapter = new KenBurnsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(kenBurnsPagerAdapter);
    }

    // EXPOSED METHODS

    public void addTabFragment(@StringRes int tabTitle, Fragment tabFragment) {
        addTabFragment(getResources().getString(tabTitle), tabFragment);
    }

    public void addTabFragment(String tabTitle, Fragment tabFragment) {
        tabLayout.addTab(tabLayout.newTab().setText(tabTitle));
        kenBurnsPagerAdapter.addPageFragment(tabFragment, tabTitle);
    }

    public void setHeaderImage(String imageUrl) {
        Glide.with(this).load(imageUrl).asBitmap().into(new BitmapImageViewTarget(headerImageView) {
            @Override public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                super.onResourceReady(resource, glideAnimation);
                headerImageView.setImageBitmap(resource);
                Palette.generateAsync(resource, new Palette.PaletteAsyncListener() {
                    @Override public void onGenerated(Palette palette) {
                        int vibrantColor =
                            palette.getVibrantColor(getResources().getColor(android.R.color.holo_blue_dark));
                        int vibrantDarkColor =
                            palette.getDarkVibrantColor(getResources().getColor(android.R.color.holo_green_light));
                        collapsingToolbarLayout.setContentScrimColor(vibrantColor);
                        setTaskBarColored(vibrantDarkColor);
                        collapsingToolbarLayout.setStatusBarScrimColor(vibrantDarkColor);
                    }
                });
            }

            @Override public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
            }
        });
    }

    public void setHeaderLayout(View headerLayoutView) {
        coordinatorLayout.addView(headerLayoutView);
    }

    private void setTaskBarColored(@ColorInt int vibrantDarkColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //status bar height
            int statusBarHeight = getStatusBarHeight();

            View view = new View(this);
            view.setLayoutParams(
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.getLayoutParams().height = statusBarHeight;
            ((ViewGroup) w.getDecorView()).addView(view);
            view.setBackgroundColor(vibrantDarkColor);
        }
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
