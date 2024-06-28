package com.ammar.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ammar.journalapp.util.JournalUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    // Widgets
    AutoCompleteTextView emailACTV;
    EditText passwordET;
    Button logInBtn;
    Button registerBtn;

    // Firebase Authentication
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    // Firebase Connection
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersCollectionRef = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        emailACTV = findViewById(R.id.emailACTV);
        passwordET = findViewById(R.id.passwordET);
        logInBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);

        // Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance();

        logInBtn.setOnClickListener(v -> {
            // Login
            String email = emailACTV.getText().toString();
            String password = passwordET.getText().toString();
            LoginEmailPasswordUser(email, password);
        });

        registerBtn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SignUpActivity.class));
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void LoginEmailPasswordUser(String email, String password) {
        // checking for empty text fields
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Login successful
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            assert user != null;
                            final String currentUserId = user.getUid();
                            usersCollectionRef.
                                    whereEqualTo("userId", currentUserId)
                                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                @Override
                                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                    if (error != null) {
                                                        return;
                                                    }
                                                    assert value != null;
                                                    if (!value.isEmpty()) {
                                                        // getting all QueryDocumentSnapshot documents
                                                        for (QueryDocumentSnapshot snapshot: value) {
                                                            JournalUser journalUser = JournalUser.getInstance();
                                                            journalUser.setUsername(snapshot.getString("username"));
                                                            journalUser.setUserId(snapshot.getString("userId"));


                                                            // Go to ListActivity after successful login
                                                            startActivity(new Intent(MainActivity.this, JournalListActivity.class));

                                                        }
                                                    }
                                                }
                                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // if failed
                            Toast.makeText(MainActivity.this, "Something went wrong: "+e , Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }
}