package com.example.restaurantsapp.domain.entity;

public final class Collection implements Entity {

    private final int id;

    private final String title;

    private final String imageUrl;

    private final int resCount;

    public Collection(int id, String title, String imageUrl, int resCount) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.resCount = resCount;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getResCount() {
        return resCount;
    }
}
