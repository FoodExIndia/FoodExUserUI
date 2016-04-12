package user.com.foodexuserui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import user.com.Entities.MenuBean;
import user.com.Entities.SubOrderBean;

public class Dinner extends Fragment {

    public Dinner() {

    }

    EditText datecheckDP;
    Button getDinnerMenu,placeOrder,nextDateButton;

    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final SimpleDateFormat sdf = new SimpleDateFormat("d-M-yyyy");
        final List<SubOrderBean> dnSuborderList = new ArrayList<SubOrderBean>();
        //final SharedPreferences menuItems = this.getActivity().getSharedPreferences("PlanMenu", 0);

        final View dinner;
        dinner = inflater.inflate(R.layout.activity_dinner, container, false);

        View plan1;
        plan1 = inflater.inflate(R.layout.activity_plan1, container, false);

        final String foodName = getActivity().getIntent().getExtras().getString("foodItem").toString();
        final String foodPrice = getActivity().getIntent().getExtras().getString("itemPrice");
        final String SeeAll = getActivity().getIntent().getExtras().getString("SeeAll").toString();

        final SharedPreferences prefs1 = this.getActivity().getSharedPreferences("MenuData", 0);
        final String menuJsonList = prefs1.getString("list1", "");

        final SharedPreferences imgandDate = this.getActivity().getSharedPreferences("PlanData", 0);

        final SharedPreferences prefs = this.getActivity().getSharedPreferences("FoodOrder", 0);
        final String subOrderList = prefs.getString("SubOrderList", "");

        final GridLayout rl = (GridLayout) dinner.findViewById(R.id.GridLayoutDinner);
        rl.removeAllViews();
        final GridLayout rl1 = (GridLayout) dinner.findViewById(R.id.GridLayoutDinner1);
        rl1.removeAllViews();

        final Plan1 planCart = new Plan1();
        //final EditText dpDate = (EditText) plan1.findViewById(R.id.plan1DatePicker);

        //getDinnerMenu = (Button) dinner.findViewById(R.id.buttonDN);
        //placeOrder = (Button) dinner.findViewById(R.id.cartOrder);

        //Check PlanType and Date accordingly

        String lastDate = null;
        //String datecheck = imgandDate.getString("planDate", "");
        //final String currentDate = ((Plan1) getActivity()).getDPdate();
        //final String planTypImage = imgandDate.getString("planType", "");

        Calendar c = Calendar.getInstance();
        try {
            //c.setTime(sdf.parse(datecheck));
        }
        catch(Exception ex){
            ex.getMessage();
            ex.printStackTrace();
        }

