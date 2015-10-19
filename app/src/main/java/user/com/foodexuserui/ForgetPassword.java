package user.com.foodexuserui;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.BasicResponseHandler;

import user.com.commons.HttpHelper;

public class ForgetPassword extends AppCompatActivity {

    Button OTPButton;
    HttpResponse response;
    EditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        phoneNumber = (EditText) findViewById(R.id.fp_phoneNumber);
        OTPButton = (Button) findViewById(R.id.OTPbtn);

        OTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneBean = phoneNumber.getText().toString();

                if(phoneBean.length()!=10){
                    phoneNumber.setError("Enter a valid Phone Number");
                }

                else{

                    int SDK_INT = android.os.Build.VERSION.SDK_INT;
                    try {
                        if (SDK_INT > 8) {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                    .permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            HttpHelper helper = new HttpHelper();

                            response = helper.get("sendotp?loginId="+phoneBean);
                            String responseString = new BasicResponseHandler().handleResponse(response);

                            if(responseString.equalsIgnoreCase("success")) {
                                Intent i = new Intent(ForgetPassword.this, EnterOTP.class);
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


            }
        });

    }

}
