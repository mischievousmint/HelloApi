package com.sebastian.helloapi;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.sebastian.helloapi.api.ApiInterface;
import com.sebastian.helloapi.model.Film;
import com.sebastian.helloapi.model.Films;
import com.sebastian.helloapi.model.Vehicles;
import java.util.List;


public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {

    // private Films<Film> filmModelList;
    private List<Film> filmModelList;

    // put the data on list
    public FilmAdapter(Films userModelList) {
        this.filmModelList = userModelList.getResults();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_list_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = filmModelList.get(position).getTitle();
        holder.name.setText(name);
    }

    @Override
    public int getItemCount() {
        return filmModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.tvTitleFilm);
        }
    }

}