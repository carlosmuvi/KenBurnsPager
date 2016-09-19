package com.minube.kenburnspager;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.minube.kenburnspager.pageradapter.KenBurnsPagerAdapter;

public class KenBurnsPagerActivity extends AppCompatActivity {

    private KenBurnsView headerImageView;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private KenBurnsPagerAdapter kenBurnsPagerAdapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ken_burns_pager);
        bindViews();

        initToolbar();
        initViewPager();
        initTabLayout();
        initCollapsingToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Parallax Tabs");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initCollapsingToolbar() {
        collapsingToolbarLayout.setTitleEnabled(false);
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

    private void bindViews() {
        headerImageView = (KenBurnsView) findViewById(R.id.header_image);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

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
                        collapsingToolbarLayout.setStatusBarScrimColor(vibrantDarkColor);
                    }
                });
            }

            @Override public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
            }
        });
    }
}
