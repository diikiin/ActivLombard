package com.example.activgoldlombard.ui;

import static java.lang.Math.round;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.example.activgoldlombard.model.PiedgeTicket;
import com.example.activgoldlombard.model.SampleType;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    Button button;
    TextView sum, depSum;

    public MainFragment() {
        // Required empty public constructor
    }

    DatabaseReference databaseReference;

    String options[] = {"AU999", "AU750", "AU585"};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        sum = rootView.findViewById(R.id.summa);
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

        databaseReference = FirebaseDatabase.getInstance().getReference("PiedgeTicket");

        button.setOnClickListener(v -> {
            if (TextUtils.isEmpty(grEdt.toString()) & TextUtils.isEmpty(sampleSpinner.getSelectedItem().toString())
                    & TextUtils.isEmpty(daySpinner.getSelectedItem().toString())) {
                Toast.makeText(getContext(), "Please add some data.", Toast.LENGTH_SHORT).show();
            } else {
                SampleType sampleType = new SampleType(sampleSpinner.getSelectedItem().toString(),
                        0.1105, Double.parseDouble(grEdt.getText().toString()
                        .substring(0, grEdt.getText().toString().trim().length() - 2).trim()),
                        map.get(sampleSpinner.getSelectedItem().toString().trim()));
                PiedgeTicket a = new PiedgeTicket(sampleType, daySpinner.getSelectedItem().toString(),
                        (long) (sampleType.getPriceForGr() * sampleType.getWeight()),
                        (long) (sampleType.getPriceForGr() * sampleType.getWeight() * (1.0 + sampleType.getPercent())));

                sum.setText(String.valueOf(round(sampleType.getPriceForGr() * sampleType.getWeight())));
                depSum.setText(String.valueOf(round(sampleType.getPriceForGr() * sampleType.getWeight() * (1.0 + sampleType.getPercent()))));

            }
        });
        return rootView;
    }

    public AdapterView.OnItemSelectedListener getItem() {
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                String item = (String) parent.getItemAtPosition(position);
                Toast.makeText(parent.getContext(), item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        return itemSelectedListener;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public Map<String, Integer> getInitSample() {
        Map<String, Integer> map = new HashMap<>();
        map.put("AU999", 23243);
        map.put("AU750", 17734);
        map.put("AU585", 13598);
        map.put("AU333", 7740);
        map.put("AU375", 8717);
        map.put("AU500", 11622);
        map.put("AU875", 20349);
        map.put("AU916", 21293);
        map.put("AU958", 22269);
        return map;
    }
}