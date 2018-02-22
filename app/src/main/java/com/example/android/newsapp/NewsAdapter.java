package com.example.android.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by phartmann on 20/02/2018.
 */

public class NewsAdapter extends ArrayAdapter<com.example.android.newsapp.NewsList> {

    public NewsAdapter( @NonNull Context context, @NonNull List <com.example.android.newsapp.NewsList> objects ) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView( int position, @Nullable View convertView, @NonNull ViewGroup parent ) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        /* Catch an item of list */
        com.example.android.newsapp.NewsList currentItem = getItem(position);

        /* Set title to textView */
        TextView titleView = listItemView.findViewById(R.id.title_view);
        titleView.setText(currentItem.getmTitle());

        /* Set author to textView*/
        TextView sectionView = listItemView.findViewById(R.id.section_view);
        sectionView.setText(currentItem.getmSection());

        return listItemView;
    }
}
