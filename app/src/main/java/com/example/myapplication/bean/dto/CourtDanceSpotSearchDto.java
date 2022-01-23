package com.example.myapplication.bean.dto;


import com.example.myapplication.bean.model.BaseInput;

public class CourtDanceSpotSearchDto extends BaseInput {

    /*
    跳舞点名称
     */
    private String name;


    private String danceType;

    /*
    经度
    */
    private Double longitude;

    /*
    纬度
     */
    private Double latitude;

    /*
    纬度
     */
    private Double radius;


    private int pageNo;

    private int pageSize = 20;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDanceType() {
        return danceType;
    }

    public void setDanceType(String danceType) {
        this.danceType = danceType;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
