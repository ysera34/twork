package com.tacademy.moviest.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tacademy.moviest.R;
import com.tacademy.moviest.model.MovieVO;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 9. 9..
 */
public class MoviePageAdapter extends PagerAdapter {

    public static final String TAG = MoviePageAdapter.class.getSimpleName();
    Context context;
    private LayoutInflater layoutInflater;
    ArrayList<MovieVO> movieVOs;

    private TextView title;
    private TextView director;
    private TextView year;
    private TextView synopsis;

    public MoviePageAdapter(Context context, ArrayList<MovieVO> movieVOs) {
        this.context = context;
        this.movieVOs = movieVOs;

        for (int i = 0; i < movieVOs.size(); i++) {
            Log.i("MoviePageAdapter.movies", movieVOs.get(i).toString());
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
        layoutInflater = (LayoutInflater)
                context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.fragment_movie, container, false);

        title = (TextView) view.findViewById(R.id.title);
        director = (TextView) view.findViewById(R.id.director);
        year = (TextView) view.findViewById(R.id.year);
        synopsis = (TextView) view.findViewById(R.id.synopsis);

        displayMetaInfo(position);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return movieVOs.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    private void displayMetaInfo(int position) {
        title.setText(movieVOs.get(position).getTitle());
        director.setText(movieVOs.get(position).getDirector());
        year.setText(String.valueOf(movieVOs.get(position).getYear()));
        synopsis.setText(movieVOs.get(position).getSynopsis());
    }

}