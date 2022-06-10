package com.example.activgoldlombard.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.activgoldlombard.R;
import com.example.activgoldlombard.databinding.FragmentPaidBinding;
import com.example.activgoldlombard.databinding.FragmentPayBinding;
import com.example.activgoldlombard.model.PiedgeTicket;

import java.util.Objects;

public class PaidFragment extends Fragment {
    FragmentPaidBinding binding;
    TextView txtId, txtDate, txtSum, txtPay, txtItogo, txtSum2;

    public PaidFragment() {
        // Required empty public constructor
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPaidBinding.inflate(inflater, container, false);

        Bundle bundle = this.getArguments();
        PiedgeTicket piedgeTicket = new PiedgeTicket(Objects.requireNonNull(bundle).get("id").toString(),
                bundle.get("date").toString(), Double.parseDouble(bundle.get("credit").toString()));

        double percent = Double.parseDouble(bundle.get("percent").toString());

        binding.txtId.setText(String.valueOf(piedgeTicket.getId()));
        binding.zaimIdTxt.setText(piedgeTicket.getId());
        binding.txtSum.setText(String.valueOf(piedgeTicket.getCredit() - percent));
        binding.txtDate.setText(String.valueOf(piedgeTicket.getDate()));
        binding.txtPercent.setText(String.valueOf(percent));
        binding.txtItogo.setText(String.valueOf(percent));
        binding.txtSum2.setText(String.valueOf(percent));

        return binding.getRoot();
    }

    //Перейти на другой фрагмент
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.commit();
    }
}