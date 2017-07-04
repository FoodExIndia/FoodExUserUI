package user.com.foodexuserui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.BasicResponseHandler;

/*
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
*/

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import user.com.Entities.DeliveryAddressEntity;
import user.com.Entities.FinalSubOrderBean;
import user.com.Entities.MenuBean;
import user.com.Entities.SubOrderBean;
import user.com.commons.HttpHelper;

public class DeliveryAddress extends AppCompatActivity {

        /*public void onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {*/

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);

        /*final View address;
        address = inflater.inflate(R.layout.activity_my_address, container, false);*/
        final GridLayout rl = (GridLayout) findViewById(R.id.GridDeliveryLayoutAddress);

        final SharedPreferences myAccountPrefs = getSharedPreferences("MyAccountUserData", 0);
        final String userClientKey = myAccountPrefs.getString("myAccountClientKey", "");

        int SDK_INT = android.os.Build.VERSION.SDK_INT;

        try {

            if (SDK_INT > 8) {

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);

                HttpHelper helper = new HttpHelper();
                HttpResponse response = helper.get(("addressList?clientId=" + userClientKey + ""), DeliveryAddress.this);

                //progressDialog = ProgressDialog.show(Login.this,"Loading...",
                //        "Loading application View, please wait...", false, false);

                String responseString = new BasicResponseHandler().handleResponse(response);
                //System.out.println("Response Status:" + responseString);

                Gson json = new Gson();
                List<DeliveryAddressEntity> userAddressBean = null;
                Type listType1 = new TypeToken<List<DeliveryAddressEntity>>() {
                }.getType();

                userAddressBean = json.fromJson(responseString, listType1);
                //System.out.println("User Address List:" + userAddressBean);

                for (final DeliveryAddressEntity bean : userAddressBean) {

                    final View rowView;
                    /*rowView = inflater.inflate(R.layout.address_list_item, container, false);*/
                    rowView = View.inflate(this, R.layout.address_list_item, null);

                    String userAddressLine1 = bean.getClientAddressLine1();
                    //System.out.println("User Address Line 1 : " + userAddressLine1);
                    String userAddressLine2 = bean.getClientAddressLine2();
                    String userArea = bean.getClientArea();

                    TextView addressTitle = (TextView) rowView.findViewById(R.id.address);
                    addressTitle.setText(userAddressLine1);

                    TextView addressdesc = (TextView) rowView.findViewById(R.id.addressDesc);
                    addressdesc.setText(userAddressLine2);

                    rl.addView(rowView);

                }

            } else {

                Toast.makeText(getApplicationContext(), "Error Fetching Address !!!",
                        Toast.LENGTH_SHORT).show();

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.print("Connection Error !!! Try relaunching the App !!!");
            e.printStackTrace();
        }

    /*Button addNewAddressButton = (Button)this.findViewById(R.id.addNewAddress);
    addNewAddressButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });*/

        Button checkOutButton = (Button) this.findViewById(R.id.checkOut);
        checkOutButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                SharedPreferences finalPrefs = getSharedPreferences("SubOrderCart", 0);
                String bfList = finalPrefs.getString("BreakfastSubOrderCartList", "");
                String lnList = finalPrefs.getString("LunchSubOrderCartList", "");
                String dnList = finalPrefs.getString("DinnerSubOrderCartList", "");
                String delivDate = finalPrefs.getString("deliveryDateCart", "");

                Gson json = new Gson();
                //ObjectMapper mapper=new ObjectMapper();

                //ArrayList<SubOrderBean> listMenuBean = new ArrayList<SubOrderBean>();
                //ArrayList<FinalSubOrderBean> Samplelist = new ArrayList<FinalSubOrderBean>();

                ArrayList<Object> listMenuBean = new ArrayList<Object>();
                ArrayList<Object> Samplelist = new ArrayList<Object>();
                FinalSubOrderBean SampleBean = new FinalSubOrderBean();

            /*List<SubOrderBean> subOrderBeanList = new ArrayList<SubOrderBean>();
            Type listTypeSuborder = new TypeToken<ArrayList<SubOrderBean>>() {
            }.getType();*/

                Type listType = new TypeToken<ArrayList<SubOrderBean>>() {
                }.getType();
                /*TypeReference<ArrayList<SubOrderBean>> listType=new TypeReference<ArrayList<SubOrderBean>>() {
                };*/
try {
    listMenuBean = json.fromJson(bfList.replaceAll("\\",""), ArrayList.class);
    Samplelist.add(listMenuBean);
    listMenuBean.clear();
}catch(Exception e)
{
    System.out.print(bfList);
    e.printStackTrace();
}
                //listMenuBean= mapper.convertValue(bfList, listType);

                //Samplelist.add(listMenuBean);
                //listMenuBean.clear();

                ///Intent i = new Intent(DeliveryAddress.this, DeliveryAddress.class);
                //startActivity(i);

            }

        });

    }

}

