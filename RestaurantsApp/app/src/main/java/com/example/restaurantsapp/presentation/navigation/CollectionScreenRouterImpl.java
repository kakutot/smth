package com.example.restaurantsapp.presentation.navigation;

import android.support.v7.app.AppCompatActivity;

import com.example.restaurantsapp.domain.navigation.CollectionsRouter;

public class CollectionScreenRouterImpl extends BaseRouter
        implements CollectionsRouter {

    protected CollectionScreenRouterImpl(AppCompatActivity context) {
        super(context);
    }

    @Override
    public void routeToCollectionsScreen() {

    }

    @Override
    public void routeToRestaurantsScreen(int collectionId) {

    }

    @Override
    public void routeToRestaurantsScreen(String collectionId) {

    }

    @Override
    public void navigateBack() {

    }
}
