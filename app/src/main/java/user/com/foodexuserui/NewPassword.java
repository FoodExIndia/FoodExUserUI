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

public class NewPassword extends AppCompatActivity {

    EditText newPassword,confirmPassword,phoneNumber;
    Button savePasswords;
    HttpResponse response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        newPassword = (EditText)findViewById(R.id.fp_newpassword);
        confirmPassword = (EditText)findViewById(R.id.fp_confirmpassword);
        phoneNumber = (EditText)findViewById(R.id.fp_phoneNumber);

        savePasswords = (Button) findViewById(R.id.fp_Save);

        savePasswords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phNum = phoneNumber.getText().toString();
                System.out.print("Phone Number Id:"+phNum);

                String passwordBean = newPassword.getText().toString();
                if(passwordBean.length()==0){
                    newPassword.setError("Please enter Password!");
                }
                else if(passwordBean.length()>20 || passwordBean.length()<8){
                    newPassword.setError("Password must be 8 - 20 characters");
                }

                String confirmPasswordBean = confirmPassword.getText().toString();
                if(confirmPasswordBean.length()==0){
                    confirmPassword.setError("Please Confirm password!");
                }
                else if(confirmPasswordBean.length()>20 || confirmPasswordBean.length()<8){
                    confirmPassword.setError("Password must be 8 - 20 characters");
                }

                if(!passwordBean.equals(confirmPasswordBean)){
                    newPassword.setError("Password Mismatch");
                    confirmPassword.setError("Password Mismatch");
                    newPassword.setText("");
                    confirmPassword.setText("");
                }

                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                try {
                    if (SDK_INT > 8) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                .permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                        HttpHelper helper = new HttpHelper();
                        response = helper.get(("newpassword?password="+newPassword.getText().toString()+""),NewPassword.this);
                        String responseString = new BasicResponseHandler().handleResponse(response);
                        System.out.println("Response Status:" + responseString);
                        if(responseString.equalsIgnoreCase("success") )
                        {
                            Intent i = new Intent(NewPassword.this, FoodExHome.class);
                            startActivity(i);
                        }

                        else{
                            System.out.println("SignUp Error");
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
