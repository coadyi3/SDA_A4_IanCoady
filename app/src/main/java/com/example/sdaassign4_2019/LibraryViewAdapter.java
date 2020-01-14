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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import static com.example.sdaassign4_2019.Settings.PREF_KEY;


/*
 * @author Chris Coughlan 2019
 */
public class LibraryViewAdapter extends RecyclerView.Adapter<LibraryViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private Context mNewContext;

    //add array for each item\
    private ArrayList<String> mAuthor;
    private ArrayList<String> mTitle;
    private ArrayList<String> mImageID;

    LibraryViewAdapter(Context mNewContext, ArrayList<String> author, ArrayList<String> title, ArrayList<String> imageId) {
        this.mNewContext = mNewContext;
        this.mAuthor = author;
        this.mTitle = title;
        this.mImageID = imageId;

    }

    //declare methods
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        Log.d(TAG, "onBindViewHolder: was called");

        viewHolder.authorText.setText(mAuthor.get(position));
        viewHolder.titleText.setText(mTitle.get(position));

        Glide.with(mNewContext)
                .asBitmap()
                .load(mImageID.get(position))
                .into(viewHolder.imageItem);

        //should check here to see if the book is available.
        viewHolder.checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                SharedPreferences prefs = mNewContext.getSharedPreferences(PREF_KEY,Context.MODE_PRIVATE);

                final String name       = prefs.getString("NAME_KEY", "");
                final String email      = prefs.getString("EMAIL_KEY", "");
                final int id            = prefs.getInt("ID_KEY" , 0);

                if(name.equals("") || email.equals("") || id <= 0)
                {
                    Toast.makeText(mNewContext, R.string.details_prompt,Toast.LENGTH_SHORT).show();
                }

                else
                {
                    Toast.makeText(mNewContext, mTitle.get(position), Toast.LENGTH_SHORT).show();

                    Intent myOrder = new Intent (mNewContext, CheckOut.class);
                    myOrder.putExtra("title", mTitle.get(position));
                    mNewContext.startActivity(myOrder);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAuthor.size();
    }

    //view holder class for recycler_list_item.xml
    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageItem;
        TextView authorText;
        TextView titleText;
        Button checkOut;
        RelativeLayout itemParentLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            //grab the image, the text and the layout id's
            imageItem = itemView.findViewById(R.id.bookImage);
            authorText = itemView.findViewById(R.id.authorText);
            titleText = itemView.findViewById(R.id.bookTitle);
            checkOut = itemView.findViewById(R.id.out_button);
            itemParentLayout = itemView.findViewById(R.id.listItemLayout);

        }
    }
}
