package com.ammar.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ammar.journalapp.model.Journal;
import com.ammar.journalapp.ui.JournalRecyclerAdapter;
import com.ammar.journalapp.util.JournalUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class JournalListActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private StorageReference storageReference;
    private List<Journal> journalList;

    private RecyclerView journalListRV;
    private JournalRecyclerAdapter journalRecyclerAdapter;

    private CollectionReference journalCollectionRef = db.collection("Journal");
    private TextView noJournalEntryTV;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_journal_list);
        // 1. Initializing variables and References
        // Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        // Widgets
        journalListRV = findViewById(R.id.journal_listRV);
        noJournalEntryTV = findViewById(R.id.list_no_postTV);
        journalListRV.setHasFixedSize(true);
        journalListRV.setLayoutManager(new LinearLayoutManager(this));

        // Posts array list
        journalList = new ArrayList<>();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // 2. Adding the Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_add_journal) {
            // Going to Add Journal Activity
            if (user != null && firebaseAuth != null) {
                startActivity(new Intent(
                        JournalListActivity.this, AddJournalActivity.class));
            }
        } else if (itemId == R.id.action_sign_out) {
            // Sign out
            if (user != null && firebaseAuth != null) {
                firebaseAuth.signOut();
                startActivity(new Intent(
                        JournalListActivity.this, MainActivity.class));
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    // 3. getting all posts

    @Override
    protected void onStart() {
        super.onStart();

        journalCollectionRef.whereEqualTo("userId", JournalUser.getInstance().getUserId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (QueryDocumentSnapshot journals : queryDocumentSnapshots) {
                                Journal journal = journals.toObject(Journal.class);
                                journalList.add(journal);
                            }

                            // RecyclerView Adapter
                            journalRecyclerAdapter = new JournalRecyclerAdapter(
                                    JournalListActivity.this, journalList);
                            journalListRV.setAdapter(journalRecyclerAdapter);
                            journalRecyclerAdapter.notifyDataSetChanged();


                        } else {
                            noJournalEntryTV.setVisibility(TextView.VISIBLE);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Any Failure
                        Toast.makeText(JournalListActivity.this, "Error: "
                                + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }
}