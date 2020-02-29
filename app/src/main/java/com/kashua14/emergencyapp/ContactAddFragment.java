package com.kashua14.emergencyapp;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kashua14.emergencyapp.FeedReaderContract.FeedEntry;

import com.google.android.material.badge.BadgeUtils;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactAddFragment extends Fragment {

    EditText name, phone, email, relationship;
    Button save, cancel;

    private DatabaseHelper dbHelper = new DatabaseHelper(getContext());

    public ContactAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_add, container, false);

        // Initialize Variables
        name = view.findViewById(R.id.name);
        phone = view.findViewById(R.id.phone);
        email = view.findViewById(R.id.email);
        relationship = view.findViewById(R.id.relationship);
        save = view.findViewById(R.id.save);
        cancel = view.findViewById(R.id.cancel);


        final MainActivity mainActivity = (MainActivity) getActivity();

        Fragment contactAddFragment = null;
        if (getFragmentManager() != null) {
            contactAddFragment = getFragmentManager().findFragmentById(R.id.contact_add_frame);
        }

        final Fragment finalContactAddFragment = contactAddFragment;

        cancel.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction().remove(finalContactAddFragment).commit();
                }
                if (mainActivity != null) {
                    mainActivity.displayFloatingButton();
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = name.getText().toString();
                String p = phone.getText().toString();
                String e = email.getText().toString();
                String r = relationship.getText().toString();

                boolean savedOrNot = addTupple(n, p, e, r);
                if (savedOrNot){
                    name.setText("");
                    phone.setText("");
                    email.setText("");
                    relationship.setText("");
                    Toast.makeText(getActivity(), "Saved data !!", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return view;
    }


    private boolean addTupple(String name, String phone, String email, String relationship){

        dbHelper = new DatabaseHelper(getContext());
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_NAME, name);
        values.put(FeedEntry.COLUMN_NAME_PHONE, phone);
        values.put(FeedEntry.COLUMN_NAME_EMAIL, email);
        values.put(FeedEntry.COLUMN_NAME_RELATIONSHIP, relationship);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(FeedEntry.TABLE_NAME, null, values);
        if(newRowId == -1){
            return false;
        }
        return true;
    }

    private void getAllContacts(){
        dbHelper = new DatabaseHelper(getContext());
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] projection = {
                BaseColumns._ID,
                FeedEntry.COLUMN_NAME_NAME,
                FeedEntry.COLUMN_NAME_PHONE,
                FeedEntry.COLUMN_NAME_EMAIL,
                FeedEntry.COLUMN_NAME_RELATIONSHIP
        };

        String sortOrder =
                FeedEntry.COLUMN_NAME_NAME + " DESC";

        Cursor cursor = db.query(
                FeedEntry.TABLE_NAME, projection,
                null, null, null, null, sortOrder);


    }

    @Override
    public void onDestroyView() {
        dbHelper.close();
        super.onDestroyView();
    }
}
