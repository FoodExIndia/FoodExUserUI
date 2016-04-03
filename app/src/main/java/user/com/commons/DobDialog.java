package user.com.commons;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by Admin on 10/21/2015.
 */
@SuppressLint("ValidFragment")
public class DobDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        EditText dobDate;
        public DobDialog(View view){
            dobDate = (EditText)view;
        }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar cal = Calendar.getInstance();
        int dobYear = cal.get(Calendar.YEAR);
        int dobMonth = cal.get(Calendar.MONTH);
        int dobDay = cal.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),this,dobYear,dobMonth,dobDay);
    }

    public void onDateSet(DatePicker view, int year, int month, int day){
        String date = day+"-"+(month+1)+"-"+year;
        dobDate.setText(date);
    }

}
