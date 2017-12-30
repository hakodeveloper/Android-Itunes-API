package com.hakodev.androiditunesapi.activities;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.anthonycr.grant.PermissionsManager;
import com.anthonycr.grant.PermissionsResultAction;
import com.google.gson.Gson;
import com.hakodev.androiditunesapi.AndroidItunesAPI;
import com.hakodev.androiditunesapi.R;
import com.hakodev.androiditunesapi.adapters.ArtistsListAdapter;
import com.hakodev.androiditunesapi.models.ItunesResponse;
import com.hakodev.androiditunesapi.models.Result;
import com.hakodev.androiditunesapi.util.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ArtistListActivity extends AppCompatActivity {

    private final static String TAG = "ArtistListActivity";
    private final static String SEARCH_URL = "https://itunes.apple.com/search?term=%1$s&country=US&entity=musicArtist&limit=10";
    private final static String[] DEMO_ARTISTS = {"Valentine", "Children", "Red"};

    private OkHttpClient networkClient;
    private ArtistsListAdapter artistsListAdapter;
    private ArrayList<String> artistsList = new ArrayList<>();
    private List<Result> artists;

    private ConstraintLayout lytBase;
    private ListView listArtists;
    private EditText txtArtistSearch;

    private Timer keypressTimer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTheme();
        setContentView(R.layout.activity_artists_list);
        setupViews();
        init();
        askForPermissions();

        int randomArtistIndex = new Random().nextInt(DEMO_ARTISTS.length);
        requestArtist(DEMO_ARTISTS[randomArtistIndex]);
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
        listArtists = findViewById(R.id.listArtists);
        txtArtistSearch = findViewById(R.id.txtArtistSearch);
        txtArtistSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable txt) {
                keypressTimer.cancel();
                keypressTimer = new Timer();
                keypressTimer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                if (txt.toString().length() > 3) {
                                    requestArtist(txt.toString());
                                }
                            }
                        }, 200);
            }
        });
    }

    private void init() {
        networkClient = AndroidItunesAPI.getInstance().getNetworkClient();
        artistsListAdapter = new ArtistsListAdapter(this, artistsList);
        listArtists.setAdapter(artistsListAdapter);
        listArtists.setOnItemClickListener((parent, view, position, id) -> {
            openWebLink(artists.get(position).getArtistLinkUrl());
        });
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
                            String body = response.body().string();
                            Log.w(TAG, body);
                            buildArtistsList(parseArtistsResponse(body));
                        }
                    });
        }).start();
    }

    private ArrayList<String> parseArtistsResponse(String body) {
        ItunesResponse itunesResponse = new ItunesResponse();
        try {
            itunesResponse = new Gson().fromJson(body, ItunesResponse.class);
        } catch (IllegalStateException e) {
            Log.d(TAG, "The Json was malformed, the request has been ignored");
        }
        ArrayList<String> artistsNames = new ArrayList<>();
        artists = itunesResponse.getResults();
        for (Result artist : itunesResponse.getResults()) {
            artistsNames.add(artist.getArtistName());
        }
        return artistsNames;
    }

    private void buildArtistsList(ArrayList<String> artists) {
        artistsList.clear();
        artistsList.addAll(artists);
        runOnUiThread(() -> artistsListAdapter.notifyDataSetChanged());
    }

    private void openWebLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
