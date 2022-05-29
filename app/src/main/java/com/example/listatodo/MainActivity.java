package com.example.listatodo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listatodo.taskData.TaskCategory;
import com.example.listatodo.taskData.TaskData;
import com.example.listatodo.taskData.TaskDatabaseHandler;
import com.example.listatodo.taskData.TaskStatus;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TaskDatabaseHandler db = new TaskDatabaseHandler(this);

        db.addTask(new TaskData("Title"
                ,"Description"
                ,111L
                ,222L
                , TaskStatus.COMPLETE
                ,false
                , TaskCategory.OTHER
                ,false));
        db.addTask(new TaskData("Title"
                ,"Description2"
                ,111L
                ,222L
                , TaskStatus.COMPLETE
                ,false
                , TaskCategory.OTHER
                ,false));
        db.addTask(new TaskData("Title"
                ,"Description3"
                ,111L
                ,222L
                , TaskStatus.COMPLETE
                ,false
                , TaskCategory.OTHER
                ,false));

        // Reading all contacts
        List<TaskData> contacts = db.getAllTasks();

        for (TaskData cn : contacts) {
            System.out.println(cn.getTaskDescription());
        }

        FloatingActionButton floatingButton = findViewById(R.id.fab);
        floatingButton.setOnClickListener(view -> replaceFragment());

        RecyclerView recyclerView = findViewById(R.id.recyclerViewsTasks);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerViewAdapter(this));
    }

    private void replaceFragment() {
        Intent intent = new Intent(this, CreateTaskA.class);
        startActivity(intent);
    }

    @Override
    public void onClickItem(int position) {
        System.out.println(position);
        Intent intent = new Intent(this, CreateTaskA.class);
        startActivity(intent);
    }
}