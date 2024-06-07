package com.ammar.gridview;

import android.os.Bundle;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<CourseModel> courseModelArrayList;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);

        //Data to be displayed in the GridView
        courseModelArrayList = new ArrayList<>();
        courseModelArrayList.add(new CourseModel("The Complete Android 12 Course", R.drawable.course1));
        courseModelArrayList.add(new CourseModel("The Complete Java Developer Curse", R.drawable.course2));
        courseModelArrayList.add(new CourseModel("The Complete Kotlin Course", R.drawable.course3));
        courseModelArrayList.add(new CourseModel("The Complete Data Structure and Algorithms Course", R.drawable.course4));

        //Attaching the adapter to the GridView
        adapter = new MyAdapter(this, courseModelArrayList);
        gridView.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}