        /*if(planTypImage.equalsIgnoreCase("plan1btn")){
            placeOrder.setVisibility(View.VISIBLE);
        }
        else if(planTypImage.equalsIgnoreCase("plan2btn")) {
            lastDate = imgandDate.getString("date2", "");
            //c.add(Calendar.DATE, +2);
            if(currentDate.equalsIgnoreCase(lastDate))
            {
                placeOrder.setVisibility(View.VISIBLE);
            }
            else
            {
                getDinnerMenu.setVisibility(View.VISIBLE);
            }
        }
        else if(planTypImage.equalsIgnoreCase("plan5btn")) {
            lastDate = imgandDate.getString("date5", "");
            //c.add(Calendar.DATE, +5);
            if(currentDate.equalsIgnoreCase(lastDate))
            {
                placeOrder.setVisibility(View.VISIBLE);
            }
            else
            {
                getDinnerMenu.setVisibility(View.VISIBLE);
            }
        }
        else if(planTypImage.equalsIgnoreCase("plan7btn")) {
            lastDate = imgandDate.getString("date7", "");
            //c.add(Calendar.DATE, +7);
            if(currentDate.equalsIgnoreCase(lastDate))
            {
                placeOrder.setVisibility(View.VISIBLE);
            }
            else
            {
                getDinnerMenu.setVisibility(View.VISIBLE);
            }
        }

*/
  /*      placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String datecheck = imgandDate.getString("planDate", "");
                String currentDate = ((Plan1) getActivity()).getDPdate();

                if (!datecheck.equals("")) {

                    SharedPreferences.Editor editor = menuItems.edit();
                    Gson gson = new Gson();

                    for (int k = 0; k < rl.getChildCount(); k = k + 4) {
                        CheckBox item2 = (CheckBox) rl.getChildAt(k);
                        final EditText foodQuantity = (EditText) rl.getChildAt(k + 2);
                        String foodName = item2.getText().toString();
                        int itemQuantity2 = Integer.parseInt(foodQuantity.getText().toString());

                        if (item2.isChecked() && itemQuantity2 > 0) {
                            SubOrderBean dnbean = new SubOrderBean();
                            dnbean.setFoodKey(foodQuantity.getId());
                            dnbean.setCourseFlag(3);
                            dnbean.setFoodName(foodName);
                            dnbean.setFoodQuantity(itemQuantity2);
                            try {
                                //dnbean.setDate(sdf.parse(datecheck));
                                dnbean.setDate(sdf.parse(currentDate));
                            } catch (ParseException e) {
                                System.out.print(e.getMessage());
                            }

                            dnSuborderList.add(dnbean);

                        }
                    }

                    String suborderJson = null;
                    suborderJson = prefs.getString("SubOrderList", suborderJson);
                    //If value already present in shared preference from dinner or dinner page, add that to the suborderList of this page
                    if (suborderJson != null) {
                        List<SubOrderBean> d = new ArrayList<SubOrderBean>();
                        Type listType = new TypeToken<ArrayList<SubOrderBean>>() {
                        }.getType();
                        d = new Gson().fromJson(suborderJson, listType);
                        dnSuborderList.addAll(d);
                    }

                    SharedPreferences.Editor itemsEditor = prefs.edit();
                    itemsEditor.putString("SubOrderList", new Gson().toJson(dnSuborderList));
                    itemsEditor.commit();

                    //Calls the SampleCart once Order is placed
                    Intent i = new Intent(Dinner.this.getActivity(), SampleCart.class);
                    startActivity(i);

                }
            }
        });


        getDinnerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String datecheck = imgandDate.getString("planDate", "");
                String currentDate = ((Plan1) getActivity()).getDPdate();

                if(!datecheck.equals("")) {

                SharedPreferences.Editor editor = menuItems.edit();
                Gson gson = new Gson();

                for(int k = 0; k < rl.getChildCount() ; k=k+4 )
                {
                    CheckBox item2 = (CheckBox) rl.getChildAt(k);
                    final EditText foodQuantity = (EditText) rl.getChildAt(k + 2);
                    String foodName = item2.getText().toString();
                    int itemQuantity2 = Integer.parseInt(foodQuantity.getText().toString());

                    if(item2.isChecked() && itemQuantity2>0)
                    {
                        SubOrderBean dnbean = new SubOrderBean();
                        dnbean.setFoodKey(foodQuantity.getId());
                        dnbean.setCourseFlag(3);
                        dnbean.setFoodName(foodName);
                        dnbean.setFoodQuantity(itemQuantity2);
                        try{
                            dnbean.setDate(sdf.parse(currentDate));
                        }
                        catch (ParseException e){
                            System.out.print(e.getMessage());
                        }

                        dnSuborderList.add(dnbean);

                    }
                }

                    String suborderJson = null;
                    suborderJson = prefs.getString("SubOrderList", suborderJson);
                    //If value already present in shared preference from dinner or dinner page, add that to the suborderList of this page
                    if(suborderJson != null)
                    {
                        List<SubOrderBean> d = new ArrayList<SubOrderBean>();
                        Type listType = new TypeToken<ArrayList<SubOrderBean>>() {
                        }.getType();
                        d = new Gson().fromJson(suborderJson, listType);
                        dnSuborderList.addAll(d);
                    }

                    SharedPreferences.Editor itemsEditor = prefs.edit();
                    itemsEditor.putString("SubOrderList", new Gson().toJson(dnSuborderList));
                    itemsEditor.commit();

                    //Proceeds with next date
                    ((Plan1) getActivity()).getNextDate(currentDate);

                } else {
                    Toast.makeText(getContext(), "Select a Date !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
*/
        Gson json = new Gson();

        List<MenuBean> listMenuBean = new ArrayList<MenuBean>();
        List<MenuBean> dinnerlist = new ArrayList<MenuBean>();

        List<SubOrderBean> subOrderBeanList = new ArrayList<SubOrderBean>();
        Type listTypeSuborder = new TypeToken<ArrayList<SubOrderBean>>() {
        }.getType();
        subOrderBeanList = json.fromJson(subOrderList, listTypeSuborder);

        Type listType = new TypeToken<ArrayList<MenuBean>>() {
        }.getType();
        listMenuBean = json.fromJson(menuJsonList, listType);

