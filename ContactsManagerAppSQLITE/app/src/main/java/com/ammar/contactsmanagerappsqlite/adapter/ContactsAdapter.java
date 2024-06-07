package com.ammar.contactsmanagerappsqlite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ammar.contactsmanagerappsqlite.MainActivity;
import com.ammar.contactsmanagerappsqlite.R;
import com.ammar.contactsmanagerappsqlite.db.entity.Contact;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
    // variables
    private Context context;
    private ArrayList<Contact> contactList;
    private MainActivity mainActivity;

    // 2- ViewHolder class
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // variables
        public TextView name, email;

        public MyViewHolder(android.view.View view) {
            super(view);
            // initialize variables
            name = view.findViewById(R.id.name);
            email = view.findViewById(R.id.email);
        }
    }

    // constructor
    public ContactsAdapter(Context context, ArrayList<Contact> contactList, MainActivity mainActivity) {
        this.context = context;
        this.contactList = contactList;
        this.mainActivity = mainActivity;
    }

    // 3- onCreateViewHolder method
    @Override
    public MyViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    // 4- onBindViewHolder method
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Contact contact = contactList.get(position);
        holder.name.setText(contact.getName());
        holder.email.setText(contact.getEmail());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.addAndEditContacts(true, contact, position);
            }
        });
    }

    // 5- getItemCount method
    @Override
    public int getItemCount() {
        return contactList.size();
    }

}
