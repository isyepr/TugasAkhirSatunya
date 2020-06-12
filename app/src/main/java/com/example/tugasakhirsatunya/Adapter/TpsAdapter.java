package com.example.tugasakhirsatunya.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasakhirsatunya.DetailTpsActivity;
import com.example.tugasakhirsatunya.Pojo.Tps;
import com.example.tugasakhirsatunya.R;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class TpsAdapter extends RecyclerView.Adapter<TpsAdapter.CustomViewHolder> {
    private ArrayList<Tps> dataList;
    private Context context;


    public TpsAdapter(Context context,ArrayList<Tps> dataList){
        this.context = context;
        this.dataList = dataList;
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {



        TextView title = itemView.findViewById(R.id.title);
        TextView subtitle = itemView.findViewById(R.id.subtitle);
        ImageView imgcar = itemView.findViewById(R.id.imgcar);

        TextView tps_nama = new TextView(context);
        TextView tps_jenis = new TextView(context);
        TextView tps_volume = new TextView(context);
        ImageView tps_foto =  new ImageView(context);


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goToDetail = new Intent(context, DetailTpsActivity.class);
                    goToDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                    goToDetail.putExtra("tps_nama", tps_nama.getText());
                    goToDetail.putExtra("tps_jenis", tps_jenis.getText());
                    goToDetail.putExtra("tps_volume", tps_volume.getText());



                    context.startActivity(goToDetail);
                }
            });



        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.content_card_tps, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  CustomViewHolder holder, int position) {
        holder.title.setText(dataList.get(position).getTps_nama());
        holder.subtitle.setText(dataList.get(position).getTps_jenis());
        holder.tps_nama.setText(dataList.get(position).getTps_nama());
        holder.tps_jenis.setText(dataList.get(position).getTps_jenis());
        holder.tps_volume.setText(dataList.get(position).getTps_volume());


        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(dataList.get(position).getTps_photo_url())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgcar);

        builder.build().load(dataList.get(position).getTps_photo_url())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.tps_foto);


//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Context context = v.getContext();
//                Intent intent = new Intent(context, DetailTpsActivity.class);
//                context.startActivity(intent);
//            }
//        });



    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }
}