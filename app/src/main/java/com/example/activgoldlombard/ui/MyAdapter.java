package com.example.activgoldlombard.ui;

import static java.lang.Math.round;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activgoldlombard.R;
import com.example.activgoldlombard.model.PiedgeTicket;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;

    ArrayList<PiedgeTicket> list;
    DatabaseReference rootRef;

    public MyAdapter(Context context, ArrayList<PiedgeTicket> list) {
        this.context = context;
        this.list = list;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setFilteredList(List<PiedgeTicket> filteredList){
        this.list= (ArrayList<PiedgeTicket>) filteredList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.deposit_bilet,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        rootRef = FirebaseDatabase.getInstance().getReference();

        PiedgeTicket model = list.get(position);
        double percent = model.getCredit()*model.getSampleType().getPercent();
        holder.remains.setText(String.valueOf(model.getCredit()));
        holder.date.setText(model.getDate().toString());
        holder.percentForPay.setText(String.valueOf(percent));
        holder.txtPay.setText(String.valueOf(percent));
        holder.id.setText(String.valueOf(model.getId()));
        holder.payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootRef.child("PiedgeTicket").child(model.getId()).child("credit").setValue(round(model.getCredit()-percent));
                rootRef.child("PiedgeTicket").child(model.getId()).child("sampleType").child("percent").setValue(0);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                PayFragment payFragment =new PayFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", model.getId());
                bundle.putString("date",model.getDate());
                bundle.putString("credit",String.valueOf(model.getCredit()));
                bundle.putString("percent",String.valueOf(model.getCredit()*model.getSampleType().getPercent()));
                payFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, payFragment).addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView id, date, remains, percentForPay,txtPay;
        Button payBtn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.txtId);
            date = (TextView) itemView.findViewById(R.id.txtDate);
            remains = (TextView) itemView.findViewById(R.id.txtSum);
            percentForPay = (TextView) itemView.findViewById(R.id.txtPercent);
            txtPay = (TextView)itemView.findViewById(R.id.txtPay);
            payBtn = (Button) itemView.findViewById(R.id.payBtn);

        }
    }

}
