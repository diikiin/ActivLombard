package com.example.activgoldlombard.ui.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.activgoldlombard.R;
import com.example.activgoldlombard.databinding.FragmentForgotPasswordBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgotPasswordFragment extends Fragment {

    private FirebaseAuth auth;

    private FragmentForgotPasswordBinding binding;

    private String email;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container,false);

        auth = FirebaseAuth.getInstance();

        binding.resetBtn.setOnClickListener(view -> validateData());

        binding.loginTxt.setOnClickListener(view -> replaceFragment(new LoginFragment()));

        return binding.getRoot();
    }

    //Проверяет правильно ли введены данные
    private void validateData() {
        email = Objects.requireNonNull(binding.emailTxt.getText()).toString().trim();

        if (email.isEmpty()) {
            binding.emailTxt.setError("Пожалуйста введите электронную почту");
            binding.emailTxt.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailTxt.setError("Пожалуйста введите корректную почту");
            binding.emailTxt.requestFocus();
            return;
        }

        resetPassword();
    }

    //Отправляет на почту сообщение о смене пароля
    private void resetPassword(){
        auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(getActivity(), "Проверьте свою почту, чтобы поменять пароль", Toast.LENGTH_SHORT).show();
                replaceFragment(new LoginFragment());
            }
        });
    }

    //Перейти на другой фрагмент
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.commit();
    }
}