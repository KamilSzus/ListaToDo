package com.example.listatodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    List<String> tempList = Arrays.asList("Buenos Aires", "Córdoba", "La Plata");
    private final ClickListener clickListener;

    public RecyclerViewAdapter(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task, parent, false),clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.startTask.setText(tempList.get(position));
        holder.endTask.setText(tempList.get(position));
        holder.titleOfTask.setText(tempList.get(position));
    }

    @Override
    public int getItemCount() {
        return tempList.size();
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

        }
    }

}