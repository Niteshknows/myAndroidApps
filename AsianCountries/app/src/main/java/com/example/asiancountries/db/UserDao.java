package com.example.asiancountries.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.asiancountries.Country;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

import io.reactivex.Completable;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao{
    @Insert(onConflict = REPLACE)
    public void insert(Country country);

    @Delete
    void delete(Country country);

    @Delete
    void reset(List<Country> country);


    @Query("SELECT * FROM Country_table")
    List<Country> getAll();

    @Query("DELETE FROM Country_table")
    void deleteAll();

    @Query("SELECT * FROM Country_table ORDER BY name LIMIT 1")
    LiveData<Country> loadlastTask();
}
