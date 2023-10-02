package com.example.lab3_iot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
                        /*Log.d("mensaje1", String.valueOf(listaContactos1.size()));
                        for(Result r : listaContactos1){
                            String title = r.getName().getTitle();
                            String first = r.getName().getFirst();
                            String last = r.getName().getLast();
                            String name = title+" "+first+" "+last;
                            String gender = r.getGender();
                            String city = r.getLocation().getCity();
                            String country = r.getLocation().getCountry();
                            String email = r.getEmail();
                            String phone = r.getPhone();
                            String large = r.getPicture().getLarge();
                            Log.d("nombre",name);
                            r.setName(new Name(title, first, last));
                            r.getName().setFirst(first);
                            r.getName().setLast(last);
                            r.setGender(gender);
                            r.setEmail(email);
                            r.setPhone(phone);
                        }*/

                        //mainActivityViewModel.getListMutableLiveData().setValue(listaContactos);
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