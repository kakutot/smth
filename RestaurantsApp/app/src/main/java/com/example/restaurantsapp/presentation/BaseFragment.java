package com.example.restaurantsapp.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.restaurantsapp.R;
import com.example.restaurantsapp.domain.mvpbase.BaseContract;

public abstract class BaseFragment<P extends BaseContract.BasePresenter>
        extends Fragment implements BaseContract.BaseView<P>{

    private P presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectDependencies();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getPresenter() != null){
            getPresenter().attachView(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(getPresenter() != null){
            getPresenter().detachView();
        }
    }

    @Override
    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    public P getPresenter() {
        return presenter;
    }

    @Override
    public void onError() {
        showToastMessage(getResources().getString(R.string.error_message_default));
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    protected abstract void injectDependencies();

    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    protected void showToastMessage(int message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}