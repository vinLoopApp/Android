package com.nelsonjohansen.app.vinloop.vinloop;

import java.util.UUID;

/**
 * Created by NelsonJ on 3/29/2015.
 */
public class Winery {

    private UUID mID;
    private String name;
    private String deal;
    private String dist;
    private String thumbnailUrl;
    private String origPrice;
    private String newPrice;
    private String priceRate;
    private String likes;
    private String latitude;
    private String longitude;

    //constructor
    public Winery(){
        mID = UUID.randomUUID();
    }

    public String getOrigPrice() {
        return origPrice;
    }

    public void setOrigPrice(String origPrice) {
        this.origPrice = origPrice;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    public String getPriceRate() {
        return priceRate;
    }

    public void setPriceRate(String priceRate) {
        this.priceRate = priceRate;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public UUID getmID() {
        return mID;
    }

    public void setmID(UUID mID) {
        this.mID = mID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getDeal() {
        return deal;
    }

    public void setDeal(String deal) {
        this.deal = deal;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public String toString(){
        return name;
    }

}
