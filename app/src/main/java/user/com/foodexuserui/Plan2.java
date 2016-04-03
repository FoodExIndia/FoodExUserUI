package user.com.foodexuserui;

import android.app.DatePickerDialog;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import java.util.Calendar;
import user.com.commons.TabPagerAdapter;

public class Plan2 extends AppCompatActivity {
    ViewPager Tab;
    TabPagerAdapter TabAdapter;
    ActionBar actionBar;
    EditText plan2Date;
    Calendar mcurrentDate=Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan2);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Plan 2 Menu");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        TabAdapter = new TabPagerAdapter(getSupportFragmentManager());

        Tab = (ViewPager)findViewById(R.id.pager2);
        Tab.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {

                        actionBar.setSelectedNavigationItem(position);
                    }
                });

        Tab.setAdapter(TabAdapter);

        //actionBar = getActionBar();
        //Enable Tabs on Action Bar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                Tab.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }
        };

        //Add New Tab
        actionBar.addTab(actionBar.newTab().setText("Breakfast").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Lunch").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Dinner").setTabListener(tabListener));

        plan2Date = (EditText)findViewById(R.id.plan2DatePicker);
        plan2Date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker

                System.out.print("Inside Date Picker");

                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(Plan2.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        plan2Date.setText(date);
                    }
                }, mYear, mMonth, mDay);

                mDatePicker.getDatePicker().getSpinnersShown();
                mDatePicker.getDatePicker().setMinDate(mcurrentDate.getTimeInMillis());
                mDatePicker.setTitle("Select Food Delivery Date !!");
                mDatePicker.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plan2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_prev2:
                Toast.makeText(getApplicationContext(), "Prev Date Clicked", Toast.LENGTH_SHORT).show();
                actionBar.setTitle("Changed Date");
                return true;
            case R.id.action_next2:
                Toast.makeText(getApplicationContext(), "Next Date Clicked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

}