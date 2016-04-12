package user.com.foodexuserui;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.Application;
import android.app.FragmentManager;
import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.ActionBar;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

//import com.github.florent37.materialviewpager.MaterialViewPager;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import pl.rspective.pagerdatepicker.adapter.DatePagerFragmentAdapter;
import pl.rspective.pagerdatepicker.adapter.DefaultDateAdapter;
import pl.rspective.pagerdatepicker.model.DateItem;
import pl.rspective.pagerdatepicker.view.DateRecyclerView;
import pl.rspective.pagerdatepicker.view.RecyclerViewInsetDecoration;
import user.com.commons.CustomViewPager;
import user.com.commons.TabHostListener;
import user.com.commons.TabPagerAdapter;

public class Plan1 extends AppCompatActivity {

    CustomViewPager Tab;
    CustomViewPager customViewPager;
    TabPagerAdapter TabAdapter;
    ActionBar actionBar;
    EditText plan1Date;
    Button prevDate;
    Button nextDate;
    Calendar mcurrentDate = Calendar.getInstance();
    int hot_number = 5;
    TextView count = null;

    String Changeddate = null;
    String datePickerDate = null;
    Calendar c = Calendar.getInstance();
    java.util.Date newDate = new Date(c.getTimeInMillis());
    Date maxDate = null;
    SimpleDateFormat sdf = new SimpleDateFormat("d-M-yyyy");
    pl.rspective.pagerdatepicker.view.DateRecyclerView dateList = null;
    ViewPager viewPagerDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan1);

        //TabHost tabHost = getTabHost();

        SharedPreferences itemCountInfo = this.getSharedPreferences("itemCount", 0);
        SharedPreferences.Editor itemCounteditor = itemCountInfo.edit();
        itemCounteditor.clear();
        itemCounteditor.commit();

        final FragmentTabHost tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        tabHost.addTab(
                tabHost.newTabSpec("Breakfast").setIndicator("Breakfast", null),
                Breakfast.class, null);
        tabHost.addTab(
                tabHost.newTabSpec("Lunch").setIndicator("Lunch", null),
                Lunch.class, null);
        tabHost.addTab(
                tabHost.newTabSpec("Dinner").setIndicator("Dinner", null),
                Dinner.class, null);

        final String foodCourse = getIntent().getExtras().getString("Course").toString();

        if (foodCourse.equalsIgnoreCase("Breakfast")) {
            tabHost.setCurrentTab(0);
        } else if (foodCourse.equalsIgnoreCase("Lunch")) {
            tabHost.setCurrentTab(1);
        } else if (foodCourse.equalsIgnoreCase("Dinner")) {
            tabHost.setCurrentTab(2);
        }else{
            tabHost.setCurrentTab(0);
        }

        //Tab = (CustomViewPager)findViewById(R.id.pager1);
        //Tab.setPagingEnabled(true);

        //tabHost.setOnTabChangedListener(new TabHostListener(this.getApplicationContext(), tabHost));

        //tabHost.addTab(breakFastSpec);
        //tabHost.addTab(lunchSpec);
        //tabHost.addTab(dinnerSpec);

        final View view = View.inflate(this.getApplicationContext(), R.layout.activity_plan1, null);

        /*TabHost tabHost = new TabHost(getApplicationContext());
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.tabcontent);
        TabWidget tabWidget = new TabWidget(getApplicationContext());
        TabHost.TabSpec tabSpec = null;

        // setNewTab(context, tabHost, tag, title, icon, contentID);
        this.setNewTab(this, tabHost, "tab1", R.string.title_activity_breakfast, android.R.drawable.star_on, R.id.linearLayout4);
        this.setNewTab(this, tabHost, "tab2", R.string.title_activity_lunch, android.R.drawable.star_on, R.id.linearLayout5);
        this.setNewTab(this, tabHost, "tab3", R.string.title_activity_dinner, android.R.drawable.star_on, R.id.linearLayout6);*/

        /*final TabHost tabHost = (TabHost)findViewById(R.id.tabHost2);
        TabWidget tabWidget = tabHost.getTabWidget();
        tabHost.*/

        //final String imageBtn = getIntent().getStringExtra("from");

        dateList = (pl.rspective.pagerdatepicker.view.DateRecyclerView) findViewById(R.id.date_list);
        viewPagerDate = (ViewPager) findViewById(R.id.pagerDate);

        Calendar c = Calendar.getInstance();
        Date start = new Date(c.getTimeInMillis());
        c.add(Calendar.DATE, 7);
        Date end = new Date(c.getTimeInMillis());

        dateList.addItemDecoration(new RecyclerViewInsetDecoration(this));
        dateList.addItemDecoration(new RecyclerViewInsetDecoration(this, R.dimen.date_card_insets_default));
        dateList.setAdapter(new DefaultDateAdapter(start, end, start));
        dateList.dispatchSystemUiVisibilityChanged(1);

        DatePagerFragmentAdapter fragmentAdapter = new DatePagerFragmentAdapter(getSupportFragmentManager(), dateList.getDateAdapter()) {
            @Override
            protected Fragment getFragment(int position, long date) {
                Fragment fragment = new Fragment();
                return fragment;
            }
        };

        viewPagerDate.setAdapter(fragmentAdapter);
        dateList.setPager(viewPagerDate);

        dateList.setDatePickerListener(new DateRecyclerView.DatePickerListener() {
            @Override
            public void onDatePickerItemClick(DateItem dateItem, int position) {
                String currDate = dateItem.getDate().toString();
                Toast.makeText(getApplicationContext(), currDate, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDatePickerPageSelected(int position) {
                //User changed date using swipe (left/right)
            }

            @Override
            public void onDatePickerPageStateChanged(int state) {
                //User changed page
            }

            @Override
            public void onDatePickerPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //User changed page
            }
        });

        //added to clear previously set foodOrders in FoodOrder
        /*final SharedPreferences foodOrderprefs = getSharedPreferences("FoodOrder", 0);
        SharedPreferences.Editor foodOrderEditor = foodOrderprefs.edit();
        foodOrderEditor.clear();
        foodOrderEditor.commit();*/




        /*final SharedPreferences prefs = getSharedPreferences("PlanData", 0);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();*/


        //actionBar.setDisplayShowTitleEnabled(true);
        //actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //TabAdapter = new TabPagerAdapter(getSupportFragmentManager());

        //Relates to CustomVIewPager class - also defined in Plan1 ViewPager field
        /*Tab = (CustomViewPager)findViewById(R.id.pager1);

        Tab.setOnPageChangeListener(

                new CustomViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {

                        actionBar.setSelectedNavigationItem(position);

                    }
                });

        Tab.setAdapter(TabAdapter);

        //disables the swiping between fragments in Plan1 Activity
        Tab.setPagingEnabled(true);

        //actionBar = getActionBar();
        //Enable Tabs on Action Bar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                //Tab click enabled ActionBar being restricted..
                Tab.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }
        };
*/
        //Add New Tab
        //actionBar.addTab(actionBar.newTab().setText("Breakfast").setTabListener(tabListener));
        //actionBar.addTab(actionBar.newTab().setText("Lunch").setTabListener(tabListener));
        //actionBar.addTab(actionBar.newTab().setText("Dinner").setTabListener(tabListener));

/*
        plan1Date = (EditText)findViewById(R.id.plan1DatePicker);
        plan1Date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker

                System.out.print("Inside Date Picker");

                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(Plan1.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                plan1Date.setText(date);

                                datePickerDate = plan1Date.getText().toString();
                                Changeddate = datePickerDate;

                                SharedPreferences.Editor dateEditor = prefs.edit();
                                dateEditor.putString("planDate",Changeddate);
                                dateEditor.commit();

                                try {
                                    c.setTime(sdf.parse(datePickerDate));
                                    newDate = sdf.parse(datePickerDate);
                                } catch (ParseException Ex) {
                                    Ex.getMessage();
                                }

                                if (datePickerDate.equals(sdf.format(newDate))) {
                                    prevDate.setEnabled(false);
                                    nextDate.setEnabled(true);
                                }

                                Calendar c1 = Calendar.getInstance();
                                c1.setTime(newDate);
                                prefs.edit();

                                switch (imageBtn) {
                                    case "plan1btn":
                                        c1.add(Calendar.DATE, 0);
                                        maxDate = new Date(c1.getTimeInMillis());
                                        editor.putString("date1",sdf.format(maxDate));
                                        editor.commit();
                                        break;
                                    case "plan2btn":
                                        c1.add(Calendar.DATE, +1);
                                        maxDate = new Date(c1.getTimeInMillis());
                                        editor.putString("date2",sdf.format(maxDate));
                                        editor.commit();
                                        break;
                                    case "plan5btn":
                                        c1.add(Calendar.DATE, +4);
                                        maxDate = new Date(c1.getTimeInMillis());
                                        editor.putString("date5",sdf.format(maxDate));
                                        editor.commit();
                                        break;
                                    case "plan7btn":
                                        c1.add(Calendar.DATE, +6);
                                        maxDate = new Date(c1.getTimeInMillis());
                                        editor.putString("date7",sdf.format(maxDate));
                                        editor.commit();
                                        break;
                                }
                            }
                        }, mYear, mMonth, mDay);

                mDatePicker.getDatePicker().getSpinnersShown();
                mDatePicker.getDatePicker().setMinDate(mcurrentDate.getTimeInMillis());
                mDatePicker.setTitle("Select Food Delivery Date !!");
                mDatePicker.show();

            }
        });
*/

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plan1, menu);

        MenuItem menuItem = menu.findItem(R.id.cartIcon);

        final View viewNew = View.inflate(this.getApplicationContext(), R.layout.cartaddition, null);
        TextView count = (TextView) viewNew.findViewById(R.id.count);
        String itemCount = (String) count.getText();

        menuItem.setIcon(buildCounterDrawable(Integer.parseInt(itemCount), R.drawable.cart_icon));

        return true;
    }*/

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plan1, menu);

        MenuItem menuItem = menu.findItem(R.id.cartIcon);

        final View viewNew = View.inflate(this.getApplicationContext(), R.layout.cartaddition, null);
        TextView count = (TextView) viewNew.findViewById(R.id.count);
        int itemCount = Integer.parseInt(count.getText().toString());

        menuItem.setIcon(buildCounterDrawable(itemCount, R.drawable.cart_icon));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    protected Drawable buildCounterDrawable(int count, int backgroundImageId) {

        int newCount = 0;
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.cartaddition, null);

        //if (count == 0) {
        //View counterTextPanel = view.findViewById(R.id.counterValuePanel);
        //counterTextPanel.setVisibility(View.GONE);
        //} else {
        /*TextView textView = (TextView) view.findViewById(R.id.count);
        textView.setText("" + count);*/
