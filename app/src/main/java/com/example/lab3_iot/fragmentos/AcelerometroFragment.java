package com.example.lab3_iot.fragmentos;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.lab3_iot.R;
import com.example.lab3_iot.RandomUserAdapter;
import com.example.lab3_iot.databinding.FragmentAcelerometroBinding;
import com.example.lab3_iot.dto.Result;
import com.example.lab3_iot.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;


public class AcelerometroFragment extends Fragment {
    RecyclerView recyclerview;
    List<Result> listaResult = new ArrayList<>();
    RandomUserAdapter randomUserAdapter;
    FragmentAcelerometroBinding binding;
    SensorManager sensorManagerA;
    Sensor acelerometroS;
    SensorEventListener sensorEventListAcelero;
    float lastAce = 0.0f;
    static final float SHAKE_THRESHOLD = 9.90f; //Para considerar agitación

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAcelerometroBinding.inflate(inflater,container,false);
        MainActivityViewModel mainActivityViewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
        mainActivityViewModel.getListMutableLiveData().observe(getViewLifecycleOwner(), contactos -> {
            for(Result r : contactos){
                String title = r.getName().getTitle();
                String first = r.getName().getFirst();
                String last = r.getName().getLast();
                String name = title+" "+first+" "+last;
                Log.d("nombre",name);
            }
        });
        sensorManagerA= (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        acelerometroS = sensorManagerA.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (acelerometroS != null) {
            Toast.makeText(getActivity(), "Usted está en el Acelerómetro",Toast.LENGTH_LONG).show();
            sensorEventListAcelero = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    float x = event.values[0];
                    float y = event.values[1];
                    float z = event.values[2];

                    float totalAce = (float) Math.sqrt((x*x)+(y*y)+(z*z));
                    if(Math.abs(totalAce-lastAce) > SHAKE_THRESHOLD){
                        recyclerview.scrollBy(1,1);
                    }
                    lastAce = totalAce;
                    Log.d("aceleration", String.valueOf(totalAce));
                    Log.d("threshold", String.valueOf(SHAKE_THRESHOLD));
                    Toast.makeText(getActivity(), "Su aceleración: " + totalAce + "m/s^2", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                }
            };
            sensorManagerA.registerListener(sensorEventListAcelero, acelerometroS, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Toast.makeText(getActivity(), "Su equipo no dispone de acelerómetro",Toast.LENGTH_SHORT).show();
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        recyclerview = view.findViewById(R.id.recycledview2);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        randomUserAdapter = new RandomUserAdapter(listaResult, getContext());
        recyclerview.setAdapter(randomUserAdapter);

    }
    @Override
    public void onPause() {
        super.onPause();
        // Detener la escucha del acelerómetro al cambiar de fragmento
        sensorManagerA.unregisterListener(sensorEventListAcelero);
    }

    public void actualizaListaA(List<Result> results){
        this.listaResult.clear();
        this.listaResult.addAll(results);
        randomUserAdapter.notifyDataSetChanged();
    }
}