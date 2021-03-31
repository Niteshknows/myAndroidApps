package com.example.asiancountries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asiancountries.db.AppDatabase;
import com.example.asiancountries.db.UserDao;
import com.example.asiancountries.outerScreen.OuterActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    List<Country> datalist = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    AppDatabase database;
    UserDao userDao;
    private static final String URL = "https://restcountries.eu/rest/v2/region/asia";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView flag = findViewById(R.id.flag_image);
        recyclerView = findViewById(R.id.recycler_view);
        database = AppDatabase.getDbInstance(this);
        datalist = database.userDao().getAll();

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerViewAdapter(MainActivity.this, datalist);
        recyclerView.setAdapter(adapter);
        AppDatabase db = AppDatabase.getDbInstance(this);
        List<Country> countries = db.userDao().getAll();

        if(countries.size() == 0){
            StringRequest request = new StringRequest(URL, response -> {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Country[] c = gson.fromJson(response, Country[].class);
                for(Country country : c){
                    db.userDao().insert(country);
                }
            }, error -> {

            });

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        deleteAllCountries();
        return super.onOptionsItemSelected(item);

    }

        public void deleteAllCountries(){

         Toast toast =  Toast.makeText(getApplicationContext(), "Deleting All Data", Toast.LENGTH_LONG);
            toast.show();

            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                  try{
                      userDao.deleteAll();
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
                  finally {
                      Intent intent = new Intent(MainActivity.this, OuterActivity.class );
                      startActivity(intent);
                  }

                }
            }, 1000);

        }

}
