package com.example.listatodo.MVVM;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.listatodo.taskDataModel.TaskData;

import java.util.List;

public class ViewModel extends androidx.lifecycle.ViewModel {
    private final MutableLiveData<List<TaskData>> taskData = new MutableLiveData<>();

    public void setTaskData(List<TaskData> weatherData) {
        this.taskData.setValue(weatherData);
    }

    public LiveData<List<TaskData>> getTaskData(){
        return this.taskData;
    }
}

