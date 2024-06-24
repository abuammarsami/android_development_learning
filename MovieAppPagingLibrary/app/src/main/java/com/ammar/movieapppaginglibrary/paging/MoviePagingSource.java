package com.ammar.movieapppaginglibrary.paging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.ammar.movieapppaginglibrary.api.APIClient;
import com.ammar.movieapppaginglibrary.model.Movie;
import com.ammar.movieapppaginglibrary.model.MovieResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MoviePagingSource extends RxPagingSource<Integer, Movie> {



    @NonNull
    @Override
    public Single<LoadResult<Integer, Movie>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
        // if the page number already there then initiate page variable with that value, otherwise
        // initiate with first page
        try{

            int page = loadParams.getKey() != null ? loadParams.getKey() : 1;
            return APIClient.getAPIInterface()
                    .getMoviesByPage(page)
                    .subscribeOn(Schedulers.io())
                    .map(MovieResponse::getMovies)
                    .map(movies -> toLoadResult(movies, page))
                    .onErrorReturn(LoadResult.Error::new);


        } catch (Exception e) {
            return Single.just(new LoadResult.Error<>(e));
        }
    }

    private LoadResult<Integer, Movie> toLoadResult(
            List<Movie> movies,
            int page
    ) {
        return new LoadResult.Page<>(
                movies,
                page == 1 ? null : page - 1,
                page + 1
        );

    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Movie> pagingState) {
        return 0;
    }
}
