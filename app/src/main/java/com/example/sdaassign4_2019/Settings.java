package com.example.sdaassign4_2019;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment {

    private static final String NAME_KEY = "NAME_KEY";
    private static final String EMAIL_KEY = "EMAIL_KEY";
    private static final int ID_KEY = 0;
    private TextView name, email, id;

    public Settings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        final SharedPreferences prefs = this.getActivity().getSharedPreferences("preferences", MODE_PRIVATE );

        name = root.findViewById(R.id.savedName);
        name.setText(prefs.getString(NAME_KEY, ""));
        


        return root;

    }


}
