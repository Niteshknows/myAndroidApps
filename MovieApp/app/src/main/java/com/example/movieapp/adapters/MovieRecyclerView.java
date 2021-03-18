package com.example.movieapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.R;
import com.example.movieapp.models.MovieModel;

import java.util.List;

public class MovieRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MovieModel> mMovies;
    private OnMovieListener onMovieListener;

    public MovieRecyclerView(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    public void setmMovies(List<MovieModel> mMovies) {
        this.mMovies = mMovies;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list,parent, false);
       return new MovieViewHolder(view, onMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        ((MovieViewHolder)holder).title.setText(mMovies.get(i).getTitle());
        ((MovieViewHolder)holder).release_date.setText(mMovies.get(i).getRelease_date());
        ((MovieViewHolder)holder).duration.setText(mMovies.get(i).getLanguage());

        ((MovieViewHolder)holder).ratingBar.setRating((mMovies.get(i).getVote_average())/2);

        Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w500/" +mMovies.get(i).getPoster_path())
                .into(((MovieViewHolder)holder).imageView);

    }

    @Override
    public int getItemCount() {
        if(mMovies != null) {
            return mMovies.size();
        }
        return 0;
    }

    public List<MovieModel> getmMovies() {
        return mMovies;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //widgets
        TextView duration, title, release_date;
        ImageView imageView;
        RatingBar ratingBar;

        OnMovieListener onMovieListener;

        public MovieViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
            super(itemView);

            this.onMovieListener = onMovieListener;
          duration = itemView.findViewById(R.id.movie_duration);
            title = itemView.findViewById(R.id.movie_title);
            release_date = itemView.findViewById(R.id.release_date);
            imageView = itemView.findViewById(R.id.movie_img);
            ratingBar = itemView.findViewById(R.id.rating_bar);

           itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onMovieListener.onMovieClick(getAdapterPosition());

        }
    }

    public MovieModel getSelectedMovies(int position) {
        if( mMovies!=null) {
            if(mMovies.size()>0) { return mMovies.get(position);}
        }
        return null;
    }


}

