package com.tacademy.moviest.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.tacademy.moviest.app.AppSingleton;
import com.tacademy.moviest.model.MovieVO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by yoon on 2016. 9. 9..
 */
public class MovieService {

    public static final String TAG = MovieService.class.getSimpleName();
    String url;
    JSONObject jsonObject;
    ArrayList<MovieVO> movieVOs;
    Context context;

    public MovieService(String url, JSONObject jsonObject,
                        ArrayList<MovieVO> movieVOs, Context context) {
        this.url = url;
        this.jsonObject = jsonObject;
        this.movieVOs = movieVOs;
        this.context = context;
    }

    public void requestPostMovie() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e(TAG, "requestPostMovie error : " + error.getMessage());
            }
        });
        AppSingleton.getInstance(context).addToRequestQueue(request);
    }

    public void requestPutMovie(int position, Object object) {
        MovieVO movie = (MovieVO) object;
        Gson gson = new Gson();
        String json = gson.toJson(movie);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "new jsonObject error : " + e.getMessage());
        }
        url += File.separator + position;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT,
                url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e(TAG, "requestPutMovie error : " + error.getMessage());
            }
        });
        AppSingleton.getInstance(context).addToRequestQueue(request);
    }

    public void requestDeleteMovie(int position) {
        url += File.separator + position;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE,
                url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppSingleton.getInstance(context).addToRequestQueue(request);
    }
}