        for (MenuBean bean : listMenuBean) {
            if (bean.getCourseFlag() == 1) {
                dinnerlist.add(bean);
            }
        }

        if(SeeAll.equals("No"))

        {

            for (final SubOrderBean bean : subOrderBeanList) {

                if (foodName != null && !foodName.equals("")) {

                    String newCount = String.valueOf(bean.getFoodQuantity());

                    final View rowView;
                    rowView = inflater.inflate(R.layout.home_item_list, container, false);

                    TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
                    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
                    TextView extratxt = (TextView) rowView.findViewById(R.id.textView);
                    final TextView priceAmount = (TextView) rowView.findViewById(R.id.price);
                    Button plusBtn = (Button) rowView.findViewById(R.id.plusButton);
                    final Button minusBtn = (Button) rowView.findViewById(R.id.minusButton);
                    final EditText quantity = (EditText) rowView.findViewById(R.id.Count);
                    //quantity.setText(String.valueOf(bean.getFoodQuantity()));
                    //quantity.setText(newCount);
                    //final Button cancelBtn = (Button) rowView.findViewById(R.id.cancelButton);

                    plusBtn.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {

                                                       String count = null;
                                                       int newCount = 0;
                                                       Integer q = Integer.parseInt(String.valueOf(quantity.getText()));

                                                       if (!minusBtn.isEnabled()) {

                                                           minusBtn.setEnabled(true);

                                                       }

                                                       if(q == 0)
                                                       {
                                                           rl1.removeView(rowView);
                                                           rl.removeView(rowView);
                                                           rl.addView(rowView);

                                                           SharedPreferences itemCountInfo = getActivity().getSharedPreferences("itemCount", 0);
                                                           count = itemCountInfo.getString("countOverall", "");
                                                           newCount = Integer.parseInt(count);

                                                           SharedPreferences.Editor itemCounteditor = itemCountInfo.edit();
                                                           itemCounteditor.putString("countOverall", String.valueOf(newCount + 1));
                                                           itemCounteditor.commit();

                                                           planCart.refreshActionBar(getActivity());
                                                       }

                                                       String newq = (++q).toString();
                                                       bean.setFoodQuantity(Integer.parseInt(newq));
                                                       double newPrice = Double.valueOf(foodPrice) * Double.valueOf(newq);
                                                       priceAmount.setText(String.valueOf(newPrice));
                                                       quantity.setText(newq);

                                                       final String subOrderList = prefs.getString("SubOrderList", "");
                                                       List<SubOrderBean> updateSuborderList = new ArrayList<SubOrderBean>();
                                                       Type listTypeSuborder = new TypeToken<ArrayList<SubOrderBean>>() {
                                                       }.getType();

                                                       Gson json1 = new Gson();
                                                       updateSuborderList = json1.fromJson(subOrderList, listTypeSuborder);
                                                       for (SubOrderBean updateBean : updateSuborderList) {
                                                           if (updateBean.getFoodName().equals(foodName)) {
                                                               updateBean.setFoodQuantity(Integer.parseInt(newq));
                                                           }
                                                       }

                                                       SharedPreferences.Editor itemsEditor = prefs.edit();
                                                       itemsEditor.putString("SubOrderList", new Gson().toJson(updateSuborderList));
                                                       itemsEditor.commit();

                                                   }
                                               }
                    );

                    minusBtn.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                        String count = null;
                                                        int newCount = 0;
                                                        Integer q = Integer.parseInt(String.valueOf(quantity.getText()));

                                                        if (q != 0) {

                                                            String newq = (--q).toString();

                                                            if (Integer.parseInt(newq) == 0) {

                                                                rl.removeView(rowView);
                                                                rl1.removeView(rowView);
                                                                rl1.addView(rowView);

                                                                SharedPreferences itemCountInfo = getActivity().getSharedPreferences("itemCount", 0);
                                                                count = itemCountInfo.getString("countOverall", "");
                                                                newCount = Integer.parseInt(count);

                                                                SharedPreferences.Editor itemCounteditor = itemCountInfo.edit();
                                                                itemCounteditor.putString("countOverall", String.valueOf(newCount - 1));
                                                                itemCounteditor.commit();

                                                                planCart.refreshActionBar(getActivity());

                                                            }

                                                            double newPrice = Double.valueOf(foodPrice) * Double.valueOf(newq);
                                                            priceAmount.setText(String.valueOf(newPrice));
                                                            quantity.setText(newq);

                                                        } else if (q == 0) {

                                                            rl.removeView(rowView);
                                                            rl1.removeView(rowView);
                                                            rl1.addView(rowView);

                                                        }
                                                    }
                                                }
                    );

                    txtTitle.setText(foodName);
                    imageView.setImageResource(R.drawable.food);
                    extratxt.setText("Description of Food Item :  " + foodName);
                    priceAmount.setText(foodPrice);
                    rl.addView(rowView);

                }
            }


        }
        final TextView addNewItem = new TextView(this.getActivity());
        addNewItem.setText("Add More Items");
        addNewItem.setTextSize(20);
        addNewItem.setVisibility(View.VISIBLE);
        addNewItem.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        addNewItem.setPadding(0, 5, 0, 20);
        addNewItem.setTextColor(Color.BLACK);
        addNewItem.setId(001);
        addNewItem.setLayoutParams(new ViewGroup.LayoutParams(1000, ViewGroup.LayoutParams.MATCH_PARENT));
        rl1.addView(addNewItem);

        for (int j = 0; j < dinnerlist.size(); j++) {

            final String foodNameNew = dinnerlist.get(j).getItemName();
            double foodItemPrice = dinnerlist.get(j).getItemPrice();
            final int foodKey = dinnerlist.get(j).getFoodKey();

            if (!foodName.equalsIgnoreCase(foodNameNew)) {

                final View rowView;
                rowView = inflater.inflate(R.layout.home_item_list, container, false);

                TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
                ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
                TextView extratxt = (TextView) rowView.findViewById(R.id.textView);
                TextView priceAmount = (TextView) rowView.findViewById(R.id.price);
                Button plusBtn = (Button) rowView.findViewById(R.id.plusButton);
                final Button minusBtn = (Button) rowView.findViewById(R.id.minusButton);
                final EditText quantity = (EditText) rowView.findViewById(R.id.Count);
                quantity.setText("0");

                plusBtn.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {

                                                   int newCount = 0;
                                                   String count = null;
                                                   Integer q = Integer.parseInt(String.valueOf(quantity.getText()));

                                                   if (!minusBtn.isEnabled()) {
                                                       minusBtn.setEnabled(true);
                                                   }
                                                   String newq = (++q).toString();
                                                   quantity.setText(newq);

                                                   if (Integer.parseInt(newq) == 1) {

                                                       rl1.removeView(rowView);
                                                       rl.removeView(rowView);
                                                       rl.addView(rowView);

                                                       SharedPreferences itemCountInfo = getActivity().getSharedPreferences("itemCount", 0);
                                                       count = itemCountInfo.getString("countOverall", "");
                                                       newCount = Integer.parseInt(count);

                                                       SharedPreferences.Editor itemCounteditor = itemCountInfo.edit();
                                                       itemCounteditor.putString("countOverall", String.valueOf(newCount + 1));
                                                       itemCounteditor.commit();

                                                       final SubOrderBean bfbean = new SubOrderBean();
                                                       bfbean.setFoodKey(foodKey);
                                                       bfbean.setCourseFlag(1);
                                                       bfbean.setFoodName(foodNameNew);
                                                       bfbean.setFoodQuantity(q);

                                                       String suborderJson = null;
                                                       suborderJson = prefs.getString("SubOrderList", suborderJson);
                                                           /*if(suborderJson != null) {
                                                               SharedPreferences.Editor editor = prefs.edit();
                                                               editor.clear();
                                                               editor.commit();
                                                           }*/

                                                       //If value already present in shared preference from dinner or lunch page, add that to the suborderList of this page
                                                       if (suborderJson != null) {
                                                           List<SubOrderBean> l = new ArrayList<SubOrderBean>();
                                                           Type listTypeSubOrder = new TypeToken<ArrayList<SubOrderBean>>() {
                                                           }.getType();
                                                           l = new Gson().fromJson(suborderJson, listTypeSubOrder);
                                                           dnSuborderList.addAll(l);
                                                       }

                                                       dnSuborderList.add(bfbean);
                                                       SharedPreferences.Editor subOrderEditor = prefs.edit();
                                                       subOrderEditor.putString("SubOrderList", new Gson().toJson(dnSuborderList));
                                                       subOrderEditor.commit();

                                                       planCart.refreshActionBar(getActivity());

                                                   }


                                               }
                                           }
                );

                minusBtn.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    int newCount = 0;
                                                    String count = null;
                                                    Integer q = Integer.parseInt(String.valueOf(quantity.getText()));

                                                    if (q != 0) {

                                                        String newq = (--q).toString();

                                                        if (Integer.parseInt(newq) == 0) {

                                                            rl.removeView(rowView);
                                                            rl1.removeView(rowView);
                                                            rl1.addView(rowView);

                                                            SharedPreferences itemCountInfo = getActivity().getSharedPreferences("itemCount", 0);
                                                            count = itemCountInfo.getString("countOverall", "");
                                                            newCount = Integer.parseInt(count);

                                                            SharedPreferences.Editor itemCounteditor = itemCountInfo.edit();
                                                            itemCounteditor.putString("countOverall", String.valueOf(newCount - 1));
                                                            itemCounteditor.commit();


                                                            planCart.refreshActionBar(getActivity());

                                                        }
                                                        //double newPrice = Double.valueOf(foodPrice) * Double.valueOf(newq);
                                                        //priceAmount.setText(String.valueOf(newPrice));
                                                        quantity.setText(newq);
                                                    }
                                                    else if(q == 0){
                                                        rl.removeView(rowView);
                                                        rl1.removeView(rowView);
                                                        rl1.addView(rowView);
                                                    }
                                                }
                                            }
                );


                txtTitle.setText(foodNameNew);
                imageView.setImageResource(R.drawable.food);
                extratxt.setText("Description of Food Item :  " + foodNameNew);
                priceAmount.setText(String.valueOf(foodItemPrice));
                rl1.addView(rowView);
            }

        }

        /*for (int j = 0; j < dinnerlist.size(); j++) {

            CheckBox chk1 = new CheckBox(this.getActivity());
            chk1.setText(dinnerlist.get(j).getItemName());
            chk1.setTextSize(20);
            chk1.setVisibility(View.VISIBLE);
            chk1.setGravity(Gravity.CENTER);
            rl.addView(chk1);

            Button minusBtn = new Button(this.getActivity());
            minusBtn.setText("-");
            minusBtn.setTextSize(20);
            minusBtn.setVisibility(View.VISIBLE);
            minusBtn.setGravity(Gravity.CENTER);
            minusBtn.setId(dinnerlist.get(j).getFoodKey());
            minusBtn.setEnabled(false);
            minusBtn.setLayoutParams(new ViewGroup.LayoutParams(150, ViewGroup.LayoutParams.WRAP_CONTENT));
            rl.addView(minusBtn);

            EditText itemCount = new EditText(this.getActivity());
            itemCount.setText("0");
            itemCount.setTextSize(20);
            itemCount.setVisibility(View.VISIBLE);
            itemCount.setGravity(Gravity.CENTER);
            itemCount.setId(dinnerlist.get(j).getFoodKey());
            itemCount.setEnabled(false);
            itemCount.setLayoutParams(new ViewGroup.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT));
            rl.addView(itemCount);

            Button plusbtn = new Button(this.getActivity());
            plusbtn.setText("+");
            plusbtn.setTextSize(20);
            plusbtn.setVisibility(View.VISIBLE);
            plusbtn.setGravity(Gravity.CENTER);
            plusbtn.setId(dinnerlist.get(j).getFoodKey());
            plusbtn.setEnabled(false);
            plusbtn.setLayoutParams(new ViewGroup.LayoutParams(150, ViewGroup.LayoutParams.WRAP_CONTENT));
            rl.addView(plusbtn);

        }
*/
        /*final int size = rl.getChildCount();

        for(int k = 0; k < size ; k=k+4 )
        {
            CheckBox item1 = (CheckBox) rl.getChildAt(k);
            final Button btn2 = (Button) rl.getChildAt(k+1);
            final EditText itemQuantity = (EditText) rl.getChildAt(k+2);
            final Button btn1 = (Button) rl.getChildAt(k+3);

            item1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    enableButtons(dinner, btn1, btn2);
                }
            });

            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Integer q1 = Integer.parseInt(String.valueOf(itemQuantity.getText()));
                    if(!btn2.isEnabled())
                    {
                        btn2.setEnabled(true);
                    }
                    String newq = (++q1).toString();
                    itemQuantity.setText(newq);

                }

            });


            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Integer q2 = Integer.parseInt(String.valueOf(itemQuantity.getText()));

                    if (q2 != 0) {
                        String newq = (--q2).toString();
                        itemQuantity.setText(newq);
                    }

                }
            });
        }
*/
        return dinner;

    }

    public void enableButtons(View view, Button plus,Button minus){

        if(!plus.isEnabled()) {
            plus.setEnabled(true);
            minus.setEnabled(true);
        }
        else
        {
            plus.setEnabled(false);
            minus.setEnabled(false);
        }
    }

}

