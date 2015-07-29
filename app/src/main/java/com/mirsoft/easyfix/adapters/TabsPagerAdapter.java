package com.mirsoft.easyfix.adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mirsoft.easyfix.common.OrderType;
import com.mirsoft.easyfix.fragments.MasterListFragment;
import com.mirsoft.easyfix.fragments.NewOrdersFragment;
import com.mirsoft.easyfix.fragments.UserOrderListFragment;

public class TabsPagerAdapter extends FragmentStatePagerAdapter {

    private final String[] TITLES;

    public TabsPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.TITLES = titles;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NewOrdersFragment();
            case 1:
                return new MasterListFragment();
            case 2:
                return new UserOrderListFragment();
            default:
                return new NewOrdersFragment();
        }
    }

    @Override
    public String getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }
}
