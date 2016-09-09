package com.tacademy.moviest.adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tacademy.moviest.R;
import com.tacademy.moviest.model.MovieVO;

import java.util.List;

/**
 * Created by yoon on 2016. 9. 7..
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ItemViewHolder> {

    private List<MovieVO> movieVOList;
    private Context context;

    public class ItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TextView id;
        public TextView title;
        public TextView director;
        public TextView year;
        public TextView synopsis;
        public ImageView overflow;

        public ItemViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.id);
            title = (TextView) itemView.findViewById(R.id.title);
            director = (TextView) itemView.findViewById(R.id.director);
            year = (TextView) itemView.findViewById(R.id.year);
            synopsis = (TextView) itemView.findViewById(R.id.synopsis);
            overflow = (ImageView) itemView.findViewById(R.id.overflow);
            overflow.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            showPopupMenu(overflow);
        }
    }

    public MovieAdapter(Context context, List<MovieVO> movieVOList) {
        this.context = context;
        this.movieVOList = movieVOList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        MovieVO movie = movieVOList.get(position);
//        holder.id.setText(movie.getId());
        holder.title.setText(movie.getTitle());
//        holder.director.setText(movie.getDirector());
//        holder.year.setText("(" + movie.getYear() + ")");
//        holder.synopsis.setText(movie.getSynopsis());
//        holder.overflow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPopupMenu(holder.overflow);
//            }
//        });
    }

    private void showPopupMenu(View view) {

        PopupMenu popupMenu = new PopupMenu(context, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_movie, popupMenu.getMenu());
//        popupMenu.getMenuInflater().inflate(R.menu.menu_movie, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new CustomMenuItemClickListener());
        popupMenu.show();
    }

    class CustomMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public CustomMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_show_desc :
                Toast.makeText(context,
                            "Show Movie", Toast.LENGTH_SHORT).show();
                return true;
                case R.id.action_update_movie :
                    Toast.makeText(context,
                            "Update movie", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return movieVOList.size();
    }
}
