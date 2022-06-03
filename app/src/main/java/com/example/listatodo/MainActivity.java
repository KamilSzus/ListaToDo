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
import com.example.listatodo.recyclerView.ClickListener;
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