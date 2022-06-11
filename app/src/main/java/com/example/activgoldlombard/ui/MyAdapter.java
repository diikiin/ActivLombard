package com.example.activgoldlombard.ui;

import static java.lang.Math.round;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activgoldlombard.R;
import com.example.activgoldlombard.model.PiedgeTicket;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;

    private ArrayList<PiedgeTicket> list;

    private DatabaseReference rootRef;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public MyAdapter(Context context, ArrayList<PiedgeTicket> list) {
        this.context = context;
        this.list = list;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setFilteredList(List<PiedgeTicket> filteredList) {
        this.list = (ArrayList<PiedgeTicket>) filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.deposit_bilet, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        rootRef = FirebaseDatabase.getInstance().getReference("PiedgeTicket")
                .child(Objects.requireNonNull(auth.getCurrentUser()).getUid());

        PiedgeTicket model = list.get(position);
        double percent = model.getCredit() * model.getSampleType().getPercent();
        holder.remains.setText(String.valueOf(model.getCredit()));
        holder.date.setText(model.getDate());
        holder.percentForPay.setText(String.valueOf(percent));
        holder.txtPay.setText(String.valueOf(percent));
        holder.id.setText(String.valueOf(model.getId()));
        if (!model.isPaid()) {
            holder.payBtn.setOnClickListener(view -> {
                rootRef.child(model.getId()).child("credit").setValue(round(model.getCredit() - percent));
                rootRef.child(model.getId()).child("paid").setValue(true);
                rootRef.child(model.getId()).child("sampleType").child("percent").setValue(0);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                PayFragment payFragment = new PayFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", model.getId());
                bundle.putString("date", model.getDate());
                bundle.putString("credit", String.valueOf(model.getCredit()));
                bundle.putString("percent", String.valueOf(model.getCredit() * model.getSampleType().getPercent()));
                payFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, payFragment).addToBackStack(null).commit();
            });
        }
        else {
            holder.payBtn.setVisibility(View.GONE);
            holder.txtPay.setVisibility(View.GONE);
            holder.imgTenge.setVisibility(View.GONE);
            holder.paid.setText("Оплачено");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id, date, remains, percentForPay, txtPay, paid;
        Button payBtn;
        ImageView imgTenge;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.txtId);
            date = itemView.findViewById(R.id.txtDate);
            remains = itemView.findViewById(R.id.txtSum);
            percentForPay = itemView.findViewById(R.id.txtPercent);
            txtPay = itemView.findViewById(R.id.txtPay);
            payBtn = itemView.findViewById(R.id.payBtn);
            imgTenge = itemView.findViewById(R.id.imgTenge3);
            paid = itemView.findViewById(R.id.textView12);
        }
    }

}
