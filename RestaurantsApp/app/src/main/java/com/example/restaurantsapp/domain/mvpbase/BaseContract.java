package com.example.restaurantsapp.domain.mvpbase;

public interface BaseContract {

    interface BaseView<P extends BasePresenter> {

        void onError();

        boolean isActive();

        void setPresenter(P presenter);

        P getPresenter();
    }

    interface BasePresenter<V extends BaseView> {

        void attachView(V v);

        void detachView();

        V getView();

        boolean isViewAttached();

        void onError(Throwable throwable);

        Navigator getNavigator();
    }
}