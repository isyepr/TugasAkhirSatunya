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
import com.example.tugasakhirsatunya.Pojo.DetailLaporan;
import com.example.tugasakhirsatunya.Pojo.Komentar;
import com.example.tugasakhirsatunya.Pojo.Laporan;
import com.example.tugasakhirsatunya.R;

import java.util.ArrayList;

public class KomentarAdapter extends RecyclerView.Adapter<KomentarAdapter.CustomViewHolder> {
    private ArrayList<Komentar> dataList;
    private Context context;


    public KomentarAdapter(Context context, ArrayList<Komentar> dataList){
        this.context = context;
        this.dataList = dataList;
    }






    public class CustomViewHolder extends RecyclerView.ViewHolder {


        TextView isi = itemView.findViewById(R.id.isi);
//        TextView subtitle = itemView.findViewById(R.id.subtitle);

        TextView komentar = new TextView(context);
//        TextView laporan_judul = new TextView(context);
//        TextView laporan_isi = new TextView(context);
//        TextView laporan_tanggal = new TextView(context);


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goToKomentar = new Intent(context, DetailLaporanActivity.class);
                    goToKomentar.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    goToKomentar.putExtra("komentar", komentar.getText());

                    context.startActivity(goToKomentar);


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
        View view = layoutInflater.inflate(R.layout.content_card_komentar, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  CustomViewHolder holder, int position) {
        holder.isi.setText(dataList.get(position).getKomentar_isi());
//        holder.subtitle.setText(dataList.get(position).getLaporan_isi());
//        holder.laporan_id.setText(dataList.get(position).getId().toString());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Context context = v.getContext();
//                Intent intent = new Intent(context, DetailLaporanActivity.class);
//                intent.putExtra("komentar", komentar);
//                context.startActivity(intent);
//            }
//        });



    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }
}