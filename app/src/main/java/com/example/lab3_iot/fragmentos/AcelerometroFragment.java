package com.example.lab3_iot.fragmentos;

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

import com.example.lab3_iot.R;
import com.example.lab3_iot.RandomUserAdapter;
import com.example.lab3_iot.databinding.FragmentAcelerometroBinding;
import com.example.lab3_iot.databinding.FragmentMagnetometroBinding;
import com.example.lab3_iot.dto.Result;
import com.example.lab3_iot.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;


public class AcelerometroFragment extends Fragment {
    RecyclerView recyclerview;
    List<Result> listaResult = new ArrayList<>();
    RandomUserAdapter randomUserAdapter;
    FragmentAcelerometroBinding binding;

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

    public void actualizaListaA(List<Result> results){
        this.listaResult.clear();
        this.listaResult.addAll(results);
        randomUserAdapter.notifyDataSetChanged();
    }
}