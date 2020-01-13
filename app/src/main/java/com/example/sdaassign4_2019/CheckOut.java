package com.example.sdaassign4_2019;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.example.sdaassign4_2019.Settings.PREF_KEY;

public class CheckOut extends AppCompatActivity {

    FirebaseFirestore mFirestore;

    TextView mDisplaySummary, mTitleSummary,mStatusSummary;
    Calendar mDateAndTime = Calendar.getInstance();
    String currentDate, selectedDate,titleText, userName;
    String bookStatus = "Available";
    int userid,dateBtnClicked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        Bundle extras = getIntent().getExtras();
        titleText = extras.getString("title");
        SharedPreferences prefs = this.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        userName = prefs.getString("NAME_KEY", "");
        userid = prefs.getInt("ID_KEY", 0);

        mFirestore = FirebaseFirestore.getInstance();
        final CollectionReference bookList = mFirestore.collection("books");


        //find the summary textview
        mDisplaySummary = findViewById(R.id.orderSummary);
        mTitleSummary = findViewById(R.id.confirm);
        mStatusSummary = findViewById(R.id.availability);

        mStatusSummary.setText("This book is currently "+bookStatus+".");
        mTitleSummary.setText("Checkout book: "+ titleText);


        final Button sendBtn = findViewById(R.id.orderButton);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dateBtnClicked > 0) {
                   if(bookStatus.equals("Available")) {
                       bookStatus = "Unavailable";


                       Map<String, Object> bookDetails = new HashMap<>();
                       bookDetails.put("userName", userName);
                       bookDetails.put("selectedDate", selectedDate);
                       bookDetails.put("userID", String.valueOf(userid));
                       bookDetails.put("bookStatus", bookStatus);
                       bookList.document(titleText).set(bookDetails);

                       sendBtn.setEnabled(false);

                       mDisplaySummary.setText("Success!! You have reserved the book: " + titleText + ".");
                       mStatusSummary.setText("This book is currently "+bookStatus+".");
                   }

                   else{
                       Toast.makeText(getApplicationContext(), "This book is unavailable at the moment, come back later and try again.", Toast.LENGTH_LONG).show();
                   }

                }
                else{
                    Toast.makeText(getApplicationContext(), "Please select a date first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button reset = findViewById(R.id.resetButton);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBtn.setEnabled(true);
                bookStatus = "Available";
                mDisplaySummary.setText("This book is available for rental again");
                mStatusSummary.setText("This book is currently "+bookStatus+".");

                Map<String, Object> bookDetails = new HashMap<>();
                bookDetails.put("userName", userName);
                bookDetails.put("selectedDate", null);
                bookDetails.put("userID", String.valueOf(userid));
                bookDetails.put("bookStatus", bookStatus);
                bookList.document(titleText).set(bookDetails);

            }
        });

        //set the toolbar we have overridden
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



    }

    //source SDA_2019 android course examples ViewGroup demo
    public void onDateClicked(View v) {

        dateBtnClicked++;

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
    }
}
