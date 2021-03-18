package com.example.movieapp.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movieapp.AppExecutors;
import com.example.movieapp.models.MovieModel;
import com.example.movieapp.response.MovieSearchResponse;
import com.example.movieapp.utils.Credentials;
import com.example.movieapp.utils.MovieApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieApiClient {
    private static MutableLiveData<List<MovieModel>> mMovies;
    private static MovieApiClient instance;
    private static RetrieveMoviesRunnable retrieveMoviesRunnable;

    public static MovieApiClient getInstance() {
        if (instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient() {

        mMovies = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return mMovies;
    }

    public static void searchMoviesApi(String query, int pageNumber) {
        if (retrieveMoviesRunnable != null) {
            retrieveMoviesRunnable = null;
        }
        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);
        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                // cancelling the retrofit call
                myHandler.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);
    }

    public static class RetrieveMoviesRunnable implements Runnable {
        private String query;
        private int pageNumber;
        public boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {

            try {
                Response response = getMovies(query, pageNumber).execute();

                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {

                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response.body()).getMovies());
                    if (pageNumber == 1) {
                        mMovies.postValue(list);
                    } else {
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }

                } else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "error " + error);
                    mMovies.postValue(null);
                }
            } catch (IOException E) {
                E.printStackTrace();
                mMovies.postValue(null);
            }
        }

        private void cancelRequest () {
            Log.v("Tag", "Cancelling search request");
            cancelRequest = true;
        }
            private Call<MovieSearchResponse> getMovies(String query, int pageNumber) {
                return Servicey.getMovieApi().searchMovie(
                        Credentials.API_KEY,
                        query,
                        pageNumber
                );
            }


    }
}
