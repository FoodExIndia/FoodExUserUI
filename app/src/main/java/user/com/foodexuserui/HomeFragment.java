package user.com.foodexuserui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
//import android.app.AlertDialog;
import android.support.v7.app.AlertDialog;
import java.text.SimpleDateFormat;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vstechlab.easyfonts.EasyFonts;
import com.synnapps.carouselview.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

import user.com.Entities.MenuBean;
import user.com.Entities.SubOrderBean;
import user.com.commons.HorizontalListView;

public class HomeFragment extends Fragment {

    CarouselView carouselView;

    int[] sampleImages = {R.drawable.plan1, R.drawable.plan1, R.drawable.plan1, R.drawable.plan1, R.drawable.plan1};

    HorizontalListView list;
    Integer[] imgid={
            R.drawable.food,
            R.drawable.food,
            R.drawable.food,
            R.drawable.food,
            R.drawable.food,
            R.drawable.food,
            R.drawable.food,
            R.drawable.food
    };

    //pl.rspective.pagerdatepicker.view.DateRecyclerView dateList = null;
    //ViewPager viewPagerDate = null;

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static HomeFragment newInstance() {

        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment() {
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_home, container,
                false);

        carouselView = (CarouselView)rootView.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        final SharedPreferences prefs1 = this.getActivity().getSharedPreferences("MenuData", 0);
        final String menuJsonList = prefs1.getString("list1", "");

        final SharedPreferences prefs = this.getActivity().getSharedPreferences("FoodOrder", 0);
        final String planType = prefs.getString("planType", "");

        final SharedPreferences deliveryDatePrefs = this.getActivity().getSharedPreferences("deliveryDatePrefs", 0);

        final List<SubOrderBean> subOrderBeanList = new ArrayList<SubOrderBean>();
        final List<SubOrderBean> bfSuborderList = new ArrayList<SubOrderBean>();

        Gson json = new Gson();

        List<MenuBean> listMenuBean = new ArrayList<MenuBean>();
        final List<MenuBean> breakfastlist = new ArrayList<MenuBean>();

        Type listType = new TypeToken<ArrayList<MenuBean>>() {
        }.getType();
        listMenuBean = json.fromJson(menuJsonList, listType);

        for (MenuBean bean : listMenuBean) {
            if (bean.getCourseFlag() == 1) {
                breakfastlist.add(bean);
            }
        }

        final GridLayout layoutBFTemp = (GridLayout)rootView.findViewById(R.id.GridLayoutTemp1);

        for (int j = 0; j < breakfastlist.size(); j++) {

            final View rowView;
            rowView = inflater.inflate(R.layout.sample_horizontal_design, container, false);

            if (j < breakfastlist.size()) {

            final String foodItemName = breakfastlist.get(j).getItemName();
            final double foodPrice = breakfastlist.get(j).getItemPrice();

            //final byte[] foodImage = breakfastlist.get(j).getFoodImage();
            //Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage,0,foodImage.length);

            final SubOrderBean bfbean = new SubOrderBean();
            bfbean.setFoodKey(breakfastlist.get(j).getFoodKey());
            bfbean.setCourseFlag(1);
            bfbean.setFoodName(foodItemName);
            bfbean.setFoodQuantity(1);

            String suborderJson = null;
            suborderJson = prefs.getString("SubOrderList", suborderJson);

            if(suborderJson != null) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();

                //If value already present in shared preference from dinner or lunch page, add that to the suborderList of this page
                List<SubOrderBean> l = new ArrayList<SubOrderBean>();
                Type listTypeSubOrder = new TypeToken<ArrayList<SubOrderBean>>() {
                }.getType();
                l = new Gson().fromJson(suborderJson, listTypeSubOrder);
                bfSuborderList.addAll(l);

            }

                TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
                ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
                TextView extratxt = (TextView) rowView.findViewById(R.id.textView);

                txtTitle.setText(foodItemName);
                imageView.setImageResource(R.drawable.food);
                //imageView.setImageBitmap(bitmap);
                extratxt.setText("₹ " + (String.format("%.2f", foodPrice)));

                imageView.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {

                                                     Calendar c = Calendar.getInstance();
                                                     SimpleDateFormat sdf = new SimpleDateFormat("EEEE , MMMM dd");
                                                     String Date1 = sdf.format(c.getTime());
                                                     c.add(Calendar.DATE,1);
                                                     String Date2 = sdf.format(c.getTime());
                                                     c.add(Calendar.DATE,1);
                                                     String Date3 = sdf.format(c.getTime());

                                                     final LayoutInflater inflaterDialog = getActivity().getLayoutInflater();
                                                     final View dialogLayout = inflaterDialog.inflate(R.layout.activity_alertdialog, null);

                                                     RadioButton orderDate1 = (RadioButton) dialogLayout.findViewById(R.id.dateRadio1);
                                                     orderDate1.setText(Date1);
                                                     RadioButton orderDate2 = (RadioButton) dialogLayout.findViewById(R.id.dateRadio2);
                                                     orderDate2.setText(Date2);
                                                     RadioButton orderDate3 = (RadioButton) dialogLayout.findViewById(R.id.dateRadio3);
                                                     orderDate3.setText(Date3);

                                                     //added temporarily to check date selection
                                                     AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                                                     ad.setIcon(R.drawable.cart_icon);
                                                     ad.setTitle(" Select Delivery Date ");
                                                     //ad.setView(LayoutInflater.from(getActivity()).inflate(R.layout.activity_alertdialog, null));
                                                     ad.setView(dialogLayout);

                                                     ad.setPositiveButton("OK",
                                                             new android.content.DialogInterface.OnClickListener() {
                                                                 public void onClick(DialogInterface dialog, int arg1) {
                                                                     // OK, go back to Main menu

                                                                     //LayoutInflater inflaterDialog = getActivity().getLayoutInflater();
                                                                     //View dialogLayout = inflaterDialog.inflate(R.layout.activity_alertdialog, null);

                                                                     final RadioGroup radioGroup = (RadioGroup) dialogLayout.findViewById(R.id.radioGroup1);
                                                                     int selectedId = radioGroup.getCheckedRadioButtonId();
                                                                     final RadioButton radioButton = (RadioButton) dialogLayout.findViewById(selectedId);
                                                                     String deliveryDate = radioButton.getText().toString();

                                                                     //Toast.makeText(getActivity(), deliveryDate, Toast.LENGTH_SHORT).show();

                                                                     SharedPreferences.Editor editor = prefs.edit();
                                                                     editor.clear();
                                                                     editor.commit();
                                                                     subOrderBeanList.clear();

                                                                     SharedPreferences.Editor deliveryDateEditor = deliveryDatePrefs.edit();
                                                                     deliveryDateEditor.putString("deliveryDate", deliveryDate);
                                                                     deliveryDateEditor.commit();

                                                                     subOrderBeanList.add(bfbean);
                                                                     SharedPreferences.Editor itemsEditor = prefs.edit();
                                                                     itemsEditor.putString("SubOrderList", new Gson().toJson(subOrderBeanList));
                                                                     itemsEditor.commit();
                                                                     Intent myIntent = new Intent(getActivity(), Plan1.class);
                                                                     startActivity(myIntent.putExtra("foodItem", foodItemName).putExtra("Course", "Breakfast").putExtra("itemPrice", String.format("%.2f", foodPrice)).putExtra("SeeAll", "No"));
                                                                 }
                                                             }
                                                     );

                                                     ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                                                public void onCancel(DialogInterface dialog) {
                                                                                    // OK, go back to Main menu
                                                                                }
                                                                            }
                                                     );

                                                     ad.show();

                                                     /*SharedPreferences.Editor editor = prefs.edit();
                                                     editor.clear();
                                                     editor.commit();
                                                     subOrderBeanList.clear();
                                                     subOrderBeanList.add(bfbean);
                                                     SharedPreferences.Editor itemsEditor = prefs.edit();
                                                     itemsEditor.putString("SubOrderList", new Gson().toJson(subOrderBeanList));
                                                     itemsEditor.commit();
                                                     Intent myIntent = new Intent(v.getContext(), Plan1.class);
                                                     startActivity(myIntent.putExtra("foodItem", foodItemName).putExtra("Course", "Breakfast").putExtra("itemPrice",String.format("%.2f", foodPrice)).putExtra("SeeAll","No"));*/

                                                 }
                                             }
                );

            layoutBFTemp.addView(rowView);

        }

        if (j == breakfastlist.size()) {

            layoutBFTemp.addView(getViewAll(rowView,"Breakfast"));

        }

        }

        final GridLayout layoutLNTemp = (GridLayout)rootView.findViewById(R.id.GridLayoutTemp2);

        for (int j = 0; j <= breakfastlist.size(); j++) {

            final View rowView;
            rowView = inflater.inflate(R.layout.sample_horizontal_design, container, false);

            if (j < breakfastlist.size()) {

                final String foodItemName = breakfastlist.get(j).getItemName();
                final double foodPrice = breakfastlist.get(j).getItemPrice();

                final SubOrderBean lnbean = new SubOrderBean();
                lnbean.setFoodKey(breakfastlist.get(j).getFoodKey());
                lnbean.setCourseFlag(1);
                lnbean.setFoodName(foodItemName);
                lnbean.setFoodQuantity(1);

                TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
                ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
                TextView extratxt = (TextView) rowView.findViewById(R.id.textView);

                txtTitle.setText(foodItemName);
                imageView.setImageResource(R.drawable.food);
                extratxt.setText("₹ " + (String.format("%.2f", foodPrice)));

                imageView.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     SharedPreferences.Editor editor = prefs.edit();
                                                     editor.clear();
                                                     editor.commit();
                                                     subOrderBeanList.clear();
                                                     subOrderBeanList.add(lnbean);
                                                     SharedPreferences.Editor itemsEditor = prefs.edit();
                                                     itemsEditor.putString("SubOrderList", new Gson().toJson(subOrderBeanList));
                                                     itemsEditor.commit();
                                                     Intent myIntent = new Intent(v.getContext(), Plan1.class);
                                                     startActivity(myIntent.putExtra("foodItem", foodItemName).putExtra("Course", "Lunch").putExtra("itemPrice", String.format("%.2f", foodPrice)).putExtra("SeeAll", "No"));
                                                 }
                                             }
                );

                layoutLNTemp.addView(rowView);

            }

            if (j == breakfastlist.size()) {

                layoutLNTemp.addView(getViewAll(rowView,"Lunch"));

            }
        }


        final GridLayout layoutDNTemp = (GridLayout)rootView.findViewById(R.id.GridLayoutTemp3);

        for (int j = 0; j < breakfastlist.size(); j++) {

            final View rowView;
            rowView = inflater.inflate(R.layout.sample_horizontal_design, container, false);

            if (j < breakfastlist.size()) {

            final String foodItemName = breakfastlist.get(j).getItemName();
            final double foodPrice = breakfastlist.get(j).getItemPrice();

            final SubOrderBean dnbean = new SubOrderBean();
            dnbean.setFoodKey(breakfastlist.get(j).getFoodKey());
            dnbean.setCourseFlag(1);
            dnbean.setFoodName(foodItemName);
            dnbean.setFoodQuantity(1);

            TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            TextView extratxt = (TextView) rowView.findViewById(R.id.textView);

            txtTitle.setText(foodItemName);
            imageView.setImageResource(R.drawable.food);
            extratxt.setText("₹ " + (String.format("%.2f", foodPrice)));

            imageView.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 SharedPreferences.Editor editor = prefs.edit();
                                                 editor.clear();
                                                 editor.commit();
                                                 subOrderBeanList.clear();
                                                 subOrderBeanList.add(dnbean);
                                                 SharedPreferences.Editor itemsEditor = prefs.edit();
                                                 itemsEditor.putString("SubOrderList", new Gson().toJson(subOrderBeanList));
                                                 itemsEditor.commit();
                                                 Intent myIntent = new Intent(v.getContext(), Plan1.class);
                                                 startActivity(myIntent.putExtra("foodItem", foodItemName).putExtra("Course", "Dinner").putExtra("itemPrice", String.format("%.2f", foodPrice)).putExtra("SeeAll","No"));
                                             }
                                         }
            );

        layoutDNTemp.addView(rowView);

    }

    if (j == breakfastlist.size()) {

        layoutDNTemp.addView(getViewAll(rowView,"Lunch"));

    }

        }


        final GridLayout layoutAddOnTemp = (GridLayout)rootView.findViewById(R.id.GridLayoutTemp4);

        for (int j = 0; j < breakfastlist.size(); j++) {

            final String foodItemName = breakfastlist.get(j).getItemName();
            final double foodPrice = breakfastlist.get(j).getItemPrice();

            final View rowView;
            rowView = inflater.inflate(R.layout.sample_horizontal_design, container, false);

            TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            TextView extratxt = (TextView) rowView.findViewById(R.id.textView);

            txtTitle.setText(foodItemName);
            imageView.setImageResource(R.drawable.food);
            extratxt.setText("₹ " + (String.format("%.2f", foodPrice)));

            imageView.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 Intent myIntent = new Intent(v.getContext(), Plan1.class);
                                                 startActivity(myIntent.putExtra("foodItem", foodItemName).putExtra("Course", "AddOn").putExtra("itemPrice", String.format("%.2f", foodPrice)).putExtra("SeeAll","No"));
                                             }
                                         }
            );

            layoutAddOnTemp.addView(rowView);

        }


        final GridLayout layoutComboTemp = (GridLayout)rootView.findViewById(R.id.GridLayoutTemp5);

        for (int j = 0; j < breakfastlist.size(); j++) {

            final String foodItemName = breakfastlist.get(j).getItemName();
            final double foodPrice = breakfastlist.get(j).getItemPrice();

            final View rowView;
            rowView = inflater.inflate(R.layout.sample_horizontal_design, container, false);

            TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            TextView extratxt = (TextView) rowView.findViewById(R.id.textView);

            txtTitle.setText(foodItemName);
            imageView.setImageResource(R.drawable.food);
            extratxt.setText("₹ " + (String.format("%.2f", foodPrice)));

            imageView.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 Intent myIntent = new Intent(v.getContext(), Plan1.class);
                                                 startActivity(myIntent.putExtra("foodItem", foodItemName).putExtra("Course", "Combo").putExtra("itemPrice", String.format("%.2f", foodPrice)).putExtra("SeeAll","Yes"));
                                             }
                                         }
            );

            layoutComboTemp.addView(rowView);

        }

