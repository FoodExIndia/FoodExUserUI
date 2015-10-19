package user.com.foodexuserui;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.BasicResponseHandler;

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

                        response = helper.get("sendotp?loginId="+userphoneNumber);
                        String responseString = new BasicResponseHandler().handleResponse(response);

                        if(responseString.equalsIgnoreCase("success")) {
                            Intent i = new Intent(EnterOTP.this, EnterOTP.class);
                            //startActivity(i.putExtra("from", "ForgetPassword").putExtra("phNumber", phoneNumber.getText().toString()));
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

                        response = helper.get("otp?loginId="+userphoneNumber.getText().toString()+"&otp=" + OTPvalue.getText().toString() + "");
                        String responseString = new BasicResponseHandler().handleResponse(response);
                        System.out.println("Response Status:" + responseString);
                        if (responseString.equalsIgnoreCase("success")) {
                            if (getIntent().getStringExtra("from").equalsIgnoreCase("SignUp")) {
                                Intent i = new Intent(EnterOTP.this, FoodExHome.class);
                                startActivity(i);
                            } else if (getIntent().getStringExtra("from").equalsIgnoreCase("ForgetPassword")) {
                                Intent i = new Intent(EnterOTP.this, NewPassword.class);
                                startActivity(i);
                            }
                        } else {
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
