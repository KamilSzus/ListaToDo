package com.example.listatodo.recyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listatodo.R;
import com.example.listatodo.taskDataModel.TaskData;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final List<TaskData> taskDataList;
    private final ClickListener clickListener;

    public RecyclerViewAdapter(List<TaskData> taskDataList, ClickListener clickListener) {
        this.taskDataList = taskDataList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task, parent, false),clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.startTask.setText(convertTime(taskDataList.get(position).getTaskStart()));
        holder.endTask.setText(convertTime(taskDataList.get(position).getTaskEnd()));
        holder.titleOfTask.setText(taskDataList.get(position).getTaskTitle());
    }
    private String convertTime(Long time) {
        final DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd");

        final long unixTime = time;
        return Instant.ofEpochSecond(unixTime)
                .atZone(ZoneId.systemDefault())
                .format(formatter);
    }
    @Override
    public int getItemCount() {
        return taskDataList.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.task;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleOfTask;
        TextView startTask;
        TextView endTask;
        ImageButton finishTask;

        ViewHolder(View itemView,ClickListener clickListener) {
            super(itemView);
            titleOfTask = itemView.findViewById(R.id.taskToBeDone);
            startTask = itemView.findViewById(R.id.taskStartDate);
            endTask = itemView.findViewById(R.id.taskEndDate);
            finishTask = itemView.findViewById(R.id.endTask);

            itemView.setOnClickListener(v -> clickListener.onClickItem(getAdapterPosition()));

            itemView.setOnLongClickListener(view -> {
                clickListener.onLongClickItem(getAdapterPosition());
                return true;
            });

        }
    }

}