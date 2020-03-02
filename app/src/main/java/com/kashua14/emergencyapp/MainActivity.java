package com.kashua14.emergencyapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kashua14.emergencyapp.Adaptors.ListData;
import com.kashua14.emergencyapp.Adaptors.RecyclerViewAdapter;
import com.kashua14.emergencyapp.Database.DatabaseHelper;
import com.kashua14.emergencyapp.Fragments.ContactAddFragment;
import com.kashua14.emergencyapp.services.ContactsService;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    FragmentManager fragmentManager;

    private DatabaseHelper databaseHelper;
    private List<ListData> contactsList;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private ListView listView;

    ContactAddFragment contactAddFragment = new ContactAddFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.main_activity_list);

        databaseHelper = new DatabaseHelper(MainActivity.this);
        try {
            contactsList = databaseHelper.getAllContacts();
        } catch (RuntimeException e) {
            databaseHelper.onCreate(databaseHelper.getWritableDatabase());
            System.out.println(e.getMessage());
        }

        if (contactsList.isEmpty()) {
            findViewById(R.id.no_contacts).setVisibility(View.VISIBLE);
        }

        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, contactsList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        startService(new Intent(this, ContactsService.class));

        fragmentManager = getSupportFragmentManager();

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, ContactAddFragment.class);
//                startActivity(intent);
                changeFragment(contactAddFragment, "Add Emergency Contact");
                findViewById(R.id.main_activity_list).setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
//                    fab.setVisibility(View.VISIBLE);
            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void changeFragment(Fragment fragment, String fragment_title) {
        fragmentManager.beginTransaction().replace(R.id.contact_add_frame, fragment, null)
                .commit();
        Objects.requireNonNull(getSupportActionBar()).setTitle(fragment_title);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void displayFloatingButton(){
        fab.setVisibility(View.VISIBLE);
        findViewById(R.id.main_activity_list).setVisibility(View.VISIBLE);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Emergency Contact App");
    }

}
