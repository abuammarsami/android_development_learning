package com.ammar.firestoreapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText nameET;
    private EditText emailET;
    private Button saveBtn;

    // for 2nd CRUD Operation (Reading Data)
    private TextView dataTV;
    private Button showBtn;

    // Firebase Firestore Reference:
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    // for 2nd CRUD Operation (Reading Data)
    private DocumentReference friendRef = db.collection("Users")
            .document("Friends");

    //Keys for Firestore:
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        nameET = findViewById(R.id.nameET);
        emailET = findViewById(R.id.emailET);
        saveBtn = findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(v -> {
            String name = nameET.getText().toString().trim();
            String email = emailET.getText().toString().trim();
            saveDataToFireStore(name, email);
        });


        // for 2nd CRUD Operation (Reading Data)
        dataTV = findViewById(R.id.dataTV);
        showBtn = findViewById(R.id.retrieveBtn);

        showBtn.setOnClickListener(v -> {
            // Reading Data from Firestore
            friendRef.get()
                    .addOnSuccessListener(documentSnapshot -> {
                        // Lets Display retrieved data into text view
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString(KEY_NAME);
                            String email = documentSnapshot.getString(KEY_EMAIL);
                            dataTV.setText("Name: " + name + "\nEmail: " + email);
                        } else {
                            dataTV.setText("No Data Found!");
                        }
                    })
                    .addOnFailureListener(e -> {
                        dataTV.setText("Error!");
                    });
        });







        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void saveDataToFireStore(String name, String email) {
        // Saving data as key-value pairs (MAP data structure)
        Map<String, Object> data = new HashMap<>();
        data.put(KEY_NAME, name);
        data.put(KEY_EMAIL, email);

        // Adding data to Firestore
        // Collection Name: Users
        db.collection("Users")
                .document("Friends")
                .set(data)
                .addOnSuccessListener(aVoid -> {
                    // Data added successfully
                    Toast.makeText(MainActivity.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
//                    nameET.setText("");
//                    emailET.setText("");
                })
                .addOnFailureListener(e -> {
                    // Handle errors
                    Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                });


    }

    // Building CRUD Application with Firestore
    // 1- Saving Dara on Firestore (Creating Data)       [DONE]

    // 2- Reading Data from Firestore (Reading Data)     [DONE]

    // 2.1 - Listening for Realtime Updates (Reading Data) [DONE]


    @Override
    protected void onStart() {
        super.onStart();
        // 2.1 - Listening for Realtime Updates (Reading Data)
        friendRef.addSnapshotListener(this, (value, error) -> {
            if (error != null) {
                Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (value != null && value.exists()) {
                String name = value.getString(KEY_NAME);
                String email = value.getString(KEY_EMAIL);
                dataTV.setText("Name: " + name + "\nEmail: " + email);
            } else {
                dataTV.setText("No Data Found!");
            }
        });
    }


    // 3- Updating Data on Firestore (Updating Data)

}