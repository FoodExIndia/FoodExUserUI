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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    EditText datecheckDP;
    Button getDinnerMenu,placeOrder,nextDateButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final SimpleDateFormat sdf = new SimpleDateFormat("d-M-yyyy");
        final SharedPreferences menuItems = this.getActivity().getSharedPreferences("PlanMenu", 0);

        final View dinner;
        dinner = inflater.inflate(R.layout.activity_dinner, container, false);

        View plan1;
        plan1 = inflater.inflate(R.layout.activity_plan1, container, false);

        final String foodName = getActivity().getIntent().getExtras().getString("foodItem").toString();
        final String foodPrice = getActivity().getIntent().getExtras().getString("itemPrice");
        final String SeeAll = getActivity().getIntent().getExtras().getString("SeeAll").toString();

        final SharedPreferences prefs1 = this.getActivity().getSharedPreferences("MenuData", 0);
        final String menuJsonList = prefs1.getString("list1", "");

        final SharedPreferences prefs = this.getActivity().getSharedPreferences("FoodOrder", 0);
        final String subOrderList = prefs.getString("SubOrderList", "");

        final List<SubOrderBean> dnSuborderList = new ArrayList<SubOrderBean>();

        //final SharedPreferences imgandDate = this.getActivity().getSharedPreferences("PlanData", 0);

        final GridLayout rl = (GridLayout) dinner.findViewById(R.id.GridLayoutDinner);
        rl.removeAllViews();
        /*final GridLayout rl1 = (GridLayout) dinner.findViewById(R.id.GridLayoutDinner1);
        rl1.removeAllViews();*/

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


        Gson json = new Gson();

        List<MenuBean> listMenuBean = new ArrayList<MenuBean>();
        List<MenuBean> dinnerlist = new ArrayList<MenuBean>();

        List<SubOrderBean> subOrderBeanList = new ArrayList<SubOrderBean>();
        Type listTypeSuborder = new TypeToken<ArrayList<SubOrderBean>>() {
        }.getType();
        //subOrderBeanList = json.fromJson(dnsubOrderList, listTypeSuborder);

        Type listType = new TypeToken<ArrayList<MenuBean>>() {
        }.getType();
        listMenuBean = json.fromJson(menuJsonList, listType);

        for (MenuBean bean : listMenuBean) {
            if (bean.getCourseFlag() == 1) {
                dinnerlist.add(bean);
            }
        }


        for (int j = 0; j < dinnerlist.size(); j++) {

            final String foodNameNew = dinnerlist.get(j).getItemName();
            double foodItemPrice = dinnerlist.get(j).getItemPrice();
            final int foodKey = dinnerlist.get(j).getFoodKey();

            /*if (!foodName.equalsIgnoreCase(foodNameNew)) {*/

                final View rowView;
                rowView = inflater.inflate(R.layout.home_item_list, container, false);

                TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
                ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
                TextView extratxt = (TextView) rowView.findViewById(R.id.textView);
                TextView priceAmount = (TextView) rowView.findViewById(R.id.price);
                Button plusBtn = (Button) rowView.findViewById(R.id.plusButton);
                final Button minusBtn = (Button) rowView.findViewById(R.id.minusButton);
                final EditText quantity = (EditText) rowView.findViewById(R.id.Count);

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

                            final SubOrderBean dnbean = new SubOrderBean();
                            dnbean.setFoodKey(foodKey);
                            dnbean.setCourseFlag(1);
                            dnbean.setFoodName(foodNameNew);
                            dnbean.setFoodQuantity(q);

                            String suborderJson = null;
                            suborderJson = prefs.getString("dnSubOrderList", suborderJson);

                            /*if (suborderJson != null) {
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.clear();
                                editor.commit();
                            }

                            //If value already present in shared preference from dinner or lunch page, add that to the suborderList of this page

                                                       /*if (suborderJson != null) {
                                                           List<SubOrderBean> l = new ArrayList<SubOrderBean>();
                                                           Type listTypeSubOrder = new TypeToken<ArrayList<SubOrderBean>>() {
                                                           }.getType();
                                                           l = new Gson().fromJson(suborderJson, listTypeSubOrder);
                                                           dnSuborderList.addAll(l);
                                                       } */

                                                       dnSuborderList.add(dnbean);
                                                       SharedPreferences.Editor subOrderEditor = prefs.edit();
                                                       subOrderEditor.putString("dnSubOrderList", new Gson().toJson(dnSuborderList));
                                                       subOrderEditor.commit();

                            SharedPreferences itemCountInfo = getActivity().getSharedPreferences("itemCount", 0);
                            count = itemCountInfo.getString("countOverall", "");
                            newCount = Integer.parseInt(count);

                            SharedPreferences.Editor itemCounteditor = itemCountInfo.edit();
                            itemCounteditor.putString("countOverall", String.valueOf(newCount + 1));
                            itemCounteditor.commit();

                            planCart.refreshActionBar(getActivity());

                        }

                        if (Integer.parseInt(newq) > 1) {

                            String suborderJson = null;
                            suborderJson = prefs.getString("dnSubOrderList", suborderJson);

                            /*if (suborderJson != null) {
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.clear();
                                editor.commit();
                            }*/

                            for (SubOrderBean bean : dnSuborderList) {
                                if (bean.getFoodKey() == foodKey) {
                                    bean.setFoodQuantity(Integer.parseInt(newq));
                                }
                            }

                            SharedPreferences.Editor subOrderEditor = prefs.edit();
                            subOrderEditor.putString("dnSubOrderList", new Gson().toJson(dnSuborderList));
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
                                                            suborderJson = prefs.getString("dnSubOrderList", suborderJson);

                                                            /*if(suborderJson != null) {
                                                                SharedPreferences.Editor editor = prefs.edit();
                                                                editor.clear();
                                                                editor.commit();
                                                            }*/

                                                            for (SubOrderBean bean:dnSuborderList) {
                                                                if(bean.getFoodKey() == foodKey){
                                                                    bean.setFoodQuantity(Integer.parseInt(newq));
                                                                }
                                                            }

                                                            SharedPreferences.Editor subOrderEditor = prefs.edit();
                                                            subOrderEditor.putString("dnSubOrderList", new Gson().toJson(dnSuborderList));
                                                            subOrderEditor.commit();

                                                        }


                                                        if (Integer.parseInt(newq) == 0) {

                                                            String suborderJson = null;
                                                            suborderJson = prefs.getString("dnSubOrderList", suborderJson);

                                                            /*if(suborderJson != null) {
                                                                SharedPreferences.Editor editor = prefs.edit();
                                                                editor.clear();
                                                                editor.commit();
                                                            }*/

                                                            for (SubOrderBean bean:dnSuborderList) {
                                                                if(bean.getFoodKey() == foodKey){
                                                                    dnSuborderList.remove(bean);
                                                                }
                                                            }

                                                            SharedPreferences.Editor subOrderEditor = prefs.edit();
                                                            subOrderEditor.putString("dnSubOrderList", new Gson().toJson(dnSuborderList));
                                                            subOrderEditor.commit();

                                                            SharedPreferences itemCountInfo = getActivity().getSharedPreferences("itemCount", 0);
                                                            count = itemCountInfo.getString("countOverall", "");
                                                            newCount = Integer.parseInt(count);

                                                            SharedPreferences.Editor itemCounteditor = itemCountInfo.edit();
                                                            itemCounteditor.putString("countOverall", String.valueOf(newCount - 1));
                                                            itemCounteditor.commit();

                                                            planCart.refreshActionBar(getActivity());

                                                        }

                                                        //double newPrice = Double.valueOf
                                                        // (foodPrice) * Double.valueOf(newq);
                                                        //priceAmount.setText(String.valueOf(newPrice));
                                                        quantity.setText(newq);
                                                    }
                                                }
                                            }
                );


                txtTitle.setText(foodNameNew);
                imageView.setImageResource(R.drawable.food);
                extratxt.setText("Description of Food Item :  " + foodNameNew);
                priceAmount.setText(String.valueOf(foodItemPrice));
                rl.addView(rowView);
            /*}*/

        }

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
