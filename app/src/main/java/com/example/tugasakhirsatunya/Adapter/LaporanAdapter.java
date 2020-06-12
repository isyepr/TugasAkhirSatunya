package com.example.tugasakhirsatunya.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugasakhirsatunya.DetailLaporanActivity;
import com.example.tugasakhirsatunya.Pojo.Laporan;
import com.example.tugasakhirsatunya.R;


import java.util.ArrayList;

public class LaporanAdapter extends RecyclerView.Adapter<LaporanAdapter.CustomViewHolder> {
    private ArrayList<Laporan> dataList;
    private Context context;


    public LaporanAdapter(Context context,ArrayList<Laporan> dataList){
        this.context = context;
        this.dataList = dataList;
    }






    public class CustomViewHolder extends RecyclerView.ViewHolder {


        TextView title = itemView.findViewById(R.id.title);
        TextView subtitle = itemView.findViewById(R.id.subtitle);

        TextView laporan_id = new TextView(context);
//        TextView laporan_judul = new TextView(context);
//        TextView laporan_isi = new TextView(context);
//        TextView laporan_tanggal = new TextView(context);


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goToDetail = new Intent(context, DetailLaporanActivity.class);
                    goToDetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    goToDetail.putExtra("laporan_id", laporan_id.getText());
//                    goToDetail.putExtra("laporan_isi", laporan_isi.getText());

                    context.startActivity(goToDetail);


                }
            });



        }

//        public interface onLaporanListener{
//            void onLaporanClick(int position);
//        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.content_card_laporan, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  CustomViewHolder holder, int position) {
        holder.title.setText(dataList.get(position).getLaporan_judul());
        holder.subtitle.setText(dataList.get(position).getLaporan_isi());
        holder.laporan_id.setText(dataList.get(position).getId().toString());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Context context = v.getContext();
//                Intent intent = new Intent(context, DetailLaporanActivity.class);
//                intent.putExtra("laporan_id", laporan_id);
//                context.startActivity(intent);
//            }
//        });



    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }
}