package com.example.listatodo.createTask;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.listatodo.MainActivity;
import com.example.listatodo.R;
import com.example.listatodo.recyclerView.TaskRecyclerViewFragment;
import com.example.listatodo.taskDataModel.TaskCategory;
import com.example.listatodo.taskDataModel.TaskData;
import com.example.listatodo.taskDataModel.TaskStatus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateTask extends Fragment {

    private Button createTask;
    private Button cancel;
    private EditText editTextTaskTitle;
    private EditText editTextTaskDescription;
    private EditText editTextDateStart;
    private EditText editTextDateEnd;
    private EditText editTextHaveAttachment;
    private Spinner spinnerCategory;
    private Spinner spinnerNotification;

    private Long startTaskDate;
    private Long endTaskDate;
    private String taskTitle;
    private String taskDescription;

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
        cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(v -> switchFragment());
        createTask = view.findViewById(R.id.createNewTask);
        createTask.setOnClickListener(v -> createTask());
    }

    private Long convertStringDateToLong(String date) {
        long startDate = 0;
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date newDate = sdf.parse(date);

            startDate = newDate.getTime();
            System.out.println(startDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate;
    }

    public byte[] loadImage(){
        if (editTextHaveAttachment.getText().toString().isEmpty()){
            return null;
        }
        if(editTextHaveAttachment.getText().toString().contains(".jpeg") || editTextHaveAttachment.getText().toString().contains(".png")){
            String stringFilePath = Environment.getExternalStorageDirectory().getPath()+"/Download/"+editTextHaveAttachment.getText().toString();
            Bitmap bitmap = BitmapFactory.decodeFile(stringFilePath);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
        try {
            return Files.readAllBytes(Paths.get(Environment.getExternalStorageDirectory().getPath() + "/Download/" + editTextHaveAttachment.getText().toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void createTask() {
        if (validateData()) {
            TaskData taskData = new TaskData(taskTitle
                    , taskDescription
                    , startTaskDate
                    , endTaskDate
                    , TaskStatus.ACTIVE
                    , spinnerNotification.getSelectedItem().toString().equals("ON")
                    , TaskCategory.valueOf(spinnerCategory.getSelectedItem().toString())
                    , loadImage());

            ((MainActivity) requireActivity()).getDb().addTask(taskData);
            ((MainActivity) requireActivity()).loadTasks();

            if (spinnerNotification.getSelectedItem().toString().equals("ON")) {
                ((MainActivity) requireActivity()).setAlarm(taskData);
            }
            switchFragment();
        }
    }

    private void switchFragment() {
        ((MainActivity) requireActivity()).getFloatingActionButton().show();
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.mainLayout, new TaskRecyclerViewFragment())
                .setReorderingAllowed(true)
                .commit();
    }

    private boolean validateData() {
        taskTitle = editTextTaskTitle.getText().toString();
        if (taskTitle.isEmpty()) {
            editTextTaskTitle.setError("Pole nie moze byc puste");
            return false;
        }
        taskDescription = editTextTaskDescription.getText().toString();
        if (taskDescription.isEmpty()) {
            editTextTaskDescription.setError("Pole nie moze byc puste");
            return false;
        }
        startTaskDate = convertStringDateToLong(editTextDateStart.getText().toString());
        if (startTaskDate == 0) {
            editTextDateStart.setError("niew??a??ciwy format daty");
            return false;
        }
        endTaskDate = convertStringDateToLong(editTextDateEnd.getText().toString());
        if (endTaskDate == 0) {
            editTextDateEnd.setError("niew??a??ciwy format daty");
            return false;
        }
        if (startTaskDate > endTaskDate) {
            editTextDateEnd.setError("data zakonczenia zadanie nie moze byc wczesniejsza niz jego rozpoczecia");
            return false;
        }

        if (!editTextHaveAttachment.getText().toString().isEmpty()){
            try {
                byte[] temp = Files.readAllBytes(Paths.get(Environment.getExternalStorageDirectory().getPath() + "/Download/" + editTextHaveAttachment.getText().toString()));
            } catch (IOException e) {
                editTextHaveAttachment.setError("Plik o podanej nazwie nie istnieje");
                return false;
            }
        }

        return true;
    }
}