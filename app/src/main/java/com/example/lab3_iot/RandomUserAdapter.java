package com.example.lab3_iot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab3_iot.dto.Result;

import java.util.List;

public class RandomUserAdapter extends RecyclerView.Adapter<RandomUserAdapter.RandomUserViewHolder> {
    private List<Result> listaResult;
    private Context context;

    Result contact;
    public RandomUserAdapter(List<Result> listaResult){
        this.listaResult = listaResult;
    }
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
        contact = listaResult.get(position);
        holder.result = contact;
        TextView txtName = holder.itemView.findViewById(R.id.txtName);
        TextView txtGender = holder.itemView.findViewById(R.id.txtGender);
        TextView txtCity = holder.itemView.findViewById(R.id.txtCity);
        TextView txtCountry = holder.itemView.findViewById(R.id.txtCountry);
        TextView txtEmail = holder.itemView.findViewById(R.id.txtEmail);
        TextView txtPhone = holder.itemView.findViewById(R.id.txtPhone);

        txtName.setText(contact.getName().getTitle()+ " " + contact.getName().getFirst() + " " +contact.getName().getLast());
        txtGender.setText(contact.getGender());
        txtCity.setText(contact.getLocation().getCity());
        txtCountry.setText(contact.getLocation().getCountry());
        txtEmail.setText(contact.getEmail());
        txtPhone.setText(contact.getPhone());
    }
    public class RandomUserViewHolder extends RecyclerView.ViewHolder{
        Result result;
        public RandomUserViewHolder(@NonNull View itemView){
            super(itemView);
            ImageButton delete = itemView.findViewById(R.id.imageButton);
            delete.setOnClickListener(view ->{
                int position = listaResult.indexOf(contact);
                listaResult.remove(position);
                notifyItemRemoved(position);
            });
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
