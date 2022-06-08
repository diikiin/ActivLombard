package com.example.activgoldlombard.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activgoldlombard.R;
import com.example.activgoldlombard.model.PiedgeTicket;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;

    ArrayList<PiedgeTicket> list;


    public MyAdapter(Context context, ArrayList<PiedgeTicket> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.deposit_bilet,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PiedgeTicket model = list.get(position);
        double percent = model.getCredit()*model.getSampleType().getPercent();
        holder.remains.setText(String.valueOf(model.getCredit()));
        holder.date.setText(model.getDate().toString());
        holder.percentForPay.setText(String.valueOf(percent));
        holder.txtPay.setText(String.valueOf(percent));
        holder.id.setText(String.valueOf(model.hashCode()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView id, date, remains, percentForPay,txtPay;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.txtId);
            date = (TextView) itemView.findViewById(R.id.txtDate);
            remains = (TextView) itemView.findViewById(R.id.txtSum);
            percentForPay = (TextView) itemView.findViewById(R.id.txtPercent);
            txtPay = (TextView)itemView.findViewById(R.id.txtPay);

        }
    }

}
