package com.kashua14.emergencyapp.Fragments;


import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.kashua14.emergencyapp.Adaptors.ListData;
import com.kashua14.emergencyapp.Adaptors.RecyclerViewAdapter;
import com.kashua14.emergencyapp.Database.DatabaseHelper;
import com.kashua14.emergencyapp.MainActivity;
import com.kashua14.emergencyapp.R;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactAddFragment extends Fragment {

    private EditText name, phone, relationship;

    private DatabaseHelper databaseHelper;
    private List<ListData> contactsList;
    private RecyclerViewAdapter recyclerViewAdapter;
//    private RecyclerView recyclerView ;

    public ContactAddFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_add, container, false);

        // Initialize Variables
        name = view.findViewById(R.id.name);
        phone = view.findViewById(R.id.phone);
        relationship = view.findViewById(R.id.relationship);
        Button save = view.findViewById(R.id.save);
        Button cancel = view.findViewById(R.id.cancel);
//        RecyclerView recyclerView = view.findViewById(R.id.main_activity_list);

        databaseHelper = new DatabaseHelper(getActivity());
        contactsList = databaseHelper.getAllContacts();
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), contactsList);
//        recyclerView.setAdapter(recyclerViewAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
                    if (finalContactAddFragment != null) {
                        getFragmentManager().beginTransaction().remove(finalContactAddFragment).commit();
                    }
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
                String r = relationship.getText().toString();

//                RecyclerView recyclerView = v.findViewById(R.id.main_activity_list);

                if (!n.isEmpty() && !p.isEmpty() && !r.isEmpty()) {
                    if (databaseHelper.addTupple(n, p, r)) {
                        name.setText("");
                        phone.setText("");
                        relationship.setText("");

                        Toast.makeText(getActivity(), "One row inserted...",
                                Toast.LENGTH_SHORT).show();

                        // Clear List
                        contactsList.clear();
                        contactsList.addAll(databaseHelper.getAllContacts());

                        // Refresh the RecyclerView Data
                        recyclerViewAdapter.notifyDataSetChanged();
//                        recyclerView.invalidate();
//                        recyclerView.refreshDrawableState();
                    }
                }
            }
        });
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
