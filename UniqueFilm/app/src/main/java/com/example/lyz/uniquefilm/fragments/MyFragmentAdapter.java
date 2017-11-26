package com.example.lyz.uniquefilm.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyz on 17-11-19.
 */

public class MyFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> list;
    public MyFragmentAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.list=list;
        // TODO Auto-generated constructor stub
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

}
