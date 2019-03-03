package com.example.android.todolist;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.util.Log;

import com.example.android.todolist.database.TaskDao;
import com.example.android.todolist.database.TaskEntry;

import java.util.List;

public class Repository {
    private static final String LOG_TAG = Repository.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static Repository rInstance;


    private final TaskDao mTaskDao;
    private final AppExecutors mExecutors;

    private Repository(TaskDao taskDao,AppExecutors appExecutors){
        mTaskDao =  taskDao;
        mExecutors = appExecutors;
    }

    public synchronized static Repository getInstance(TaskDao taskDao, AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the repository");
        if (rInstance == null) {
            synchronized (LOCK) {
                rInstance = new Repository(taskDao,executors);
                Log.d(LOG_TAG, "Made new repository");
            }
        }
        return rInstance;
    }

    public LiveData<List<TaskEntry>> getTasks(){
        return mTaskDao.loadAllTasks();
    }

    public LiveData<TaskEntry> getTask(int id){
        return mTaskDao.loadTaskById(id);
    }

    public void deleteTask(TaskEntry taskEntry ){
        mExecutors.diskIO().execute(() ->
                {
                    mTaskDao.deleteTask(taskEntry);
                }
        );

    }

    public void insertTask(TaskEntry taskEntry){
        mExecutors.diskIO().execute(() ->
                {
                    mTaskDao.insertTask(taskEntry);
                }
        );
    }

    public void updateTask(TaskEntry taskEntry){
        mExecutors.diskIO().execute(() ->
                {
                    mTaskDao.updateTask(taskEntry);
                }
        );
    }
}