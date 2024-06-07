package com.ammar.sharedpreference;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;
    Button saveBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.et_input);
        textView = findViewById(R.id.tv_output);
        saveBtn = findViewById(R.id.btn_save);

        // Show any saved text if available
        DisplaySavedText();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredText = editText.getText().toString();
                DisplayAndSaveText(enteredText);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void DisplaySavedText() {
        // Retrieving the entered text from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String savedText = sharedPreferences.getString("name", "");
        textView.setText(savedText);
    }

    private void DisplayAndSaveText(String enteredText) {
        // Display the entered text
        textView.setText(enteredText);

        // Saving the entered text into shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        //Writing data to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("name", enteredText);
        editor.commit();

    }
}