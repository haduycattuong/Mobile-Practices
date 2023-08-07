package com.example.thbuoi3_listcountry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CountryAdapter extends BaseAdapter {
    Context context;
    List<Country> list;
    LayoutInflater inflater;

    public CountryAdapter(Context context, List<Country> list) {
        this.context = context;
        this.list = list;
        this.inflater = (LayoutInflater.from((context.getApplicationContext())));
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.country_layout, null);
        TextView name = (TextView) view.findViewById(R.id.txtName);
        name.setText(list.get(i).getName());
        ImageView img = (ImageView)  view.findViewById(R.id.img);
        img.setImageResource(list.get(i).getFlag());
        return view;
    }

}
