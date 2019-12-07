package com.example.doorbellcamera;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<Photo> photoList;
    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<Photo> photos){
        this.mContext = context;
        this.photoList = photos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.detailPhoto.setText(photoList.get(position).getPath());
        holder.timestampPhoto.setText(String.valueOf(position+1));
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
