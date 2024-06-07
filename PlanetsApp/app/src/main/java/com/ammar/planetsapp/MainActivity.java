package com.ammar.planetsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Planet> plantesArrayList;
    private static  MyCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 1. AdapterView: a ListView
        listView = findViewById(R.id.listView);

        // 2- Data Source: ArrayList<Planet>
        plantesArrayList = new ArrayList<>();

        Planet planet1 = new Planet("Earth", "1 Moon", R.drawable.earth);
        Planet planet2 = new Planet("Mercury", "0 Moons", R.drawable.mercury);
        Planet planet3 = new Planet("Venus", "0 Moons", R.drawable.venus);
        Planet planet4 = new Planet("Mars", "2 Moons", R.drawable.mars);
        Planet planet5 = new Planet("Jupiter", "79 Moons", R.drawable.jupiter);
        Planet planet6 = new Planet("Saturn", "83 Moons", R.drawable.saturn);
        Planet planet7 = new Planet("Uranus", "27 Moons", R.drawable.uranus);
        Planet planet8 = new Planet("Neptune", "14 Moons", R.drawable.neptune);

        plantesArrayList.add(planet1);
        plantesArrayList.add(planet2);
        plantesArrayList.add(planet3);
        plantesArrayList.add(planet4);
        plantesArrayList.add(planet5);
        plantesArrayList.add(planet6);
        plantesArrayList.add(planet7);
        plantesArrayList.add(planet8);

        // 3: Adapter

        adapter = new MyCustomAdapter(getApplicationContext(), plantesArrayList);
        listView.setAdapter(adapter);

        //handling click events
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,
                        "Planet Name: " + adapter.getItem(position).getPlanetName(),
                        Toast.LENGTH_SHORT).show();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}