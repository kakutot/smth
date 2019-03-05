package com.example.restaurantsapp.domain.repository;

import com.example.restaurantsapp.domain.entity.Collection;

import io.reactivex.Observable;

public interface CollectionRepository {

    Observable<Collection> getCollectionsByCityId(int cityId);

    Observable<Collection> getCollectionsByCityName(String cityId);

}
