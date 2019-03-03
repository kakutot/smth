package com.example.android.todolist;

import android.arch.persistence.room.Database;
import android.content.Context;

import com.example.android.todolist.database.AppDatabase;
import com.example.android.todolist.viewmodels.AddTaskViewModelFactory;
import com.example.android.todolist.viewmodels.MainViewModel;
import com.example.android.todolist.viewmodels.MainViewModelFactory;

public class InjectorUtils {

    public static Repository provideRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();

        return Repository.getInstance(database.taskDao(),executors);
    }

    public static MainViewModelFactory provideMainViewModelFactory(Context context){
        return new MainViewModelFactory (provideRepository(context));
    }

    public static AddTaskViewModelFactory provideAddTaskViewModelFactory(Context context,int id){
        return new AddTaskViewModelFactory (provideRepository(context),id);
    }
}
