package com.example.activgoldlombard.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.activgoldlombard.R;
import com.example.activgoldlombard.model.PiedgeTicket;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class PiedgeAdapter extends FirebaseRecyclerAdapter<
        PiedgeTicket, PiedgeAdapter.piedgeViewholder>{

    public PiedgeAdapter(
            @NonNull FirebaseRecyclerOptions<PiedgeTicket> options)
    {
        super(options);
    }



    @Override
    protected void
    onBindViewHolder(@NonNull piedgeViewholder holder,
                     int position, @NonNull PiedgeTicket model) {
        {
            double percent = model.getCredit()*model.getSampleType().getPercent();
//            holder.id.setText(String.valueOf(model.getId()));
            holder.remains.setText(String.valueOf(model.getCredit()));
            holder.date.setText(model.getDate().toString());
            holder.percentForPay.setText(String.valueOf(percent));
        }
    }

    @NonNull
    @Override
    public piedgeViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deposit_bilet, parent, false);
        return new PiedgeAdapter.piedgeViewholder(view);
    }

    class piedgeViewholder
            extends RecyclerView.ViewHolder {
        TextView id, date, remains, percentForPay;
        public piedgeViewholder(@NonNull View itemView)
        {
            super(itemView);

            id = (TextView) itemView.findViewById(R.id.txtId);
            date = (TextView) itemView.findViewById(R.id.txtDate);
            remains = (TextView) itemView.findViewById(R.id.txtSum);
            percentForPay = (TextView) itemView.findViewById(R.id.txtPercent);
        }
        @NonNull
        public piedgeViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deposit_bilet, parent, false);
            return new PiedgeAdapter.piedgeViewholder(view);
        }
    }
}
