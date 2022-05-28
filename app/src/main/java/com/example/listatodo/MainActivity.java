package com.example.listatodo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements ClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton floatingButton = findViewById(R.id.fab);
        floatingButton.setOnClickListener(view -> replaceFragment());

        RecyclerView recyclerView = findViewById(R.id.recyclerViewsTasks);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(new RecyclerViewAdapter(this));
    }

    private void replaceFragment() {
        Intent intent = new Intent(this, CreateTaskA.class);
        startActivity(intent);
    }

    @Override
    public void onClickItem(int position) {
        System.out.println(position);
    }
}