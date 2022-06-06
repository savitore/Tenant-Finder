package com.example.tenantfinder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.MyViewHolder>
{
    Context context;

    public MyAdapter1(Context context, ArrayList<User1> list) {
        this.context = context;
        this.list = list;
    }

    ArrayList<User1> list;
    @NonNull
    @Override
    public MyAdapter1.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.list_profile,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter1.MyViewHolder holder, int position) {
        User1 user= list.get(position);
        holder.type.setText(user.Type);
        holder.price.setText(user.price);
        holder.address.setText(user.address);
        holder.details.setText(user.details);
        holder.phone.setText(user.phone);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

        private TextView type;
        private TextView address;
        private TextView price;
        private TextView phone;
        private TextView details;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            type=itemView.findViewById(R.id.Type1);
            address=itemView.findViewById(R.id.address1);
            price=itemView.findViewById(R.id.price1);
            phone=itemView.findViewById(R.id.phone1);
            details=itemView.findViewById(R.id.details1);
        }
    }
}
