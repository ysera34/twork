package com.tacademy.moviest.adapter;

import android.content.Context;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tacademy.moviest.R;
import com.tacademy.moviest.app.AppSingleton;
import com.tacademy.moviest.model.MovieVO;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
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
//            showPopupMenu(overflow);
            showPopup(overflow);
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

    private void showPopup(View view) {

        ListPopupWindow popupWindow = new ListPopupWindow(context);

        List<String> data = new ArrayList<>();
        data.add("Edit");
        data.add("Delete");
        ArrayAdapter adapter = new ArrayAdapter(
                context, R.layout.popup_menu_layout, data);

        popupWindow.setAnchorView(view);
        popupWindow.setAdapter(adapter);
        popupWindow.setWidth(300);
        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0 : //edit
                        Toast.makeText(context, "Edit Movie", Toast.LENGTH_SHORT).show();
                        editItem();
                        break;
                    case 1 : //delete
                        Toast.makeText(context, "Delete Movie", Toast.LENGTH_SHORT).show();

//                        deleteItem();
                        break;
                }
            }
        });
        popupWindow.show();
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
//                case R.id.action_show_desc :
//                Toast.makeText(context,
//                            "Show Movie", Toast.LENGTH_SHORT).show();
//                return true;
//                case R.id.action_update_movie :
//                    Toast.makeText(context,
//                            "Update movie", Toast.LENGTH_SHORT).show();
//                    return true;
                case R.id.movie_edit :
                    Toast.makeText(context,
                            "movie_edit", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.movie_delete :
                    Toast.makeText(context,
                            "movie_delete", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    }

    public void editItem() {

    }

    public void deleteItem(int position) {
        String URL = "http://192.168.21.14:3000/movies";
        URL += File.separator + position;
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.DELETE, URL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppSingleton.getInstance(context).addToRequestQueue(req);
    }

    @Override
    public int getItemCount() {
        return movieVOList.size();
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MovieAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context,
                                     final RecyclerView recyclerView, final MovieAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
