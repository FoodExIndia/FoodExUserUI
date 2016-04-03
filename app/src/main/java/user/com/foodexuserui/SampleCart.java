package user.com.foodexuserui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import user.com.Entities.MenuBean;
import user.com.Entities.SubOrderBean;

public class SampleCart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sample_cart);
        final GridLayout rl = (GridLayout)findViewById(R.id.GridLayoutCart);

        Gson gson = new Gson();
        final SharedPreferences Cartprefs = getSharedPreferences("FoodOrder", 0);
        final SharedPreferences imgandDate = getSharedPreferences("PlanData", 0);
        String datecheck = imgandDate.getString("planDate", "");
        final SimpleDateFormat sdf = new SimpleDateFormat("d-M-yyyy");
        java.util.Date cartDate = null;

        ArrayList<SubOrderBean> cartOrderList = new ArrayList<SubOrderBean>();
        String cartOrder = Cartprefs.getString("SubOrderList", "");

        Type listType = new TypeToken<ArrayList<SubOrderBean>>() {
        }.getType();
        cartOrderList = gson.fromJson(cartOrder, listType);
        String count = null;
        String name = null;

        System.out.println("before sort Cart List : "+cartOrderList);

        Collections.sort(cartOrderList);

        System.out.println("after sort Cart List : " + cartOrderList);
        for (SubOrderBean bean : cartOrderList) {

            count = String.valueOf(bean.getFoodQuantity());
            name = String.valueOf(bean.getFoodName());

                TextView itemName = new EditText(this);
                itemName.setTextSize(20);
                itemName.setText(name);
                itemName.setTextColor(Color.BLACK);
                itemName.setVisibility(View.VISIBLE);
                itemName.setGravity(Gravity.CENTER);
                rl.addView(itemName);

                TextView itemCount = new EditText(this);
                itemCount.setTextSize(20);
                itemCount.setText(count);
                itemName.setTextColor(Color.BLACK);
                itemCount.setVisibility(View.VISIBLE);
                itemCount.setGravity(Gravity.CENTER);
                rl.addView(itemCount);
            }

        SharedPreferences.Editor itemsEditor = Cartprefs.edit();
        itemsEditor.clear();

        }

    }

