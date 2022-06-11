package com.example.activgoldlombard.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.activgoldlombard.R;
import com.example.activgoldlombard.databinding.FragmentReqHouseBinding;
import com.example.activgoldlombard.model.ReqHouse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class ReqHouseFragment extends Fragment {

    private FragmentReqHouseBinding binding;

    private FirebaseAuth auth;
    private DatabaseReference db;
    private StorageReference storageReference;

    private String fio, phone, city, address;
    private boolean photos = false;
    private final ArrayList<Uri> imageUri = new ArrayList<>();

    private ActivityResultLauncher<Intent> intentActivityResultLauncher;

    public ReqHouseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intentActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            imageUri.add(data.getData());
                        }
                        if (!photos) {
                            photos = true;
                            binding.uploadTxt.setText("Фото выбрано");
                        }
                    }
                });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReqHouseBinding.inflate(inflater, container, false);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference("Requests");
        storageReference = FirebaseStorage.getInstance().getReference();

        binding.uploadTxt.setOnClickListener(view -> choosePicture());
        binding.uploadImg.setOnClickListener(view -> choosePicture());

        binding.backBtn.setOnClickListener(view -> replaceFragment(new OformlenieZaimaFragment()));

        binding.sendBtn.setOnClickListener(view -> validateData());

        return binding.getRoot();
    }

    private void choosePicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intentActivityResultLauncher.launch(intent);
    }

    private void validateData() {
        fio = Objects.requireNonNull(binding.fioTxt.getText()).toString().trim();
        phone = Objects.requireNonNull(binding.phoneTxt.getText()).toString().trim();
        city = Objects.requireNonNull(binding.cityTxt.getText()).toString().trim();
        address = Objects.requireNonNull(binding.addressTxt.getText()).toString().trim();

        if (fio.isEmpty()) {
            binding.fioTxt.setError("Пожалуйста введите ФИО");
            binding.fioTxt.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            binding.phoneTxt.setError("Пожалуйста введите номер телефона");
            binding.phoneTxt.requestFocus();
            return;
        }
        if (!Patterns.PHONE.matcher(phone).matches()) {
            binding.phoneTxt.setError("Пожалуйста введите корректный номер телефона");
            binding.phoneTxt.requestFocus();
            return;
        }
        if (city.isEmpty()) {
            binding.cityTxt.setError("Пожалуйста введите город");
            binding.cityTxt.requestFocus();
            return;
        }
        if (address.isEmpty()) {
            binding.addressTxt.setError("Пожалуйста введите адрес");
            binding.addressTxt.requestFocus();
            return;
        }
        if (!photos) {
            Toast.makeText(getActivity(), "Пожалуйста, выберите фотографии", Toast.LENGTH_LONG).show();
            return;
        }

        uploadImages();
    }

    //Отправляет запрос
    private void upload() {
        ReqHouse reqHouse = new ReqHouse(fio, phone, city, address);
        db.child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).push().setValue(reqHouse)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Запрос отправлен", Toast.LENGTH_SHORT).show();
                        replaceFragment(new OformlenieZaimaFragment());
                    } else {
                        Toast.makeText(getActivity(), "Ошибка! Запрос не отправлен", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //Загружает фотографии
    private void uploadImages() {
        for (Uri uri : imageUri) {
            StorageReference ref = storageReference.child("images/"
                    + Objects.requireNonNull(auth.getCurrentUser()).getUid()
                    + "/" + LocalDate.now() + "/"+ UUID.randomUUID().toString());

            ref.putFile(uri)
                    .addOnFailureListener(e -> Toast.makeText(getContext(), "Ошибка! Фото не загружено", Toast.LENGTH_LONG).show());
        }

        upload();
    }

    //Перейти на другой фрагмент
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.commit();
    }
}