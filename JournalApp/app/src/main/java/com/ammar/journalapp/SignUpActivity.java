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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ammar.journalapp.util.JournalUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    // Widgets
    AutoCompleteTextView emailACTV;
    EditText passwordET;
    Button signUpBtn;
    EditText usernameET;

    // Firebase Authentication
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    // Firebase Connection
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = db.collection("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        emailACTV = findViewById(R.id.signUpEmailACTV);
        passwordET = findViewById(R.id.signUpPasswordET);
        usernameET = findViewById(R.id.signUpUsernameET);
        signUpBtn = findViewById(R.id.signUpBtn);

        // Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Firebase Auth requires presence of Google Account on device

        // Authentication
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {
                    // User already logged in

                }
                else {
                    // No user yet!

                }
            }
        };

        signUpBtn.setOnClickListener(v -> {
            // Sign Up
            String email = emailACTV.getText().toString().trim();
            String password = passwordET.getText().toString().trim();
            String username = usernameET.getText().toString().trim();
            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                CreateUserEmailAccount(email, password, username);
            }
            else {
                // Empty fields
                Toast.makeText(SignUpActivity.this,
                        "Please fill in all fields",
                        Toast.LENGTH_SHORT).show();
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void CreateUserEmailAccount(String email, String password, String username) {
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // We take user to Next Activity: (AddJournal Activity
                            currentUser = firebaseAuth.getCurrentUser();
                            assert currentUser != null;
                            final String currentUserId = currentUser.getUid();

                            // Create a userMap so we can create a user in the user collection in Firestore
                            Map<String, String> userObj = new HashMap<>();
                            userObj.put("userId", currentUserId);
                            userObj.put("username", username);

                            // Save user to Firestore
                            usersRef.add(userObj)
                                    .addOnSuccessListener(documentReference -> {
                                        documentReference.get()
                                                .addOnCompleteListener(task1 -> {
                                                    if (task1.isSuccessful()) {
                                                        if (Objects.requireNonNull(task1.getResult()).exists()) {
                                                            String createdUsername = task1.getResult().getString("username");
                                                            // if the user is registered successfully, take them to the AddJournalActivity

                                                            // Getting use of Global Journal USER
                                                            JournalUser journalUser = JournalUser.getInstance();
                                                            journalUser.setUserId(currentUserId);
                                                            journalUser.setUsername(createdUsername);


                                                            Intent intent = new Intent(SignUpActivity.this, AddJournalActivity.class);
                                                            intent.putExtra("username", createdUsername);
                                                            intent.putExtra("userId", currentUserId);
                                                            startActivity(intent);
                                                        } else {
                                                            // User not created
                                                        }
                                                    }
                                                });
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Display failed Message to user
                                            Toast.makeText(SignUpActivity.this,
                                                    "User not created, something went wrong",
                                                    Toast.LENGTH_SHORT).show();

                                        }
                                    });
                        } else {
                            // Sign up failed
                            Toast.makeText(SignUpActivity.this,
                                    "Sign up failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        currentUser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}