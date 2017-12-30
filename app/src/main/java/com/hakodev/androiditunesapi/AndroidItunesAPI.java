package com.hakodev.androiditunesapi;

import android.app.Application;

import okhttp3.OkHttpClient;

public class AndroidItunesAPI extends Application {

    private static AndroidItunesAPI instance = null;

    private OkHttpClient networkClient;

    public static AndroidItunesAPI getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        networkClient = new OkHttpClient();
    }

    public OkHttpClient getNetworkClient() {
        return networkClient;
    }
}
