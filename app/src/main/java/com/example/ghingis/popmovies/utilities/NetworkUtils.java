package com.example.ghingis.popmovies.utilities;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.ghingis.popmovies.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the TMDB servers.
 */
public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static final String IMAGE_URL = "https://image.tmdb.org/t/p/w185";
    public static final String IMAGE_BACKDROP_URL = "https://image.tmdb.org/t/p/w500";

    private static final String TMDB_URL = "https://api.themoviedb.org/3";

    private static final String MOVIE_ENDPOINT = "/movie";

    /**
     * Builds the URL used to get movie lists by order.
     *
     * @param order The order of the requested list
     * @return The URL to use to query movie lists.
     */
    public static URL buildMovieListUrl(String order) {
        Uri builtUri = Uri.parse(TMDB_URL+MOVIE_ENDPOINT+order);

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     * Builds the URL used to get an exact movie.
     *
     * @param id The tmdb id of the movie
     * @return The URL to use to query movie lists.
     */
    public static URL buildMovieUrl(Long id) {
        Uri builtUri = Uri.parse(TMDB_URL+MOVIE_ENDPOINT+id.toString());

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     * Completes the URL used to talk to tmdb server with the api key.
     *
     * @param url Requested URL which will be completed with tmdb api key
     * @return The Url to use to query tmdb server.
     */
    private static URL _addApiKey(Context context, URL url) {
        String apiKey = context.getString(R.string.THE_MOVIE_DB_API_TOKEN);
        try{
            Uri uri = Uri.parse(url.toURI().toString()).buildUpon().appendQueryParameter("api_key",apiKey).build();

            return new URL(uri.toString());
        } catch (URISyntaxException e){
            e.printStackTrace();
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return url;
        }
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(Context context, URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) _addApiKey(context, url).openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}