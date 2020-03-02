package com.kashua14.emergencyapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kashua14.emergencyapp.Adaptors.ListData;
import com.kashua14.emergencyapp.Adaptors.RecyclerViewAdapter;
import com.kashua14.emergencyapp.Database.DatabaseHelper;

import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    List<ListData> contactsList;
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
//    private Object Tag = "josh";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        recyclerView = findViewById(R.id.contact_activity_list);

        databaseHelper = new DatabaseHelper(ContactsActivity.this);
        contactsList = databaseHelper.getAllContacts();

        if (contactsList.isEmpty()) {
            findViewById(R.id.no_contacts).setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
        }

        recyclerViewAdapter = new RecyclerViewAdapter(ContactsActivity.this, contactsList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ContactsActivity.this));

//        initRecyclerView();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                + WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                + WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                + WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

//    public void initRecyclerView(){
//        RecyclerView recyclerView = findViewById(R.id.contacts_list);
//        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, contacts);
//        recyclerView.setAdapter(recyclerViewAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
////        System.out.println("Contacts : ===========================================================");
////        System.out.println("Contacts : ===========================================================");
////        Log.d((String) Tag, "onCreate: "+contacts.get(0));
////        Log.d((String) Tag, "onCreate: "+contacts.get(1));
////        Log.d((String) Tag, "onCreate: "+contacts.get(2));
////        Log.d((String) Tag, "onCreate: "+contacts.get(3));
////        Log.d((String) Tag, "onCreate: "+contacts.get(4));
//    }

}
