package com.example.listatodo.MoreDetailt;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.listatodo.MainActivity;
import com.example.listatodo.R;
import com.example.listatodo.recyclerView.TaskRecyclerViewFragment;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class MoreDetailsAboutTask extends Fragment {

    private TextView textViewTaskTitle;
    private TextView textViewTaskDescription;
    private TextView textViewDateStart;
    private TextView textViewDateEnd;
    private TextView textViewTaskCategory;
    private TextView textViewTaskStatus;
    private TextView textViewHaveAttachment;
    private Button buttonBack;
    private Button buttonModifyTask;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_more_details_about_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) requireActivity()).getFloatingActionButton().hide();

        bundle = this.getArguments();

        textViewTaskTitle = view.findViewById(R.id.textViewTaskTitle);
        textViewTaskTitle.setText(bundle.getString("title"));

        textViewTaskDescription = view.findViewById(R.id.textViewTaskDescription);
        textViewTaskDescription.setText(bundle.getString("description"));

        textViewDateStart = view.findViewById(R.id.textViewDateStart);
        textViewDateStart.setText(convertTime(bundle.getLong("start", 0L)));

        textViewDateEnd = view.findViewById(R.id.textViewDateEnd);
        textViewDateEnd.setText(convertTime(bundle.getLong("end", 0L)));

        textViewTaskCategory = view.findViewById(R.id.textViewTaskCategory);
        textViewTaskCategory.setText((bundle.getSerializable("category")).toString());

        textViewTaskStatus = view.findViewById(R.id.textViewTaskStatus);
        textViewTaskStatus.setText((bundle.getSerializable("status")).toString());

        textViewHaveAttachment = view.findViewById(R.id.textViewHaveAttachment);
        textViewHaveAttachment.setText(String.valueOf(bundle.getBoolean("attachment", false)));

        buttonBack = view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> switchFragment(new TaskRecyclerViewFragment()));

        buttonModifyTask = view.findViewById(R.id.buttonModifyTask);
        buttonModifyTask.setOnClickListener(v -> switchFragment(new ModifyTask()));
    }

    private void switchFragment(Fragment fragment) {
        fragment.setArguments(bundle);
        ((MainActivity) requireActivity()).getFloatingActionButton().show();
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.mainLayout, fragment)
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
}