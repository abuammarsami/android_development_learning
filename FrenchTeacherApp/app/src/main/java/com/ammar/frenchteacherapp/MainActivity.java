package com.ammar.frenchteacherapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

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

        Button blackBtn = findViewById(R.id.blackBtn);
        Button greenBtn = findViewById(R.id.greenBtn);
        Button purpleBtn = findViewById(R.id.purpleBtn);
        Button redBtn = findViewById(R.id.redBtn);
        Button yellowBtn = findViewById(R.id.yellowBtn);

        blackBtn.setOnClickListener(this);
        greenBtn.setOnClickListener(this);
        purpleBtn.setOnClickListener(this);
        redBtn.setOnClickListener(this);
        yellowBtn.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        // find out which button was clicked and play the corresponding sound
        int clickedBtnId = v.getId();
        if (clickedBtnId == R.id.blackBtn) {
            playSound(R.raw.black);
        } else if (clickedBtnId == R.id.greenBtn) {
            playSound(R.raw.green);
        } else if (clickedBtnId == R.id.purpleBtn) {
            playSound(R.raw.purple);
        } else if (clickedBtnId == R.id.redBtn) {
            playSound(R.raw.red);
        } else if (clickedBtnId == R.id.yellowBtn) {
            playSound(R.raw.yellow);
        }

    }

    public void playSound(int btnId) {
        // Play sound
        MediaPlayer mediaPlayer = MediaPlayer.create(this, btnId);
        mediaPlayer.start();

    }
}