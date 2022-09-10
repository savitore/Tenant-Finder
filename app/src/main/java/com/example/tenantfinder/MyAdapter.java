package com.example.tenantfinder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    private Context context;
    static ArrayList<User1> user1ArrayList;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();

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
//       Glide.with(context).load(user1ArrayList.get(position).getImageUrl()).circleCrop().into(holder.imageView);
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
    public void filterlist(ArrayList<User1> filteredlist)
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
       ImageButton fav;
//       ImageView imageView;

       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           textViewhead=(TextView) itemView.findViewById(R.id.Type);
           textViewdesc=(TextView) itemView.findViewById(R.id.price);
           textViewaddress=(TextView) itemView.findViewById(R.id.address);
           textViewdetails=(TextView) itemView.findViewById(R.id.details);
           textViewpho=(TextView) itemView.findViewById(R.id.phone);
           fav=(ImageButton) itemView.findViewById(R.id.fav);
           fav.setOnClickListener(new View.OnClickListener() {
               boolean isPressed=false;
               @Override
               public void onClick(View view) {
                   if(isPressed)
                   {
                       fav.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
//                       pushData();
                   }
                   else
                   {
                       fav.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
                   }
                   isPressed=!isPressed;

               }

           });

//           imageView=(ImageView) itemView.findViewById(R.id.image);

       }
   }

//   private void pushData()
//   {
//       db.collection("Fav").document(db.collection("Fav").document().getId()).set(note)
//               .addOnSuccessListener(new OnSuccessListener<Void>() {
//                   @Override
//                   public void onSuccess(Void unused) {
//                   }
//               })
//               .addOnFailureListener(new OnFailureListener() {
//                   @Override
//                   public void onFailure(@NonNull Exception e) {
////                       Log.d(TAG, e.toString());
//                   }
//               });
//   }
}
