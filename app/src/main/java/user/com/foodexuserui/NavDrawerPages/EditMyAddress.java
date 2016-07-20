package user.com.foodexuserui.NavDrawerPages;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import user.com.foodexuserui.R;
import user.com.foodexuserui.SignUpAddressInfo;

public class EditMyAddress extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        final Button saveAddress = (Button)findViewById(R.id.saveAddress);

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

    }
}
