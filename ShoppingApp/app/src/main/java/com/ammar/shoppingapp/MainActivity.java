package com.ammar.shoppingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    EditText mPriceField;
    Button mUpdateButton;
    TextView mPriceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        // crete a database to in firebase web app before the following line
        mDatabase = FirebaseDatabase.getInstance().getReference("GoldPrice").child("price");

        mPriceView = findViewById(R.id.textView);

        // Reading Simple Data from Firebase
        // reading the gold price from firebase
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // get the value of the gold price from firebase when the data changes
                String price = snapshot.getValue(String.class);
                mPriceView.setText(price);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        // writing Data to Firebase
        // writing a simple data ( no custom object)

        // updating the gold Price to 2000 upon launching the app
//        mDatabase.setValue(2000);


        // now lets build the gold price app
        mPriceField = findViewById(R.id.editTextNumberDecimal);
        mUpdateButton = findViewById(R.id.button);

        // updating the gold price in firebase
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = mPriceField.getText().toString();
                mDatabase.setValue(price);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}