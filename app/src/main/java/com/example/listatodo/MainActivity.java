package com.example.listatodo;

import static android.app.PendingIntent.FLAG_IMMUTABLE;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.example.listatodo.MVVM.ViewModel;
import com.example.listatodo.createTask.CreateTask;
import com.example.listatodo.database.TaskDatabaseHandler;
import com.example.listatodo.notification.NotificationReceiver;
import com.example.listatodo.recyclerView.TaskRecyclerViewFragment;
import com.example.listatodo.taskDataModel.TaskCategory;
import com.example.listatodo.taskDataModel.TaskData;
import com.example.listatodo.taskDataModel.TaskStatus;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private List<TaskData> taskData;
    private TaskDatabaseHandler db;
    private ViewModel model;
    private FloatingActionButton floatingButton;
    private Boolean showCompletedTasks;
    private String categoryTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new TaskDatabaseHandler(this);
        model = new ViewModelProvider(this).get(ViewModel.class);
        createNotificationChannel();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        showCompletedTasks = prefs.getBoolean("completedTask",true);
        categoryTasks = prefs.getString("categoryTasks","ALL");

        loadTasks();

        floatingButton = findViewById(R.id.fab);
        floatingButton.setOnClickListener(view -> replaceFragment(new CreateTask()));
        replaceFragment(new TaskRecyclerViewFragment());
    }

    public void loadTasks() {
        taskData = db.getAllTasks();
        taskData.sort(Comparator.comparing(TaskData::getTaskEnd));
        if(showCompletedTasks.equals(false)) {
            taskData = taskData.stream()
                    .filter(taskData1 -> taskData1.getTaskStatus()
                            .equals(TaskStatus.ACTIVE))
                    .collect(Collectors.toList());
        }
        if(!categoryTasks.equals("ALL")){
            taskData = taskData.stream()
                    .filter(taskData1 -> taskData1.getTaskCategory()
                            .equals(TaskCategory.valueOf(categoryTasks)))
                    .collect(Collectors.toList());
        }
        model.setTaskData(taskData);
    }

    public void loadTasksByTitle(String title) {
        loadTasks();
        taskData = taskData.stream()
                .filter(taskData1 -> taskData1
                        .getTaskTitle()
                        .contains(title))
                .collect(Collectors.toList());

        taskData.sort(Comparator.comparing(TaskData::getTaskEnd));
        model.setTaskData(taskData);
    }

    public FloatingActionButton getFloatingActionButton() {
        return floatingButton;
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainLayout, fragment)
                .setReorderingAllowed(true)
                .commit();
    }

    public TaskDatabaseHandler getDb() {
        return db;
    }

    public void setAlarm(TaskData task) {
        Intent intent = new Intent(MainActivity.this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        System.out.println(task.getTaskEnd());

        alarmManager.set(AlarmManager.RTC_WAKEUP, task.getTaskEnd(), pendingIntent);
    }

    public void createNotificationChannel() {
        CharSequence name = "testChannel";
        String description = "des";

        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("notify", name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}