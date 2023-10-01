package com.example.lab3_iot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab3_iot.dto.Result;

import java.util.List;

public class RandomUserAdapter extends RecyclerView.Adapter<RandomUserAdapter.RandomUserViewHolder> {
    private List<Result> listaResult;
    private Context context;

    @NonNull
    @Override
    public RandomUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View inflate = LayoutInflater.from(context).inflate(R.layout.contact_irv, parent, false);
        return new RandomUserViewHolder(inflate);
    }

    @NonNull
    public int getItemCount(){
        return listaResult.size();
    }
    @Override
    public void onBindViewHolder(@NonNull RandomUserViewHolder holder, int position) {
        Result contact = listaResult.get(position);
        holder.result = contact;

    }
    public class RandomUserViewHolder extends RecyclerView.ViewHolder{
        Result result;
        public RandomUserViewHolder(@NonNull View itemView){
            super(itemView);
        }

    }

    public List<Result> getListaResult() {
        return listaResult;
    }

    public void setListaResult(List<Result> listaResult) {
        this.listaResult = listaResult;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
