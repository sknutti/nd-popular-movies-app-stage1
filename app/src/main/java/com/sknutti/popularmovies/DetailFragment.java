package com.sknutti.popularmovies;

import android.content.ContentUris;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sknutti.popularmovies.data.MovieContract;
import com.squareup.picasso.Picasso;

/**
 * Created by sknutti on 8/3/15.
 */
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private Cursor mDetailCursor;
    private View mRootView;
    private Uri mUri;
    private Long mMovieId;
    private static final int CURSOR_LOADER_ID = 0;

    public DetailFragment() { }

    public static DetailFragment newInstance(Uri uri) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        fragment.mUri = uri;
        fragment.mMovieId = ContentUris.parseId(uri);
        args.putLong("id", fragment.mMovieId);
        fragment.setArguments(args);
        return fragment;
    }

    public static class ViewHolder {
        public final ImageView imageView;
        public final TextView titleView;
        public final TextView releaseDateView;
        public final TextView lengthView;
        public final TextView ratingView;
        public final TextView synopsisView;
        public final Button button = null;


        public ViewHolder(View view){
            imageView = (ImageView) view.findViewById(R.id.movie_poster);
            titleView = (TextView) view.findViewById(R.id.movie_title);
            releaseDateView = (TextView) view.findViewById(R.id.movie_release_date);
            lengthView = (TextView) view.findViewById(R.id.movie_length);
            ratingView = (TextView) view.findViewById(R.id.movie_rating);
            synopsisView = (TextView) view.findViewById(R.id.movie_synopsis);
//            button = (Button) view.findViewById(R.id.button_favorite);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ViewHolder viewHolder = new ViewHolder(rootView);
        rootView.setTag(viewHolder);
        mRootView = rootView;
        Bundle args = this.getArguments();
        getLoaderManager().initLoader(CURSOR_LOADER_ID, args, DetailFragment.this);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection = null;
        String [] selectionArgs = null;
        if (args != null){
            selection = MovieContract.MovieEntry._ID + " = ?";
            selectionArgs = new String[]{String.valueOf(args.get("id"))};
        }
        return new CursorLoader(getActivity(),
                mUri,
                MovieContract.MovieEntry.MOVIE_PROJECTION,
                selection,
                selectionArgs,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ViewHolder viewHolder = (ViewHolder) mRootView.getTag();

        mDetailCursor = data;
        mDetailCursor.moveToFirst();
        DatabaseUtils.dumpCursor(data);

        Picasso.with(getActivity())
                .load(MovieAdapter.BASE_POSTER_URI + MovieAdapter.PHONE_SIZE + mDetailCursor.getString(MovieContract.MovieEntry.COL_POSTER_PATH))
                .into(viewHolder.imageView);
        viewHolder.titleView.setText(mDetailCursor.getString(MovieContract.MovieEntry.COL_MOVIE_TITLE));
        viewHolder.releaseDateView.setText(mDetailCursor.getString(MovieContract.MovieEntry.COL_RELEASE_DATE).substring(0,4));
        viewHolder.lengthView.setText(mDetailCursor.getString(MovieContract.MovieEntry.COL_MOVIE_LENGTH) + " min");
        viewHolder.ratingView.setText(mDetailCursor.getString(MovieContract.MovieEntry.COL_MOVIE_RATING) + "/10");
        viewHolder.synopsisView.setText(mDetailCursor.getString(MovieContract.MovieEntry.COL_MOVIE_SYNOPSIS));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mDetailCursor = null;
    }
}
