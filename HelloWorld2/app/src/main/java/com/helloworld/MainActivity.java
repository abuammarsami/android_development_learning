package com.helloworld;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        TextView myTextView = findViewById(R.id.myFirstTextView);
        myTextView.setText("Hello, World from Java!");

        Button myBtn = findViewById(R.id.myFirstButton);
//        myBtn.setOnClickListener(v -> myTextView.setText("Button clicked!"));
        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Toast.makeText(MainActivity.this,
                        "You clicked the Button!",
                        Toast.LENGTH_SHORT).show();
            }
        });

        ImageView myImageView = findViewById(R.id.myFirstImageView);
//        myImageView.setImageResource(R.drawable.ic_launcher_foreground); // This is the default icon, here how we can change it programmatically


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}