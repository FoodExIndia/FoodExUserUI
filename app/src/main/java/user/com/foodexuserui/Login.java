package user.com.foodexuserui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncStatusObserver;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import user.com.Entities.MenuBean;
import user.com.Entities.SubOrderBean;
import user.com.Entities.UserDetails;
import user.com.Entities.UsersEntity;
import user.com.Entities.ClientEntity;
import user.com.Entities.DeliveryAddressEntity;
import user.com.commons.HttpHelper;
import user.com.foodexuserui.NavDrawerPages.MyAccount;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Login extends Activity {

    Button loginButton;
    Button SignupButton;
    TextView ErrorMessage, ForgotPassword, TermsConds;
    EditText Username, Password;
    AlertDialog.Builder socketError = null;
    AlertDialog.Builder timeoutError = null;
    HttpResponse response;
    private ProgressDialog progressDialog;
    List<MenuBean> foodBeanList = new ArrayList<MenuBean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        final SharedPreferences prefs = getSharedPreferences("UserData", 0);
        final String userId = prefs.getString("userId", "");
        final String pwd = prefs.getString("password", "");

        if (userId != "" && pwd != "") {
            Intent i = new Intent(Login.this, FoodExHome.class);
            startActivity(i);
        }
        //Button goButton = (Button) findViewById(R.id.button1);

        Username = (EditText) findViewById(R.id.Username);
        Password = (EditText) findViewById(R.id.password);

        loginButton = (Button) findViewById(R.id.loginbtn);
        SignupButton = (Button) findViewById(R.id.signupbtn);

        ErrorMessage = (TextView) findViewById(R.id.errormsg);
        ForgotPassword = (TextView) findViewById(R.id.forgotpwd);
        TermsConds = (TextView) findViewById(R.id.TC);

        TermsConds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<MenuBean> listMenuBean = new ArrayList<MenuBean>();
                MenuBean bean1 = new MenuBean();
                bean1.setFoodKey(101);
                bean1.setItemName("Idly");
                bean1.setItemPrice(5);
                bean1.setCourseFlag(1);

                MenuBean bean2 = new MenuBean();
                bean2.setFoodKey(102);
                bean2.setItemName("Dosa");
                bean2.setItemPrice(5);
                bean2.setCourseFlag(1);

                MenuBean bean3 = new MenuBean();
                bean3.setFoodKey(103);
                bean3.setItemName("Poori");
                bean3.setItemPrice(5);
                bean3.setCourseFlag(1);

                MenuBean bean4 = new MenuBean();
                bean4.setFoodKey(104);
                bean4.setItemName("Chappathi");
                bean4.setItemPrice(5);
                bean4.setCourseFlag(1);

                MenuBean bean5 = new MenuBean();
                bean5.setFoodKey(105);
                bean5.setItemName("Pongal");
                bean5.setItemPrice(5);
                bean5.setCourseFlag(1);

                MenuBean bean6 = new MenuBean();
                bean6.setFoodKey(105);
                bean6.setItemName("BA");
                bean6.setItemPrice(5);
                bean6.setCourseFlag(1);

                MenuBean bean7 = new MenuBean();
                bean7.setFoodKey(105);
                bean7.setItemName("CC");
                bean7.setItemPrice(5);
                bean7.setCourseFlag(1);

                MenuBean bean8 = new MenuBean();
                bean8.setFoodKey(105);
                bean8.setItemName("BB");
                bean8.setItemPrice(5);
                bean8.setCourseFlag(1);

                MenuBean bean9 = new MenuBean();
                bean9.setFoodKey(105);
                bean9.setItemName("DD");
                bean9.setItemPrice(5);
                bean9.setCourseFlag(1);

                MenuBean bean10 = new MenuBean();
                bean10.setFoodKey(105);
                bean10.setItemName("AA");
                bean10.setItemPrice(5);
                bean10.setCourseFlag(1);

                MenuBean bean11 = new MenuBean();
                bean11.setFoodKey(105);
                bean11.setItemName("CV");
                bean11.setItemPrice(5);
                bean11.setCourseFlag(1);

                MenuBean bean12 = new MenuBean();
                bean12.setFoodKey(105);
                bean12.setItemName("KL");
                bean12.setItemPrice(5);
                bean12.setCourseFlag(1);

                MenuBean bean13 = new MenuBean();
                bean13.setFoodKey(105);
                bean13.setItemName("FG");
                bean13.setItemPrice(5);
                bean13.setCourseFlag(1);

                MenuBean bean14 = new MenuBean();
                bean14.setFoodKey(105);
                bean14.setItemName("DVD");
                bean14.setItemPrice(5);
                bean14.setCourseFlag(1);


                MenuBean bean15 = new MenuBean();
                bean15.setFoodKey(105);
                bean15.setItemName("DVGGH");
                bean15.setItemPrice(5);
                bean15.setCourseFlag(1);

                listMenuBean.add(bean1);
                listMenuBean.add(bean2);
                listMenuBean.add(bean3);
                listMenuBean.add(bean4);
                listMenuBean.add(bean5);
                listMenuBean.add(bean6);
                listMenuBean.add(bean7);
                listMenuBean.add(bean8);
                listMenuBean.add(bean9);
                listMenuBean.add(bean10);
                listMenuBean.add(bean11);
                listMenuBean.add(bean12);
                listMenuBean.add(bean13);
                listMenuBean.add(bean14);
                listMenuBean.add(bean15);





                /*int SDK_INT = android.os.Build.VERSION.SDK_INT;
                try {
                    if (SDK_INT > 8) {

                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                .permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                        HttpHelper helper = new HttpHelper();
                        response = helper.get("foodItem",Login.this);

                        //progressDialog = ProgressDialog.show(Login.this,"Loading...",
                        //        "Loading application View, please wait...", false, false);

                        String responseString = new BasicResponseHandler().handleResponse(response);
                        System.out.println("Response Status:" + responseString);

                        if (responseString != null) {

                            Gson json = new Gson();

                            Type listTypeFood = new TypeToken<ArrayList<MenuBean>>() {
                            }.getType();
                            foodBeanList = json.fromJson(responseString, listTypeFood);

                            Intent i = new Intent(Login.this, NavigationDrawer.class);
                            startActivity(i);

                        } else {

                            Toast.makeText(getApplicationContext(), " Error !!!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }


                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.print("Connection Error in Login");
                    e.printStackTrace();
                }*/

                SharedPreferences prefs = getSharedPreferences("MenuData", 0);
                SharedPreferences.Editor editor = prefs.edit();
                Gson json = new Gson();
                editor.putString("list1", json.toJson(foodBeanList));
                editor.commit();

                Intent i = new Intent(Login.this, NavigationDrawer.class);
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

                if (user.length() != 10)
                    Username.setError("Give a Valid UserName!");

                String pwd = Password.getText().toString();

                if (pwd.length() == 0)
                    Password.setError("Password is required!");

                System.out.println("Credentials:" + user + " " + pwd);

                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                try {
                    if (SDK_INT > 8) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                .permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                        HttpHelper helper = new HttpHelper();
                        response = helper.get(("login?loginId=" + user + "&password=" + pwd + ""), Login.this);

                        //progressDialog = ProgressDialog.show(Login.this,"Loading...",
                        //        "Loading application View, please wait...", false, false);

                        String responseString = new BasicResponseHandler().handleResponse(response);
                        System.out.println("Response Status:" + responseString);

                        Gson json = new Gson();
                        UserDetails userDetailsBean = new UserDetails();

                        Type listType1 = new TypeToken<UserDetails>() {
                        }.getType();

                        try {
                            userDetailsBean = json.fromJson(responseString, listType1);
                        }
                        catch (Exception ex){
                            ex.printStackTrace();
                        }

                        if (userDetailsBean.getResultCode() == 1) {

                            SharedPreferences prefs = getSharedPreferences("UserData", 0);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("userId", user);
                            editor.putString("password", pwd);
                            editor.commit();

                            String userEmailID = userDetailsBean.getUserEntity().getEmailId();
                            String userPhoneNumber = String.valueOf(userDetailsBean.getUserEntity().getMobileNum());
                            String userName = userDetailsBean.getClientEntity().getClientFirstName();
                            String userClientKey = userDetailsBean.getClientEntity().getClientKey();

                            SharedPreferences myAccountPrefs = getSharedPreferences("MyAccountUserData", 0);
                            SharedPreferences.Editor myAccountEditor = myAccountPrefs.edit();
                            myAccountEditor.putString("myAccountEmailId", userEmailID);
                            myAccountEditor.putString("myAccountPhoneNumber", userPhoneNumber);
                            myAccountEditor.putString("myAccountUserName", userName);
                            myAccountEditor.putString("myAccountClientKey", userClientKey);
                            myAccountEditor.commit();

                            Intent i = new Intent(Login.this, MyAccount.class);
                            startActivity(i);

                        } else {

                            //ErrorMessage.setText("UserName or Password Error !!! ");
                            Animation shake = AnimationUtils.loadAnimation(Login.this, R.anim.shake);
                            Username.startAnimation(shake);
                            Password.startAnimation(shake);

                            Toast.makeText(getApplicationContext(), "UserName or Password Error !!!",
                                    Toast.LENGTH_SHORT).show();

                            Username.setText("");
                            Password.setText("");
                        }
                    }
                }
                    catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.print("Connection Error in Login");
                    e.printStackTrace();
                }
            }
        });
    }
}
