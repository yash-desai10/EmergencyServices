package com.example.jigish.emse;

/**
 * Created by JD on 4/6/2017.
 */
import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class MyApp extends Application {
    static List<String> allloc;
    static Map<String,Result> allres;
    static String catType="fire_station";

    static String lati,longi;

    static String myphone;
    static String family_no;


    @Override
    public void onCreate() {
        super.onCreate();

        allloc=new ArrayList<>();
        allres=new HashMap<>();
    }

}

