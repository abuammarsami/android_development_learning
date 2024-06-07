package com.ammar.customlistview;

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

    // This is the main activity class that displays the list of countries
    // The list is displayed using a custom adapter
    // The custom adapter inflates the item_list_layout.xml file
    // The item_list_layout.xml file contains an ImageView and two TextViews

    ListView listView;
    ArrayList<CountryModelClass> dataModels;
    private static CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // 1- AdapterView: a listView
        listView = findViewById(R.id.listView);

        // 2- Data Source: a list of country objects
        dataModels = new ArrayList<>();

        dataModels.add(new CountryModelClass("Brazil", "5", R.drawable.brazil));
        dataModels.add(new CountryModelClass("Germany", "4", R.drawable.germany));
        dataModels.add(new CountryModelClass("France", "2", R.drawable.france));
        dataModels.add(new CountryModelClass("Spain", "1", R.drawable.spain));
        dataModels.add(new CountryModelClass("Saudi Arabia", "0", R.drawable.saudiarabia));
        dataModels.add(new CountryModelClass("United Kingdom", "1", R.drawable.unitedkingdom));
        dataModels.add(new CountryModelClass("United States", "0", R.drawable.unitedstates));


        // 3- Adapter: a custom adapter that displays the data source
        adapter = new CustomAdapter(dataModels, getApplicationContext());

        listView.setAdapter(adapter);

        // 4- Handling the click events on Listview items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click
                // When an item is clicked, display a toast message with the country name and the number of world cup wins
                CountryModelClass dataModel= dataModels.get(position);
                String countryName = dataModel.getCountryName();
                String cupCount = dataModel.getWorldCupWinCount();
                Toast.makeText(MainActivity.this, countryName + " has won " + cupCount + " world cups", Toast.LENGTH_SHORT).show();

                // The code above is equivalent to the following code with get data from AdapterView:
                // String countryName = ((CountryModelClass) parent.getItemAtPosition(position)).getCountryName();
                // String cupCount = ((CountryModelClass) parent.getItemAtPosition(position)).getWorldCupWinCount();
                // Toast.makeText(MainActivity.this, countryName + " has won " + cupCount + " world cups", Toast.LENGTH_SHORT).show();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}