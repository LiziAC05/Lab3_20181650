package com.example.lab3_iot.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lab3_iot.R;
import com.example.lab3_iot.databinding.FragmentMagnetometroBinding;

public class MagnetometroFragment extends Fragment {
    FragmentMagnetometroBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMagnetometroBinding.inflate(inflater,container,false);
        binding.btnGoToAcele.setOnClickListener(view -> {
            NavController navController = NavHostFragment.findNavController(MagnetometroFragment.this);
            navController.navigate(R.id.action_magnetometroFragment_to_acelerometroFragment);
        });
        return binding.getRoot();
    }
}