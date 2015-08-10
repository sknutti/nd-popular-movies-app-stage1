package com.sknutti.popularmovies;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            Uri uri = getIntent().getData();

            DetailFragment fragment = DetailFragment.newInstance(uri);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_detail_container, fragment)
                    .addToBackStack(null).commit();
        }
    }
}
