package com.example.sdaassign4_2019;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/*
 * viewPager adapter.
 * @author Chris Coughlan 2019
 */
public class ViewPageAdapter extends FragmentPagerAdapter {

    private Context context;

    ViewPageAdapter(FragmentManager fm, int behavior, Context nContext) {
        super(fm, behavior);
        context = nContext;
    }

    /*
    Runs the appropriate activity based on what tab is active.
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {

        Fragment fragment = new Fragment();

        //finds the tab position (note array starts at 0)
        position = position+1;

        //finds the fragment
        switch (position)
        {
            case 1:
                //code
                fragment = new Welcome();
                break;
            case 2:
                //code
                fragment = new BookList();
                break;
            case 3:
                //code
                fragment = new Settings();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    /*
    Sets the text on the tabs.
     */
    @Override
    public CharSequence getPageTitle(int position) {
        position = position+1;

        CharSequence tabTitle = "";

        //finds the fragment
        switch (position)
        {
            case 1:
                //code
                tabTitle = context.getString(R.string.home_tab_text);
                break;
            case 2:
                //code
                tabTitle = context.getString(R.string.books_tab_text);
                break;
            case 3:
                //code
                tabTitle = context.getString(R.string.settings_tab_text);
                break;
        }

        return tabTitle;
    }
}
