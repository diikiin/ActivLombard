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
import android.widget.TextView;
import android.widget.Toast;

import com.example.activgoldlombard.R;
import com.example.activgoldlombard.databinding.FragmentMyBinding;
import com.example.activgoldlombard.model.PiedgeTicket;
import com.example.activgoldlombard.model.SampleType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;


public class MainFragment extends Fragment implements AdapterView.OnItemSelectedListener{
Button button;
TextView sum, depSum;
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

        sum=rootView.findViewById(R.id.summa);
        depSum = rootView.findViewById(R.id.depSumma);

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
        //получаем список проб и цен
        Map<String, Integer> map = getInitSample();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference("PiedgeTicket");

        button.setOnClickListener(v -> {
        if(TextUtils.isEmpty(grEdt.toString())&TextUtils.isEmpty(sampleSpinner.getSelectedItem().toString())&TextUtils.isEmpty(daySpinner.getSelectedItem().toString())) {
        // if the text fields are empty
        // then show the below message.
        Toast.makeText(getContext(), "Please add some data.", Toast.LENGTH_SHORT).show();
        } else {
        // else call the method to add
        // data to our database.

        SampleType sampleType = new SampleType(sampleSpinner.getSelectedItem().toString(),0.1105,Double.parseDouble(grEdt.getText().toString().substring(0,grEdt.getText().toString().trim().length()-2).trim()), map.get(sampleSpinner.getSelectedItem().toString().trim()));
        PiedgeTicket a= new PiedgeTicket(sampleType,daySpinner.getSelectedItem().toString(),(long)(sampleType.getPriceForGr()*sampleType.getWeight()), (long) (sampleType.getPriceForGr() * sampleType.getWeight() * (1.0 + sampleType.getPercent())));

        sum.setText(String.valueOf(sampleType.getPriceForGr()*sampleType.getWeight()));
        depSum.setText(String.valueOf((sampleType.getPriceForGr() * sampleType.getWeight() * (1.0 + sampleType.getPercent()))));

        databaseReference.child(String.valueOf(a.hashCode())).setValue(a).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                grEdt.setText("");
                Toast.makeText(container.getContext(),"Successfuly Inserted",Toast.LENGTH_SHORT).show();

            }
        });
    }
    });
    return rootView;
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = (String)parent.getItemAtPosition(position);
        Toast.makeText(parent.getContext(), item, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public Map<String,Integer>getInitSample(){
        Map<String, Integer> map = new HashMap<>();
        map.put("AU999",23488);
        map.put("AU750",17434);
        map.put("AU585",13598);
        map.put("AU333",8088);
        map.put("AU375",9108);
        map.put("AU500",12144);
        map.put("AU875",21253);
        map.put("AU916",22248);
        map.put("AU958",23269);
        return  map;
    }
}