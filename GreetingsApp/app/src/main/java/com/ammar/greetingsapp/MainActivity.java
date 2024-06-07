package com.ammar.greetingsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button helloBtn;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        editText = findViewById(R.id.editText);
        helloBtn = findViewById(R.id.editTextBtn);
        title = findViewById(R.id.title);

        helloBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                // now I need to display a message in Toast.
                // Toast is a small Popup message that appears temporarily on the screen to display brief information or notification to the user.
                // It is a non-intrusive way of displaying information to the user.

                Toast.makeText(MainActivity.this,
                        String.format("Hello, %s! Welcome to Greetings App!", name),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}