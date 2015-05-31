package com.nelsonjohansen.app.vinloop.vinloop;

import java.util.ArrayList;

/**
 * Created by NelsonJ on 4/26/2015.
 */
public class filterHelper {

    private boolean priceButton1;
    private boolean priceButton2;
    private boolean priceButton3;
    private boolean priceButton4;

    private String distance;
    private String sortBy;
    private ArrayList<String> varietal;

    private boolean walkIn;
    private boolean byAppt;

    public filterHelper(){
        priceButton1 = false;
        priceButton2 = false;
        priceButton3 = false;
        priceButton4 = false;

        distance = null;
        sortBy = null;
        varietal = new ArrayList<String>();

        walkIn = false;
        byAppt = false;
    }

    public boolean isPriceButton1() {
        return priceButton1;
    }

    public void setPriceButton1(boolean priceButton1) {
        this.priceButton1 = priceButton1;
    }

    public boolean isPriceButton2() {
        return priceButton2;
    }

    public void setPriceButton2(boolean priceButton2) {
        this.priceButton2 = priceButton2;
    }

    public boolean isPriceButton3() {
        return priceButton3;
    }

    public void setPriceButton3(boolean priceButton3) {
        this.priceButton3 = priceButton3;
    }

    public boolean isPriceButton4() {
        return priceButton4;
    }

    public void setPriceButton4(boolean priceButton4) {
        this.priceButton4 = priceButton4;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public boolean isWalkIn() {
        return walkIn;
    }

    public void setWalkIn(boolean walkIn) {
        this.walkIn = walkIn;
    }

    public boolean isByAppt() {
        return byAppt;
    }

    public void setByAppt(boolean byAppt) {
        this.byAppt = byAppt;
    }

    public ArrayList<String> getVarietal() {
        return varietal;
    }

    public void setVarietal(ArrayList<String> varietal) {
        this.varietal = varietal;
    }
}
