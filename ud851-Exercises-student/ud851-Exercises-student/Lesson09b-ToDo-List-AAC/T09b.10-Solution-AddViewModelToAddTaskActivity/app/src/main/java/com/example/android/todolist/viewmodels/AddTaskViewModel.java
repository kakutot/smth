package com.example.android.todolist.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.todolist.Repository;
import com.example.android.todolist.database.AppDatabase;
import com.example.android.todolist.database.TaskEntry;

// COMPLETED (5) Make this class extend ViewModel
public class AddTaskViewModel extends ViewModel {

    // COMPLETED (6) Add a task member variable for the TaskEntry object wrapped in a LiveData
    private final LiveData<TaskEntry> task;
    private final Repository mRepository;

    // COMPLETED (8) Create a constructor where you call loadTaskById of the taskDao to initialize the tasks variable
    // Note: The constructor should receive the database and the taskId
    public AddTaskViewModel(Repository repository, int taskId) {
        mRepository = repository;
        task = mRepository.getTask(taskId);
    }

    public Repository getRepository() {
        return mRepository;
    }
    // COMPLETED (7) Create a getter for the task variable
    public LiveData<TaskEntry> getTask() {
        return task;
    }




}
