package com.example.listatodo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listatodo.recyclerView.ClickListener;
import com.example.listatodo.recyclerView.RecyclerViewAdapter;
import com.example.listatodo.taskDataModel.TaskData;
import com.example.listatodo.database.TaskDatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ClickListener {

    private List<TaskData> taskData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TaskDatabaseHandler db = new TaskDatabaseHandler(this);

       // db.addTask(new TaskData("Title"
       //         ,"Description"
       //         ,111L
       //         ,222L
       //         , TaskStatus.COMPLETE
       //         ,false
       //         , TaskCategory.OTHER
       //         ,false));
       // db.addTask(new TaskData("Title"
       //         ,"Description2"
       //         ,111L
       //         ,222L
       //         , TaskStatus.COMPLETE
       //         ,false
       //         , TaskCategory.OTHER
       //         ,false));
       // db.addTask(new TaskData("Title"
       //         ,"Description3"
       //         ,111L
       //         ,222L
       //         , TaskStatus.COMPLETE
       //         ,false
       //         , TaskCategory.OTHER
       //         ,false));

        taskData = db.getAllTasks();

        for (TaskData task : taskData) {
            System.out.println(task.getTaskDescription());
        }

        FloatingActionButton floatingButton = findViewById(R.id.fab);
        floatingButton.setOnClickListener(view -> replaceFragment());

        RecyclerView recyclerView = findViewById(R.id.recyclerViewsTasks);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerViewAdapter(taskData, this));
    }

    private void replaceFragment() {
        Intent intent = new Intent(this, CreateTaskA.class);
        startActivity(intent);
    }

    @Override
    public void onClickItem(int position) {
        System.out.println(position);
        Intent intent = new Intent(this, MoreDetailsAboutTask.class);

        intent.putExtra("title",taskData.get(position).getTaskTitle());
        intent.putExtra("description",taskData.get(position).getTaskDescription());
        intent.putExtra("start",taskData.get(position).getTaskStart());
        intent.putExtra("end",taskData.get(position).getTaskEnd());
        intent.putExtra("category",taskData.get(position).getTaskCategory());
        intent.putExtra("status",taskData.get(position).getTaskStatus());
        intent.putExtra("attachment",taskData.get(position).getHaveAttachment());

        startActivity(intent);
    }
}