/*

final View dinner;
        dinner = inflater.inflate(R.layout.activity_dinner, container, false);

        final View plan1;
        plan1 = inflater.inflate(R.layout.activity_plan1, container, false);

        final SharedPreferences prefs = this.getActivity().getSharedPreferences("PlanData", 0);
        final String planType = prefs.getString("planType", "");

        Date dateNow = null;
        Date planDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("d-M-yyyy");
        placeOrder = (Button)dinner.findViewById(R.id.placeOrder);
        getDinnerMenu = (Button)dinner.findViewById(R.id.buttonDinner);

        if(planType.equalsIgnoreCase("plan1btn"))
        {
            placeOrder.setVisibility(View.VISIBLE);
            getDinnerMenu.setVisibility(View.INVISIBLE);
        }
        else {
            if (planType.equalsIgnoreCase("plan2btn") || planType.equalsIgnoreCase("plan5btn") || planType.equalsIgnoreCase("plan7btn")) {

                Calendar c = Calendar.getInstance();

                try {
                    planDate = sdf.parse(datecheck.getText().toString());
                    c.setTime(dateNow);

                    switch (planType) {
                        case "plan2btn":
                            c.add(Calendar.DATE, 2);
                            break;
                        case "plan5btn":
                            c.add(Calendar.DATE, 5);
                            break;
                        case "plan7btn":
                            c.add(Calendar.DATE, 7);
                            break;
                    }

                    dateNow = new Date(c.getTimeInMillis());
                    Toast.makeText(getContext(), dateNow.toString(), Toast.LENGTH_SHORT).show();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //if(dateNow == datecheck.getText().toString()) //datecheck + 5
                {
                    //placeOrder.setVisibility(View.VISIBLE);
                    //getDinnerMenu.setVisibility(View.VISIBLE);
                }
            }
        }

        final SharedPreferences menuItems = this.getActivity().getSharedPreferences("PlanMenu", 0);

        getDinnerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Integer> menuItemsCount = new ArrayList<Integer>();

                HashMap<String,Integer> xxx = new HashMap<String,Integer>();

                String dn1 = DinnerItem1quantity.getText().toString();
                menuItemsCount.add(Integer.parseInt(dn1));
                String dn2 = DinnerItem2quantity.getText().toString();
                menuItemsCount.add(Integer.parseInt(dn2));
                String dn3 = DinnerItem3quantity.getText().toString();
                menuItemsCount.add(Integer.parseInt(dn3));
                String dn4 = DinnerItem4quantity.getText().toString();
                menuItemsCount.add(Integer.parseInt(dn4));

                final String bf1 = prefs.getString("bf1", "");
                menuItemsCount.add(Integer.parseInt(bf1));
                final String bf2 = prefs.getString("bf2", "");
                menuItemsCount.add(Integer.parseInt(bf2));
                final String bf3 = prefs.getString("bf3", "");
                menuItemsCount.add(Integer.parseInt(bf3));
                final String bf4 = prefs.getString("bf4", "");
                menuItemsCount.add(Integer.parseInt(bf4));

                final String ln1 = prefs.getString("ln1", "");
                menuItemsCount.add(Integer.parseInt(ln1));
                final String ln2 = prefs.getString("ln2", "");
                menuItemsCount.add(Integer.parseInt(ln2));
                final String ln3 = prefs.getString("ln3", "");
                menuItemsCount.add(Integer.parseInt(ln3));
                final String ln4 = prefs.getString("ln4", "");
                menuItemsCount.add(Integer.parseInt(ln4));

                    SharedPreferences.Editor editor = menuItems.edit();
                    editor.putString("dn1", dn1);
                    menuItemsCount.add(Integer.parseInt(dn1));
                    editor.putString("dn2", dn2);
                    menuItemsCount.add(Integer.parseInt(dn2));
                    editor.putString("dn3", dn3);
                    menuItemsCount.add(Integer.parseInt(dn3));
                    editor.putString("dn4", dn4);
                    menuItemsCount.add(Integer.parseInt(dn4));

                if( datecheck == null )
                {
                    Toast.makeText(getContext(), "Select a date !!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    for (Integer i : menuItemsCount) {
                        if (i != 0) {
                            editor.commit();
                            break;
                        } else {
                            Toast.makeText(getContext(), "Select atleast one item !!!", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
                //((Plan1)getActivity()).selectFragment(2);
            }
        });

        DinnerItem1chkbox = (CheckBox) dinner.findViewById(R.id.DinnerItem1);
        DinnerItem1quantity = (EditText) dinner.findViewById(R.id.DinnerItem1Count);
        DinnerItem1quantity.setText("0");
        DinnerItem1plusbtn = (Button) dinner.findViewById(R.id.pD1);
        DinnerItem1minusbtn = (Button) dinner.findViewById(R.id.mD1);
        datecheck = (EditText)plan1.findViewById(R.id.plan1DatePicker);
        DinnerItem1chkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableButtons(dinner, DinnerItem1plusbtn, DinnerItem1minusbtn);
            }
        });

        DinnerItem1plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer q1 = Integer.parseInt(String.valueOf(DinnerItem1quantity.getText()));
                if (!DinnerItem1minusbtn.isEnabled()) {
                    DinnerItem1minusbtn.setEnabled(true);
                }
                String newq = (++q1).toString();
                DinnerItem1quantity.setText(newq);

            }

        });

        DinnerItem1minusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer q2 = Integer.parseInt(String.valueOf(DinnerItem1quantity.getText()));

                if (q2 != 0) {
                    String newq = (--q2).toString();
                    DinnerItem1quantity.setText(newq);
                }

            }
        });

        DinnerItem2chkbox = (CheckBox) dinner.findViewById(R.id.DinnerItem2);
        DinnerItem2quantity = (EditText) dinner.findViewById(R.id.DinnerItem2Count);
        DinnerItem2quantity.setText("0");
        DinnerItem2plusbtn = (Button) dinner.findViewById(R.id.pD2);
        DinnerItem2minusbtn = (Button) dinner.findViewById(R.id.mD2);

        DinnerItem2chkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableButtons(dinner, DinnerItem2plusbtn, DinnerItem2minusbtn);
            }
        });

        DinnerItem2plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer q1 = Integer.parseInt(String.valueOf(DinnerItem2quantity.getText()));
                if (!DinnerItem2minusbtn.isEnabled()) {
                    DinnerItem2minusbtn.setEnabled(true);
                }
                String newq = (++q1).toString();
                DinnerItem2quantity.setText(newq);

            }

        });

        DinnerItem2minusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer q2 = Integer.parseInt(String.valueOf(DinnerItem2quantity.getText()));

                if (q2 != 0) {
                    String newq = (--q2).toString();
                    DinnerItem2quantity.setText(newq);
                }

            }
        });

        DinnerItem3chkbox = (CheckBox) dinner.findViewById(R.id.DinnerItem3);
        DinnerItem3quantity = (EditText) dinner.findViewById(R.id.DinnerItem3Count);
        DinnerItem3quantity.setText("0");
        DinnerItem3plusbtn = (Button) dinner.findViewById(R.id.pD3);
        DinnerItem3minusbtn = (Button) dinner.findViewById(R.id.mD3);

        DinnerItem3chkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableButtons(dinner, DinnerItem3plusbtn, DinnerItem3minusbtn);
            }
        });

        DinnerItem3plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer q1 = Integer.parseInt(String.valueOf(DinnerItem3quantity.getText()));
                if (!DinnerItem3minusbtn.isEnabled()) {
                    DinnerItem3minusbtn.setEnabled(true);
                }
                String newq = (++q1).toString();
                DinnerItem3quantity.setText(newq);

            }

        });

        DinnerItem3minusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer q2 = Integer.parseInt(String.valueOf(DinnerItem3quantity.getText()));

                if (q2 != 0) {
                    String newq = (--q2).toString();
                    DinnerItem3quantity.setText(newq);
                }

            }
        });

        DinnerItem4chkbox = (CheckBox) dinner.findViewById(R.id.DinnerItem4);
        DinnerItem4quantity = (EditText) dinner.findViewById(R.id.DinnerItem4Count);
        DinnerItem4quantity.setText("0");
        DinnerItem4plusbtn = (Button) dinner.findViewById(R.id.pD4);
        DinnerItem4minusbtn = (Button) dinner.findViewById(R.id.mD4);

        DinnerItem4chkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableButtons(dinner, DinnerItem4plusbtn, DinnerItem4minusbtn);
            }
        });

        DinnerItem4plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer q1 = Integer.parseInt(String.valueOf(DinnerItem4quantity.getText()));
                if (!DinnerItem4minusbtn.isEnabled()) {
                    DinnerItem4minusbtn.setEnabled(true);
                }
                String newq = (++q1).toString();
                DinnerItem4quantity.setText(newq);

            }

        });

        DinnerItem4minusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer q2 = Integer.parseInt(String.valueOf(DinnerItem4quantity.getText()));

                if (q2 != 0) {
                    String newq = (--q2).toString();
                    DinnerItem4quantity.setText(newq);
                }

            }

        });


        return dinner;

    }

    public void enableButtons(View view, Button plus, Button minus) {

        if (!plus.isEnabled()) {
            plus.setEnabled(true);
            minus.setEnabled(true);
        } else {
            plus.setEnabled(false);
            minus.setEnabled(false);
        }

    }
}
*/

