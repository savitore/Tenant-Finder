package com.example.tenantfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {
    private RecyclerView recyclerView;
    MyAdapter1 myAdapter;
    ArrayList<User1> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerview1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list= new ArrayList<User1>();
        myAdapter=new MyAdapter1(this,list);
        recyclerView.setAdapter(myAdapter);

    }
}