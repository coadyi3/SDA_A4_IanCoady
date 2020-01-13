package com.example.sdaassign4_2019;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CheckOut extends AppCompatActivity {

    TextView mDisplaySummary, mTitleSummary;
    Calendar mDateAndTime = Calendar.getInstance();

    String currentDate, selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        //find the summary textview
        mDisplaySummary = findViewById(R.id.orderSummary);
        mTitleSummary = findViewById(R.id.confirm);


        Bundle extras = getIntent().getExtras();

        String titleText = extras.getString("title");
        mTitleSummary.setText("Checkout book: "+ titleText);

        //set the toolbar we have overridden
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



    }

    //source SDA_2019 android course examples ViewGroup demo
    public void onDateClicked(View v) {

        DatePickerDialog.OnDateSetListener mDateListener = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                mDateAndTime.set(Calendar.YEAR, year);
                mDateAndTime.set(Calendar.MONTH, monthOfYear);
                mDateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateAndTimeDisplay();
            }
        };

        new DatePickerDialog(CheckOut.this, mDateListener,
                mDateAndTime.get(Calendar.YEAR),
                mDateAndTime.get(Calendar.MONTH),
                mDateAndTime.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void updateDateAndTimeDisplay() {
        //date time year
        String currentTime = DateUtils.formatDateTime(this, mDateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_TIME);
        selectedDate = DateUtils.formatDateTime(this, mDateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR);
        String finalSummary = selectedDate + " current time is " + currentTime;
        mDisplaySummary.setText(finalSummary);
    }
}
