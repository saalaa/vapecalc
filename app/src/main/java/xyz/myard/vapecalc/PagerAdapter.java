package xyz.myard.vapecalc;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class PagerAdapter extends FragmentPagerAdapter {
    CalculatorFragment calculator;

    PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;

        if (position == 0) {
            fragment = calculator = new CalculatorFragment();
        }
        else {
            fragment = new BatteriesFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
