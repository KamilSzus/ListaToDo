package com.example.listatodo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CreateTaskA extends AppCompatActivity {

    private Button createTask;
    private EditText editTextTaskTitle;
    private EditText editTextTaskDescription;
    private EditText editTextDateStart;
    private EditText editTextDateEnd;
    private EditText editTextTaskCategory;
    private EditText editTextTaskStatus;
    private EditText editTextHaveAttachment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        editTextTaskTitle = findViewById(R.id.editTextTaskTitle);
        editTextTaskDescription = findViewById(R.id.editTextTaskDescription);
        editTextDateStart = findViewById(R.id.editTextDateStart);
        editTextDateEnd = findViewById(R.id.editTextDateEnd);
        editTextTaskCategory = findViewById(R.id.editTextTaskCategory);
        editTextTaskStatus = findViewById(R.id.editTextTaskStatus);
        editTextHaveAttachment = findViewById(R.id.editTextHaveAttachment);

        createTask = findViewById(R.id.createNewTask);
        createTask.setOnClickListener(view -> createTask());
    }

    private void createTask() {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }
}