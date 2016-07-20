package user.com.foodexuserui;

/**
 * Created by Admin on 2/29/2016.
 */

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.BasicResponseHandler;

import java.util.Calendar;

import user.com.Entities.SignUpBean;
import user.com.commons.HttpHelper;

public class SignUpAddressInfo extends AppCompatActivity {

    EditText addressline1, addressline2, cityName, areaName, stateName, pincodeNumber;

    Calendar mcurrentDate=Calendar.getInstance();

    SignUpBean bean = new SignUpBean();
    Button registerButton;
    HttpResponse response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_address_info);

        addressline1 = (EditText) findViewById(R.id.address1);
        addressline2 = (EditText) findViewById(R.id.address2);

        //cityName = (EditText)findViewById(R.id.city);
        final Spinner citydropdown = (Spinner) findViewById((R.id.city));
        //final String citybean = String.valueOf(citydropdown.getSelectedItem());

        //areaName = (EditText)findViewById(R.id.area);
        final Spinner areadropdown = (Spinner) findViewById((R.id.area));
        //final String areabean = String.valueOf(areadropdown.getSelectedItem());

        //stateName = (EditText)findViewById(R.id.state);
        final Spinner statedropdown = (Spinner) findViewById((R.id.state));
        final String statebean = String.valueOf(statedropdown.getSelectedItem());

        pincodeNumber = (EditText) findViewById(R.id.pincode);
        registerButton = (Button) findViewById(R.id.registerbtn1);

        final SharedPreferences signUpInfo = getSharedPreferences("signUpInfo", 0);
        final String fNameBean = signUpInfo.getString("firstName", "");
        final String lNameBean = signUpInfo.getString("lastName", "");
        final String phoneBean = signUpInfo.getString("phoneNumber", "");
        final String emailBean = signUpInfo.getString("emailId", "");
        final String passwordBean = signUpInfo.getString("password", "");

        System.out.println("1 : "+fNameBean+"");
        System.out.println("1 : " + lNameBean + "");
        System.out.println("1 : "+phoneBean+"");
        System.out.println("1 : "+emailBean+"");
        System.out.println("1 : "+passwordBean+"");

        final String fromActivity = getIntent().getStringExtra("from");

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fromActivity.equalsIgnoreCase("MyAccount")){
                }else{
                }

                String cred1 = phoneBean.toString();
                String cred2 = passwordBean.toString();

                SharedPreferences prefs = getSharedPreferences("UserData", 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("userId", cred1);
                editor.putString("password", cred2);
                editor.commit();

                String address1bean = addressline1.getText().toString();
                if (address1bean.length() == 0) {
                    addressline1.setError("Please enter Address !");
                }

                String address2bean = addressline2.getText().toString();
                if (address2bean.length() == 0) {
                    addressline2.setError("Please enter Address !");
                }

                String statebean = String.valueOf(statedropdown.getSelectedItem());
                String citybean = String.valueOf(citydropdown.getSelectedItem());
                String areabean = String.valueOf(areadropdown.getSelectedItem());

                //String citybean = cityName.getText().toString();
                if (citybean.length() == 0) {
                    cityName.setError("Please Select City !");
                }

                //String areabean = areaName.getText().toString();
                if (areabean.length() == 0) {
                    areaName.setError("Please Select Area !");
                }

                //String statebean = stateName.getText().toString();
                if (statebean.length() == 0) {
                    stateName.setError("Please Select State !");
                }

                String pincodebean = pincodeNumber.getText().toString();
                if (pincodebean.length() == 0) {
                    pincodeNumber.setError("Please enter Pincode!");
                }
                if (pincodebean.length() != 6) {
                    pincodeNumber.setError("Enter a valid Pincode!");
                }


                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                try {
                    if (SDK_INT > 8) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                .permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                        bean.setFirstName(fNameBean);
                        bean.setLastname(lNameBean);
                        bean.setEmailId(emailBean);
                        bean.setPhoneNumber(phoneBean);
                        bean.setPassword(passwordBean);
                        //bean.getDob(dobBean);
                        bean.setAddressLine1(address1bean);
                        bean.setAddressLine2(address2bean);
                        bean.setState(statebean);
                        bean.setCity(citybean);
                        bean.setArea(areabean);
                        bean.setPincode(pincodebean);

                        Gson gson = new Gson();
                        final String signUpJson = gson.toJson(bean);
                        HttpHelper helper = new HttpHelper();
                        response = helper.post("signup", signUpJson);
                        String responseString = new BasicResponseHandler().handleResponse(response);

                        if (responseString.length()>0) {

                            Intent i = new Intent(SignUpAddressInfo.this, EnterOTP.class);
                            startActivity(i.putExtra("from", "SignUp").putExtra("phNumber", phoneBean.toString()));

                        } else {

                            System.out.println("SignUp Error");
                            System.exit(0);

                        }

                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

}
