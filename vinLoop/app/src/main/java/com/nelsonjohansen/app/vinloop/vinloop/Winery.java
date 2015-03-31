package com.nelsonjohansen.app.vinloop.vinloop;

import java.util.UUID;

/**
 * Created by NelsonJ on 3/29/2015.
 */
public class Winery {

    private UUID mID;
    private String name;
    private int location;

    public Winery(){
        mID = UUID.randomUUID();
        name = "First Winery";
        location = 3;
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

    public int getLocation() {

        return location;
    }

    @Override
    public String toString(){
        return name;
    }

    public void setLocation(int location) {
        this.location = location;
    }
}
