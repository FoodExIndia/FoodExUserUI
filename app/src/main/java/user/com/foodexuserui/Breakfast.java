package user.com.foodexuserui;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import user.com.Entities.MenuBean;
import user.com.Entities.SubOrderBean;

import static android.support.v4.app.ActivityCompat.invalidateOptionsMenu;

public class Breakfast extends Fragment {

    public Breakfast() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Button getBreakfastMenu;
    android.support.v7.app.ActionBar actionBar;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final SimpleDateFormat sdf = new SimpleDateFormat("d-M-yyyy");
        final SharedPreferences menuItems = this.getActivity().getSharedPreferences("PlanMenu", 0);

        final View breakfast;
        breakfast = inflater.inflate(R.layout.activity_breakfast, container, false);

        final View plan1;
        plan1 = inflater.inflate(R.layout.activity_plan1, container, false);

        final String foodName = getActivity().getIntent().getExtras().getString("foodItem").toString();
        final String SeeAll = getActivity().getIntent().getExtras().getString("SeeAll").toString();
        final String foodPrice = getActivity().getIntent().getExtras().getString("itemPrice");

        final SharedPreferences imgandDate = this.getActivity().getSharedPreferences("PlanData", 0);
        final SharedPreferences prefs1 = this.getActivity().getSharedPreferences("MenuData", 0);
        final String menuJsonList = prefs1.getString("list1", "");

        final SharedPreferences prefs = this.getActivity().getSharedPreferences("FoodOrder", 0);
        final String subOrderList = prefs.getString("SubOrderList", "");

        final List<SubOrderBean> bfSuborderList = new ArrayList<SubOrderBean>();

        //final SharedPreferences imgandDate = this.getActivity().getSharedPreferences("PlanData", 0);

        final GridLayout rl = (GridLayout) breakfast.findViewById(R.id.GridLayoutBreakfast);
        rl.removeAllViews();
        //final GridLayout rl1 = (GridLayout) breakfast.findViewById(R.id.GridLayoutBreakfast1);
        //rl1.removeAllViews();
        final Plan1 planCart = new Plan1();

        //final EditText dpDate = (EditText) plan1.findViewById(R.id.plan1DatePicker);

        //getBreakfastMenu = (Button) breakfast.findViewById(R.id.buttonBF);

        /*getBreakfastMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final List<SubOrderBean> bfSuborderList = new ArrayList<SubOrderBean>();
                String datecheck = imgandDate.getString("planDate", "");
                String currentDate = ((Plan1) getActivity()).getDPdate();

                if (!datecheck.equals("")) {

                    SharedPreferences.Editor editor = menuItems.edit();
                    Gson gson = new Gson();

                    for (int k = 0; k < rl.getChildCount(); k = k + 4) {

                        CheckBox item2 = (CheckBox) rl.getChildAt(k);
                        String foodName = item2.getText().toString();
                        final EditText foodQuantity = (EditText) rl.getChildAt(k + 2);
                        int itemQuantity2 = Integer.parseInt(foodQuantity.getText().toString());

                        if (item2.isChecked() && itemQuantity2 > 0) {
                            SubOrderBean bfbean = new SubOrderBean();
                            bfbean.setFoodKey(foodQuantity.getId());
                            bfbean.setCourseFlag(1);
                            bfbean.setFoodName(foodName);
                            bfbean.setFoodQuantity(itemQuantity2);

                            try {
                                bfbean.setDate(sdf.parse(currentDate));
                            } catch (ParseException e) {
                                System.out.print(e.getMessage());
                            }

                            bfSuborderList.add(bfbean);

                        }
                    }

                    String suborderJson = null;
                    suborderJson = prefs.getString("SubOrderList", suborderJson);
                    //If value already present in shared preference from dinner or lunch page, add that to the suborderList of this page
                    if (suborderJson != null) {
                        List<SubOrderBean> l = new ArrayList<SubOrderBean>();
                        Type listType = new TypeToken<ArrayList<SubOrderBean>>() {
                        }.getType();
                        l = new Gson().fromJson(suborderJson, listType);
                        bfSuborderList.addAll(l);

                    }

                    SharedPreferences.Editor itemsEditor = prefs.edit();
                    itemsEditor.putString("SubOrderList", new Gson().toJson(bfSuborderList));
                    itemsEditor.commit();
                    ((Plan1) getActivity()).selectFragment(1);
                } else {
                    Toast.makeText(getContext(), "Selact a Date !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
*/
        Gson json = new Gson();

