package user.com.foodexuserui;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.params.HttpParams;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import user.com.Entities.DeliveryAddressEntity;
import user.com.Entities.MenuBean;
import user.com.Entities.SubOrderBean;
import user.com.Entities.UserDetails;
import user.com.Entities.UsersEntity;
import user.com.commons.HttpHelper;

public class EnterOTP extends AppCompatActivity {

    TextView phoneNumber;
    TextView userphoneNumber;
    EditText OTPvalue;
    Button submitButton;
    TextView ResendOTP;
    HttpResponse response = null;
    TextView OTPlabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

        OTPvalue = (EditText) findViewById(R.id.OTP);
        OTPlabel = (TextView)findViewById(R.id.OTPlabel1);
        ResendOTP = (TextView) findViewById(R.id.Resendotp);

        final String phNum = getIntent().getStringExtra("phNumber");

        userphoneNumber = (TextView) findViewById(R.id.OTPlabel2);
        submitButton = (Button) findViewById(R.id.confirmOTPbtn);

        userphoneNumber.setText(phNum);
        userphoneNumber.setVisibility(View.INVISIBLE);

        ResendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userphoneNumber.setText(phNum);

                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                try {
                    if (SDK_INT > 8) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                .permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        HttpHelper helper = new HttpHelper();

                        response = helper.get(("sendotp?loginId="+userphoneNumber),EnterOTP.this);
                        String responseString = new BasicResponseHandler().handleResponse(response);

                        if(responseString.equalsIgnoreCase("success")) {
                            Intent i = new Intent(EnterOTP.this, Plan1.class);
                            startActivity(i.putExtra("from", "ForgetPassword").putExtra("phNumber", phoneNumber.getText().toString()));
                        }

                        else{

                            System.out.print("Error");
                        }
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (OTPvalue.getText().toString().length() != 6) {
                    OTPvalue.setError("Please enter valid OTP !");
                }

                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                try {
                    if (SDK_INT > 8) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                .permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        HttpHelper helper = new HttpHelper();

                        response = helper.get(("otp?loginId="+userphoneNumber.getText().toString()+"&otp=" + OTPvalue.getText().toString() + ""),EnterOTP.this);
                        String responseString = new BasicResponseHandler().handleResponse(response);
                        System.out.println("Response Status:" + responseString);

                        if (responseString.equalsIgnoreCase("success")) {

                            HttpResponse userDetailsResponse = null;
                            userDetailsResponse = helper.get(("userdetails?loginId="+userphoneNumber.getText().toString()+""),EnterOTP.this);
                            String userResponseString = new BasicResponseHandler().handleResponse(userDetailsResponse);
                            System.out.println("Response Status:" + userResponseString);

                            Gson json = new Gson();

                            List<UserDetails> userDetailsBean = new ArrayList<UserDetails>();

                            Type listType1 = new TypeToken<ArrayList<UserDetails>>() {
                            }.getType();
                            userDetailsBean = json.fromJson(userResponseString, listType1);

                            if (getIntent().getStringExtra("from").equalsIgnoreCase("SignUp")) {
                                Intent i = new Intent(EnterOTP.this, NavigationDrawer.class);
                                startActivity(i);
                            } else if (getIntent().getStringExtra("from").equalsIgnoreCase("ForgetPassword")) {
                                Intent i = new Intent(EnterOTP.this, NewPassword.class);
                                startActivity(i);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "SignUp Error !!!",
                                    Toast.LENGTH_SHORT).show();
                            System.out.println("Sign Up Error..");
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
