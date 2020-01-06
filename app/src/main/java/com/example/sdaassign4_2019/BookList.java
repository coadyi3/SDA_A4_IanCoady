package com.example.sdaassign4_2019;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Objects;


/**
 * Images used are sourced from Public Domain Day 2019.
 * by Duke Law School's Center for the Study of the Public Domain
 * is licensed under a Creative Commons Attribution-ShareAlike 3.0 Unported License.
 * A simple {@link Fragment} subclass.
 * @author Chris Coughlan
 */
public class BookList extends Fragment {


    FirebaseStorage images = FirebaseStorage.getInstance();

    public BookList() {
        // Required empty public constructor
    }

    ViewPageAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_book_list, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.bookView_view);

        //add array for each item
        ArrayList<String> mAuthor = new ArrayList<>();
        ArrayList<String> mTitle = new ArrayList<>();
        ArrayList<String> mImageID = new ArrayList<>();


        mImageID.add("https://firebasestorage.googleapis.com/v0/b/assign4-iancoady.appspot.com/o/drawable%2Fsku10001.jpg?alt=media&token=ae34bc8c-f67b-4bfa-af4b-2c1f8df84f9f");
        mImageID.add("https://firebasestorage.googleapis.com/v0/b/assign4-iancoady.appspot.com/o/drawable%2Fsku10002.jpg?alt=media&token=4a229018-c49d-4e48-9874-2d7a80d4f851");
        mImageID.add("https://firebasestorage.googleapis.com/v0/b/assign4-iancoady.appspot.com/o/drawable%2Fsku10003.jpg?alt=media&token=549e4f0f-d12d-46b5-ac9d-54ddb5b415a4");
        mImageID.add("https://firebasestorage.googleapis.com/v0/b/assign4-iancoady.appspot.com/o/drawable%2Fsku10004.jpg?alt=media&token=b310c16b-4b59-48e8-9d1a-776340898c0b");
        mImageID.add("https://firebasestorage.googleapis.com/v0/b/assign4-iancoady.appspot.com/o/drawable%2Fsku10005.jpg?alt=media&token=8594a80b-d69b-46d2-903a-1c1ba5a97932");
        mImageID.add("https://firebasestorage.googleapis.com/v0/b/assign4-iancoady.appspot.com/o/drawable%2Fsku10006.jpg?alt=media&token=266946fd-bc15-44b6-98e7-047a31f7612b");
        mImageID.add("https://firebasestorage.googleapis.com/v0/b/assign4-iancoady.appspot.com/o/drawable%2Fsku10007.jpg?alt=media&token=f8e7e118-7621-4c3c-bdb5-3d581f3a11f5");
        mImageID.add("https://firebasestorage.googleapis.com/v0/b/assign4-iancoady.appspot.com/o/drawable%2Fsku10008.jpg?alt=media&token=f0fbabd7-20db-4ade-ab23-bee1e83fba62");
        mImageID.add("https://firebasestorage.googleapis.com/v0/b/assign4-iancoady.appspot.com/o/drawable%2Fsku10009.jpg?alt=media&token=60f13555-5653-4f6f-8e65-12fe7ce558b0");
        mImageID.add("https://firebasestorage.googleapis.com/v0/b/assign4-iancoady.appspot.com/o/drawable%2Fsku100010.jpg?alt=media&token=14aded73-3e4f-421f-afee-1d7ab1c4784e");
        mImageID.add("https://firebasestorage.googleapis.com/v0/b/assign4-iancoady.appspot.com/o/drawable%2Fsku100011.jpg?alt=media&token=6a1b5315-99c7-4c01-a854-e3074a412166");
        mImageID.add("https://firebasestorage.googleapis.com/v0/b/assign4-iancoady.appspot.com/o/drawable%2Fsku100012.jpg?alt=media&token=5702a234-0d39-4d4c-b59f-2908d6fd01b5");
        mImageID.add("https://firebasestorage.googleapis.com/v0/b/assign4-iancoady.appspot.com/o/drawable%2Fsku100013.jpg?alt=media&token=91b9c312-eaf3-447a-bdbe-a0f32ae60c8d");
        mImageID.add("https://firebasestorage.googleapis.com/v0/b/assign4-iancoady.appspot.com/o/drawable%2Fsku100014.jpg?alt=media&token=5b1a974e-b2d3-43ce-955a-e7c278486eac");

        //adding author and title.
        mAuthor.add("Edgar Rice Burroughs"); mTitle.add("Tarzan and the Golden Lion");
        mAuthor.add("Agatha Christie"); mTitle.add("The Murder on the Links");
        mAuthor.add("Winston S. Churchill"); mTitle.add("The World Crisis");
        mAuthor.add("E.e. cummings"); mTitle.add("Tulips and Chimneys");
        mAuthor.add("Robert Frost"); mTitle.add("New Hampshire");
        mAuthor.add("Kahlil Gibran"); mTitle.add("The Prophet");
        mAuthor.add("Aldous Huxley"); mTitle.add("Antic Hay");
        mAuthor.add("D.H. Lawrence"); mTitle.add("Kangaroo");
        mAuthor.add("Bertrand and Dora Russell"); mTitle.add("The Prospects of Industrial Civilization");
        mAuthor.add("Carl Sandberg"); mTitle.add("Rootabaga Pigeons");
        mAuthor.add("Edith Wharton"); mTitle.add("A Son at the Front");
        mAuthor.add("P.G. Wodehouse"); mTitle.add("The Inimitable Jeeves");
        mAuthor.add("P.G. Wodehouse"); mTitle.add("Leave it to Psmith");
        mAuthor.add("Viginia Woolf"); mTitle.add("Jacob's Room");

        LibraryViewAdapter recyclerViewAdapter = new LibraryViewAdapter(getContext(), mAuthor, mTitle, mImageID);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }

}
