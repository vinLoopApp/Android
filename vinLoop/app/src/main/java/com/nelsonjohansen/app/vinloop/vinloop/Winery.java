package com.nelsonjohansen.app.vinloop.vinloop;

import java.util.UUID;

/**
 * Created by NelsonJ on 3/29/2015.
 */
public class Winery {

    private UUID mID;
    private String name;
    private String descr;
    private String dist;
    private String thumbnailUrl;

    public Winery(){
        mID = UUID.randomUUID();
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

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
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
