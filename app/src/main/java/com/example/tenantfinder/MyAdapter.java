package com.example.tenantfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    private Context context;
    static ArrayList<User1> user1ArrayList;

    public MyAdapter(ArrayList<User1> user1ArrayList, Context context) {
        this.user1ArrayList=user1ArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context)
                .inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       User1 user= user1ArrayList.get(position);
       holder.textViewhead.setText(user.Type);
       holder.textViewdesc.setText(user.price);
       holder.textViewaddress.setText(user.address);
       holder.textViewdetails.setText(user.details);
       holder.textViewpho.setText(user.phone);
    }

    @Override
    public int getItemCount() {
        return user1ArrayList.size();
    }
    public void filterlist(List filteredlist)
    {
        user1ArrayList= (ArrayList<User1>) filteredlist;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
   {
       public TextView textViewhead;
       public TextView textViewdesc;
       public TextView textViewaddress;
       public TextView textViewdetails;
       public TextView textViewpho;

       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           textViewhead=(TextView) itemView.findViewById(R.id.Type);
           textViewdesc=(TextView) itemView.findViewById(R.id.price);
           textViewaddress=(TextView) itemView.findViewById(R.id.address);
           textViewdetails=(TextView) itemView.findViewById(R.id.details);
           textViewpho=(TextView) itemView.findViewById(R.id.phone);

       }
   }

}