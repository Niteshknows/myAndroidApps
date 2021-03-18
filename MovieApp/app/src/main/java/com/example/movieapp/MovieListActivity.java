package com.example.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.movieapp.adapters.MovieRecyclerView;
import com.example.movieapp.adapters.OnMovieListener;
import com.example.movieapp.models.MovieModel;
import com.example.movieapp.request.MovieApiClient;
import com.example.movieapp.request.Servicey;
import com.example.movieapp.request.Servicey;
import com.example.movieapp.response.MovieSearchResponse;
import com.example.movieapp.utils.Credentials;
import com.example.movieapp.utils.MovieApi;
import com.example.movieapp.viewmodels.MovieListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

public class MovieListActivity extends AppCompatActivity implements OnMovieListener {
    private RecyclerView recyclerView;
    private MovieListViewModel movieListViewModel;
    private MovieRecyclerView adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        SetupSearchView();

        recyclerView = findViewById(R.id.recycler_view);
        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
       ObserveAnyChange();
       ConfigureRecyclerView();
       movieListViewModel.searchMovieApi3("fast",1);

    }

    private void ObserveAnyChange() {
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //observe for any data change
             if(movieModels!=null) {
                 for(MovieModel movieModel: movieModels) {
                     Log.v("Tag", "onChanged" + movieModel.getTitle());
                     adapter.setmMovies(movieModels);
                 }            }
            }
        });
    }


    private void ConfigureRecyclerView() {
        adapter = new MovieRecyclerView(this);
       Log.v("Tag","nitesh 1");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
              if(!recyclerView.canScrollVertically(1)) {
                  MovieListViewModel.searchNextPage();
              }
            }
        });
    }

    private void getRetrofitResponse() {
        MovieApi movieApi = Servicey.getMovieApi();
        Call<MovieSearchResponse> responseCall = movieApi
                .searchMovie(
                        Credentials.API_KEY,
                        "Action",                     //FOR TESTING ONLY
                        Integer.valueOf("1"));

          responseCall.enqueue(new Callback<MovieSearchResponse>() {
              @Override
              public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                  if(response.code()==200) {
                      Log.v("Tag", "the response is" +response.body().toString());

                      List<MovieModel> movies = new ArrayList<>(response.body().getMovies());
                      for(MovieModel movie: movies) {
                          Log.v("Tag","the release date"+movie.getRelease_date());
                      }
                  }
                  else {
                      try {
                          Log.v("Tag", "Error :" +response.errorBody().string());
                      } catch(IOException E) {E.printStackTrace();}

                  }
              }

              @Override
              public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
                   t.printStackTrace();
              }
          });
    }

    private void GetRetrofitResponseAccordingTOID() {
        MovieApi movieApi = Servicey.getMovieApi();
        Call<MovieModel> responseCall = movieApi
                .getMovie(
                        550,
                        Credentials.API_KEY
                );
        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
              if(response.code()==200) {
                  MovieModel movie = response.body();
                  Log.v("Tag", "The response"+ movie.getTitle());

              } else {
                  Log.v("Tag", "Error" +response.errorBody().toString());
              }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });
    }



    @Override
    public void onMovieClick(int position) {

        Intent intent = new Intent(this,MovieDetails.class);
        intent.putExtra("movie", adapter.getSelectedMovies(position));
        startActivity(intent);

    }
//     private  void SetupSearchView() {
//       final SearchView searchview = findViewById(R.id.search_view);
//
//         searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                movieListViewModel.searchMovieApi3(
//                        query, 1
//                );
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//     }

}