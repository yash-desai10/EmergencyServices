package com.example.jigish.emse;

/**
 * Created by JD on 4/6/2017.
 */

public class Result {
    public String name;
    public double latitude;
    public double longitude;
    public String vicinity;

    Result (String name, double latitude, double longitude, String vicinity) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.vicinity = vicinity;
    }

    public String toString () {
        String toReturn = name + ", " + vicinity + ": " + latitude + ", " + longitude;
        return toReturn;
    }
}
