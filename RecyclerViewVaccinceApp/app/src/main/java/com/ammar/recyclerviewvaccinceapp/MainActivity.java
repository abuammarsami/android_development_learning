package com.ammar.recyclerviewvaccinceapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemClickListener{

    //1. AdapterView: RecyclerView
    RecyclerView recyclerView;

    //2. Data Source (Model)
    List<VaccineModel> vaccineModelList;

    //3. Adapter: Custom Adapter
    MyAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        vaccineModelList = Arrays.asList(
                new VaccineModel("Hepatitis B Vaccine", R.drawable.vaccine1),
                new VaccineModel("Tetanus Vaccine", R.drawable.vaccine4),
                new VaccineModel("Pneumococcal Vaccine", R.drawable.vaccine5),
                new VaccineModel("Rotavirus Vaccine", R.drawable.vaccine6),
                new VaccineModel("Measles Virus", R.drawable.vaccine7),
                new VaccineModel("Cholera Virus", R.drawable.vaccine8),
                new VaccineModel("Covid-19", R.drawable.vaccine9),
                new VaccineModel("Hepatitis B Vaccine", R.drawable.vaccine1),
                new VaccineModel("Tetanus Vaccine", R.drawable.vaccine4),
                new VaccineModel("Pneumococcal Vaccine", R.drawable.vaccine5),
                new VaccineModel("Rotavirus Vaccine", R.drawable.vaccine6),
                new VaccineModel("Measles Virus", R.drawable.vaccine7),
                new VaccineModel("Cholera Virus", R.drawable.vaccine8),
                new VaccineModel("Covid-19", R.drawable.vaccine9)
        );

        adapter = new MyAdapter(vaccineModelList);

        //4. Layout Manager

        recyclerView.setHasFixedSize(true); // it means the recyclerView size is fixed and it will not change
        recyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));// it means the recyclerView will be displayed in linear vertical order
        recyclerView.setAdapter(adapter);

        // Handling the click events
        adapter.setClickListener(this);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onClick(View view, int position) {
        Toast.makeText(this,
                "Vaccine Name: " + vaccineModelList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
    }
}