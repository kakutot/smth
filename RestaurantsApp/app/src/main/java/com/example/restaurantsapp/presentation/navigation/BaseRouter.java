package com.example.restaurantsapp.presentation.navigation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseRouter {

    private AppCompatActivity context;
    private FragmentManager fragmentManager;

    protected BaseRouter(AppCompatActivity context) {
        this.context = context;
        this.fragmentManager = context.getSupportFragmentManager();
    }

    protected void addFragmentToActivity(@NonNull Fragment fragment, @NonNull int frameId) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction
                .add(frameId, fragment)
                .commit();
    }

    protected void replaceFragmentInActivity(@NonNull Fragment fragment, @NonNull int frameId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction
                .replace(frameId, fragment)
                .commit();
    }

    protected void navigateToActivity(Intent activityIntent){
        context.startActivity(activityIntent);
    }

    protected AppCompatActivity getContext() {
        return context;
    }

    protected void setContext(AppCompatActivity context) {
        this.context = context;
    }

    protected FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    protected void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
}
