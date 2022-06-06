package com.example.listatodo.MoreDetailt;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.listatodo.MainActivity;
import com.example.listatodo.R;
import com.example.listatodo.recyclerView.TaskRecyclerViewFragment;
import com.example.listatodo.taskDataModel.TaskCategory;
import com.example.listatodo.taskDataModel.TaskData;
import com.example.listatodo.taskDataModel.TaskStatus;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class ModifyTask extends Fragment {

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
    private List<TaskData> taskDataList;
    private String oldTitle;
    private Integer id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_modify_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) requireActivity()).getFloatingActionButton().hide();

        Bundle bundle = this.getArguments();

        taskDataList = ((MainActivity) requireActivity()).getDb().getAllTasks();
        oldTitle=bundle.getString("title");

        editTextTaskTitle = view.findViewById(R.id.editTextTaskTitle);
        editTextTaskTitle.setText(oldTitle);

        editTextTaskDescription = view.findViewById(R.id.editTextTaskDescription);
        editTextTaskDescription.setText(bundle.getString("description"));

        editTextDateStart = view.findViewById(R.id.editTextDateStart);
        editTextDateStart.setText(convertTime(bundle.getLong("start", 0L)));

        editTextDateEnd = view.findViewById(R.id.editTextDateEnd);
        editTextDateEnd.setText(convertTime(bundle.getLong("end", 0L)));

        editTextHaveAttachment = view.findViewById(R.id.editTextHaveAttachment);
        editTextHaveAttachment.setText((bundle.getSerializable("category")).toString());

        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        spinnerNotification = view.findViewById(R.id.spinnerNotification);
        cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(v -> switchFragment());
        createTask = view.findViewById(R.id.createNewTask);
        createTask.setOnClickListener(v -> createTask());
    }

    public byte[] loadImage(){
        String stringFilePath = Environment.getExternalStorageDirectory().getPath()+"/Download/"+editTextHaveAttachment.getText().toString()+".jpeg";
        Bitmap bitmap = BitmapFactory.decodeFile(stringFilePath);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    private void createTask() {
        if (validateData()) {

            taskDataList.forEach(taskData -> {
                if(taskData.getTaskTitle().equals(oldTitle)) {
                    id = taskData.getId();
                }
            });

            TaskData taskData = new TaskData(id
                    , taskTitle
                    , taskDescription
                    , startTaskDate
                    , endTaskDate
                    , TaskStatus.ACTIVE
                    , spinnerNotification.getSelectedItem().toString().equals("ON")
                    , TaskCategory.valueOf(spinnerCategory.getSelectedItem().toString())
                    , loadImage());

            ((MainActivity) requireActivity()).getDb().updateTask(taskData);

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

    private String convertTime(Long time) {
        final DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return Instant.ofEpochMilli(time)
                .atZone(ZoneId.systemDefault())
                .format(formatter);
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
            editTextDateStart.setError("niewłaściwy format daty");
            return false;
        }
        endTaskDate = convertStringDateToLong(editTextDateEnd.getText().toString());
        if (endTaskDate == 0) {
            editTextDateEnd.setError("niewłaściwy format daty");
            return false;
        }
        if (startTaskDate > endTaskDate) {
            editTextDateEnd.setError("data zakonczenia zadanie nie moze byc wczesniejsza niz jego rozpoczecia");
            return false;
        }

        return true;
    }
}