package com.sknutti.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sknutti.popularmovies.data.MovieContract;
import com.squareup.picasso.Picasso;

public class MovieAdapter extends CursorAdapter {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();
    public static final Uri BASE_POSTER_URI = Uri.parse("http://image.tmdb.org/t/p/");
    public static final String PHONE_SIZE = "w185";

    private Context mContext;
    private static int mLoaderId;

    public static class ViewHolder {
        public final ImageView imageView;
        public final TextView textView;


        public ViewHolder(View view){
            imageView = (ImageView) view.findViewById(R.id.movie_poster);
            textView = (TextView) view.findViewById(R.id.movie_title);
        }
    }

    public MovieAdapter(Context context, Cursor c, int flags, int loaderId) {
        super(context, c, flags);
        mContext = context;
        Log.d(LOG_TAG, "Creating cursor...");
        mLoaderId = loaderId;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int layoutId = R.layout.movie_item;
        Log.d(LOG_TAG, "In new View");
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        Log.d(LOG_TAG, "In bind View");

        int imageIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH);
        String posterPath = cursor.getString(imageIndex);
        Log.i(LOG_TAG, "Image reference extracted: " + posterPath);

        Picasso.with(context).load(BASE_POSTER_URI + PHONE_SIZE + posterPath).into(viewHolder.imageView);
    }
}