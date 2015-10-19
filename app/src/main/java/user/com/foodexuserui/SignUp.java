package user.com.foodexuserui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.BasicResponseHandler;

import java.text.SimpleDateFormat;

import user.com.Entities.SignUpBean;
import user.com.commons.HttpHelper;

public class SignUp extends AppCompatActivity {

    EditText firstName,lastName,emailId,phoneNumber,password,confirmPassword,dobDate,addressline1,addressline2,cityName,areaName,stateName,pincodeNumber;
    DatePickerDialog birthday;
    private SimpleDateFormat dateFormatter;

    SignUpBean bean = new SignUpBean();
    Button registerButton;
    HttpResponse response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstName = (EditText)findViewById(R.id.fName);
        lastName = (EditText)findViewById(R.id.lName);
        emailId = (EditText)findViewById(R.id.email);
        phoneNumber = (EditText)findViewById(R.id.phoneNumber);
        password = (EditText)findViewById(R.id.pwd);
        confirmPassword = (EditText)findViewById(R.id.confirmpwd);

        dobDate = (EditText)findViewById(R.id.dob);
        dobDate.setOnClickListener((View.OnClickListener) this);

        addressline1 = (EditText)findViewById(R.id.address1);
        addressline2 = (EditText)findViewById(R.id.address2);

        //cityName = (EditText)findViewById(R.id.city);
        final Spinner citydropdown = (Spinner) findViewById((R.id.city));
        //final String citybean = String.valueOf(citydropdown.getSelectedItem());

        //areaName = (EditText)findViewById(R.id.area);
        final Spinner areadropdown = (Spinner) findViewById((R.id.area));
        //final String areabean = String.valueOf(areadropdown.getSelectedItem());

        //stateName = (EditText)findViewById(R.id.state);
        final Spinner statedropdown = (Spinner) findViewById((R.id.state));
        final String statebean = String.valueOf(statedropdown.getSelectedItem());

        pincodeNumber = (EditText)findViewById(R.id.pincode);
        registerButton = (Button) findViewById(R.id.registerbtn);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fNameBean = firstName.getText().toString();
                if(fNameBean.length()==0){
                    firstName.setError("Please enter First Name!");
                }

                String lNameBean = lastName.getText().toString();
                if(lNameBean.length()==0){
                    lastName.setError("Please enter Last Name!");
                }

                String emailBean = emailId.getText().toString();
                if(emailBean.length()==0){
                    emailId.setError("Please enter Email ID!");
                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailBean).matches()){
                    emailId.setError("Enter a valid email address");
                }

                String phoneBean = phoneNumber.getText().toString();
                if(phoneBean.length()==0){
                    phoneNumber.setError("Please enter Phone Number!");
                }
                if(phoneBean.length()!=10){
                    phoneNumber.setError("Enter a valid Phone Number");
                }

                String dobBean = dobDate.getText().toString();

                String passwordBean = password.getText().toString();
                if(passwordBean.length()==0){
                    password.setError("Please enter Password!");
                }
                else if(passwordBean.length()>20 || passwordBean.length()<8){
                    password.setError("Password must be 8 - 20 characters");
                }

                String confirmPasswordBean = confirmPassword.getText().toString();
                if(confirmPasswordBean.length()==0){
                    confirmPassword.setError("Please Confirm password!");
                }
                else if(confirmPasswordBean.length()>20 || confirmPasswordBean.length()<8){
                    confirmPassword.setError("Password must be 8 - 20 characters");
                }

                String address1bean = addressline1.getText().toString();
                if(address1bean.length()==0){
                    addressline1.setError("Please enter Address !");
                }

                String address2bean = addressline2.getText().toString();
                if(address2bean.length()==0){
                    addressline2.setError("Please enter Address !");
                }

                String statebean = String.valueOf(statedropdown.getSelectedItem());
                String citybean = String.valueOf(citydropdown.getSelectedItem());
                String areabean = String.valueOf(areadropdown.getSelectedItem());

                //String citybean = cityName.getText().toString();
                if(citybean.length()==0){
                    cityName.setError("Please Select City !");
                }

                //String areabean = areaName.getText().toString();
                if(areabean.length()==0){
                    areaName.setError("Please Select Area !");
                }

                //String statebean = stateName.getText().toString();
                if(statebean.length()==0){
                    stateName.setError("Please Select State !");
                }

                String pincodebean = pincodeNumber.getText().toString();
                if (pincodebean.length()==0){
                    pincodeNumber.setError("Please enter Pincode!");
                }
                if (pincodebean.length()!=6){
                    pincodeNumber.setError("Enter a valid Pincode!");
                }

                if(!passwordBean.equals(confirmPasswordBean)){
                    password.setError("Password Mismatch");
                    confirmPassword.setError("Password Mismatch");
                    password.setText("");
                    confirmPassword.setText("");
                }

                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                try {
                    if (SDK_INT > 8) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                .permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                        bean.setFirstName(fNameBean);
                        bean.setLastname(lNameBean);
                        bean.setEmailId(emailBean);
                        bean.setPhoneNumber(phoneBean);
                        bean.setPassword(passwordBean);
                        bean.getDob(dobBean);
                        bean.setAddressLine1(address1bean);
                        bean.setAddressLine2(address2bean);
                        bean.setState(statebean);
                        bean.setCity(citybean);
                        bean.setArea(areabean);
                        bean.setPincode(pincodebean);

                        Gson gson = new Gson();
                        final String signUpJson = gson.toJson(bean);
                        HttpHelper helper = new HttpHelper();
                        response = helper.post("signup", signUpJson);
                        String responseString = new BasicResponseHandler().handleResponse(response);

                        if(responseString.equalsIgnoreCase("success")) {

                            Intent i = new Intent(SignUp.this, EnterOTP.class);
                            startActivity(i.putExtra("from","SignUp").putExtra("phNumber",phoneNumber.getText().toString()));

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