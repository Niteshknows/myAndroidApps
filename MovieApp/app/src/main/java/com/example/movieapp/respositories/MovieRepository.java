package com.example.movieapp.respositories;

import androidx.lifecycle.LiveData;

import com.example.movieapp.models.MovieModel;
import com.example.movieapp.request.MovieApiClient;

import java.util.List;

public class MovieRepository {
    private static MovieRepository instance;
    private MovieApiClient movieApiClient;
    private static String mQuery;
    private static int mPageNumber;
    public static MovieRepository getInstance() {
        if(instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }
    private MovieRepository()
    {

        movieApiClient = MovieApiClient.getInstance();
    }
    public LiveData<List<MovieModel>> getMovies() {return movieApiClient.getMovies(); }

    public static void searchMovieApi2(String query, int pageNumber) {
        mQuery = query;
        mPageNumber = pageNumber;

        MovieApiClient.searchMoviesApi(query,pageNumber);
    }

    public static void searchNextPage() {
        searchMovieApi2(mQuery, mPageNumber+1);
    }
}
