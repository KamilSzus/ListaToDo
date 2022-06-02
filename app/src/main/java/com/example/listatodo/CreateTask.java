package com.example.listatodo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.listatodo.recyclerView.TaskRecyclerViewFragment;
import com.example.listatodo.taskDataModel.TaskCategory;
import com.example.listatodo.taskDataModel.TaskData;
import com.example.listatodo.taskDataModel.TaskStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateTask extends Fragment {

    private Button createTask;
    private EditText editTextTaskTitle;
    private EditText editTextTaskDescription;
    private EditText editTextDateStart;
    private EditText editTextDateEnd;
    private EditText editTextHaveAttachment;
    private Spinner spinnerCategory;
    private Spinner spinnerNotification;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) requireActivity()).getFloatingActionButton().hide();

        editTextTaskTitle = view.findViewById(R.id.editTextTaskTitle);
        editTextTaskDescription = view.findViewById(R.id.editTextTaskDescription);
        editTextDateStart = view.findViewById(R.id.editTextDateStart);
        editTextDateEnd = view.findViewById(R.id.editTextDateEnd);
        editTextHaveAttachment = view.findViewById(R.id.editTextHaveAttachment);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        spinnerNotification = view.findViewById(R.id.spinnerNotification);

        createTask = view.findViewById(R.id.createNewTask);
        createTask.setOnClickListener(v -> createTask());
    }

    private Long convertStringDateToLong(String date) {
        long startDate = 0;
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = sdf.parse(date);

            startDate = newDate.getTime();
            System.out.println(startDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate;
    }

    private void createTask() {
        ((MainActivity) requireActivity()).getDb().addTask(new TaskData(editTextTaskTitle.getText().toString()
                , editTextTaskDescription.getText().toString()
                , convertStringDateToLong(editTextDateStart.getText().toString())
                , convertStringDateToLong(editTextDateEnd.getText().toString())
                , TaskStatus.ACTIVE
                , false
                , TaskCategory.valueOf(spinnerCategory.getSelectedItem().toString())
                , Boolean.valueOf(editTextHaveAttachment.getText().toString())));

        ((MainActivity) requireActivity()).loadTasks();

        ((MainActivity) requireActivity()).getFloatingActionButton().show();
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.mainLayout, new TaskRecyclerViewFragment())
                .setReorderingAllowed(true)
                .commit();
    }
}