package com.example.restaurantsapp.domain.navigation;

public interface CollectionsRouter extends Router {

    void routeToCollectionsScreen();

    void routeToRestaurantsScreen(int collectionId);

    void routeToRestaurantsScreen(String collectionId);
}

