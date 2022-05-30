package com.example.listatodo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class CreateTask extends Fragment {

    private Button createTask;
    private EditText editTextTaskTitle;
    private EditText editTextTaskDescription;
    private EditText editTextDateStart;
    private EditText editTextDateEnd;
    private EditText editTextTaskCategory;
    private EditText editTextTaskStatus;
    private EditText editTextHaveAttachment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextTaskTitle = view.findViewById(R.id.editTextTaskTitle);
        editTextTaskDescription = view.findViewById(R.id.editTextTaskDescription);
        editTextDateStart = view.findViewById(R.id.editTextDateStart);
        editTextDateEnd = view.findViewById(R.id.editTextDateEnd);
        editTextTaskCategory = view.findViewById(R.id.editTextTaskCategory);
        editTextTaskStatus = view.findViewById(R.id.editTextTaskStatus);
        editTextHaveAttachment = view.findViewById(R.id.editTextHaveAttachment);

        createTask = view.findViewById(R.id.createNewTask);
        createTask.setOnClickListener(v -> createTask());
    }

    private void createTask() {
      //  Intent intent = new Intent(this, MainActivity.class);
//
      //  startActivity(intent);
    }
}