package user.com.commons;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import user.com.foodexuserui.Breakfast;
import user.com.foodexuserui.Dinner;
import user.com.foodexuserui.Lunch;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Fragment getItem(int i) {

        Fragment fragment = new Fragment();

        switch (i) {
            case 0:
                fragment = new Breakfast();
                break;
            case 1:
                fragment = new Lunch();
                break;
            case 2:
                fragment = new Dinner();
                break;
            default:
                return null;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 3; //No of Tabs
    }

}