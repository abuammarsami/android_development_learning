package com.ammar.movieapppaginglibrary.activty;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.ammar.movieapppaginglibrary.R;
import com.ammar.movieapppaginglibrary.adapter.MoviesAdapter;
import com.ammar.movieapppaginglibrary.adapter.MoviesLoadStateAdapter;
import com.ammar.movieapppaginglibrary.databinding.ActivityMainBinding;
import com.ammar.movieapppaginglibrary.util.GridSpace;
import com.ammar.movieapppaginglibrary.util.MovieComparator;
import com.ammar.movieapppaginglibrary.util.Utils;
import com.ammar.movieapppaginglibrary.viewmodel.MovieViewModel;
import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    MovieViewModel mainActivityViewModel;
    ActivityMainBinding binding;
    MoviesAdapter moviesAdapter;

    @Inject
    RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Inflate the layout for this activity using View Binding.
        // View Binding is a feature in Android that generates a binding class for each XML layout file present in the project.
        // In this case, the layout file is activity_main.xml, and the generated binding class is ActivityMainBinding.
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // Set the content view to the root view of the inflated layout.
        setContentView(binding.getRoot());

        // Check if the API key is null or empty.
        // If it is, display a toast message indicating an error with the API key.
        if (Utils.API_KEY == null || Utils.API_KEY.isEmpty()) {
            Toast.makeText(this, "Error in API Key", Toast.LENGTH_SHORT).show();
        }

        // Initialize the MoviesAdapter.
        // The MoviesAdapter is responsible for displaying the movie data in a RecyclerView.
        moviesAdapter = new MoviesAdapter(new MovieComparator(), requestManager);

        // Get the MovieViewModel from the ViewModelProvider.
        // The MovieViewModel is responsible for preparing and managing the data for the activity.
        mainActivityViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        // Initialize the RecyclerView and the adapter.
        initRecyclerViewAndAdapter();

        // Subscribe to the moviePagingDataFLowable from the ViewModel.
        // When new data is emitted from the Flowable, submit it to the adapter to be displayed in the RecyclerView.
        mainActivityViewModel.moviePagingDataFLowable.subscribe(moviePagingData -> {
            moviesAdapter.submitData(getLifecycle(), moviePagingData);
        });

        // Set an OnApplyWindowInsetsListener on the root view of the activity.
        // This listener adjusts the padding of the view based on the system window insets, ensuring the content is displayed correctly
        // in scenarios such as when the status bar is displayed, or in multi-window mode.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Initializes the RecyclerView and its adapter.
     * This method is responsible for setting up the RecyclerView that displays the movie data.
     */
    private void initRecyclerViewAndAdapter() {
        // Create a new GridLayoutManager with 2 columns.
        // GridLayoutManager is a RecyclerView.LayoutManager implementations that lays out items in a grid.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        // Set the layout manager of the RecyclerView to the GridLayoutManager.
        binding.recyclerViewMovies.setLayoutManager(gridLayoutManager);

        // Add a new GridSpace item decoration to the RecyclerView.
        // This adds spacing between items in the grid.
        binding.recyclerViewMovies.addItemDecoration(new GridSpace(2, 20, true));

        // Set the adapter of the RecyclerView to the MoviesAdapter.
        // The MoviesAdapter is responsible for creating the view holders and binding the movie data to the view holders.
        // The withLoadStateFooter method adds a footer to the RecyclerView that displays the loading state.
        binding.recyclerViewMovies.setAdapter(
                moviesAdapter.withLoadStateFooter(
                        new MoviesLoadStateAdapter(view -> {
                            // Retry loading data when the footer is clicked.
                            moviesAdapter.retry();
                        })
                )
        );

        // Set a SpanSizeLookup on the GridLayoutManager.
        // This determines the number of span (or columns) a position should occupy.
        // In this case, loading items occupy 1 span, and all other items occupy 2 spans.
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return moviesAdapter.getItemViewType(position) == MoviesAdapter.LOADING_ITEM ? 1 : 2 ;
            }
        });
    }
}