package com.tacademy.moviest.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tacademy.moviest.R;
import com.tacademy.moviest.app.AppSingleton;
import com.tacademy.moviest.model.MovieVO;
import com.tacademy.moviest.view.activity.MoviesActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by yoon on 2016. 9. 11..
 */
public class MovieFragment extends Fragment {

    public static final String TAG = MovieFragment.class.getSimpleName();

    final MovieVO movie = new MovieVO();

    private TextView title;
    private TextView director;
    private TextView year;
    private TextView synopsis;

    private int selectedPosition = 0;

    public static MovieFragment newInstance() {
        MovieFragment fragment = new MovieFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // customize style
        selectedPosition = getArguments().getInt("position");
        // volley request by id
        requestMovie(selectedPosition);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        title = (TextView) view.findViewById(R.id.title);
        director = (TextView) view.findViewById(R.id.director);
        year = (TextView) view.findViewById(R.id.year);
        synopsis = (TextView) view.findViewById(R.id.synopsis);

        displayMetaInfo(movie);
//        displayMetaInfo(position);

        return view;
    }

    private void displayMetaInfo(int position) {
//        title.setText(movieVOs.get(position).getTitle());
//        director.setText(movieVOs.get(position).getDirector());
//        year.setText(movieVOs.get(position).getYear());
//        synopsis.setText(movieVOs.get(position).getSynopsis());
    }

    private void displayMetaInfo(MovieVO movie) {
        title.setText(movie.getTitle());
        director.setText(movie.getDirector());
        year.setText(movie.getYear());
        synopsis.setText(movie.getSynopsis());
    }

    private void requestMovie(int position) { //movieVOs.get(position).getId();
        String url = MoviesActivity.URL + File.separator + position;
        Log.d("request url", url);
        JsonObjectRequest req = new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            movie.setId(response.getInt("id"));
                            movie.setTitle(response.getString("title"));
                            movie.setDirector(response.getString("director"));
                            movie.setYear(response.getInt("year"));
                            movie.setSynopsis(response.getString("synopsis"));

                            Log.d(TAG, movie.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "ErrorResponse : " + error.getMessage());
            }
        });
        AppSingleton.getInstance(getContext()).addToRequestQueue(req);
    }
}
