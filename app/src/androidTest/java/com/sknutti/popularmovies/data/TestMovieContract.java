package com.sknutti.popularmovies.data;

import android.net.Uri;
import android.test.AndroidTestCase;

public class TestMovieContract extends AndroidTestCase {

    private static final String TEST_MOVIE = "Jurassic World";

    public void testBuildMovieUri() {
        Uri locationUri = MovieContract.MovieEntry.buildMovieUri(TEST_MOVIE);
        assertNotNull("Error: Null Uri returned.", locationUri);
        assertEquals("Error: Movie name not properly appended to the end of the uri",
                TEST_MOVIE, locationUri.getLastPathSegment());
        assertEquals("Error: Uri doesn't match our expected result",
                locationUri.toString(), "content://com.sknutti.popularmovies/movie/Jurassic%20World");
    }
}
