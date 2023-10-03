package com.example.lab3_iot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.lab3_iot.databinding.FragmentMagnetometroBinding;
import com.example.lab3_iot.dto.Name;
import com.example.lab3_iot.dto.Result;
import com.example.lab3_iot.fragmentos.AcelerometroFragment;
import com.example.lab3_iot.fragmentos.MagnetometroFragment;
import com.example.lab3_iot.service.RandomUser;
import com.example.lab3_iot.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppActivity extends AppCompatActivity {
    RandomUser randomUser;
    Bundle bundle;
    MainActivityViewModel mainActivityViewModel;
    Button btnGoToSensor;
    List<Result> listaContactos1 = new ArrayList<>();
    List<Result> listaContactos2 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        createRandomUserService();
        ImageButton ojito = findViewById(R.id.imBtnDetail);
        Button btnAdd = findViewById(R.id.btnAdd);
        btnGoToSensor = findViewById(R.id.btnGoToSensor);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomUser.getResults().enqueue(new Callback<com.example.lab3_iot.dto.RandomUser>() {
                    @Override
                    public void onResponse(Call<com.example.lab3_iot.dto.RandomUser> call, Response<com.example.lab3_iot.dto.RandomUser> response) {
                        com.example.lab3_iot.dto.RandomUser body = response.body();
                        List<Result> listaResults = body.getResults();
                        //tengo el contacto -> ready!
                        Log.d("mensaje", String.valueOf(listaResults.size()));
                        Fragment fragmentoActual = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView2);
                        for(Result r: listaResults){
                            if(fragmentoActual instanceof AcelerometroFragment){
                                listaContactos1.add(r);
                                AcelerometroFragment acelerometroFragment = (AcelerometroFragment) fragmentoActual;
                                acelerometroFragment.actualizaListaA(listaContactos1);

                            } else if(fragmentoActual instanceof  MagnetometroFragment){
                                listaContactos2.add(r);
                                MagnetometroFragment magnetometroFragment = (MagnetometroFragment) fragmentoActual;
                                magnetometroFragment.actualizaListaM(listaContactos2);
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<com.example.lab3_iot.dto.RandomUser> call, Throwable t) {

                    }
                });
            }
        });
        btnGoToSensor.setOnClickListener(view -> {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView2);
            if (currentFragment instanceof AcelerometroFragment) {
                loadFragment(new MagnetometroFragment());
                btnGoToSensor.setText("IR AL ACELERÓMETRO");
            } else if (currentFragment instanceof MagnetometroFragment) {
                loadFragment(new AcelerometroFragment());
                btnGoToSensor.setText("IR AL MAGNETÓMETRO");
            }
        });
        ojito.setOnClickListener(view -> {
            Fragment fragmentoActual = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView2);
            if(fragmentoActual instanceof AcelerometroFragment){
                AlertDialog.Builder alertDialogAce = new AlertDialog.Builder(this);
                alertDialogAce.setTitle("Detalles - Acelerómetro");
                alertDialogAce.setMessage("Haga CLICK en 'Añadir' para agregar contactos a su lista. Esta aplicación está utilizando el ACELERÓMETRO de su dispositivo." +
                        "De esta forma, la lista hará scroll hacia abajo, cuando agite su dispositivo");
                alertDialogAce.setNeutralButton("Aceptar", ((dialogInterface, i) -> {}));
                alertDialogAce.show();
            } else if(fragmentoActual instanceof MagnetometroFragment){
                AlertDialog.Builder alertDialogMag = new AlertDialog.Builder(this);
                alertDialogMag.setTitle("Detalles - Magnetómetro");
                alertDialogMag.setMessage("Haga CLICK en 'Añadir' para agregar contactos a su lista. Esta aplicación está utilizando el MAGNETÓMETRO de su dispositivo." +
                        "De esta forma, la lista se mostrará al 100% cuando se apunte al NORTE, caso contrario, se desvanecerá");
                alertDialogMag.setNeutralButton("Aceptar", ((dialogInterface, i) -> {}));
                alertDialogMag.show();
            }
        });

    }

    public void createRandomUserService(){
        randomUser = new Retrofit.Builder()
                .baseUrl("https://randomuser.me")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RandomUser.class);
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragmentContainerView2, fragment)
        .addToBackStack(null)
        .commit();
    }

}