package com.kashua14.emergencyapp.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kashua14.emergencyapp.R;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<ListData> contacts;
    private Context context;

    public RecyclerViewAdapter(Context context, List<ListData> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        ListData contactOne = contacts.get(position);

        if (!contactOne.getName().equals("") && !contactOne.getPhone().equals("") && !contactOne.getRelationship().equals("")) {
            holder.name.setText(contactOne.getName());
            holder.phone.setText(contactOne.getPhone());
            holder.relationship.setText(contactOne.getRelationship());
        }

//        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // if the access No. and marks are shown.
//                if (holder.tittle.isShown() && holder.values.isShown()){
//                    holder.tittle.setVisibility(View.GONE);
//                    holder.values.setVisibility(View.GONE);
//                    Toast.makeText(context, mName.get(position)+"'s Details Closed", Toast.LENGTH_SHORT).show();
//                }else{  // if the access No. and marks are not shown.
//                    holder.tittle.setVisibility(View.VISIBLE);
//                    holder.values.setVisibility(View.VISIBLE);
//                    Toast.makeText(context, mName.get(position)+"'s Details Opened", Toast.LENGTH_SHORT).show();
////                    prev_regNo = mRegNo.get(position);
//                }
//
//            }
//        });

//        ListData studentInfo = data.get(position);
//        holder.name.setText(studentInfo.name);
//        holder.regNo.setText(studentInfo.regNo);

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView phone, name, relationship;
        LinearLayout parentLayout;

        MyViewHolder(View itemView) {
            super(itemView);
            phone = itemView.findViewById(R.id.phone);
            name = itemView.findViewById(R.id.name);
            parentLayout = itemView.findViewById(R.id.parent_contact_details);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
