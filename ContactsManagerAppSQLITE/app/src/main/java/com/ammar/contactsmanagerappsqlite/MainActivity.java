package com.ammar.contactsmanagerappsqlite;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ammar.contactsmanagerappsqlite.adapter.ContactsAdapter;
import com.ammar.contactsmanagerappsqlite.db.DatabaseHelper;
import com.ammar.contactsmanagerappsqlite.db.entity.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // variables
    private ContactsAdapter contactsAdapter;
    private ArrayList<Contact> contactList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My FavoriteContacts");

        //RecyclerView
        recyclerView = findViewById(R.id.recycler_view_contacts);

        //Database
        db = new DatabaseHelper(this);

        // Contacts List
        contactList.addAll(db.getAllContacts());
        contactsAdapter = new ContactsAdapter(this, contactList, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new androidx.recyclerview.widget.DefaultItemAnimator());
        recyclerView.setAdapter(contactsAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAndEditContacts(false, null, -1);
            }
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void addAndEditContacts(final boolean isUpdated, final Contact contact, final int position) {
        LayoutInflater layoutInflater= LayoutInflater.from(getApplicationContext());
        View view = layoutInflater.inflate(R.layout.layout_add_contact, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setView(view);

        TextView contactTitle = view.findViewById(R.id.new_contact_title);
        final TextView newContactName = view.findViewById(R.id.name);
        final TextView newContactEmail = view.findViewById(R.id.email);

        contactTitle.setText(!isUpdated ? "Add New Contact" : "Edit Contact");

        if (isUpdated && contact != null) {
            newContactName.setText(contact.getName());
            newContactEmail.setText(contact.getEmail());
        }

        builder.setCancelable(false)
                .setPositiveButton(isUpdated ? "Update" : "Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(isUpdated ? "Delete": "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isUpdated){
                            DeleteContact(contact, position);
                        } else{
                            dialog.cancel();
                        }
                    }
                });

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newContactName.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter A Name", Toast.LENGTH_SHORT).show();
//                    newContactName.setError("Enter Contact Name");
//                    newContactName.requestFocus();
                    return;
                } else {
                    alertDialog.dismiss();
                }


//                    if (newContactEmail.getText().toString().isEmpty()) {
//                    newContactEmail.setError("Enter Contact Email");
//                    newContactEmail.requestFocus();
//                    return;
//                }

                if (isUpdated && contact != null) {
                    UpdateContact(newContactName.getText().toString(), newContactEmail.getText().toString(), position);
                } else {
                    CreateContact(newContactName.getText().toString(), newContactEmail.getText().toString());
                }
                alertDialog.dismiss();
            }
        });


    }

    private void CreateContact(String name, String email) {
        long id = db.insertContact(name, email);

        Contact contact = db.getContact(id);

        if (contact != null) {
            contactList.add(0, contact);
            contactsAdapter.notifyDataSetChanged();
        }
    }

    private void UpdateContact(String name, String email, int position) {
        Contact contact = contactList.get(position);
        contact.setName(name);
        contact.setEmail(email);

        db.updateContact(contact);
        contactList.set(position, contact);
        contactsAdapter.notifyDataSetChanged();

    }

    private void DeleteContact(Contact contact, int position) {
        contactList.remove(position);
        db.deleteContact(contact);
        contactsAdapter.notifyDataSetChanged();
        
    }

    //Menu bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}