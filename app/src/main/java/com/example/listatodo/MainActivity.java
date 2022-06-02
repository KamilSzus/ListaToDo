package com.example.listatodo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listatodo.recyclerView.ClickListener;
import com.example.listatodo.recyclerView.RecyclerViewAdapter;
import com.example.listatodo.recyclerView.TaskRecyclerViewFragment;
import com.example.listatodo.taskDataModel.TaskData;
import com.example.listatodo.database.TaskDatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ClickListener {

    private List<TaskData> taskData;
    private TaskDatabaseHandler db;
    private ViewModel model;
    private FloatingActionButton floatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new TaskDatabaseHandler(this);
        model = new ViewModelProvider(this).get(ViewModel.class);

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

        loadTasks();

        floatingButton = findViewById(R.id.fab);
        floatingButton.setOnClickListener(view -> replaceFragment(new CreateTask()));
        replaceFragment(new TaskRecyclerViewFragment());
    }

    public void loadTasks() {
        taskData = db.getAllTasks();
        model.setTaskData(taskData);
    }

    public FloatingActionButton getFloatingActionButton(){
        return floatingButton;
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainLayout, fragment)
                .setReorderingAllowed(true)
                .commit();
    }

    public TaskDatabaseHandler getDb(){
        return db;
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

    @Override
    public void onLongClickItem(int position) {
        db.deleteTask(taskData.get(position));

    }
}