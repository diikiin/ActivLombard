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
import com.example.activgoldlombard.databinding.FragmentLoginBinding;
import com.example.activgoldlombard.ui.MyFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginFragment extends Fragment {

    private FirebaseAuth auth;

    private FragmentLoginBinding binding;

    private String email, password;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        auth = FirebaseAuth.getInstance();

        binding.loginBtn.setOnClickListener(view -> validateData());

        binding.forgotPassword.setOnClickListener(view -> replaceFragment(new ForgotPasswordFragment()));

        binding.registerTxt.setOnClickListener(view -> replaceFragment(new RegisterFragment()));

        return binding.getRoot();
    }

    //Проверяет правильно ли введены данные
    private void validateData() {
        email = Objects.requireNonNull(binding.emailTxt.getText()).toString().trim();
        password = Objects.requireNonNull(binding.passwordTxt.getText()).toString().trim();

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
        if (password.isEmpty()) {
            binding.passwordTxt.setError("Пожалуйста введите пароль");
            binding.passwordTxt.requestFocus();
            return;
        }
        if (password.length() < 6) {
            binding.passwordTxt.setError("Пароль должен быть минимум 6 символов");
            binding.passwordTxt.requestFocus();
            return;
        }

        loginUser();
    }

    //Авторизация с эмэйлом и паролем
    private void loginUser(){
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        FirebaseUser user = auth.getCurrentUser();
                        assert user != null;
                        if (user.isEmailVerified()){
                            replaceFragment(new MyFragment());
                        }
                        else {
                            user.sendEmailVerification();
                            Toast.makeText(getActivity(), "Пожалуйста, подьтвердите вашу почту", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getActivity(), "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
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