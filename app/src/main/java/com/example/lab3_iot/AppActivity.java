package com.example.lab3_iot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.widget.Button;

import com.example.lab3_iot.fragmentos.AcelerometroFragment;
import com.example.lab3_iot.fragmentos.MagnetometroFragment;

public class AppActivity extends AppCompatActivity {
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

    }
}