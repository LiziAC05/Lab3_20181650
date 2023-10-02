package com.example.lab3_iot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lab3_iot.databinding.FragmentMagnetometroBinding;
import com.example.lab3_iot.dto.Result;
import com.example.lab3_iot.fragmentos.AcelerometroFragment;
import com.example.lab3_iot.fragmentos.MagnetometroFragment;
import com.example.lab3_iot.service.RandomUser;
import com.example.lab3_iot.viewmodel.MainActivityViewModel;

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

    List<Result> listaContactos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        createRandomUserService();
        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomUser.getResults().enqueue(new Callback<com.example.lab3_iot.dto.RandomUser>() {
                    @Override
                    public void onResponse(Call<com.example.lab3_iot.dto.RandomUser> call, Response<com.example.lab3_iot.dto.RandomUser> response) {
                        com.example.lab3_iot.dto.RandomUser body = response.body();
                        List<Result> listaResults = body.getResults();
                        //tengo el contacto -> ready!

                        for(Result r: listaResults){
                            listaContactos.add(r);
                        }

                        mainActivityViewModel.getListMutableLiveData().setValue(listaContactos);
                    }

                    @Override
                    public void onFailure(Call<com.example.lab3_iot.dto.RandomUser> call, Throwable t) {

                    }
                });
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

    public void obtenUnContacto(){
        randomUser.getResults().enqueue(new Callback<com.example.lab3_iot.dto.RandomUser>() {
            @Override
            public void onResponse(Call<com.example.lab3_iot.dto.RandomUser> call, Response<com.example.lab3_iot.dto.RandomUser> response) {
                com.example.lab3_iot.dto.RandomUser body = response.body();
                List<Result> listaResults = body.getResults();
                //tengo el contacto -> ready!
                mainActivityViewModel.getListMutableLiveData().setValue(listaResults);
            }

            @Override
            public void onFailure(Call<com.example.lab3_iot.dto.RandomUser> call, Throwable t) {

            }
        });
    }
}