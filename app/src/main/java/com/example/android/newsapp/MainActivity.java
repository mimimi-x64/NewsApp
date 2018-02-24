package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsList>> {

    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Create a new NewsAdapter to this Activity */
        newsAdapter = new NewsAdapter(this, new ArrayList <NewsList>());
        ListView listView = findViewById(R.id.list);
        listView.setAdapter(newsAdapter);

        /* Set Listener */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener( ) {
            @Override
            public void onItemClick( AdapterView <?> parent, View view, int position, long id ) {
                NewsList newsList = newsAdapter.getItem(position);

                /* Do a Intent to open and URL */
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsList.getArticleUrl()));
                if (intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }
            }
        });

        /* Start Loader Mananger */
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(0, null, this);
    }

    /* Call the loader passing a List */
    @Override
    public Loader<List <NewsList>> onCreateLoader( int id, Bundle args ) {
        return new NewsLoader(this, NewsLoader.urlSearch);
    }

    /* Check if is valid and show on Activity */
    @Override
    public void onLoadFinished( Loader <List <NewsList>> loader, List <NewsList> data ) {
        /* First clean up the adapter */
        newsAdapter.clear();

        /* Then check if there are a valid list */
        if (data != null && !data.isEmpty()){
            newsAdapter.addAll(data);
        }
    }

    /* Clean up when loader is reseted */
    @Override
    public void onLoaderReset( Loader <List <NewsList>> loader ) {
        newsAdapter.clear();
    }
}
