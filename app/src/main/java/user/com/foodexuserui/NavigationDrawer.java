package user.com.foodexuserui;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import user.com.Entities.MenuBean;
import user.com.commons.CustomPagerAdapter;
import user.com.foodexuserui.NavDrawerPages.MyAccount;
import user.com.foodexuserui.R;

public class NavigationDrawer extends ActionBarActivity implements
        NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the
     * navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private static final float BITMAP_SCALE = 0.4f;
    private static final float BLUR_RADIUS = 7.5f;

    /**
     * Used to store the last screen title. For use in
     * {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar acb = getSupportActionBar();
        acb.setIcon(R.drawable.img_profile);
        setContentView(R.layout.activity_navdraw);

        //added to set breakfast list items
        List<MenuBean> listMenuBean = new ArrayList<MenuBean>();
        MenuBean bean1 = new MenuBean();
        bean1.setFoodKey(101);
        bean1.setItemName("Idly");
        bean1.setItemPrice(5.00);
        bean1.setCourseFlag(1);

        MenuBean bean2 = new MenuBean();
        bean2.setFoodKey(102);
        bean2.setItemName("Dosa");
        bean2.setItemPrice(5.00);
        bean2.setCourseFlag(1);

        MenuBean bean3 = new MenuBean();
        bean3.setFoodKey(103);
        bean3.setItemName("Poori");
        bean3.setItemPrice(5.00);
        bean3.setCourseFlag(1);

        MenuBean bean4 = new MenuBean();
        bean4.setFoodKey(104);
        bean4.setItemName("Chappathi");
        bean4.setItemPrice(5.00);
        bean4.setCourseFlag(1);

        MenuBean bean5 = new MenuBean();
        bean5.setFoodKey(105);
        bean5.setItemName("Pongal");
        bean5.setItemPrice(5.00);
        bean5.setCourseFlag(1);

        listMenuBean.add(bean1);
        listMenuBean.add(bean2);
        listMenuBean.add(bean3);
        listMenuBean.add(bean4);
        listMenuBean.add(bean5);

        SharedPreferences prefs = getSharedPreferences("MenuData", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        Gson json = new Gson();
        editor.putString("list1", json.toJson(listMenuBean));
        editor.commit();

        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (position == 0) {

            fragmentManager.beginTransaction()
                    .replace(R.id.container, HomeFragment.newInstance())
                    .commit();

        } else if (position == 1) {
            Intent i = new Intent(NavigationDrawer.this, MyAccount.class);
            startActivity(i);
            /*fragmentManager.beginTransaction()
                    .replace(R.id.container, MailFragment.newInstance())
                    .commit();*/
        } else if (position == 2) {
            Intent i = new Intent(NavigationDrawer.this, FoodExHome.class);
            startActivity(i);
            /*fragmentManager.beginTransaction()
                    .replace(R.id.container, SettingsFragment.newInstance())
                    .commit();*/
        }
        else if (position == 5) {

            SharedPreferences prefs = getSharedPreferences("UserData", 0);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.commit();

            Intent i = new Intent(NavigationDrawer.this, Login.class);
            startActivity(i);
            /*fragmentManager.beginTransaction()
                    .replace(R.id.container, SettingsFragment.newInstance())
                    .commit();*/
        }
    }

    //Get the title of current fragment
    public void onSectionAttached(int number) {
        System.out.println(mTitle);
        switch (number) {
            case 1:
                mTitle = "Home";
                break;
            case 2:
                mTitle = "My Account";
                break;
            case 3:
                mTitle = "Contact Us";
                break;
        }
    }

    //Change the title of page to current fragments title
    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
        actionBar.setHomeAsUpIndicator(R.drawable.img_drawer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.menu, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void myMethod(View view) {

        Intent intent=new Intent();

        /*switch(view.getId()) {
            case R.id.plan1btn:
                intent=new Intent(NavigationDrawer.this,Plan1.class);
                //startActivity(intent);
                startActivity(intent.putExtra("from", "plan1btn"));
                break;
            case R.id.plan2btn:
                intent=new Intent(NavigationDrawer.this,Plan1.class);
                //startActivity(intent);
                startActivity(intent.putExtra("from", "plan2btn"));
                break;
            case R.id.plan5btn:
                intent=new Intent(NavigationDrawer.this,Plan1.class);
                //startActivity(intent);
                startActivity(intent.putExtra("from", "plan5btn"));
                break;
            case R.id.plan7btn:
                intent=new Intent(NavigationDrawer.this,Plan1.class);
                //startActivity(intent);
                startActivity(intent.putExtra("from", "plan7btn"));
                break;
            default:*/
                intent=new Intent(view.getContext(),Plan1.class);
                //startActivity(intent);
                startActivity(intent);
                //break;

    }
}
