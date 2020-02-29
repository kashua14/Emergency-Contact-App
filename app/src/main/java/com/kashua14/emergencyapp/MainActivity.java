package com.kashua14.emergencyapp;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.seismic.ShakeDetector;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    FragmentManager fragmentManager;


    ContactAddFragment contactAddFragment = new ContactAddFragment();
    private int onOff = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, ContactsService.class));

        fragmentManager = getSupportFragmentManager();

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, ContactAddFragment.class);
//                startActivity(intent);
                    changeFragment(contactAddFragment, "Add Emergency Contact");
                    fab.setVisibility(View.GONE);
//                    fab.setVisibility(View.VISIBLE);
            }
        });



    }

    public void changeFragment(Fragment fragment, String fragment_title) {
        fragmentManager.beginTransaction().replace(R.id.contact_add_frame, fragment, null)
                .commit();
        getSupportActionBar().setTitle(fragment_title);
    }

    public void displayFloatingButton(){
        fab.setVisibility(View.VISIBLE);
        getSupportActionBar().setTitle("Emergency Contact App");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
