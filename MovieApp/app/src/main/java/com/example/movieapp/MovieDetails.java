package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movieapp.models.MovieModel;

import org.w3c.dom.Text;

public class MovieDetails extends AppCompatActivity {
    private TextView title, description;
    ImageView image;
    RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        title = findViewById(R.id.textView_titleDetails);
        description = findViewById(R.id.textView_description);
        image = findViewById(R.id.imageView_details);
        ratingBar = findViewById(R.id.ratingBarDetails);

         GetDataFromIntent();

    }

    private void GetDataFromIntent() {
       if(getIntent().hasExtra("movie")) {
           MovieModel movieModel = getIntent().getParcelableExtra("movie");
           title.setText(movieModel.getTitle());
           description.setText(movieModel.getMovie_overview());
           ratingBar.setRating((movieModel.getVote_average())/2);

           Glide.with(this)
                   .load("https://image.tmdb.org/t/p/w500/" +movieModel.getPoster_path())
                   .into(image);
       }
    }
}