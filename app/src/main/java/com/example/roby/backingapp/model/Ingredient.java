package com.example.roby.backingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {
    private String quantity;
    private String measure;
    private String ingredientName;

    public Ingredient() {

    }

    protected Ingredient(Parcel in) {
        quantity = in.readString();
        measure = in.readString();
        ingredientName = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public String getIngredientName() {
        return ingredientName;
    }

    public String getMeasure() {
        return measure;
    }

    public String getQuantity() {
        return quantity;
    }

    public Ingredient( String quantity, String measure, String ingredientName) {
        this.ingredientName = ingredientName;
        this.measure = measure;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return this.quantity + " " + this.measure + " of " + this.ingredientName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(quantity);
        dest.writeString(measure);
        dest.writeString(ingredientName);
    }
}
