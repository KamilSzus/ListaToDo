package com.example.listatodo;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.listatodo.MVVM.ViewModel;
import com.example.listatodo.createTask.CreateTask;
import com.example.listatodo.database.TaskDatabaseHandler;
import com.example.listatodo.recyclerView.TaskRecyclerViewFragment;
import com.example.listatodo.taskDataModel.TaskData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity{

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

        loadTasks();

        floatingButton = findViewById(R.id.fab);
        floatingButton.setOnClickListener(view -> replaceFragment(new CreateTask()));
        replaceFragment(new TaskRecyclerViewFragment());
    }

    public void loadTasks() {
        taskData = db.getAllTasks();
        taskData.sort(Comparator.comparing(TaskData::getTaskEnd));
        model.setTaskData(taskData);
    }

    public void loadTasksByTitle(String title){
        taskData = db.getAllTasks();
        taskData = taskData.stream()
                .filter(taskData1 -> taskData1
                        .getTaskTitle()
                        .contains(title))
                .collect(Collectors.toList());

        taskData.sort(Comparator.comparing(TaskData::getTaskEnd));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}