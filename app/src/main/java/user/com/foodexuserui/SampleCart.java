package user.com.foodexuserui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
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

import user.com.Entities.FinalSubOrderBean;
import user.com.Entities.MenuBean;
import user.com.Entities.SubOrderBean;
import user.com.foodexuserui.NavDrawerPages.MyAccount;

public class SampleCart extends AppCompatActivity {

    ActionBar actionBar;
    ArrayList<SubOrderBean> bfsubOrderBeanList = null;
    ArrayList<SubOrderBean> lnsubOrderBeanList = null;
    ArrayList<SubOrderBean> dnsubOrderBeanList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_cart);

        Button editOrder = (Button) findViewById(R.id.editOrder);

        final ArrayList<FinalSubOrderBean> cartSuborderList = new ArrayList<FinalSubOrderBean>();

        final SharedPreferences prefs1 = getSharedPreferences("MenuData", 0);
        final String menuJsonList = prefs1.getString("list1", "");

        final SharedPreferences prefs = getSharedPreferences("FoodOrder", 0);
        final String bfsubOrderList = prefs.getString("bfSubOrderList", "");
        final String lnsubOrderList = prefs.getString("lnSubOrderList", "");
        final String dnsubOrderList = prefs.getString("dnSubOrderList", "");

        final GridLayout rl = (GridLayout) findViewById(R.id.GridLayoutCart);
        rl.removeAllViews();
        //final GridLayout rl1 = (GridLayout) findViewById(R.id.GridLayoutCart1);
        //rl1.removeAllViews();

        Gson json = new Gson();
        List<MenuBean> listMenuBean = new ArrayList<MenuBean>();
        final List<MenuBean> cartlist = new ArrayList<MenuBean>();

        //ArrayList<SubOrderBean> bfsubOrderBeanList = new ArrayList<SubOrderBean>();
        //ArrayList<SubOrderBean> lnsubOrderBeanList = new ArrayList<SubOrderBean>();
        //ArrayList<SubOrderBean> dnsubOrderBeanList = new ArrayList<SubOrderBean>();

        bfsubOrderBeanList = new ArrayList<SubOrderBean>();
        lnsubOrderBeanList = new ArrayList<SubOrderBean>();
        dnsubOrderBeanList = new ArrayList<SubOrderBean>();

        Type listTypeSuborder = new TypeToken<ArrayList<SubOrderBean>>() {
        }.getType();

        bfsubOrderBeanList = json.fromJson(bfsubOrderList, listTypeSuborder);
        lnsubOrderBeanList = json.fromJson(lnsubOrderList, listTypeSuborder);
        dnsubOrderBeanList = json.fromJson(dnsubOrderList, listTypeSuborder);

        Type listType = new TypeToken<ArrayList<MenuBean>>() {
        }.getType();
        listMenuBean = json.fromJson(menuJsonList, listType);

        for (MenuBean bean : listMenuBean) {
            cartlist.add(bean);
        }

        if(bfsubOrderBeanList == null && lnsubOrderBeanList == null && dnsubOrderBeanList == null) {

            final TextView breakFastCart = new TextView(this);
            breakFastCart.setText("No Items In Your Cart !!!");
            breakFastCart.setTextSize(25);
            breakFastCart.setVisibility(View.VISIBLE);
            breakFastCart.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            breakFastCart.setPadding(0, 5, 0, 5);
            breakFastCart.setTextColor(Color.BLACK);
            breakFastCart.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            rl.addView(breakFastCart);

        }
        else {

            if (bfsubOrderBeanList != null) {

                final TextView breakFastCart = new TextView(this);
                breakFastCart.setText("Breakfast");
                breakFastCart.setTextSize(20);
                breakFastCart.setVisibility(View.VISIBLE);
                breakFastCart.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                breakFastCart.setPadding(0, 5, 0, 5);
                breakFastCart.setTextColor(Color.BLACK);
                breakFastCart.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                rl.addView(breakFastCart);

                for (final SubOrderBean bean : bfsubOrderBeanList) {

                    final String foodNameNew = bean.getFoodName();
                    final int foodKey = bean.getFoodKey();

                    final View rowView;
                    rowView = View.inflate(this, R.layout.home_item_list, null);

                    TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
                    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
                    TextView extratxt = (TextView) rowView.findViewById(R.id.textView);
                    TextView priceAmount = (TextView) rowView.findViewById(R.id.price);
                    Button plusBtn = (Button) rowView.findViewById(R.id.plusButton);
                    final Button minusBtn = (Button) rowView.findViewById(R.id.minusButton);
                    final EditText quantity = (EditText) rowView.findViewById(R.id.Count);

                    txtTitle.setText(foodNameNew);
                    quantity.setText(String.valueOf(bean.getFoodQuantity()));
                    imageView.setImageResource(R.drawable.food);
                    extratxt.setText("Description of Food Item :  " + foodNameNew);
                    priceAmount.setText("");
                    //rl1.addView(rowView);
                    rl.addView(rowView);

                }
            }
            if (lnsubOrderBeanList != null) {

                final TextView lunchCart = new TextView(this);
                lunchCart.setText("Lunch");
                lunchCart.setTextSize(20);
                lunchCart.setVisibility(View.VISIBLE);
                lunchCart.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                lunchCart.setPadding(0, 5, 0, 5);
                lunchCart.setTextColor(Color.BLACK);
                lunchCart.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                rl.addView(lunchCart);

                for (final SubOrderBean bean : lnsubOrderBeanList) {

                    final String foodNameNew = bean.getFoodName();
                    final int foodKey = bean.getFoodKey();

                    final View rowView;
                    rowView = View.inflate(this, R.layout.home_item_list, null);
                    rowView.setMinimumWidth(ViewGroup.LayoutParams.MATCH_PARENT);

                    TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
                    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
                    TextView extratxt = (TextView) rowView.findViewById(R.id.textView);
                    TextView priceAmount = (TextView) rowView.findViewById(R.id.price);
                    Button plusBtn = (Button) rowView.findViewById(R.id.plusButton);
                    final Button minusBtn = (Button) rowView.findViewById(R.id.minusButton);
                    final EditText quantity = (EditText) rowView.findViewById(R.id.Count);

                    txtTitle.setText(foodNameNew);
                    quantity.setText(String.valueOf(bean.getFoodQuantity()));
                    imageView.setImageResource(R.drawable.food);
                    extratxt.setText("Description of Food Item :  " + foodNameNew);
                    priceAmount.setText("");
                    //rl1.addView(rowView);
                    rl.addView(rowView);

                }
            }

            if (dnsubOrderBeanList != null) {

                final TextView dinnerCart = new TextView(this);
                dinnerCart.setText("Dinner");
                dinnerCart.setTextSize(20);
                dinnerCart.setVisibility(View.VISIBLE);
                dinnerCart.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                dinnerCart.setPadding(0, 5, 0, 5);
                dinnerCart.setTextColor(Color.BLACK);
                dinnerCart.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                rl.addView(dinnerCart);

                for (final SubOrderBean bean : dnsubOrderBeanList) {

                    final String foodNameNew = bean.getFoodName();
                    final int foodKey = bean.getFoodKey();

                    final View rowView;
                    rowView = View.inflate(this, R.layout.home_item_list, null);

                    TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
                    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
                    TextView extratxt = (TextView) rowView.findViewById(R.id.textView);
                    TextView priceAmount = (TextView) rowView.findViewById(R.id.price);
                    Button plusBtn = (Button) rowView.findViewById(R.id.plusButton);
                    final Button minusBtn = (Button) rowView.findViewById(R.id.minusButton);
                    final EditText quantity = (EditText) rowView.findViewById(R.id.Count);

                    txtTitle.setText(foodNameNew);
                    quantity.setText(String.valueOf(bean.getFoodQuantity()));
                    imageView.setImageResource(R.drawable.food);
                    extratxt.setText("Description of Food Item :  " + foodNameNew);
                    priceAmount.setText("");
                    //rl1.addView(rowView);
                    rl.addView(rowView);
                }
            }
        }

        //actionBar = getSupportActionBar();
        //actionBar.setHomeButtonEnabled(true);
        //actionBar.setDisplayHomeAsUpEnabled(true);

        Button editOrderButton = (Button)this.findViewById(R.id.editOrder);
        editOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button placeOrderButton = (Button)this.findViewById(R.id.placeOrder);
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FinalSubOrderBean finalbean = new FinalSubOrderBean();

                if (bfsubOrderBeanList != null) {
                    finalbean.setFinalSubOrderList(bfsubOrderBeanList);
                }
                if (lnsubOrderBeanList != null) {
                    finalbean.setFinalSubOrderList(lnsubOrderBeanList);
                }
                if (dnsubOrderBeanList != null) {
                    finalbean.setFinalSubOrderList(dnsubOrderBeanList);
                }

                //finalbean.setDateOfDelivery(deliveryDateFinal);

                SharedPreferences deliveryDateInfo = getSharedPreferences("deliveryDatePrefs", 0);
                String deliveryDateFinal = deliveryDateInfo.getString("deliveryDate", "");

                final SharedPreferences SubOrderCartPrefs = getSharedPreferences("SubOrderCart", 0);
                SharedPreferences.Editor SubOrderCartEditor = SubOrderCartPrefs.edit();

                Gson json = new Gson();
                if (bfsubOrderBeanList != null) {
                    SubOrderCartEditor.putString("BreakfastSubOrderCartList", json.toJson(bfsubOrderList));
                }
                if (bfsubOrderBeanList != null) {
                    SubOrderCartEditor.putString("LunchSubOrderCartList", json.toJson(lnsubOrderList));
                }
                if (bfsubOrderBeanList != null) {
                    SubOrderCartEditor.putString("DinnerSubOrderCartList", json.toJson(dnsubOrderList));
                }

                SubOrderCartEditor.putString("deliveryDateCart",deliveryDateFinal);
                SubOrderCartEditor.commit();

                Intent i = new Intent(SampleCart.this, DeliveryAddress.class);
                startActivity(i);

            }

        });

    }
}



