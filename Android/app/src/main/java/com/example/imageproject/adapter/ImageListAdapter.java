package com.example.imageproject.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.imageproject.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class ImageListAdapter  extends BaseAdapter {

    Context context;
    List<Uri> list;

    public ImageListAdapter(Context context, List<Uri> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.item_image, null);

        ImageView imageView = view.findViewById(R.id.image);

        File file = new File(list.get(position).getPath());
        Picasso.get().load(file).into(imageView);


        return view;
    }
}
