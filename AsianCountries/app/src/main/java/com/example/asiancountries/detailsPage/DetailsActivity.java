package com.example.asiancountries.detailsPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.asiancountries.Country;
import com.example.asiancountries.R;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;

import java.text.DateFormatSymbols;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    ImageView display_flag;
    TextView display_country;
    TextView display_capital;
    TextView display_subregion;
    TextView display_population;
    ListView display_borders;
    ListView display_languages;

    String[] borders;
    Context context1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        display_flag = findViewById(R.id.flag_image);
        display_country = findViewById(R.id.display_country);
        display_capital = findViewById(R.id.display_capital);
        display_borders = findViewById(R.id.display_borders);
        display_languages = findViewById(R.id.display_languages);
        display_subregion = findViewById(R.id.display_subregion);
        display_population = findViewById(R.id.display_population);

        getDataFromIntent();
    }

         private void getDataFromIntent() {
//         borders = getIntent().getStringArrayExtra("borders");
//        ArrayAdapter<String> borderAdapter = new ArrayAdapter<>(this, R.layout.activity_details, borders);
//        display_borders.setAdapter(borderAdapter);

        if(getIntent().hasExtra("country")) {
          String name = getIntent().getStringExtra("country");
          String population = getIntent().getStringExtra("population");
          String capital = getIntent().getStringExtra("capital");
          String flag = getIntent().getStringExtra("flag");
//         List<String> borders = getIntent().getStringArrayListExtra("borders");
          String subregion = getIntent().getStringExtra("subregion");

            display_country.setText(name);
            display_subregion.setText("Subregion : " +subregion);
            display_capital.setText("Capital : " +capital);
            display_population.setText("Population : " +population);

//            GlideToVectorYou.justLoadImage(DetailsActivity.this, Uri.parse(flag), display_flag);
//
//            RequestBuilder<PictureDrawable> requestBuilder = GlideToVectorYou
//                    .init()
//                    .with(context1)
//                    .getRequestBuilder();
//
//            requestBuilder
//                    .load( Uri.parse( flag ))
//                    .transition(DrawableTransitionOptions.withCrossFade())
//                    .apply(new RequestOptions()
//                            .centerCrop())
//                    .into(display_flag);


        }

    }
}