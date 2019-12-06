package com.example.doorbellcamera;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Photo> photoList = new ArrayList<>();
    private Context mContext;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Photo photo = photoList.get(position);
        holder.detailPhoto.setText(photo.getPhoto());
        holder.timestampPhoto.setText(photo.getPhoto());
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView detailPhoto;
        TextView timestampPhoto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            detailPhoto = itemView.findViewById(R.id.cameraDesc);
            timestampPhoto = itemView.findViewById(R.id.cameraDate);
        }
    }
}
