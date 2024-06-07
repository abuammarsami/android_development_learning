package com.ammar.contactmanagerappdatabinding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.ammar.contactmanagerappdatabinding.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ContactAppDatabase contactAppDatabase;
    private ArrayList<Contact> contacts = new ArrayList<>();
    private ContactDataAdapter contactDataAdapter;

    // Binding
    private ActivityMainBinding activityMainBinding;
    private MainActivityClickHandlers handlers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Dara binding
        // this line is used to bind the activity to its layout
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        handlers = new MainActivityClickHandlers(this);
        activityMainBinding.setClickHandler(handlers);


        // RecyclerView
//        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        RecyclerView recyclerView = activityMainBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Adapter
        contactDataAdapter = new ContactDataAdapter(contacts);


        // Database
        contactAppDatabase = Room.databaseBuilder(
                getApplicationContext(),
                ContactAppDatabase.class,
                "ContactDataBindingDB").allowMainThreadQueries().build();

        // Add Data
        LoadData();

        recyclerView.setAdapter(contactDataAdapter);

        // handling Swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Contact contactToDelete = contacts.get(viewHolder.getAdapterPosition());
                DeleteContact(contactToDelete);
            }
        }).attachToRecyclerView(recyclerView);

        // Since we have data binding, we don't need this
//        // FAB
//        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, AddNewContactActivity.class);
//                startActivityForResult(intent, 1);
//            }
//        });

        // use onActivityResult to add contact




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

     // reason for this method is to handle the result of the AddNewContactActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String name = data != null ? data.getStringExtra("NAME") : null;
                String email = data != null ? data.getStringExtra("EMAIL") : null;
                Contact contact = new Contact(name, email, 0);
                AddNewContact(contact);
            }
        }
    }

    private void DeleteContact(Contact contactToDelete) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Background work here
                contactAppDatabase.getContactDao().deleteContact(contactToDelete);
                contacts.remove(contactToDelete);

                // on Post Execution
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // UI Thread work here
//                        LoadData();
                        contactDataAdapter.notifyDataSetChanged();
                    }
                });
            }
        });


    }

    private void LoadData() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
                             @Override
                             public void run() {
                                 // Background work here
                                 contacts.addAll(contactAppDatabase.getContactDao().getAllContacts());


                                 // On Post Execution
                                 handler.post(new Runnable() {
                                     @Override
                                     public void run() {
                                         // UI Thread work here
                                         contactDataAdapter.setContacts(contacts);
                                         contactDataAdapter.notifyDataSetChanged();
                                     }
                                 });
                             }
                         }
        );


    }

    public void AddNewContact(Contact contact) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Background work here
                contactAppDatabase.getContactDao().addContact(contact);
                contacts.add(contact);

                // on Post Execution
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // UI Thread work here
                        contactDataAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    public class MainActivityClickHandlers {
        Context context;

        public MainActivityClickHandlers(Context context) {
            this.context = context;
        }


        public void onFABClicked(View view) {
            // Handle FAB click
            Intent intent = new Intent(MainActivity.this, AddNewContactActivity.class);
            startActivityForResult(intent, 1);
        }
    }
}