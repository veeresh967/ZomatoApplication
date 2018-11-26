package com.techpalle.b37_zomatoapplication;

/**
 * Created by skillgun on 18/09/2017.
 */

public class Restaurant {
    private String restaurant_name,
                    restaurant_cuisines,
                    restaurant_address,
                    restaurant_image,
                    restaurant_latitude,
                    restaurant_longitude,
                    restaurant_rating;

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getRestaurant_cuisines() {
        return restaurant_cuisines;
    }

    public void setRestaurant_cuisines(String restaurant_cuisines) {
        this.restaurant_cuisines = restaurant_cuisines;
    }

    public String getRestaurant_address() {
        return restaurant_address;
    }

    public void setRestaurant_address(String restaurant_address) {
        this.restaurant_address = restaurant_address;
    }

    public String getRestaurant_image() {
        return restaurant_image;
    }

    public void setRestaurant_image(String restaurant_image) {
        this.restaurant_image = restaurant_image;
    }

    public String getRestaurant_latitude() {
        return restaurant_latitude;
    }

    public void setRestaurant_latitude(String restaurant_latitude) {
        this.restaurant_latitude = restaurant_latitude;
    }

    public String getRestaurant_longitude() {
        return restaurant_longitude;
    }

    public void setRestaurant_longitude(String restaurant_longitude) {
        this.restaurant_longitude = restaurant_longitude;
    }

    public String getRestaurant_rating() {
        return restaurant_rating;
    }

    public void setRestaurant_rating(String restaurant_rating) {
        this.restaurant_rating = restaurant_rating;
    }

    public Restaurant(String restaurant_name, String restaurant_cuisines, String restaurant_address, String restaurant_image, String restaurant_latitude, String restaurant_longitude, String restaurant_rating) {
        this.restaurant_name = restaurant_name;
        this.restaurant_cuisines = restaurant_cuisines;
        this.restaurant_address = restaurant_address;
        this.restaurant_image = restaurant_image;
        this.restaurant_latitude = restaurant_latitude;
        this.restaurant_longitude = restaurant_longitude;
        this.restaurant_rating = restaurant_rating;
    }
}
