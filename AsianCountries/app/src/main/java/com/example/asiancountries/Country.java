package com.example.asiancountries;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "Country_table")
public class Country {
    private String name;
    private String capital;
    private String subregion;
    private String flag;
    private String population;
//    private List<String> borders;
//
//    public List<String> getBorders() {
//        return borders;
//    }
//
//    public void setBorders(List<String> borders) {
//        this.borders = borders;
//    }

    @PrimaryKey
    @NonNull
    private String alpha2Code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public void setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }



    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }


}
