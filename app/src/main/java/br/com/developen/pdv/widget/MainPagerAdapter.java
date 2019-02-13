package br.com.developen.pdv.widget;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainPagerAdapter extends FragmentPagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {

        super(fm);

    }

    public Fragment getItem(int position) {

        Fragment f = null;

        switch (position){

            case 0:

                f = MainTodayFragment.newInstance();

                break;

            case 1:

                f = MainTodayFragment.newInstance();

                break;

            case 2:

                f = MainTodayFragment.newInstance();

                break;

        }

        return f;

    }

    public int getCount() {

        return 3;

    }

}