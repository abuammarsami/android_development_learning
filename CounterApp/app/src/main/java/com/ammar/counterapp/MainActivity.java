package com.ammar.counterapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView titleView;
    TextView counterView;
    Button incrementBtn;
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        titleView = findViewById(R.id.titleTextView);
        counterView = findViewById(R.id.counterTextView);
        incrementBtn = findViewById(R.id.incrementButton);

        //adding the functionalities
        incrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counterView.setText(String.valueOf(increaseCounter()));

            }
        });
    }

    public int increaseCounter(){
        ++counter;
        /*
          This is the same as counter = counter + 1;
          This is the same as counter += 1;
          Why ++counter and not counter++?
          The difference is that ++counter increments the value of counter and then returns the incremented value.
          counter++ returns the value of counter and then increments the value of counter.
          In this case, we want to increment the value of counter and then return the incremented value.
         */
        return counter;
    }

}