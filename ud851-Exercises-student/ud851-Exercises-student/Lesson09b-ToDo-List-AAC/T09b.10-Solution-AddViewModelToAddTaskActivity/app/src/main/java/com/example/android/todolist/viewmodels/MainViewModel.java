package com.example.android.todolist.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.android.todolist.Repository;
import com.example.android.todolist.database.AppDatabase;
import com.example.android.todolist.database.TaskEntry;

import java.util.List;

public class MainViewModel extends ViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private final Repository mRepository;
    private LiveData<List<TaskEntry>> tasks;

    public MainViewModel(Repository repository) {
        mRepository = repository;
        tasks = mRepository.getTasks();
    }

    public LiveData<List<TaskEntry>> getTasks() {
        return tasks;
    }

    public void deleteTask(TaskEntry taskEntry){
        mRepository.deleteTask(taskEntry);
    }
}
