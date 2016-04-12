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

        ImageView editAddress = (ImageView)findViewById(R.id.editIcon);
        final Button saveAddress = (Button)findViewById(R.id.saveAddress);
        Button addAddress = (Button)findViewById(R.id.addNewAddress);

        final EditText addressline1 = (EditText) findViewById(R.id.address1);
        final EditText addressline2 = (EditText) findViewById(R.id.address2);
        final Spinner citydropdown = (Spinner) findViewById((R.id.city));
        final Spinner areadropdown = (Spinner) findViewById((R.id.area));
        final Spinner statedropdown = (Spinner) findViewById((R.id.state));
        final EditText pincodeNumber = (EditText) findViewById(R.id.pincode);

        saveAddress.setEnabled(true);

        citydropdown.setEnabled(false);
        citydropdown.setClickable(false);

        areadropdown.setEnabled(false);
        areadropdown.setClickable(false);

        statedropdown.setEnabled(false);
        statedropdown.setClickable(false);

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MyAccount.this, SignUpAddressInfo.class);
                startActivity(intent.putExtra("from","MyAccount"));

            }
        });

        editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addressline1.setEnabled(true);
                addressline1.setClickable(true);

                addressline2.setEnabled(true);
                addressline2.setClickable(true);

                citydropdown.setEnabled(true);
                citydropdown.setClickable(true);

                areadropdown.setEnabled(true);
                areadropdown.setClickable(true);

                statedropdown.setEnabled(true);
                statedropdown.setClickable(true);

                pincodeNumber.setEnabled(true);
                pincodeNumber.setClickable(true);

                saveAddress.setEnabled(true);
                saveAddress.setTextColor(Color.WHITE);
                saveAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), " Button Clicked !!!",
                                Toast.LENGTH_SHORT).show();
                    }

                });

            }

        });

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
