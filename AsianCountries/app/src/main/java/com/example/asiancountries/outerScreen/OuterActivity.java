package com.example.asiancountries.outerScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.asiancountries.MainActivity;
import com.example.asiancountries.R;

public class OuterActivity extends AppCompatActivity {

    Button loadBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outer);
        loadBtn = findViewById(R.id.loadBtn);

        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(isNetworkAvailable()) {
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(OuterActivity.this, "Check internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}