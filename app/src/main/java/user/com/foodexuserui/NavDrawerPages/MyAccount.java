package user.com.foodexuserui.NavDrawerPages;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import user.com.Entities.SubOrderBean;
import user.com.foodexuserui.ImageViewer;
import user.com.foodexuserui.Login;
import user.com.foodexuserui.R;
import user.com.foodexuserui.SignUpAddressInfo;

public class MyAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        final SharedPreferences myAccountPrefs = getSharedPreferences("MyAccountUserData", 0);
        final String userName = myAccountPrefs.getString("myAccountUserName", "");
        final String userMail = myAccountPrefs.getString("myAccountEmailId", "");
        final String userPhoneNumber = myAccountPrefs.getString("myAccountPhoneNumber", "");

        TextView userNameString = (TextView)findViewById(R.id.usrName);
        TextView userContactString = (TextView)findViewById(R.id.usrContact);
        TextView userMailString = (TextView)findViewById(R.id.usrMail);

        userNameString.setText(userName);
        userMailString.setText(userMail);
        userContactString.setText(userPhoneNumber);

            }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
