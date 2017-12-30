package com.hakodev.androiditunesapi.activities;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.hakodev.androiditunesapi.AndroidItunesAPI;
import com.hakodev.androiditunesapi.R;
import com.hakodev.androiditunesapi.util.Utils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ArtistListActivity extends AppCompatActivity {

    private final static String TAG = "ArtistListActivity";
    private final static String SEARCH_URL = "https://itunes.apple.com/search?term=%1$s&country=US&entity=musicArtist&limit=10";

    private OkHttpClient networkClient;
    private ConstraintLayout lytBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTheme();
        setContentView(R.layout.activity_artists_list);
        setupViews();
        init();
        askForPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    private void setupTheme() {
        setTheme(R.style.AppTheme);
    }

    private void setupViews() {
        lytBase = findViewById(R.id.lytBase);
    }

    private void init() {
        networkClient = AndroidItunesAPI.getInstance().getNetworkClient();
    }

    private void askForPermissions() {
        //Not needed for internet permission but more of a proof of concept
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this,
                new String[]{Manifest.permission.INTERNET}, new PermissionsResultAction() {
                    @Override
                    public void onGranted() {
                        Log.d(TAG, "Permissions Granted");
                    }

                    @Override
                    public void onDenied(String permission) {
                        Toast.makeText(ArtistListActivity.this, R.string.permissions_not_granted, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void requestArtist(String artistName) {
        new Thread(() -> {
            Request request = new Request.Builder()
                    .url(String.format(SEARCH_URL, Utils.formatStringForURL(artistName)))
                    .build();
            networkClient.newCall(request)
                    .enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull final Call call, @NonNull IOException e) {
                            Log.e(TAG, e.getLocalizedMessage());
                            runOnUiThread(() -> Snackbar.make(lytBase, R.string.network_connection_error, Snackbar.LENGTH_LONG).show());
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                            String res = response.body().string();
                            Log.w(TAG, res);
                        }
                    });
        }).start();
    }
}
