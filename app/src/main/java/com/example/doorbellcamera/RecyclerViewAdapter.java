package com.example.doorbellcamera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
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
       String image = photoList.get(position).getPath().replace("data:image/jpeg;base64,","");
       byte[] decode = Base64.decode(image.getBytes(),Base64.DEFAULT);
       Bitmap bitmap = BitmapFactory.decodeByteArray(decode,0,decode.length);
       holder.photo.setImageBitmap(bitmap);
       long countTime = Long.parseLong(photoList.get(position).getTime());
       String date = new java.text.SimpleDateFormat("dd/MM/YYYY HH:mm:ss").format(new java.util.Date(countTime));
       System.out.println("Tanggalnya: " + date);
       holder.detailPhoto.setText(date);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView detailPhoto;
        ImageView photo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            detailPhoto = itemView.findViewById(R.id.cameraDesc);
            photo= itemView.findViewById(R.id.photo);
        }
    }
}
