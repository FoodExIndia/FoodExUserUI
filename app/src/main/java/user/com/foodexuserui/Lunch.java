package user.com.foodexuserui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
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

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import user.com.Entities.MenuBean;
import user.com.Entities.SubOrderBean;

public class Lunch extends Fragment {

    String itemPrice = null;

    public Lunch()
    {

    }

    Button getLunchMenu;

    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final SimpleDateFormat sdf = new SimpleDateFormat("d-M-yyyy");

        final SharedPreferences menuItems = this.getActivity().getSharedPreferences("PlanMenu", 0);

        final View lunch;
        lunch = inflater.inflate(R.layout.activity_lunch, container, false);

        View plan1;
        plan1 = inflater.inflate(R.layout.activity_plan1, container, false);

        final String foodName = getActivity().getIntent().getExtras().getString("foodItem").toString();
        final String foodPrice = getActivity().getIntent().getExtras().getString("itemPrice");
        final String SeeAll = getActivity().getIntent().getExtras().getString("SeeAll").toString();

        final SharedPreferences prefs1 = this.getActivity().getSharedPreferences("MenuData", 0);
        final String menuJsonList = prefs1.getString("list1", "");

        final SharedPreferences prefs = this.getActivity().getSharedPreferences("FoodOrder", 0);
        final String subOrderList = prefs.getString("SubOrderList", "");

        final SharedPreferences imgandDate = this.getActivity().getSharedPreferences("PlanData", 0);

        final GridLayout rl = (GridLayout) lunch.findViewById(R.id.GridLayoutLunch);
        rl.removeAllViews();
        final GridLayout rl1 = (GridLayout) lunch.findViewById(R.id.GridLayoutLunch1);
        rl1.removeAllViews();
        //final EditText dpDate = (EditText) plan1.findViewById(R.id.plan1DatePicker);

        //getLunchMenu = (Button) lunch.findViewById(R.id.buttonLN);

/*
        getLunchMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final List<SubOrderBean> lnSuborderList = new ArrayList<SubOrderBean>();
                String datecheck = imgandDate.getString("planDate", "");
                String currentDate = ((Plan1) getActivity()).getDPdate();

                if(!datecheck.equals("")) {

                    SharedPreferences.Editor editor = menuItems.edit();
                    Gson gson = new Gson();

                    for (int k = 0; k < rl.getChildCount(); k = k + 4) {

                        CheckBox item2 = (CheckBox) rl.getChildAt(k);
                        final EditText foodQuantity = (EditText) rl.getChildAt(k + 2);
                        String foodName = item2.getText().toString();
                        int itemQuantity2 = Integer.parseInt(foodQuantity.getText().toString());

                        if(item2.isChecked() && itemQuantity2>0)
                        {
                            SubOrderBean lnbean = new SubOrderBean();
                            lnbean.setFoodKey(foodQuantity.getId());
                            lnbean.setCourseFlag(2);
                            lnbean.setFoodName(foodName);
                            lnbean.setFoodQuantity(itemQuantity2);

                            try {
                                lnbean.setDate(sdf.parse(currentDate));
                            } catch (ParseException e) {
                                System.out.print(e.getMessage());
                            }

                            lnSuborderList.add(lnbean);

                        }
                        item2.setChecked(false);
                        foodQuantity.setText("0");
                    }

                    String suborderJson = null;
                    suborderJson = prefs.getString("SubOrderList", suborderJson);
                    //If value already present in shared preference from dinner or lunch page, add that to the suborderList of this page
                    if (suborderJson != null) {
                        List<SubOrderBean> l = new ArrayList<SubOrderBean>();
                        Type listType = new TypeToken<ArrayList<SubOrderBean>>() {
                        }.getType();
                        l = new Gson().fromJson(suborderJson, listType);
                        lnSuborderList.addAll(l);
                    }

                    SharedPreferences.Editor itemsEditor = prefs.edit();
                    itemsEditor.putString("SubOrderList", new Gson().toJson(lnSuborderList));
                    itemsEditor.commit();
                    ((Plan1) getActivity()).selectFragment(2);

                } else {
                    Toast.makeText(getContext(), "Selact a Date !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
*/

        Gson json = new Gson();