////////////////////////////////////////////////////////////////////// Commented to test View All Button Click //////////////////////////////////////////////////////


        /*TextView bfViewAll = (TextView)rootView.findViewById(R.id.bfSeeAll);
        bfViewAll.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             Intent myIntent = new Intent(v.getContext(), Plan1.class);
                                             startActivity(myIntent.putExtra("foodItem", "").putExtra("Course", "BreakFast").putExtra("itemPrice", 1.00).putExtra("SeeAll","Yes"));
                                             //startActivity(myIntent.putExtra("foodItem", "").putExtra("Course", "").putExtra("itemPrice", ""));
                                         }
                                     }
        );

        TextView lnViewAll = (TextView)rootView.findViewById(R.id.lnSeeAll);
        lnViewAll.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             Intent myIntent = new Intent(v.getContext(), Plan1.class);
                                             startActivity(myIntent.putExtra("foodItem", "").putExtra("Course", "Lunch").putExtra("itemPrice", 1.00).putExtra("SeeAll","Yes"));
                                             //startActivity(myIntent.putExtra("foodItem", "").putExtra("Course", "").putExtra("itemPrice", ""));
                                         }
                                     }
        );

        TextView dnViewAll = (TextView)rootView.findViewById(R.id.dnSeeAll);
        dnViewAll.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             Intent myIntent = new Intent(v.getContext(), Plan1.class);
                                             startActivity(myIntent.putExtra("foodItem", "").putExtra("Course", "Dinner").putExtra("itemPrice", 1.00).putExtra("SeeAll","Yes"));
                                             //startActivity(myIntent.putExtra("foodItem", "").putExtra("Course", "").putExtra("itemPrice", ""));
                                         }
                                     }
        );*/


        TextView bfViewAll = (TextView)rootView.findViewById(R.id.bfSeeAll);
        bfViewAll.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View rowView) {

                                             Gson json = new Gson();

                                             List<MenuBean> listMenuBean = new ArrayList<MenuBean>();
                                             List<MenuBean> breakfastlist = new ArrayList<MenuBean>();

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

                                             final LayoutInflater inflaterDialogBF = getActivity().getLayoutInflater();
                                             final View dialogLayoutBF = inflaterDialogBF.inflate(R.layout.activity_breakfast, null);

                                             final GridLayout rl = (GridLayout) dialogLayoutBF.findViewById(R.id.GridLayoutBreakfast);
                                             rl.removeAllViews();

                                             for (int j = 0; j < breakfastlist.size(); j++) {

                                                 String foodNameNew = breakfastlist.get(j).getItemName();
                                                 double foodItemPrice = breakfastlist.get(j).getItemPrice();
                                                 int foodKey = breakfastlist.get(j).getFoodKey();

                                                 rowView = inflaterDialogBF.inflate(R.layout.home_item_list, null);

                                                 TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
                                                 ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
                                                 TextView extratxt = (TextView) rowView.findViewById(R.id.textView);
                                                 TextView priceAmount = (TextView) rowView.findViewById(R.id.price);
                                                 Button plusBtn = (Button) rowView.findViewById(R.id.plusButton);
                                                 Button minusBtn = (Button) rowView.findViewById(R.id.minusButton);
                                                 EditText quantity = (EditText) rowView.findViewById(R.id.Count);
                                                 txtTitle.setText(foodNameNew);
                                                 imageView.setImageResource(R.drawable.food);
                                                 extratxt.setText("Description of Food Item :  " + foodNameNew);
                                                 priceAmount.setText(String.valueOf(foodItemPrice));
                                                 rl.addView(rowView);

                                             }


                                             //added temporarily to check date selection
                                                                                  AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                                                                                  ad.setIcon(R.drawable.cart_icon);
                                                                                  ad.setTitle(" View BreakFast Items !!! ");
                                                                                  ad.setView(dialogLayoutBF);

                                                                                  ad.setPositiveButton("OK",
                                                                                          new android.content.DialogInterface.OnClickListener() {
                                                                                              public void onClick(DialogInterface dialog, int arg1) {
                                                                                                  // OK, go back to Main menu

                                                                                                  //Intent myIntent = new Intent(getActivity(), Plan1.class);
                                                                                                  //startActivity(myIntent.putExtra("foodItem", foodItemName).putExtra("Course", "Breakfast").putExtra("itemPrice", String.format("%.2f", foodPrice)).putExtra("SeeAll", "No"));
                                                                                              }
                                                                                          }
                                                                                  );

                                             android.support.v7.app.AlertDialog allBF = ad.create();
                                             allBF.show();
                                             allBF.getWindow().setLayout(1050,1450);

                                         }
                                     }
        );

        TextView lnViewAll = (TextView)rootView.findViewById(R.id.lnSeeAll);
        lnViewAll.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View rowView) {

                                             //Intent myIntent = new Intent(v.getContext(), Plan1.class);
                                             //startActivity(myIntent.putExtra("foodItem", "").putExtra("Course", "Lunch").putExtra("itemPrice", 1.00).putExtra("SeeAll","Yes"));
                                             //startActivity(myIntent.putExtra("foodItem", "").putExtra("Course", "").putExtra("itemPrice", ""));

                                             Gson json = new Gson();

                                             List<MenuBean> listMenuBean = new ArrayList<MenuBean>();
                                             List<MenuBean> lunchlist = new ArrayList<MenuBean>();

                                             List<SubOrderBean> subOrderBeanList = new ArrayList<SubOrderBean>();
                                             Type listTypeSuborder = new TypeToken<ArrayList<SubOrderBean>>() {
                                             }.getType();
                                             //subOrderBeanList = json.fromJson(subOrderList, listTypeSuborder);

                                             Type listType = new TypeToken<ArrayList<MenuBean>>() {
                                             }.getType();
                                             listMenuBean = json.fromJson(menuJsonList, listType);

                                             for (MenuBean bean : listMenuBean) {
                                                 if (bean.getCourseFlag() == 1) {
                                                     lunchlist.add(bean);
                                                 }
                                             }

                                             final LayoutInflater inflaterDialogLN = getActivity().getLayoutInflater();
                                             final View dialogLayoutLN = inflaterDialogLN.inflate(R.layout.activity_lunch, null);

                                             final GridLayout rl = (GridLayout) dialogLayoutLN.findViewById(R.id.GridLayoutLunch);
                                             rl.removeAllViews();

                                             for (int j = 0; j < lunchlist.size(); j++) {

                                                 String foodNameNew = lunchlist.get(j).getItemName();
                                                 double foodItemPrice = lunchlist.get(j).getItemPrice();
                                                 int foodKey = lunchlist.get(j).getFoodKey();

                                                 rowView = inflaterDialogLN.inflate(R.layout.home_item_list, null);

                                                 TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
                                                 ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
                                                 TextView extratxt = (TextView) rowView.findViewById(R.id.textView);
                                                 TextView priceAmount = (TextView) rowView.findViewById(R.id.price);
                                                 Button plusBtn = (Button) rowView.findViewById(R.id.plusButton);
                                                 Button minusBtn = (Button) rowView.findViewById(R.id.minusButton);
                                                 EditText quantity = (EditText) rowView.findViewById(R.id.Count);
                                                 txtTitle.setText(foodNameNew);
                                                 imageView.setImageResource(R.drawable.food);
                                                 extratxt.setText("Description of Food Item :  " + foodNameNew);
                                                 priceAmount.setText(String.valueOf(foodItemPrice));
                                                 rl.addView(rowView);

                                             }


                                             //added temporarily to check date selection
                                             AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                                             ad.setIcon(R.drawable.cart_icon);
                                             ad.setTitle(" View Lunch Items !!! ");
                                             ad.setView(dialogLayoutLN);

                                             ad.setPositiveButton("OK",
                                                     new android.content.DialogInterface.OnClickListener() {
                                                         public void onClick(DialogInterface dialog, int arg1) {
                                                             // OK, go back to Main menu

                                                             //Intent myIntent = new Intent(getActivity(), Plan1.class);
                                                             //startActivity(myIntent.putExtra("foodItem", foodItemName).putExtra("Course", "Breakfast").putExtra("itemPrice", String.format("%.2f", foodPrice)).putExtra("SeeAll", "No"));
                                                         }
                                                     }
                                             );

                                             android.support.v7.app.AlertDialog allLN = ad.create();
                                             allLN.show();
                                             allLN.getWindow().setLayout(1050,1450);

                                         }
                                     }
        );

        TextView dnViewAll = (TextView)rootView.findViewById(R.id.dnSeeAll);
        dnViewAll.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View rowView) {

                                             //Intent myIntent = new Intent(v.getContext(), Plan1.class);
                                             //startActivity(myIntent.putExtra("foodItem", "").putExtra("Course", "Dinner").putExtra("itemPrice", 1.00).putExtra("SeeAll","Yes"));
                                             //startActivity(myIntent.putExtra("foodItem", "").putExtra("Course", "").putExtra("itemPrice", ""));

                                             Gson json = new Gson();

                                             List<MenuBean> listMenuBean = new ArrayList<MenuBean>();
                                             List<MenuBean> dinnerlist = new ArrayList<MenuBean>();

                                             List<SubOrderBean> subOrderBeanList = new ArrayList<SubOrderBean>();
                                             Type listTypeSuborder = new TypeToken<ArrayList<SubOrderBean>>() {
                                             }.getType();
                                             //subOrderBeanList = json.fromJson(subOrderList, listTypeSuborder);

                                             Type listType = new TypeToken<ArrayList<MenuBean>>() {
                                             }.getType();
                                             listMenuBean = json.fromJson(menuJsonList, listType);

                                             for (MenuBean bean : listMenuBean) {
                                                 if (bean.getCourseFlag() == 1) {
                                                     dinnerlist.add(bean);
                                                 }
                                             }

                                             final LayoutInflater inflaterDialogDN = getActivity().getLayoutInflater();
                                             final View dialogLayoutDN = inflaterDialogDN.inflate(R.layout.activity_dinner, null);

                                             final GridLayout rl = (GridLayout) dialogLayoutDN.findViewById(R.id.GridLayoutDinner);
                                             rl.removeAllViews();

                                             for (int j = 0; j < dinnerlist.size(); j++) {

                                                 String foodNameNew = dinnerlist.get(j).getItemName();
                                                 double foodItemPrice = dinnerlist.get(j).getItemPrice();
                                                 int foodKey = dinnerlist.get(j).getFoodKey();

                                                 rowView = inflaterDialogDN.inflate(R.layout.home_item_list, null);

                                                 TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
                                                 ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
                                                 TextView extratxt = (TextView) rowView.findViewById(R.id.textView);
                                                 TextView priceAmount = (TextView) rowView.findViewById(R.id.price);
                                                 Button plusBtn = (Button) rowView.findViewById(R.id.plusButton);
                                                 Button minusBtn = (Button) rowView.findViewById(R.id.minusButton);
                                                 EditText quantity = (EditText) rowView.findViewById(R.id.Count);
                                                 txtTitle.setText(foodNameNew);
                                                 imageView.setImageResource(R.drawable.food);
                                                 extratxt.setText("Description of Food Item :  " + foodNameNew);
                                                 priceAmount.setText(String.valueOf(foodItemPrice));
                                                 rl.addView(rowView);

                                             }


                                             //added temporarily to check date selection
                                             AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                                             ad.setIcon(R.drawable.cart_icon);
                                             ad.setTitle(" View Dinner Items !!! ");
                                             ad.setView(dialogLayoutDN);

                                             ad.setPositiveButton("OK",
                                                     new android.content.DialogInterface.OnClickListener() {
                                                         public void onClick(DialogInterface dialog, int arg1) {
                                                             // OK, go back to Main menu

                                                             //Intent myIntent = new Intent(getActivity(), Plan1.class);
                                                             //startActivity(myIntent.putExtra("foodItem", foodItemName).putExtra("Course", "Breakfast").putExtra("itemPrice", String.format("%.2f", foodPrice)).putExtra("SeeAll", "No"));
                                                         }
                                                     }
                                             );

                                             android.support.v7.app.AlertDialog allDN = ad.create();
                                             allDN.show();
                                             allDN.getWindow().setLayout(1050,1450);


                                         }
                                     }
        );


        TextView bfText = (TextView)rootView.findViewById(R.id.bfText);
        bfText.setTypeface(EasyFonts.droidSerifRegular(this.getContext()));

        TextView lnText = (TextView)rootView.findViewById(R.id.lnText);
        lnText.setTypeface(EasyFonts.droidSerifRegular(this.getContext()));

        TextView dnText = (TextView)rootView.findViewById(R.id.dnText);
        dnText.setTypeface(EasyFonts.droidSerifRegular(this.getContext()));

        TextView addOnText = (TextView)rootView.findViewById(R.id.AddOnText);
        addOnText.setTypeface(EasyFonts.droidSerifRegular(this.getContext()));

        TextView comboText = (TextView)rootView.findViewById(R.id.ComboText);
        comboText.setTypeface(EasyFonts.droidSerifRegular(this.getContext()));

        //ArrayList<View> v1 = new ArrayList<View>();
        //final Plan1 plan1 = new Plan1();
        //final Intent intent = null;

        //LinearLayout layoutBF = (LinearLayout) rootView.findViewById(R.id.image_containerBF);
        /*GridLayout layoutBF = (GridLayout)rootView.findViewById(R.id.GridLayoutTemp1);
        LinearLayout.LayoutParams layoutParamsBF = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < 5; i++) {
            //layoutParamsBF.setMargins(50, 50, 50, 50);
            //layoutParamsBF.gravity = Gravity.CENTER;

            final String foodItemName = breakfastlist.get(i).getItemName();

            ImageView imageViewBF = new ImageView(this.getActivity());
            imageViewBF.setImageResource(R.drawable.food);
            layoutBF.addView(imageViewBF);
            android.view.ViewGroup.LayoutParams layoutParams = imageViewBF.getLayoutParams();
            layoutParams.width = 300;
            layoutParams.height = 300;
            imageViewBF.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 Intent myIntent = new Intent(v.getContext(),Plan1.class);
                                                 startActivity(myIntent.putExtra("foodItem", foodItemName).putExtra("Course", "Breakfast"));
                                             }
                                         }
            );

        }

        for (int i = 0; i < 5; i++) {

            TextView bfItem = new TextView(this.getActivity());
            bfItem.setTextSize(20);
            bfItem.setText(breakfastlist.get(i).getItemName());
            bfItem.setTextColor(Color.BLUE);
            layoutBF.addView(bfItem);
            ((GridLayout.LayoutParams)bfItem.getLayoutParams()).setGravity(Gravity.CENTER_HORIZONTAL);
        }

        for (int i = 0; i < 5; i++) {

            float x = (float) breakfastlist.get(i).getItemPrice();
            TextView bfItem = new TextView(this.getActivity());
            bfItem.setTextSize(20);
            bfItem.setText(String.format("%.2f",x));
            bfItem.setTextColor(Color.BLUE);
            layoutBF.addView(bfItem);
            ((GridLayout.LayoutParams)bfItem.getLayoutParams()).setGravity(Gravity.CENTER_HORIZONTAL);
        }
*/


        /*GridLayout layoutLN = (GridLayout) rootView.findViewById(R.id.GridLayoutTemp2);
        LinearLayout.LayoutParams layoutParamsLN = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < 5; i++) {

            final String foodItemName = breakfastlist.get(i).getItemName();

            ImageView imageViewLN = new ImageView(this.getActivity());
            imageViewLN.setImageResource(R.drawable.food);
            layoutLN.addView(imageViewLN);
            android.view.ViewGroup.LayoutParams layoutParams = imageViewLN.getLayoutParams();
            layoutParams.width = 300;
            layoutParams.height = 300;
            imageViewLN.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 Intent myIntent = new Intent(v.getContext(),Plan1.class);
                                                 startActivity(myIntent.putExtra("foodItem", foodItemName).putExtra("Course","Lunch"));
                                             }
                                         }
            );

            *//*ImageView imageView = new ImageView(this.getActivity());
            imageView.setImageResource(R.drawable.food);
            layoutLN.addView(imageView);
            android.view.ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
            layoutParams.width = 400;
            layoutParams.height = 400;*//*

        }

        for (int i = 0; i < 5; i++) {

            TextView lnItem = new TextView(this.getActivity());
            lnItem.setTextSize(20);
            lnItem.setText(breakfastlist.get(i).getItemName());
            lnItem.setTextColor(Color.BLUE);
            layoutLN.addView(lnItem);
            ((GridLayout.LayoutParams)lnItem.getLayoutParams()).setGravity(Gravity.CENTER_HORIZONTAL);
        }

        for (int i = 0; i < 5; i++) {

            float x = (float) breakfastlist.get(i).getItemPrice();
            TextView lnItem = new TextView(this.getActivity());
            lnItem.setTextSize(20);
            lnItem.setText(String.format("%.2f", x));
            lnItem.setTextColor(Color.BLUE);
            layoutLN.addView(lnItem);
            ((GridLayout.LayoutParams)lnItem.getLayoutParams()).setGravity(Gravity.CENTER_HORIZONTAL);
        }


        GridLayout layoutDN = (GridLayout) rootView.findViewById(R.id.GridLayoutTemp3);
        LinearLayout.LayoutParams layoutParamsDN = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < 5; i++) {

            final String foodItemName = breakfastlist.get(i).getItemName();

            ImageView imageViewDN = new ImageView(this.getActivity());
            imageViewDN.setImageResource(R.drawable.food);
            layoutDN.addView(imageViewDN);
            android.view.ViewGroup.LayoutParams layoutParams = imageViewDN.getLayoutParams();
            layoutParams.width = 300;
            layoutParams.height = 300;
            imageViewDN.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 Intent myIntent = new Intent(v.getContext(),Plan1.class);
                                                 startActivity(myIntent.putExtra("foodItem", foodItemName).putExtra("Course","Dinner"));
                                             }
                                         }
            );

        }

        for (int i = 0; i < 5; i++) {

            TextView dnItem = new TextView(this.getActivity());
            dnItem.setTextSize(20);
            dnItem.setText(breakfastlist.get(i).getItemName());
            dnItem.setTextColor(Color.BLUE);
            layoutDN.addView(dnItem);
            ((GridLayout.LayoutParams)dnItem.getLayoutParams()).setGravity(Gravity.CENTER_HORIZONTAL);
        }

        for (int i = 0; i < 5; i++) {

            float x = (float) breakfastlist.get(i).getItemPrice();
            TextView dnItem = new TextView(this.getActivity());
            dnItem.setTextSize(20);
            dnItem.setText(String.format("%.2f", x));
            dnItem.setTextColor(Color.BLUE);
            layoutDN.addView(dnItem);
            ((GridLayout.LayoutParams)dnItem.getLayoutParams()).setGravity(Gravity.CENTER_HORIZONTAL);
        }
*/
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((NavigationDrawer) activity).onSectionAttached(1);
    }

    public View getViewAll(View rowView, final String course){

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView);

        txtTitle.setText("View All");
        imageView.setImageResource(R.drawable.food);
        extratxt.setText("");

        imageView.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             Intent myIntent = new Intent(v.getContext(), Plan1.class);
                                             startActivity(myIntent.putExtra("foodItem", "").putExtra("Course", course).putExtra("itemPrice", 1.00).putExtra("SeeAll","Yes"));
                                         }
                                     }
        );

        return rowView;

    }

}