        List<MenuBean> listMenuBean = new ArrayList<MenuBean>();
        final List<MenuBean> breakfastlist = new ArrayList<MenuBean>();

        List<SubOrderBean> subOrderBeanList = new ArrayList<SubOrderBean>();
        Type listTypeSuborder = new TypeToken<ArrayList<SubOrderBean>>() {
        }.getType();
        //subOrderBeanList = json.fromJson(subOrderList, listTypeSuborder);

        Type listType = new TypeToken<ArrayList<MenuBean>>() {
        }.getType();
        listMenuBean = json.fromJson(menuJsonList, listType);

        for (MenuBean bean : listMenuBean) {
            if (bean.getCourseFlag() == 1) {
                breakfastlist.add(bean);
            }
        }

/*if(SeeAll.equals("No"))
{
    for (final SubOrderBean bean : subOrderBeanList) {

        //&& bean.getFoodName().equals(foodName)

        if (foodName != null && !foodName.equals("")) {

            //String itemCount = String.valueOf(bean.getFoodQuantity());

            final View rowView;
            rowView = inflater.inflate(R.layout.home_item_list, container, false);

            TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            TextView extratxt = (TextView) rowView.findViewById(R.id.textView);
            final TextView priceAmount = (TextView) rowView.findViewById(R.id.price);
            Button plusBtn = (Button) rowView.findViewById(R.id.plusButton);
            final Button minusBtn = (Button) rowView.findViewById(R.id.minusButton);
            final EditText quantity = (EditText) rowView.findViewById(R.id.Count);

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

            txtTitle.setText(bean.getFoodName());
            imageView.setImageResource(R.drawable.food);
            //quantity.setText();
            //byte[] blob = getBlob("");
            //Bitmap bitmap = BitmapFactory.decodeByteArray()
            //imageView.setImageBitmap(bitmap);
            extratxt.setText("Description of Food Item :  " + foodName);
            priceAmount.setText(foodPrice);
            //quantity.setText(String.valueOf(bean.getFoodQuantity()));
            quantity.setText("7");
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
*/
            for (int j = 0; j < breakfastlist.size(); j++) {

                final String foodNameNew = breakfastlist.get(j).getItemName();
                double foodItemPrice = breakfastlist.get(j).getItemPrice();
                final int foodKey = breakfastlist.get(j).getFoodKey();

                /*List<String> foodItemList = new ArrayList<String>();
                for (final SubOrderBean bean : subOrderBeanList) {
                    foodItemList.add(bean.getFoodName());
                }*/

                //if (foodItemList.contains(foodNameNew)) {

                    final View rowView;
                    rowView = inflater.inflate(R.layout.home_item_list, container, false);

                    TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
                    ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
                    TextView extratxt = (TextView) rowView.findViewById(R.id.textView);
                    TextView priceAmount = (TextView) rowView.findViewById(R.id.price);
                    Button plusBtn = (Button) rowView.findViewById(R.id.plusButton);
                    final Button minusBtn = (Button) rowView.findViewById(R.id.minusButton);
                    final EditText quantity = (EditText) rowView.findViewById(R.id.Count);

                /*if(prefs.contains("bfSubOrderList")) {
                    String suborderList = new String();
                    prefs.getString("bfSubOrderList", suborderList);
                    Gson json1 = new Gson();
                    List<SubOrderBean> newBfBean = new ArrayList<SubOrderBean>();
                    newBfBean = json1.fromJson(subOrderList, listTypeSuborder);
                    for (SubOrderBean updateBean : bfSuborderList) {
                        if (updateBean.getFoodName().equals(foodName)) {
                            quantity.setText(updateBean.getFoodQuantity());
                        }
                    }
                }*/

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

                                                       if (Integer.parseInt(newq)==1) {

                                                           final SubOrderBean bfbean = new SubOrderBean();
                                                           bfbean.setFoodKey(foodKey);
                                                           bfbean.setCourseFlag(1);
                                                           bfbean.setFoodName(foodNameNew);
                                                           bfbean.setFoodQuantity(q);

                                                           String suborderJson = null;
                                                           suborderJson = prefs.getString("bfSubOrderList", suborderJson);

                                                           /*if(suborderJson != null) {
                                                               SharedPreferences.Editor editor = prefs.edit();
                                                               editor.clear();
                                                               editor.commit();
                                                           }

                                                           //If value already present in shared preference from dinner or lunch page, add that to the suborderList of this page
                                                           if (suborderJson != null) {
                                                               List<SubOrderBean> l = new ArrayList<SubOrderBean>();
                                                               Type listTypeSubOrder = new TypeToken<ArrayList<SubOrderBean>>() {
                                                               }.getType();
                                                               l = new Gson().fromJson(suborderJson, listTypeSubOrder);
                                                               bfSuborderList.addAll(l);
                                                           }*/

                                                           bfSuborderList.add(bfbean);
                                                           SharedPreferences.Editor subOrderEditor = prefs.edit();
                                                           subOrderEditor.putString("bfSubOrderList", new Gson().toJson(bfSuborderList));
                                                           subOrderEditor.commit();

                                                           SharedPreferences itemCountInfo = getActivity().getSharedPreferences("itemCount", 0);
                                                           count = itemCountInfo.getString("countOverall", "");
                                                           newCount = Integer.parseInt(count);

                                                           SharedPreferences.Editor itemCounteditor = itemCountInfo.edit();
                                                           itemCounteditor.putString("countOverall", String.valueOf(newCount + 1));
                                                           itemCounteditor.commit();

                                                           planCart.refreshActionBar(getActivity());

                                                       }


                                                       if (Integer.parseInt(newq)>1) {

                                                           String suborderJson = null;
                                                           suborderJson = prefs.getString("bfSubOrderList", suborderJson);

                                                           /*if(suborderJson != null) {
                                                               SharedPreferences.Editor editor = prefs.edit();
                                                               editor.clear();
                                                               editor.commit();
                                                           }*/

                                                           for (SubOrderBean bean:bfSuborderList) {
                                                               if(bean.getFoodKey() == foodKey){
                                                                   bean.setFoodQuantity(Integer.parseInt(newq));
                                                               }
                                                           }

                                                           SharedPreferences.Editor subOrderEditor = prefs.edit();
                                                           subOrderEditor.putString("bfSubOrderList", new Gson().toJson(bfSuborderList));
                                                           subOrderEditor.commit();

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

                                if (Integer.parseInt(newq) > 0) {

                                    String suborderJson = null;
                                    suborderJson = prefs.getString("bfSubOrderList", suborderJson);

                                    /*if(suborderJson != null) {
                                        SharedPreferences.Editor editor = prefs.edit();
                                        editor.clear();
                                        editor.commit();
                                    }*/

                                    for (SubOrderBean bean:bfSuborderList) {
                                        if(bean.getFoodKey() == foodKey){
                                            bean.setFoodQuantity(Integer.parseInt(newq));
                                        }
                                    }

                                    SharedPreferences.Editor subOrderEditor = prefs.edit();
                                    subOrderEditor.putString("bfSubOrderList", new Gson().toJson(bfSuborderList));
                                    subOrderEditor.commit();

                                }


                                if (Integer.parseInt(newq) == 0) {

                                    String suborderJson = null;
                                    suborderJson = prefs.getString("bfSubOrderList", suborderJson);

                                    /*if(suborderJson != null) {
                                        SharedPreferences.Editor editor = prefs.edit();
                                        editor.clear();
                                        editor.commit();
                                    }*/

                                    for (SubOrderBean bean:bfSuborderList) {
                                        if(bean.getFoodKey() == foodKey){
                                            bfSuborderList.remove(bean);
                                        }
                                    }

                                    SharedPreferences.Editor subOrderEditor = prefs.edit();
                                    subOrderEditor.putString("bfSubOrderList", new Gson().toJson(bfSuborderList));
                                    subOrderEditor.commit();

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

                            } else if (q == 0) {

                            }

                    }
                }
                    );

                    txtTitle.setText(foodNameNew);
                    imageView.setImageResource(R.drawable.food);
                    extratxt.setText("Description of Food Item :  " + foodNameNew);
                    priceAmount.setText(String.valueOf(foodItemPrice));
                    rl.addView(rowView);
                }

            //}

        return breakfast;

    }

   /* public void onSaveInstanceState(Bundle outState){
        getFragmentManager().putFragment(outState,"myfragment",getTargetFragment());
    }

    public void onRestoreInstanceState(Bundle inState){
       Fragment fragment = getFragmentManager().getFragment(inState,"myfragment");
    }*/

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

