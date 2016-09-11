package com.tacademy.moviest.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.tacademy.moviest.R;
import com.tacademy.moviest.adapter.MovieAdapter;
import com.tacademy.moviest.app.AppSingleton;
import com.tacademy.moviest.model.MovieVO;
import com.tacademy.moviest.view.fragment.MovieSliderFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MoviesActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener{

    private String TAG = MoviesActivity.class.getSimpleName();
    private static final String URL = "http://192.168.200.125:3000/movies";

    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<MovieVO> movieVOList;
    private ProgressDialog pDialog;
    private MovieAdapter mAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG + " : token", "#####" + token + "#####");
        String deviceID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.i(TAG + " : deviceID", "#####" + deviceID + "#####");

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        pDialog = new ProgressDialog(this);
        movieVOList = new ArrayList<>();
        mAdapter = new MovieAdapter(MoviesActivity.this, movieVOList);

        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext(), 1, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new MovieAdapter.RecyclerTouchListener(
                getApplicationContext(), recyclerView, new MovieAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("movies", movieVOList);
                bundle.putInt("position", position);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                MovieSliderFragment newFragment = MovieSliderFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        fetchMovies();
                                    }
                                }
        );
    }

    @Override
    public void onRefresh() {
        fetchMovies();
    }

    public void fetchMovies() {
        pDialog.setMessage("Loading Data.....");
        pDialog.show();

        JsonObjectRequest req = new JsonObjectRequest(URL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        pDialog.hide();

                        movieVOList.clear();
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = jsonArray.length(); i > 0; i--) {
                                JSONObject object = jsonArray.getJSONObject(i-1);
                                MovieVO movieVO = new MovieVO();
                                movieVO.setId(object.getInt("id"));
                                movieVO.setTitle(object.getString("title"));

                                movieVOList.add(movieVO);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e(TAG, "JSON parsing error : " + e.getMessage());
                        }
                        mAdapter.notifyDataSetChanged();
                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "ErrorResponse : " + error.getMessage());
                pDialog.hide();

                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(req);
    }

    public void editMovie() {

    }

    public void deleteMovie() {

    }
}
