package com.example.sdaassign4_2019;


import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import static android.content.Context.MODE_PRIVATE;

/*
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment {

    //Global variables
    static final            String              PREF_KEY = "preferences";
    private static final    String              NAME_KEY = "NAME_KEY";
    private static final    String              EMAIL_KEY = "EMAIL_KEY";
    private static final    String              ID_KEY = "ID_KEY";
    private                 TextView savedName, savedEmail, savedId;
    private                 EditText            validName, validEmail, validId;
    public                  SharedPreferences   prefs;


    public Settings() {
        // Required empty public constructor
    }

    /*
    Ref: PrivatePreferences - loop.dcu.ie @author Chris Coughlan
    Ref: DataManagementSharedPreferencs - SDA Github 2020

    This fragment contains two button listeners, one to saved the users details inside of SharedPreferences
    and the other to clear the shared preferences.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_settings, container, false);

                prefs               = this.getActivity().getSharedPreferences(PREF_KEY, MODE_PRIVATE );
        final   Button saveBtn      = root.findViewById(R.id.saveButton);
                savedName           = root.findViewById(R.id.savedName);
                savedEmail          = root.findViewById(R.id.savedEmail);
                savedId             = root.findViewById(R.id.savedId);

        savedName       .setText(prefs.getString(NAME_KEY, ""));
        savedEmail      .setText(prefs.getString(EMAIL_KEY, ""));
        savedId         .setText(String.valueOf(prefs.getInt(ID_KEY, 0)));

        if(Integer.parseInt(savedId.getText().toString()) == 0) savedId.setVisibility(View.INVISIBLE);


        validName   = root.findViewById(R.id.userName);
        validEmail  = root.findViewById(R.id.email);
        validId     = root.findViewById(R.id.borrowerID);

        /*
        Button listener to firstly check if the values input into the text views are valid, if they
        come back as valid then they are saved in shared preferences.
        Also populates the existing details text views with the valid inputs.
         */
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempName = validName.getText().toString();
                String tempEmail = validEmail.getText().toString();
                int tempId;

                if(validId.getText().toString().equals("")){ tempId = 0;}
                else { tempId = Integer.parseInt(validId.getText().toString());}

                if (tempName.length() > 2 && isValidEmail(tempEmail) && (tempId > 10000000 && tempId < 99999999)) {

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(NAME_KEY, tempName);
                    editor.putString(EMAIL_KEY, tempEmail);
                    editor.putInt(ID_KEY, tempId);
                    editor.apply();

                    savedName   .setText(tempName);
                    savedEmail  .setText(tempEmail);
                    savedId     .setText(String.valueOf(tempId));

                    savedId.setVisibility(View.VISIBLE);

                }

                //If the user inputs are not valid, inform the user and clear the text boxes.
                else{
                    Toast.makeText(getContext(), R.string.validation_alert_text, Toast.LENGTH_SHORT).show();

                    validName.setText("");
                    validEmail.setText("");
                    validId.setText("");
                }

            }
        });

        /*
        Another button listener to clear the saved shared preferences values.
         */
        final Button deleteBtn = root.findViewById(R.id.deleteButton);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(NAME_KEY, "");
                editor.putString(EMAIL_KEY,"");
                editor.putInt(ID_KEY, 0);
                editor.apply();

                savedName   .setText("");
                savedEmail  .setText("");
                savedId     .setText("");

                validName   .setText("");
                validEmail  .setText("");
                validId     .setText("");

            }
        });

        return root;

    }

    /*
    REF:https://stackoverflow.com/questions/12947620/email-address-validation-in-android-on-edittext
    Method to validate the users email address
     */
    private static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


}
