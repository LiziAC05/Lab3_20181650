package com.example.lab3_iot.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab3_iot.R;
import com.example.lab3_iot.databinding.FragmentAcelerometroBinding;
import com.example.lab3_iot.databinding.FragmentMagnetometroBinding;


public class AcelerometroFragment extends Fragment {

    FragmentAcelerometroBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAcelerometroBinding.inflate(inflater,container,false);
        binding.btnGoToMagneto.setOnClickListener(view -> {
            NavController navController = NavHostFragment.findNavController(AcelerometroFragment.this);
            navController.navigate(R.id.action_acelerometroFragment_to_magnetometroFragment);
        });
        return binding.getRoot();
    }
}