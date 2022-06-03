package com.example.listatodo.recyclerView;

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

import com.example.listatodo.MVVM.ViewModel;
import com.example.listatodo.MainActivity;
import com.example.listatodo.MoreDetailt.MoreDetailsAboutTask;
import com.example.listatodo.R;
import com.example.listatodo.taskDataModel.TaskData;

import java.util.List;

public class TaskRecyclerViewFragment extends Fragment implements ClickListener {

    private View view;
    private List<TaskData> taskData;
    private RecyclerViewAdapter adapter;

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
        ViewModel model = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        model.getTaskData().observe(getViewLifecycleOwner(), taskDataObserver);
        taskData = model.getTaskData().getValue();
    }

    private void startRecyclerView(List<TaskData> taskData) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewsTasks);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecyclerViewAdapter(taskData, this);
        recyclerView.setAdapter(adapter);
    }

    private void replaceFragment(Bundle bundle) {
        Fragment fragment = new MoreDetailsAboutTask();
        fragment.setArguments(bundle);
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.mainLayout, fragment)
                .setReorderingAllowed(true)
                .commit();
    }

    @Override
    public void onClickItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("title",taskData.get(position).getTaskTitle());
        bundle.putString("description",taskData.get(position).getTaskDescription());
        bundle.putString("description",taskData.get(position).getTaskDescription());
        bundle.putLong("start",taskData.get(position).getTaskStart());
        bundle.putLong("end",taskData.get(position).getTaskEnd());
        bundle.putSerializable("category",taskData.get(position).getTaskCategory());
        bundle.putSerializable("status",taskData.get(position).getTaskStatus());
        bundle.putBoolean("attachment",taskData.get(position).getHaveAttachment());
        replaceFragment(bundle);
    }

    @Override
    public void onLongClickItem(int position) {
        ((MainActivity) requireActivity()).getDb().deleteTask(taskData.get(position));
        taskData.remove(position);
        adapter.notifyItemRemoved(position);
    }
}