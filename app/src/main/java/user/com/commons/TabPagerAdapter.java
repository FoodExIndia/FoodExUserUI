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
        /*switch (i) {
            case 0:
                return new Breakfast();
            case 1:
                return new Lunch();
            case 2:
                return new Dinner();
        }*/
        return null;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 3; //No of Tabs
    }

}