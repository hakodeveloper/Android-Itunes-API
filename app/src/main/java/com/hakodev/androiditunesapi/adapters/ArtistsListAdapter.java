package com.hakodev.androiditunesapi.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hakodev.androiditunesapi.R;

import java.util.ArrayList;

public class ArtistsListAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> values;

    public ArtistsListAdapter(Context context, ArrayList<String> values) {
        super(context, R.layout.artists_list_adapter, values);
        this.context = context;
        this.values = values;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            if (inflater != null) {
                convertView = inflater.inflate(R.layout.artists_list_adapter, parent, false);
            }
        }
        TextView textView = convertView.findViewById(R.id.lblArtistName);
        ImageView imageView = convertView.findViewById(R.id.imgArtist);
        textView.setText(values.get(position));
        imageView.setImageResource(R.drawable.default_artist);
        return convertView;
    }
}
