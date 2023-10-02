package com.example.lab3_iot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab3_iot.dto.RandomUser;
import com.example.lab3_iot.dto.Result;

import java.util.ArrayList;
import java.util.List;

public class RandomUserAdapter extends RecyclerView.Adapter<RandomUserAdapter.RandomUserViewHolder> {
    private List<Result> listaResult;
    private Context context;

    Result contact;
    RandomUser randomUser;
    public RandomUserAdapter(List<Result> listaResult, Context context){
        this.listaResult = listaResult;
        this.context = context;
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
        ImageView imageView = holder.itemView.findViewById(R.id.imageViewContact);
        txtName.setText(contact.getName().getTitle()+ " " + contact.getName().getFirst() + " " +contact.getName().getLast());
        txtGender.setText("Gender: "+contact.getGender());
        txtCity.setText("City: "+contact.getLocation().getCity());
        txtCountry.setText("Country: "+contact.getLocation().getCountry());
        txtEmail.setText("Email: "+contact.getEmail());
        txtPhone.setText("Phone: "+contact.getPhone());


    }
    public class RandomUserViewHolder extends RecyclerView.ViewHolder {
        Result result;

        public RandomUserViewHolder(@NonNull View itemView) {
            super(itemView);
            ImageButton delete = itemView.findViewById(R.id.imageButton);
            delete.setOnClickListener(view -> {
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
