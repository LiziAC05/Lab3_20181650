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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lab3_iot.R;
import com.example.lab3_iot.RandomUserAdapter;
import com.example.lab3_iot.databinding.FragmentMagnetometroBinding;
import com.example.lab3_iot.dto.Result;
import com.example.lab3_iot.viewmodel.MainActivityViewModel;

import java.util.List;

public class MagnetometroFragment extends Fragment {
    FragmentMagnetometroBinding binding;
    RecyclerView recyclerview;
    List<Result> listaResult;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMagnetometroBinding.inflate(inflater,container,false);
        binding.btnGoToAcele.setOnClickListener(view -> {
            NavController navController = NavHostFragment.findNavController(MagnetometroFragment.this);
            navController.navigate(R.id.action_magnetometroFragment_to_acelerometroFragment);
        });
        MainActivityViewModel mainActivityViewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
        mainActivityViewModel.getListMutableLiveData().observe(getViewLifecycleOwner(), contactos -> {

        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        recyclerview = view.findViewById(R.id.recyclerview1);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);
        RandomUserAdapter randomUserAdapter = new RandomUserAdapter(listaResult);
        recyclerview.setAdapter(randomUserAdapter);
        randomUserAdapter.notifyDataSetChanged();

    }
}