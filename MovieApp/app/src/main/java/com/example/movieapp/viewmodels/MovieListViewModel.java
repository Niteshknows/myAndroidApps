package com.example.movieapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.models.MovieModel;
import com.example.movieapp.respositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {
    private MovieRepository movieRepository;

    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return movieRepository.getMovies();
    }

    public void searchMovieApi3(String query, int pageNumber) {
        MovieRepository.searchMovieApi2(query, pageNumber);
    }

    public static void searchNextPage() {
        MovieRepository.searchNextPage();
    }
}