/*
    public void getBreakfastMenu(View view){

        Intent intent=new Intent();

        switch(view.getId()) {
            case R.id.plan1btn:
                intent = new Intent(Dinner.this.getActivity(), SampleCart.class);
                //startActivity(intent);
                startActivity(intent.putExtra("fromPlan", "plan1btn"));
                startActivity(intent.putExtra("fromMenu", "Breakfast"));
                startActivity(intent.putExtra("idlyCount", DinnerItem1quantity.getText()));
                startActivity(intent.putExtra("dosaCount", DinnerItem2quantity.getText()));
                startActivity(intent.putExtra("chappathiCount", DinnerItem3quantity.getText()));
                startActivity(intent.putExtra("pooriCount", DinnerItem4quantity.getText()));
                break;
            case R.id.plan2btn:
                intent = new Intent(Dinner.this.getActivity(), SampleCart.class);
                //startActivity(intent);
                startActivity(intent.putExtra("from", "plan2btn"));
                startActivity(intent.putExtra("fromMenu","Breakfast"));
                startActivity(intent.putExtra("idlyCount", DinnerItem1quantity.getText()));
                startActivity(intent.putExtra("dosaCount", DinnerItem2quantity.getText()));
                startActivity(intent.putExtra("chappathiCount", DinnerItem3quantity.getText()));
                startActivity(intent.putExtra("pooriCount", DinnerItem4quantity.getText()));
                break;
            case R.id.plan5btn:
                intent = new Intent(Dinner.this.getActivity(), SampleCart.class);
                //startActivity(intent);
                startActivity(intent.putExtra("from", "plan5btn"));
                startActivity(intent.putExtra("fromMenu","Breakfast"));
                startActivity(intent.putExtra("idlyCount", DinnerItem1quantity.getText()));
                startActivity(intent.putExtra("dosaCount", DinnerItem2quantity.getText()));
                startActivity(intent.putExtra("chappathiCount", DinnerItem3quantity.getText()));
                startActivity(intent.putExtra("pooriCount", DinnerItem4quantity.getText()));
                break;
            case R.id.plan7btn:
                intent = new Intent(Dinner.this.getActivity(), SampleCart.class);
                //startActivity(intent);
                startActivity(intent.putExtra("from", "plan7btn"));
                startActivity(intent.putExtra("fromMenu","Breakfast"));
                startActivity(intent.putExtra("idlyCount", DinnerItem1quantity.getText()));
                startActivity(intent.putExtra("dosaCount", DinnerItem2quantity.getText()));
                startActivity(intent.putExtra("chappathiCount", DinnerItem3quantity.getText()));
                startActivity(intent.putExtra("pooriCount", DinnerItem4quantity.getText()));
                break;
        }
    }*//*


/*plusbtn.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public  onLongClick(View v) {
                    Integer lq1 = Integer.parseInt(String.valueOf(quantity.getText()));
                    String newq = (++lq1).toString();
                    quantity.setText(newq);
                    //return false;
                }

            });*/


/*plusbtn.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (event.getAction() == MotionEvent.ACTION_UP) {

                        Integer q1 = Integer.parseInt(String.valueOf(quantity.getText()));
                        if (!minusbtn.isEnabled()) {
                            minusbtn.setEnabled(true);
                        }
                        String newq = (++q1).toString();
                        quantity.setText(newq);

                    }
                    return true;
                }
            });
*/