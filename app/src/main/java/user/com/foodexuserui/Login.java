package user.com.foodexuserui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SyncStatusObserver;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.BasicResponseHandler;

import user.com.commons.HttpHelper;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Login extends Activity {

    Button loginButton;
    Button SignupButton;
    TextView ErrorMessage,ForgotPassword,TermsConds;
    EditText Username, Password;

    HttpResponse response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Button goButton = (Button) findViewById(R.id.button1);

        Username = (EditText) findViewById(R.id.Username);
        Password = (EditText) findViewById(R.id.password);

        loginButton = (Button) findViewById(R.id.loginbtn);
        SignupButton = (Button) findViewById(R.id.signupbtn);

        ErrorMessage = (TextView)findViewById(R.id.errormsg);
        ForgotPassword = (TextView)findViewById(R.id.forgotpwd);
        TermsConds = (TextView)findViewById(R.id.TC);

        TermsConds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Login.this, ImageViewer.class);
                startActivity(i);

            }
        });

        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Login.this, ForgetPassword.class);
                startActivity(i);

            }
        });

        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Login.this, SignUp.class);
                startActivity(i);

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String user = Username.getText().toString();

                if( user.length() == 0 )
                    Username.setError( "User name is required!" );

                String pwd = Password.getText().toString();
                if( pwd.length() == 0 )
                    Password.setError( "Password is required!" );

                System.out.println("Credentials:"+user+" "+pwd);

                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                try {
                    if (SDK_INT > 8)
                    {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                .permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        HttpHelper helper = new HttpHelper();
                        response = helper.get("login?loginId="+user+"&password="+pwd+"");
                        String responseString = new BasicResponseHandler().handleResponse(response);
                        System.out.println("Response Status:"+responseString);
                        if(responseString.equalsIgnoreCase("success"))
                        {
                            Intent i = new Intent(Login.this, FoodExHome.class);
                            startActivity(i);
                        }
                        else
                        {
                            ErrorMessage.setText("UserName or Password Error !!! ");
                            Animation shake = AnimationUtils.loadAnimation(Login.this, R.anim.shake);
                            Username.startAnimation(shake);
                            Password.startAnimation(shake);

                            Username.setText("");
                            Password.setText("");

                            //ErrorMessage.setVisibility(View.VISIBLE);
                            ErrorMessage.postDelayed(new Runnable() {
                                public void run() {
                                    ErrorMessage.setVisibility(View.INVISIBLE);
                                }
                            }, 5000);
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

// Setup event handlers
 /*       loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                try {
                    if (SDK_INT > 8)
                    {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                .permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        HttpHelper helper = new HttpHelper();
                        response = helper.get("login?loginId="+user+"&password="+pwd+"");
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }



            }
        });
    }


}
*/