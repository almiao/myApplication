package com.example.myapplication;

/**
 * date:2021/4/28
 * author:wsm(admin)
 * funcation:地址
 */
public class LocationListBean {
    private String name;
    private String distance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public LocationListBean(String name, String distance) {
        this.name = name;
        this.distance = distance;
    }
}
