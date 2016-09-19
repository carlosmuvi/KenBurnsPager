package com.minube.kenburnspager.pageradapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlosmuvi on 19/09/16.
 */

public class KenBurnsPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitles = new ArrayList<>();

    public KenBurnsPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override public int getCount() {
        return fragmentList.size();
    }

    public void addPageFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitles.add(title);
        notifyDataSetChanged();
    }

    @Override public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }
}