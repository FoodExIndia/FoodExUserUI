package user.com.foodexuserui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Date;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.BasicResponseHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import user.com.Entities.SignUpBean;
import user.com.commons.DobDialog;
import user.com.commons.HttpHelper;

public class SignUp extends AppCompatActivity {

    EditText firstName, lastName, emailId, phoneNumber, password, confirmPassword, dobDate, addressline1, addressline2, cityName, areaName, stateName, pincodeNumber;

    Calendar mcurrentDate=Calendar.getInstance();

    SignUpBean bean = new SignUpBean();
    Button registerButton;
    HttpResponse response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstName = (EditText) findViewById(R.id.fName);
        lastName = (EditText) findViewById(R.id.lName);
        emailId = (EditText) findViewById(R.id.email);
        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        password = (EditText) findViewById(R.id.pwd);
        confirmPassword = (EditText) findViewById(R.id.confirmpwd);

        dobDate = (EditText)findViewById(R.id.dob);
        dobDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(SignUp.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        dobDate.setText(date);
                    }
                }, mYear, mMonth, mDay);

                mDatePicker.getDatePicker().getSpinnersShown();
                mDatePicker.getDatePicker().setMaxDate(mcurrentDate.getTimeInMillis());
                mDatePicker.setTitle("Select Your Birthdate");
                mDatePicker.show();
            }
        });

     /*   addressline1 = (EditText) findViewById(R.id.address1);
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
*/
        registerButton = (Button) findViewById(R.id.registerbtn);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cred1 = phoneNumber.getText().toString();
                String cred2 = password.getText().toString();

                SharedPreferences prefs = getSharedPreferences("UserData", 0);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("userId", cred1);
                editor.putString("password", cred2);
                editor.commit();

                String fNameBean = firstName.getText().toString();
                if (fNameBean.length() == 0) {
                    firstName.setError("Please enter First Name!");
                }

                String lNameBean = lastName.getText().toString();
                if (lNameBean.length() == 0) {
                    lastName.setError("Please enter Last Name!");
                }

                String emailBean = emailId.getText().toString();
                if (emailBean.length() == 0) {
                    emailId.setError("Please enter Email ID!");
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailBean).matches()) {
                    emailId.setError("Enter a valid email address");
                }

                String phoneBean = phoneNumber.getText().toString();
                if (phoneBean.length() == 0) {
                    phoneNumber.setError("Please enter Phone Number!");
                }
                if (phoneBean.length() != 10) {
                    phoneNumber.setError("Enter a valid Phone Number");
                }

                String dobBean = dobDate.getText().toString();

                String passwordBean = password.getText().toString();
                if (passwordBean.length() == 0) {
                    password.setError("Please enter Password!");
                } else if (passwordBean.length() > 20 || passwordBean.length() < 8) {
                    password.setError("Password must be 8 - 20 characters");
                }

                String confirmPasswordBean = confirmPassword.getText().toString();
                if (confirmPasswordBean.length() == 0) {
                    confirmPassword.setError("Please Confirm password!");
                } else if (confirmPasswordBean.length() > 20 || confirmPasswordBean.length() < 8) {
                    confirmPassword.setError("Password must be 8 - 20 characters");
                }

  /*              String address1bean = addressline1.getText().toString();
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
*/
                if (!passwordBean.equals(confirmPasswordBean)) {
                    password.setError("Password Mismatch");
                    confirmPassword.setError("Password Mismatch");
                    password.setText("");
                    confirmPassword.setText("");
                }

                    SharedPreferences signupInfo = getSharedPreferences("signUpInfo", 0);
                    SharedPreferences.Editor signUpeditor = signupInfo.edit();
                    editor.putString("firstName", fNameBean);
                    editor.putString("lastName", lNameBean);
                    editor.putString("phoneNumber", phoneBean);
                    editor.putString("emailId", emailBean);
                    editor.putString("password", passwordBean);
                    editor.commit();

                if(!fNameBean.equals("") && !lNameBean.equals("") && !phoneBean.equals("") && !emailBean.equals("")
                        && !passwordBean.equals("") && !confirmPasswordBean.equals("")) {

                    Intent i = new Intent(SignUp.this, SignUpAddressInfo.class);
                    try{
                    startActivity(i);
                    }
                    catch(Exception ex){
                        ex.printStackTrace();
                    }

                }

            }
        });
    }

    /*public void onStart(){
        super.onStart();
        EditText dobDate = (EditText)findViewById(R.id.dob);
        dobDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    DobDialog dialog = new DobDialog(v);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft,"DatePicker");
                }

            }
        });
    }*/
}
