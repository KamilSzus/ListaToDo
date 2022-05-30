package com.example.listatodo.recyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listatodo.MainActivity;
import com.example.listatodo.MoreDetailsAboutTask;
import com.example.listatodo.R;
import com.example.listatodo.ViewModel;
import com.example.listatodo.taskDataModel.TaskData;

import java.util.List;

public class TaskRecyclerViewFragment extends Fragment implements ClickListener {

    private View view;
    private List<TaskData> taskData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.view = view;
        final Observer<List<TaskData>> taskDataObserver = this::startRecyclerView;
        ViewModel model = new ViewModelProvider((MainActivity)requireActivity()).get(ViewModel.class);
        model.getTaskData().observe(getViewLifecycleOwner(),taskDataObserver);
        taskData = model.getTaskData().getValue();
    }

    private void startRecyclerView(List<TaskData> taskData) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewsTasks);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new RecyclerViewAdapter(taskData, this));
    }


    @Override
    public void onClickItem(int position) {
        System.out.println(position);
        Intent intent = new Intent(getActivity(), MoreDetailsAboutTask.class);

        intent.putExtra("title",taskData.get(position).getTaskTitle());
        intent.putExtra("description",taskData.get(position).getTaskDescription());
        intent.putExtra("start",taskData.get(position).getTaskStart());
        intent.putExtra("end",taskData.get(position).getTaskEnd());
        intent.putExtra("category",taskData.get(position).getTaskCategory());
        intent.putExtra("status",taskData.get(position).getTaskStatus());
        intent.putExtra("attachment",taskData.get(position).getHaveAttachment());

        startActivity(intent);
    }
}