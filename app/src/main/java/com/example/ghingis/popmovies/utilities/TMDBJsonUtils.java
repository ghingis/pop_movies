package com.example.ghingis.popmovies.utilities;

import android.content.Context;
import android.widget.Toast;

import com.example.ghingis.popmovies.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Utility functions to handle TMDB JSON data.
 */
public final class TMDBJsonUtils {

    /**
     * This method parses JSON from a web response and returns an array of MovieModels
     * describing a list of movie.
     *
     * @param jsonStr JSON response from server
     * @return Array of MovieModels describing a list of movie
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static MovieModel[] getMoviesDataFromJson(Context context, String jsonStr)
            throws JSONException {

        /* Movie information. Each movie info is an element of the "results" array */
        final String TMDB_LIST = "results";

        /* Movie json properties*/
        final String TMDB_ID = "id";
        final String TMDB_ORIGINAL_TITLE = "original_title";
        final String TMDB_OVERVIEW = "overview";
        final String TMDB_RELEASE_DATE = "release_date";
        final String TMDB_POSTER = "poster_path";
        final String TMDB_BACKDROP = "backdrop_path";
        final String TMDB_RATING = "vote_average";

        final String TMDB_STATUS_CODE = "status_code";
        final String TMDB_STATUS_MESSAGE = "status_message";

        /* MovieModel array to hold each movie's data */
        MovieModel[] parsedMovieData;

        JSONObject json = new JSONObject(jsonStr);

        /* Is there an error? */
        if (json.has(TMDB_STATUS_CODE)) {
            /* We only get status code if there is an error*/
            if (json.has(TMDB_STATUS_MESSAGE)) {
                int code = json.getInt(TMDB_STATUS_CODE);
                String message = json.getString(TMDB_STATUS_MESSAGE);
                Toast.makeText(context, code+": "+message, Toast.LENGTH_LONG).show();
            }

            return null;
        }

        JSONArray movieArray = json.getJSONArray(TMDB_LIST);

        parsedMovieData = new MovieModel[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++) {
            int id = 0;
            String title = null;
            String overview = null;
            String releaseDate = null;
            String posterPath = null;
            String backdropPath = null;
            Double rating = null;

            /* Get the JSON object representing the movie */
            JSONObject movieObject = movieArray.getJSONObject(i);
            if (movieObject.has(TMDB_ID)) {
                id = movieObject.getInt(TMDB_ID);
            }
            if (movieObject.has(TMDB_ORIGINAL_TITLE)) {
                title = movieObject.getString(TMDB_ORIGINAL_TITLE);
            }
            if (movieObject.has(TMDB_OVERVIEW)) {
                overview = movieObject.getString(TMDB_OVERVIEW);
            }
            if (movieObject.has(TMDB_RELEASE_DATE)) {
                releaseDate = movieObject.getString(TMDB_RELEASE_DATE);
            }
            if (movieObject.has(TMDB_POSTER)) {
                posterPath = movieObject.getString(TMDB_POSTER);
            }
            if (movieObject.has(TMDB_BACKDROP)) {
                backdropPath = movieObject.getString(TMDB_BACKDROP);
            }
            if (movieObject.has(TMDB_RATING)) {
                rating = movieObject.getDouble(TMDB_RATING);
            }

            parsedMovieData[i] = new MovieModel(id, title,overview,releaseDate,posterPath,backdropPath,rating);
        }

        return parsedMovieData;
    }

}