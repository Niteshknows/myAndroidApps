package com.example.asiancountries;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.asiancountries.db.AppDatabase;
import com.example.asiancountries.detailsPage.DetailsActivity;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    Context context;
    List<Country> countryList;
    MainActivity mainActivity;
    private AppDatabase database;

    public RecyclerViewAdapter(Context context, List<Country> country) {
        this.context = context;
        this.countryList = country;
    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
       return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
      Country country = countryList.get(position);
      holder.countryTitle.setText(country.getName());
      holder.capital.setText(country.getCapital());

        RequestBuilder<PictureDrawable> requestBuilder = GlideToVectorYou
                .init()
                .with(context)
                .getRequestBuilder();

        requestBuilder
                .load( Uri.parse( country.getFlag() ))
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(new RequestOptions()
                        .centerCrop())
                .into(holder.countryImage);

//      Glide.with(holder.itemView.getContext()).load(countryList.get(position).getFlag()).into(holder.countryImage);
      database = AppDatabase.getDbInstance(context);

      holder.itemView.setOnClickListener(v -> {
          Intent intent = new Intent(v.getContext(), DetailsActivity.class);
          intent.putExtra("country",country.getName());
          intent.putExtra("capital",country.getCapital());
          intent.putExtra("subregion",country.getSubregion());
          intent.putExtra("flag",country.getFlag());
          intent.putExtra("population",country.getPopulation());
//          intent.putExtra("flag",country.getFlag());
//          intent.putExtra("borders", (Parcelable) country.getBorders());

          context.startActivity(intent);
      });
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView countryImage;
        TextView countryTitle;
        TextView capital;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            countryImage = (ImageView) itemView.findViewById(R.id.flag_image);
            countryTitle = (TextView) itemView.findViewById(R.id.country_name);
            capital = itemView.findViewById(R.id.capital_name);
        }

    }


    public Country getSelectedCountry(int position) {
        if(countryList!= null) {
            if(countryList.size() > 0) {
                return countryList.get(position);
            }
        } return null;
    }
}
