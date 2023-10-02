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
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.lab3_iot.R;
import com.example.lab3_iot.RandomUserAdapter;
import com.example.lab3_iot.databinding.FragmentMagnetometroBinding;
import com.example.lab3_iot.dto.Result;
import com.example.lab3_iot.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class MagnetometroFragment extends Fragment {
    FragmentMagnetometroBinding binding;
    RecyclerView recyclerview;
    List<Result> listaResult = new ArrayList<>();
    RandomUserAdapter randomUserAdapter;
    SensorManager sensorManagerM;
    Sensor magnetometroS;

    SensorEventListener sensorEventListMagneto;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMagnetometroBinding.inflate(inflater,container,false);
        sensorManagerM= (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        magnetometroS = sensorManagerM.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

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

        if(magnetometroS != null){
            Toast.makeText(getActivity(), "Usted está en el magnetómetro",Toast.LENGTH_SHORT).show();

            sensorEventListMagneto = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    float magneticX = sensorEvent.values[0];
                    float magneticY = sensorEvent.values[1];
                    float magneticZ = sensorEvent.values[2];

                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {
                    //cambio de presicion del sensor
                }
            };
            sensorManagerM.registerListener(sensorEventListMagneto, magnetometroS, SensorManager.SENSOR_DELAY_NORMAL);
        } else{
            Toast.makeText(getActivity(), "Su equipo no dispone de Magnetómetro",Toast.LENGTH_SHORT).show();
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        recyclerview = view.findViewById(R.id.recyclerview1);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        randomUserAdapter = new RandomUserAdapter(listaResult, getContext());
        recyclerview.setAdapter(randomUserAdapter);

    }
    @Override
    public void onPause() {
        super.onPause();
        // Detener la escucha del magnetómetro al cambiar de fragmento
        sensorManagerM.unregisterListener(sensorEventListMagneto);
    }
    public void actualizaListaM(List<Result> results){
        this.listaResult.clear();
        this.listaResult.addAll(results);
        randomUserAdapter.notifyDataSetChanged();
    }
}