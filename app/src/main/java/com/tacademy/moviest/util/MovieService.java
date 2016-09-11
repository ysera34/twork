package com.tacademy.moviest.util;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by yoon on 2016. 9. 9..
 */
public class MovieService {

    String url;
    JSONObject jsonObject;

    public void requestPostMovie() {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    public void requestPutMovie(int position) {

    }

    public void requestDeleteMovie(int position) {

    }
}
