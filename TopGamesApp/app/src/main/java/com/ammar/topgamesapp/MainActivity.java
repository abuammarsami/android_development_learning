package com.ammar.topgamesapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ItemClickListener{

    // 1. RecyclerView
    private RecyclerView recyclerView;

    // 2. Data
    private ArrayList<GameModel> gameList;

    // 3. Adapter: MyAdapter
    private MyAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        // 4. Data
        gameList = new ArrayList<>();
        gameList.add(new GameModel("Horizon Chase", R.drawable.card1));
        gameList.add(new GameModel("PUBG", R.drawable.card2));
        gameList.add(new GameModel("Head Ball 2", R.drawable.card3));
        gameList.add(new GameModel("Hooked On You", R.drawable.card4));
        gameList.add(new GameModel("Fortnite", R.drawable.card6));
        gameList.add(new GameModel("FIFA 2022", R.drawable.card5));


        adapter = new MyAdapter(gameList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        // 5. Handle click event
        adapter.setItemClickListener(this);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void OnClick(View view, int position) {
        // 6. Handle click event
        GameModel gameModel = gameList.get(position);
        String gameName = gameModel.getGameName();
        Toast.makeText(this, "You choose: " + gameName, Toast.LENGTH_SHORT).show();
    }
}