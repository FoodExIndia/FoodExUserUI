package user.com.foodexuserui.NavDrawerPages;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.BasicResponseHandler;

import java.lang.reflect.Type;
import java.util.List;

import user.com.Entities.DeliveryAddressEntity;
import user.com.Entities.SubOrderBean;
import user.com.Entities.UserDetails;
import user.com.commons.HttpHelper;
import user.com.foodexuserui.R;
import user.com.foodexuserui.SignUpAddressInfo;

public class MyAddress extends AppCompatActivity {

    /*public void onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {*/

        @Override
        protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        /*final View address;
        address = inflater.inflate(R.layout.activity_my_address, container, false);*/
        final GridLayout rl = (GridLayout) findViewById(R.id.GridLayoutAddress);

        final SharedPreferences myAccountPrefs = getSharedPreferences("MyAccountUserData", 0);
        final String userClientKey = myAccountPrefs.getString("myAccountClientKey", "");

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        try {
            if (SDK_INT > 8) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);

                HttpHelper helper = new HttpHelper();
                HttpResponse response = helper.get(("addressList?clientId=" + userClientKey + ""), MyAddress.this);

                //progressDialog = ProgressDialog.show(Login.this,"Loading...",
                //        "Loading application View, please wait...", false, false);

                String responseString = new BasicResponseHandler().handleResponse(response);
                System.out.println("Response Status:" + responseString);

                Gson json = new Gson();
                List<DeliveryAddressEntity> userAddressBean = null;
                Type listType1 = new TypeToken<List<DeliveryAddressEntity>>() {
                }.getType();

                userAddressBean = json.fromJson(responseString, listType1);
                System.out.println("User Address List:" + userAddressBean);

                for (final DeliveryAddressEntity bean : userAddressBean) {

                    final View rowView;
                    /*rowView = inflater.inflate(R.layout.address_list_item, container, false);*/
                    rowView = View.inflate(this, R.layout.address_list_item, null);

                    String userAddressLine1 = bean.getClientAddressLine1();
                    System.out.println("User Address Line 1 : " + userAddressLine1);
                    String userAddressLine2 = bean.getClientAddressLine2();
                    String userArea = bean.getClientArea();

                    TextView addressTitle = (TextView) rowView.findViewById(R.id.address);
                    addressTitle.setText(userAddressLine1);

                    rl.addView(rowView);

                }

                } else {

                    Toast.makeText(getApplicationContext(), "Error Fetching Address !!!",
                            Toast.LENGTH_SHORT).show();

                }
            }

        catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.print("Connection Error !!! Try relaunching the App !!!");
            e.printStackTrace();
        }

    }
}
