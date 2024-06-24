package com.ammar.movieapppaginglibrary.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.ammar.movieapppaginglibrary.model.Movie;
import com.ammar.movieapppaginglibrary.paging.MoviePagingSource;

import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

public class MovieViewModel extends ViewModel {

    public Flowable<PagingData<Movie>> moviePagingDataFLowable;

    public MovieViewModel() {
       init();
    }

    private void init() {

        //Defining Paging Source --> Data Source
        MoviePagingSource moviePagingSource = new MoviePagingSource();

        Pager<Integer, Movie> pager = new Pager<>(
                new PagingConfig(
                        20,
                        20,
                        false,
                        20,
                        20*499),
                () -> moviePagingSource
        );

        // Initialising the Flowable
        moviePagingDataFLowable = PagingRx.getFlowable(pager);
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(moviePagingDataFLowable, coroutineScope);

    }

}
