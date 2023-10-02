package com.example.lab3_iot.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lab3_iot.dto.Result;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    MutableLiveData<List<Result>> listMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Result>> getListMutableLiveData() {
        return listMutableLiveData;
    }

    public void setListMutableLiveData(MutableLiveData<List<Result>> listMutableLiveData) {
        this.listMutableLiveData = listMutableLiveData;
    }
}
