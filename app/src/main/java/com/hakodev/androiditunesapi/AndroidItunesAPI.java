package com.hakodev.androiditunesapi;

import android.app.Application;

import pro.oncreate.easynet.EasyNet;
import pro.oncreate.easynet.models.NResponseModel;

public class AndroidItunesAPI extends Application {

    private static Application instance = null;

    public static Application getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        EasyNet.getInstance()
                .setWriteLogs(false)
                .setDefaultRequestListener(request -> request.setHost("http://192.168.1.151:25555/BeamNGTelemetry"))
                .addOnErrorDefaultListener(new EasyNet.OnErrorDefaultListenerWithCode(404) {
                    @Override
                    public void onError(NResponseModel responseModel) {
                        // For example, intercepted error 404
                    }
                });
    }
}
