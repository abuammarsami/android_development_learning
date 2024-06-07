package com.ammar.contactsmanagerapproomdatabase;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ammar.contactsmanagerapproomdatabase.adapter.ContactsAdapter;
import com.ammar.contactsmanagerapproomdatabase.db.ContactsAppDatabase;
import com.ammar.contactsmanagerapproomdatabase.db.entity.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class MainActivity extends AppCompatActivity {

    // variables
    private ContactsAdapter contactsAdapter;
    private ArrayList<Contact> contactList = new ArrayList<>();
    private RecyclerView recyclerView;
   private ContactsAppDatabase contactsAppDatabase;

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

        //last step for call back concept
        RoomDatabase.Callback myCallBack = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                // These are 4 contacts already created in the app when installed (Built-In Contacts)
                CreateContact("Bill Gates", "billgates@microsoft.com");
                CreateContact("Elon Musk", "elonmusk@tesla.com");
                CreateContact("Jeff Bezos", "jeffbezos@ghorardim.com");
                CreateContact("Mark Zuckerberg", "mark@meta.com");
                Log.i("TAG", "onCreate: 4 contacts created in the database.");
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                Log.i("TAG", "Database has been opened");
                super.onOpen(db);
            }
        };

        //Database
        contactsAppDatabase = Room.databaseBuilder(
                getApplicationContext(),
                ContactsAppDatabase.class,
                "ContactDB")
                .allowMainThreadQueries()
                .addCallback(myCallBack)
                .build();


        // Displaying All Contacts List from the database
        // we will get error if it takes more than 5 seconds to load the data(Application not responding)
        // while using the main thread to load the data, so we will use AsyncTask to load the data
//        contactList.addAll(contactsAppDatabase.getContactDAO().getAllContacts()); old mai thread

        DisplayAllContactInBackGround();

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
        long id = contactsAppDatabase.getContactDAO().addContact(new Contact( name, email, 0));

        Contact contact = contactsAppDatabase.getContactDAO().getContact(id);

        if (contact != null) {
            contactList.add(0, contact);
            contactsAdapter.notifyDataSetChanged();
        }
    }

    private void UpdateContact(String name, String email, int position) {
        Contact contact = contactList.get(position);
        contact.setName(name);
        contact.setEmail(email);

        contactsAppDatabase.getContactDAO().updateContact(contact);
        contactList.set(position, contact);
        contactsAdapter.notifyDataSetChanged();

    }

    private void DeleteContact(Contact contact, int position) {
        contactList.remove(position);
        contactsAppDatabase.getContactDAO().deleteContact(contact);
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

    private void DisplayAllContactInBackGround() {
        // there is an object that executes submitted reasonable tasks, And for using execute as we are going to use that execute their services.
        //The Java execute or service is a construct that allows you to pass a task to be executed by a thread
        // This service creates and maintains a reusable pool for threads for executing submitted tasks.
        // The service also manages a queue which is used when there are more tasks than the number of threads
        // in the pool, and there is a need to queue up tasks until there is a free thread available to execute

        ExecutorService executorService = java.util.concurrent.Executors.newSingleThreadExecutor();

        //Now we need a handler because we need these things to be handled that execute our service handler handler
        //The handler is used to send and process messages and runnable objects associated with a thread's message queue.
        //so a handler object takes log messages from a logger and exports them.
        //It might, for example, write them to a console or write them to a file or send them to a network logging
        //service or forwarded them to and or as logging or whatever.
        //So we need to pass that logging looper to get main loop.
        // Android Looper is a Java class within Android user interface that together with the handler class to
        //process UI events such as button clicks, screen read rows and orientation switches.
        //They may also be used to upload content to beat Edge to be service, resize images and execute remote
        //requests.
        Handler handler = new Handler(getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {

                // background Work
                contactList.addAll(contactsAppDatabase.getContactDAO().getAllContacts());

                // Executed after the background work had finished
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // inform the recycler view that the data has changed and it should update
                        contactsAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}