        List<MenuBean> listMenuBean = new ArrayList<MenuBean>();
        List<MenuBean> lunchlist = new ArrayList<MenuBean>();

        List<SubOrderBean> subOrderBeanList = new ArrayList<SubOrderBean>();
        Type listTypeSuborder = new TypeToken<ArrayList<SubOrderBean>>() {
        }.getType();
        subOrderBeanList = json.fromJson(subOrderList, listTypeSuborder);

        Type listType = new TypeToken<ArrayList<MenuBean>>() {
        }.getType();
        listMenuBean = json.fromJson(menuJsonList, listType);

        for (MenuBean bean : listMenuBean) {
            if (bean.getCourseFlag() == 1) {
                lunchlist.add(bean);
            }
        }





        if(SeeAll.equals("No"))

        {

            for (final SubOrderBean bean : subOrderBeanList) {

                if (foodName != null && !foodName.equals("") && bean.getFoodName().equals(foodName)) {

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

                                                       Integer q = Integer.parseInt(String.valueOf(quantity.getText()));

                                                       if (!minusBtn.isEnabled()) {
                                                           minusBtn.setEnabled(true);
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

                                                        Integer q = Integer.parseInt(String.valueOf(quantity.getText()));

                                                        if (q != 0) {
                                                            String newq = (--q).toString();
                                                            if (Integer.parseInt(newq) == 0) {
                                                                rl.removeView(rowView);
                                                                rl1.removeView(rowView);
                                                                rl1.addView(rowView);
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

        for (int j = 0; j < lunchlist.size(); j++) {

            String foodNameNew = lunchlist.get(j).getItemName();
            double foodItemPrice = lunchlist.get(j).getItemPrice();

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

                                                   Integer q = Integer.parseInt(String.valueOf(quantity.getText()));

                                                   if (!minusBtn.isEnabled()) {
                                                       minusBtn.setEnabled(true);
                                                   }
                                                   String newq = (++q).toString();
                                                   quantity.setText(newq);

                                                   if(Integer.parseInt(newq) == 1) {
                                                       rl1.removeView(rowView);
                                                       rl.removeView(rowView);
                                                       rl.addView(rowView);
                                                   }

                                               }
                                           }
                );

                minusBtn.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    Integer q = Integer.parseInt(String.valueOf(quantity.getText()));

                                                    if (q != 0) {
                                                        String newq = (--q).toString();
                                                        if(Integer.parseInt(newq) == 0){
                                                            rl.removeView(rowView);
                                                            rl1.removeView(rowView);
                                                            rl1.addView(rowView);
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




        /*


        if(foodName != null)
        {
            final View rowView;
            rowView = inflater.inflate(R.layout.home_item_list, container, false);

            TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            TextView extratxt = (TextView) rowView.findViewById(R.id.textView);
            final TextView priceAmount = (TextView) rowView.findViewById(R.id.price);
            Button plusBtn = (Button)rowView.findViewById(R.id.plusButton);
            final Button minusBtn = (Button)rowView.findViewById(R.id.minusButton);
            final EditText quantity = (EditText)rowView.findViewById(R.id.Count);

            plusBtn.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {

                                                 Integer q = Integer.parseInt(String.valueOf(quantity.getText()));

                                                 if(!minusBtn.isEnabled())
                                                 {
                                                     minusBtn.setEnabled(true);
                                                 }
                                                 String newq = (++q).toString();
                                                 double newPrice = Double.valueOf(foodPrice)*Double.valueOf(newq);
                                                 priceAmount.setText(String.valueOf(newPrice));
                                                 quantity.setText(newq);

                                             }
                                         }
            );

            minusBtn.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {

                                               Integer q = Integer.parseInt(String.valueOf(quantity.getText()));

                                               if (q != 0) {
                                                   String newq = (--q).toString();
                                                   double newPrice = Double.valueOf(foodPrice)*Double.valueOf(newq);
                                                   priceAmount.setText(String.valueOf(newPrice));
                                                   quantity.setText(newq);
                                               }
                                           }
                                       }
            );

            txtTitle.setText(foodName);
            imageView.setImageResource(R.drawable.food);
            extratxt.setText("Description of Food Item :  " + foodName);
            //priceAmount.setText(foodPrice);
            priceAmount.setText(foodPrice);
            rl.addView(rowView);

        }

        TextView addNewItem = new TextView(this.getActivity());
        addNewItem.setText("Add More Items");
        addNewItem.setTextSize(20);
        addNewItem.setTextColor(Color.BLACK);
        addNewItem.setVisibility(View.VISIBLE);
        addNewItem.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        addNewItem.setPadding(0, 5, 0, 20);
        addNewItem.setLayoutParams(new ViewGroup.LayoutParams(1000, ViewGroup.LayoutParams.MATCH_PARENT));
        //addNewItem.setLayoutParams(new ViewGroup.LayoutParams(150, ViewGroup.LayoutParams.WRAP_CONTENT));
        rl.addView(addNewItem);

        for (int j = 0; j < lunchlist.size(); j++) {

            String foodNameNew = lunchlist.get(j).getItemName();
            double foodItemPrice = lunchlist.get(j).getItemPrice();

            if (!foodName.equalsIgnoreCase(foodNameNew)) {

                final View rowView;
                rowView = inflater.inflate(R.layout.home_item_list, container, false);

                TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
                ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
                TextView extratxt = (TextView) rowView.findViewById(R.id.textView);
                TextView priceAmount = (TextView) rowView.findViewById(R.id.price);
                Button plusBtn = (Button)rowView.findViewById(R.id.plusButton);
                Button minusBtn = (Button)rowView.findViewById(R.id.minusButton);
                EditText quantity = (EditText)rowView.findViewById(R.id.Count);

                txtTitle.setText(foodNameNew);
                imageView.setImageResource(R.drawable.food);
                extratxt.setText("Description of Food Item :  " + foodNameNew);
                //priceAmount.setText(String.valueOf(foodItemPrice));
                priceAmount.setText(foodPrice);
                rl.addView(rowView);

            }
        }

*/
        //for (int j = 0; j < lunchlist.size(); j++) {

            //CheckBox chk1 = new CheckBox(this.getActivity());
            //chk1.setText(lunchlist.get(j).getItemName());
            //chk1.setTextSize(20);
            //chk1.setVisibility(View.VISIBLE);
            //chk1.setGravity(Gravity.CENTER);
            //rl.addView(chk1);

            /*Button minusBtn = new Button(this.getActivity());
            minusBtn.setText("-");
            minusBtn.setTextSize(20);
            minusBtn.setVisibility(View.VISIBLE);
            minusBtn.setGravity(Gravity.CENTER);
            minusBtn.setId(lunchlist.get(j).getFoodKey());
            minusBtn.setEnabled(false);
            minusBtn.setLayoutParams(new ViewGroup.LayoutParams(150, ViewGroup.LayoutParams.WRAP_CONTENT));
            rl.addView(minusBtn);

            EditText itemCount = new EditText(this.getActivity());
            itemCount.setText("0");
            itemCount.setTextSize(20);
            itemCount.setVisibility(View.VISIBLE);
            itemCount.setGravity(Gravity.CENTER);
            itemCount.setId(lunchlist.get(j).getFoodKey());
            itemCount.setEnabled(false);
            itemCount.setLayoutParams(new ViewGroup.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT));
            rl.addView(itemCount);

            Button plusbtn = new Button(this.getActivity());
            plusbtn.setText("+");
            plusbtn.setTextSize(20);
            plusbtn.setVisibility(View.VISIBLE);
            plusbtn.setGravity(Gravity.CENTER);
            plusbtn.setId(lunchlist.get(j).getFoodKey());
            plusbtn.setEnabled(false);
            plusbtn.setLayoutParams(new ViewGroup.LayoutParams(150, ViewGroup.LayoutParams.WRAP_CONTENT));
            rl.addView(plusbtn);
*/
        //}

        final int size = rl.getChildCount();

        /*for(int k = 0; k < size ; k=k+4 )
        {
            CheckBox item1 = (CheckBox) rl.getChildAt(k);
            final Button btn2 = (Button) rl.getChildAt(k+1);
            final EditText itemQuantity = (EditText) rl.getChildAt(k+2);
            final Button btn1 = (Button) rl.getChildAt(k+3);

            item1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    enableButtons(lunch, btn1, btn2);
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
        }*/

        return lunch;

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