package com.nelsonjohansen.app.vinloop.vinloop;

import java.util.UUID;

/**
 * Created by NelsonJ on 3/29/2015.
 */
public class Winery {

    private UUID mID;
    private String name;
    private String location;

    public Winery(){
        mID = UUID.randomUUID();
        name = null;
        location = null;
    }

    public UUID getID() {
        return mID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {

        return location;
    }

    @Override
    public String toString(){
        return name;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
