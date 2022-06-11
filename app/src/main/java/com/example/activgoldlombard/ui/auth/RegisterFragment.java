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
import com.example.activgoldlombard.databinding.FragmentRegisterBinding;
import com.example.activgoldlombard.model.User;
import com.example.activgoldlombard.ui.auth.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterFragment extends Fragment {

    private FirebaseAuth auth;
    private DatabaseReference db;

    private FragmentRegisterBinding binding;

    private String username, email, password, confirmPassword;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference("Users");

        binding.registerBtn.setOnClickListener(view -> validateData());

        binding.loginTxt.setOnClickListener(view -> replaceFragment(new LoginFragment()));

        return binding.getRoot();
    }

    //Проверяет правильно ли введены данные
    private void validateData() {
        username = Objects.requireNonNull(binding.usernameTxt.getText()).toString().trim();
        email = Objects.requireNonNull(binding.emailTxt.getText()).toString().trim();
        password = Objects.requireNonNull(binding.passwordTxt.getText()).toString().trim();
        confirmPassword = Objects.requireNonNull(binding.confirmPasswordTxt.getText()).toString().trim();

        if (username.isEmpty()) {
            binding.usernameTxt.setError("Пожалуйста введите имя пользователя");
            binding.usernameTxt.requestFocus();
            return;
        }
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
        if (confirmPassword.isEmpty()) {
            binding.confirmPasswordTxt.setError("Пожалуйста подтвердите пароль");
            binding.confirmPasswordTxt.requestFocus();
            return;
        }
        if (!confirmPassword.equals(password)) {
            binding.confirmPasswordTxt.setError("Пароли не одинаковые");
            binding.confirmPasswordTxt.requestFocus();
            return;
        }

        registerUser();
    }

    //Регистрирует нового пользователя. Отправляет на почту сообщение верификации почты
    private void registerUser() {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        User user = new User(username, email);
                        db.child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).setValue(user)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()){
                                        auth.getCurrentUser().sendEmailVerification();
                                        Toast.makeText(getActivity(), "Пожалуйста, подьтвердите вашу почту", Toast.LENGTH_SHORT).show();
                                        replaceFragment(new LoginFragment());
                                    }
                                    else {
                                        Toast.makeText(getActivity(), "Ошибка! Ползователь не создан", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                    else {
                        Toast.makeText(getActivity(), "Ошибка! Ползователь не создан", Toast.LENGTH_SHORT).show();
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