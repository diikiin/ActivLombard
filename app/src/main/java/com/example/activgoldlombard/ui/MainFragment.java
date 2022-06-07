package com.example.activgoldlombard.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.activgoldlombard.R;
import com.example.activgoldlombard.databinding.FragmentMyBinding;
import com.example.activgoldlombard.model.PiedgeTicket;
import com.example.activgoldlombard.model.SampleType;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class MainFragment extends Fragment implements AdapterView.OnItemSelectedListener{
Button button;
    private FragmentMyBinding binding;
    public MainFragment() {
        // Required empty public constructor
    }
    EditText employeeNameEdt;
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;
    String options[]={"AU999","AU750","AU585" };
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//        binding = FragmentMyBinding.inflate(inflater, container, false);

        Spinner sampleSpinner = rootView.findViewById(R.id.ext_sample);

        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        assert container != null;
//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(container.getContext(),android.R.layout.simple_spinner_item,options);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(container.getContext(), R.array.extension_sample, android.R.layout.simple_spinner_dropdown_item);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        sampleSpinner.setAdapter(adapter);
        sampleSpinner.setOnItemSelectedListener(this);
        EditText grEdt = rootView.findViewById(R.id.textView2);

        Spinner daySpinner = rootView.findViewById(R.id.ext_day);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(container.getContext(), R.array.extension_days, android.R.layout.simple_spinner_dropdown_item);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        daySpinner.setAdapter(adapter2);
        sampleSpinner.setOnItemSelectedListener(this);

        button = rootView.findViewById(R.id.saveBtn);
        Map<String, Integer> map = new HashMap<>();
        map.put("AU999",23488);
        map.put("AU750",17434);
        map.put("AU585",13598);

firebaseDatabase = FirebaseDatabase.getInstance();
databaseReference =firebaseDatabase.getReference().child("PiedgeTicket");
button.setOnClickListener(v -> {
    if(TextUtils.isEmpty(grEdt.toString()))

    {
        // if the text fields are empty
        // then show the below message.
        Toast.makeText(getContext(), "Please add some data.", Toast.LENGTH_SHORT).show();
    } else

    {
        // else call the method to add
        // data to our database.
        SampleType sampleType = new SampleType(sampleSpinner.toString(),0.1105,Double.parseDouble(grEdt.getText().toString().trim()), map.get(sampleSpinner.getSelectedItem().toString()));
        PiedgeTicket a= new PiedgeTicket(sampleType,daySpinner.toString(),sampleType.getPriceForGr()*sampleType.getWeight(),sampleType.getPriceForGr()*sampleType.getWeight()*(1.0+sampleType.getPercent()));

        addDatatoFirebase(a);
    }
});
return rootView;
//        return binding.getRoot();
    }

    //Перейти на другой фрагмент
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.commit();
    }
    public AdapterView.OnItemSelectedListener getItem(){
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                String item = (String)parent.getItemAtPosition(position);
                Toast.makeText(parent.getContext(), item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        return  itemSelectedListener;
    }
    private void addDatatoFirebase(PiedgeTicket piedgeTicket) {
        // below 3 lines of code is used to set
        // data in our object class.
//        databaseReference =  FirebaseDatabase.getInstance().getReferenceFromUrl("xxxxxxxxxxxxxxxxxxxxxx");
//        mMovieRef = mRef.child("Name");


        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.setValue(piedgeTicket);

                // after adding this data we are showing toast message.
                Toast.makeText(getContext(), "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(getContext(), "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = (String)parent.getItemAtPosition(position);
        Toast.makeText(parent.getContext(), item, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}