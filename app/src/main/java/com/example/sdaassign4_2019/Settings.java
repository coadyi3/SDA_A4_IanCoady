package com.example.sdaassign4_2019;


import android.content.Context;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment {

    static final            String              PREF_KEY = "preferences";
    private static final    String              NAME_KEY = "NAME_KEY";
    private static final    String              EMAIL_KEY = "EMAIL_KEY";
    private static final    String              ID_KEY = "ID_KEY";
    private                 TextView            name, email, id;
    private                 EditText            validName, validEmail, validId;
    public                  SharedPreferences   prefs;


    public Settings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        prefs = this.getActivity().getSharedPreferences(PREF_KEY, MODE_PRIVATE );

        name    = root.findViewById(R.id.savedName);
        email   = root.findViewById(R.id.savedEmail);
        id      = root.findViewById(R.id.savedId);

        name        .setText(prefs.getString(NAME_KEY, ""));
        email       .setText(prefs.getString(EMAIL_KEY, ""));
        id          .setText(String.valueOf(prefs.getInt(ID_KEY, 0)));

        if(Integer.parseInt(id.getText().toString()) == 0) id.setVisibility(View.INVISIBLE);


        validName   = root.findViewById(R.id.userName);
        validEmail  = root.findViewById(R.id.email);
        validId     = root.findViewById(R.id.borrowerID);

        final Button saveBtn = root.findViewById(R.id.saveButton);

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

                    name.setText(tempName);
                    email.setText(tempEmail);
                    id.setText(String.valueOf(tempId));

                    id.setVisibility(View.VISIBLE);

                }

                else{
                    Toast.makeText(getContext(), R.string.validation_alert_text, Toast.LENGTH_SHORT).show();

                    validName.setText("");
                    validEmail.setText("");
                    validId.setText("");
                }

            }
        });


        final Button deleteBtn = root.findViewById(R.id.deleteButton);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(NAME_KEY, "");
                editor.putString(EMAIL_KEY,"");
                editor.putInt(ID_KEY, 0);
                editor.apply();

                name        .setText("");
                email       .setText("");
                id          .setText("");

                validName   .setText("");
                validEmail  .setText("");
                validId     .setText("");

            }
        });

        return root;

    }

    private static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


}
