package com.ammar.journalapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ammar.journalapp.model.Journal;
import com.ammar.journalapp.util.JournalUser;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;

public class AddJournalActivity extends AppCompatActivity {

    private static final int GALLERY_CODE = 1;
    // Widgets
    private Button saveJournalBtn;
    private ProgressBar progressBar;
    private ImageView addPhotoBtn;
    private EditText titleET;
    private TextView currentUserTV;
    private ImageView imageView;
    private EditText thoughtsET;

    // User Id & Username
    private String currentUserId;
    private String currentUsername;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;

    // Connection to Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;

    private CollectionReference journalRef = db.collection("Journal");
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_journal);

        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.postProgressBar);
        titleET = findViewById(R.id.postTitleET);
        thoughtsET = findViewById(R.id.postDescriptionET);
        currentUserTV = findViewById(R.id.postUsernameTV);
        saveJournalBtn = findViewById(R.id.postSaveJournalBtn);
        addPhotoBtn = findViewById(R.id.postCameraBtn);
        imageView = findViewById(R.id.postIV);

        progressBar.setVisibility(View.INVISIBLE);

        if (JournalUser.getInstance().getUsername() != null) {
            currentUserId = JournalUser.getInstance().getUserId();
            currentUsername = JournalUser.getInstance().getUsername();

            currentUserTV.setText(currentUsername);
        }

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User already logged in
                    currentUserTV.setText(user.getDisplayName());
                }
                else {
                    // No user yet!
                }
            }
        };
        
        saveJournalBtn.setOnClickListener(v -> {
            // Save Journal
            SaveJournal();
        });

        addPhotoBtn.setOnClickListener(v -> {
            // Add Photo
            //Getting Image from Gallery
            Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, GALLERY_CODE);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * This method is used to save a journal entry to Firebase Firestore.
     * It first retrieves the title and thoughts from the respective EditText fields and trims any leading or trailing whitespace.
     * It then checks if the title, thoughts, and imageUri are not empty.
     * If they are not empty, it creates a StorageReference to the path where the image will be stored in Firebase Storage.
     * The image is then uploaded to Firebase Storage.
     * Upon successful upload, the download URL of the image is retrieved and a new Journal object is created with the title, thoughts, image URL, current time, user ID, and username.
     * The Journal object is then added to the Firestore collection.
     * If the journal is successfully added to Firestore, the progress bar is hidden and the user is redirected to the JournalListActivity.
     * If there is an error while adding the journal to Firestore, the progress bar is hidden and a Toast message is displayed with the error message.
     * If the title, thoughts, or imageUri are empty, the progress bar is hidden and a Toast message is displayed asking the user to fill in all fields.
     */
    private void SaveJournal() {
        final String title = titleET.getText().toString().trim();
        final String thoughts = thoughtsET.getText().toString().trim();

        progressBar.setVisibility(View.VISIBLE);

        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(thoughts) && imageUri != null) {
            // the saving path of the image in the storage firebase:
            // /journal_images/my_image.png
            final StorageReference filePath = storageReference
                    .child("journal_images")
                    .child("my_image_" + Timestamp.now().getSeconds());

            // Uploading the image to the storage

            filePath.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        filePath.getDownloadUrl().addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();
                            // Creating object of Journal



                            Journal journal = new Journal();
                            journal.setTitle(title);
                            journal.setThoughts(thoughts);
                            journal.setImageUrl(imageUrl);
                            journal.setTimeAdded(new Timestamp(new Date()));
                            journal.setUserId(currentUserId);
                            journal.setUsername(currentUsername);
                            // invoking collection reference
                            journalRef.add(journal)
                                    .addOnSuccessListener(documentReference -> {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        startActivity(new Intent(AddJournalActivity.this,
                                                JournalListActivity.class));
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(AddJournalActivity.this,
                                                "Error saving Journal: " + e.getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    });
                        });
                    })
                    .addOnFailureListener(e -> {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(AddJournalActivity.this,
                                "Error saving Journal: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    });
        }
        else {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(AddJournalActivity.this,
                    "Please fill in all fields",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is called when an activity you launched exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     *
     * @param requestCode The integer request code originally supplied to startActivityForResult(),
     *                    allowing you to identify who this result came from.
     * @param resultCode  The integer result code returned by the child activity through its setResult().
     * @param data        An Intent, which can return result data to the caller (various data can be attached to Intent "extras").
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check if the request code matches the GALLERY_CODE and the result is OK
        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            // Check if the returned data is not null
            if (data != null) {
                // Get the URI of the selected image from the gallery
                imageUri = data.getData();
                // Set the selected image to the ImageView
                imageView.setImageURI(imageUri);
            }
        }
    }

    /**
     * This method is called when the activity is about to become visible.
     * It retrieves the current user from Firebase authentication and adds an authentication state listener.
     */
    @Override
    protected void onStart() {
        super.onStart();
        user = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    /**
     * This method is called when the current activity is no longer visible.
     * It removes the authentication state listener from Firebase authentication if it's not null.
     */
    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuth != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}