package com.ammar.movieapppaginglibrary.api;

import com.ammar.movieapppaginglibrary.model.MovieResponse;
import com.ammar.movieapppaginglibrary.util.Utils;

import io.reactivex.rxjava3.core.Single;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class APIClient {

    static APIInterface apiInterface;

    //Retrofit Instance
    public static APIInterface getAPIInterface() {
        if (apiInterface == null) {
            // Create Retrofit instance in another way
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();
                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api_key", Utils.API_KEY)
                        .build();
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);
                Request request = requestBuilder.build();
                return chain.proceed(request);
            });

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Utils.BASE_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
            apiInterface = retrofit.create(APIInterface.class);
        }
        return apiInterface;
    }


    // we can create this interface separately
    public interface APIInterface {
        @GET("movie/popular")
        Single<MovieResponse> getMoviesByPage(@Query("page") int page);
    }
}
