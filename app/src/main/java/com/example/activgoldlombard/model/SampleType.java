package com.example.activgoldlombard.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class SampleType implements Serializable {
    public String name;
    public double percent;
    public double weight;
    public double priceForGr;

    public SampleType( String name, double percent, double weight, double priceForGr) {
        this.name = name;
        this.percent = percent;
        this.weight = weight;
        this.priceForGr = priceForGr;
    }

    public SampleType() {
    }

    protected SampleType(Parcel in) {
        name = in.readString();
        percent = in.readDouble();
        weight = in.readDouble();
        priceForGr = in.readDouble();
    }



    public double getPriceForGr() {
        return priceForGr;
    }

    public void setPriceForGr(double priceForGr) {
        this.priceForGr = priceForGr;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }


}
