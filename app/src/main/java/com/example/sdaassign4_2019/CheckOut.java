package com.example.sdaassign4_2019;

/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.sdaassign4_2019.Settings.PREF_KEY;

public class CheckOut extends AppCompatActivity {

    //Global required variables
    FirebaseFirestore   myFirestoreDB;
    TextView            mDisplaySummary, mTitleSummary,mStatusSummary;
    Calendar            mDateAndTime = Calendar.getInstance();
    String              currentDate, selectedDate,titleText, userName;
    String              bookStatus = "Available";       //Hardcoded string - When I tried using bookStatus = getString(R.string.avail); the app would crash? Could you explain in my feedback please.
    int                 userid,dateBtnClicked = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        final   Button              sendBtn             = findViewById(R.id.orderButton);
                                    myFirestoreDB       = FirebaseFirestore.getInstance();
        final   CollectionReference bookList            = myFirestoreDB.collection("books");
                Bundle              extras              = getIntent().getExtras();
                SharedPreferences   prefs               = this.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
                Button              reset               = findViewById(R.id.resetButton);
                                    userName            = prefs.getString   ("NAME_KEY", "");
                                    userid              = prefs.getInt      ("ID_KEY", 0);
                                    mDisplaySummary     = findViewById(R.id.orderSummary);
                                    mTitleSummary       = findViewById(R.id.confirm);
                                    mStatusSummary      = findViewById(R.id.availability);

        if (extras != null) {
            titleText = extras.getString("title");
            mTitleSummary.setText(String.format("%s %s", getString(R.string.title_summary_text), titleText));
        }

        /*
        REF: https://firebase.google.com/docs/firestore/query-data/get-data#get_a_document

        Document reference to load the books availability status from the DB and check if its
        available or not, if available allow the user to choose a date and reserve the book, if not
        disable the checkout button and inform the user the book is unavailable for rental.
         */
        DocumentReference myDocRef = myFirestoreDB.collection("books").document(titleText);
        myDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();

                    if (doc != null) {
                        if(doc.exists()){

                            bookStatus = doc.getString("bookStatus");
                            mStatusSummary.setText(String.format("%s %s.", getString(R.string.current_status_msg), bookStatus));

                            if(bookStatus.equals(getString(R.string.avail))){
                                sendBtn.setEnabled(true);
                            }
                            else{
                                sendBtn.setEnabled(false);
                            }
                        }

                        else{
                            bookStatus = getString(R.string.avail);
                            mStatusSummary.setText(String.format("%s %s.", getString(R.string.current_status_msg), bookStatus));
                        }
                    }
                }
            }
        });

        /*
        REF: FireNoSQLDataBase example - loop.dcu.ie @author Chris Coughlan.
        REF: FireChatApp - SDA Github 2020.

        Send Button listener, First checks if the user had chosen a date and if the book status =
        available, if so then sets the book status as unavailable and enters the required details
        into the firebase DB. Also informs the user if they were successful in reserving the book.
         */
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dateBtnClicked > 0) {
                   if(bookStatus.equals(getString(R.string.avail))) {
                       bookStatus = getString(R.string.unavail);

                       Date currentDayTime = java.util.Calendar.getInstance().getTime();
                       currentDate = currentDayTime.toString();

                       Map<String, Object> bookDetails = new HashMap<>();
                       bookDetails.put("userName", userName);
                       bookDetails.put("selectedDate", selectedDate);
                       bookDetails.put("userID", String.valueOf(userid));
                       bookDetails.put("bookStatus", bookStatus);
                       bookDetails.put("currentDate", currentDate);
                       bookList.document(titleText).set(bookDetails);

                       sendBtn.setEnabled(false);

                       mDisplaySummary.setText(String.format("%s %s.", getString(R.string.success_reservation_msg), titleText));
                       mStatusSummary.setText(String.format("%s %s.", getString(R.string.current_status_msg), bookStatus));
                   }

                   else{
                       Toast.makeText(getApplicationContext(), R.string.unavail_message, Toast.LENGTH_LONG).show();
                       sendBtn.setEnabled(false);
                   }

                }
                else{
                    Toast.makeText(getApplicationContext(), R.string.select_date_msg, Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*
        Reset button lister to mark the book as available, also removes the selected and current
        times from the DB as the book is now marked as available.
         */
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBtn             .setEnabled(true);
                bookStatus          = getString(R.string.avail);
                mDisplaySummary     .setText(R.string.summary_avail);
                mStatusSummary      .setText(String.format("%s %s.", getString(R.string.current_status_msg), bookStatus));

                Map<String, Object> bookDetails = new HashMap<>();
                bookDetails.put("userName", userName);
                bookDetails.put("selectedDate", null);
                bookDetails.put("userID", String.valueOf(userid));
                bookDetails.put("bookStatus", bookStatus);
                bookDetails.put("currentDate", null);
                bookList.document(titleText).set(bookDetails);
            }
        });

        //set the toolbar we have overridden
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /*
    REF: SDA_2019 android course examples ViewGroup demo

    Takes the date chosen by the user in the calander widget and sets the global variable selectedDate
    to the chose date and current time.
     */
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

    /*
    Method to set the selected date variable and update the button clicked varible to ensure the
    user has selected a date.
     */
    private void updateDateAndTimeDisplay() {
        //date time year
        selectedDate = DateUtils.formatDateTime(this, mDateAndTime.getTimeInMillis(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR);
        dateBtnClicked++;
    }
}
