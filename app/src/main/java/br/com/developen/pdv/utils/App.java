package br.com.developen.pdv.utils;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static App instance;

    public void onCreate() {

        super.onCreate();

        instance = this;

    }

    public static App getInstance() {

        return instance;

    }

    public static Context getContext(){

        return instance;

    }

}