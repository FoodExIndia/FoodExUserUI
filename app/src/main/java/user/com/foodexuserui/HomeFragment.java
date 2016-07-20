package user.com.foodexuserui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.LayoutDirection;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vstechlab.easyfonts.EasyFonts;
import com.synnapps.carouselview.*;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import pl.rspective.pagerdatepicker.adapter.DatePagerFragmentAdapter;
import pl.rspective.pagerdatepicker.adapter.DefaultDateAdapter;
import pl.rspective.pagerdatepicker.model.DateItem;
import pl.rspective.pagerdatepicker.view.DateRecyclerView;
import pl.rspective.pagerdatepicker.view.RecyclerViewInsetDecoration;
import user.com.Entities.MenuBean;
import user.com.Entities.SubOrderBean;
import user.com.commons.HomePagerAdapter;
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

                                                     SharedPreferences.Editor editor = prefs.edit();
                                                     editor.clear();
                                                     editor.commit();
                                                     subOrderBeanList.clear();
                                                     subOrderBeanList.add(bfbean);
                                                     SharedPreferences.Editor itemsEditor = prefs.edit();
                                                     itemsEditor.putString("SubOrderList", new Gson().toJson(subOrderBeanList));
                                                     itemsEditor.commit();
                                                     Intent myIntent = new Intent(v.getContext(), Plan1.class);
                                                     startActivity(myIntent.putExtra("foodItem", foodItemName).putExtra("Course", "Breakfast").putExtra("itemPrice",String.format("%.2f", foodPrice)).putExtra("SeeAll","No"));
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


        TextView bfViewAll = (TextView)rootView.findViewById(R.id.bfSeeAll);
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
