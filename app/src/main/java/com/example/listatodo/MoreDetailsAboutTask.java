package com.example.listatodo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MoreDetailsAboutTask extends AppCompatActivity {

    private TextView textViewTaskTitle;
    private TextView textViewTaskDescription;
    private TextView textViewDateStart;
    private TextView textViewDateEnd;
    private TextView textViewTaskCategory;
    private TextView textViewTaskStatus;
    private TextView textViewHaveAttachment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_details_about_task);

        textViewTaskTitle = findViewById(R.id.textViewTaskTitle);
        textViewTaskTitle.setText(getIntent().getStringExtra("title"));

        textViewTaskDescription = findViewById(R.id.textViewTaskDescription);
        textViewTaskDescription.setText(getIntent().getStringExtra("description"));

        textViewDateStart = findViewById(R.id.textViewDateStart);
        textViewDateStart.setText(String.valueOf(getIntent().getLongExtra("start",0L)));

        textViewDateEnd = findViewById(R.id.textViewDateEnd);
        textViewDateEnd.setText(String.valueOf(getIntent().getLongExtra("end",0L)));

        textViewTaskCategory = findViewById(R.id.textViewTaskCategory);
        textViewTaskCategory.setText((getIntent().getSerializableExtra("category")).toString());

        textViewTaskStatus = findViewById(R.id.textViewTaskStatus);
        textViewTaskStatus.setText((getIntent().getSerializableExtra("status")).toString());

        textViewHaveAttachment = findViewById(R.id.textViewHaveAttachment);
        textViewHaveAttachment.setText(String.valueOf(getIntent().getBooleanExtra("attachment",false)));
    }
}