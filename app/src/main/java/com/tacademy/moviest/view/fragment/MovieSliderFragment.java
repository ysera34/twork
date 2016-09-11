package com.tacademy.moviest.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tacademy.moviest.R;
import com.tacademy.moviest.adapter.MoviePageAdapter;
import com.tacademy.moviest.model.MovieVO;

import java.util.ArrayList;

/**
 * Created by yoon on 2016. 9. 7..
 */
public class MovieSliderFragment extends DialogFragment {

    public static final String TAG = MovieSliderFragment.class.getSimpleName();
    private ArrayList<MovieVO> movieVOs;
    private ViewPager viewPager;
    private MoviePageAdapter moviePageAdapter;

    private int selectedPosition = 0;

    public MovieSliderFragment() {
    }

    public static MovieSliderFragment newInstance() {
        MovieSliderFragment fragment = new MovieSliderFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_DeviceDefault_Light);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_slider, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);

        movieVOs = (ArrayList<MovieVO>) getArguments().getSerializable("movies");
        selectedPosition = getArguments().getInt("position");

        moviePageAdapter = new MoviePageAdapter(getContext(), movieVOs);
        viewPager.setAdapter(moviePageAdapter);

        for (int i = 0; i < movieVOs.size(); i++) {
            Log.d(TAG, movieVOs.get(i).toString());
        }
        Log.d(TAG, String.valueOf(selectedPosition));

        viewPager.addOnPageChangeListener(pageChangeListener);
        setCurrentItem(selectedPosition);

        return view;
    }

    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
    }

    ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}