//        }

        TextView textView = (TextView) view.findViewById(R.id.count);

        SharedPreferences itemCountInfo = getSharedPreferences("itemCount", 0);
        String count1 =  itemCountInfo.getString("countOverall", "");

        if(!count1.equalsIgnoreCase("")) {
            newCount = Integer.parseInt(count1);
            textView.setText(String.valueOf(newCount));
        }
        else {
            String count2 = textView.getText().toString();
            newCount = Integer.parseInt(count2)+1;
            textView.setText(String.valueOf(newCount));
        }

        SharedPreferences.Editor itemCounteditor = itemCountInfo.edit();
        itemCounteditor.putString("countOverall", String.valueOf(newCount));
        itemCounteditor.commit();

        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(getResources(), bitmap);

    }

    public void refreshActionBar(Activity activity){

        activity.invalidateOptionsMenu();

    }


    /*public void selectFragment(int position)
    {
        Tab.setCurrentItem(position, false);
    }

    public void selectFragmentInfo(int position, String foodItemName)
    {
        Tab.setCurrentItem(position, false);

    }*/

    /*public void getNextDate(String dateNew)
    {
        final SharedPreferences imgandDate = this.getSharedPreferences("PlanData", 0);
        EditText plan1DateDinner = (EditText) findViewById(R.id.plan1DatePicker);
        java.util.Date nextDateDinner = null;
        String datecheckNext = imgandDate.getString("planDate", "");

        try {
            nextDateDinner = sdf.parse(dateNew);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c1 = Calendar.getInstance();
        c1.setTime(nextDateDinner);
        c1.add(Calendar.DATE, +1);

        try {
            plan1DateDinner.setText(sdf.format(c1.getTime()));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        selectFragment(0);
    }

    public String getDPdate() {

        String dpDate = plan1Date.getText().toString();
        return dpDate;

    }*/

}