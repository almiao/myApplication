package com.example.myapplication.bean.vo;


import com.example.myapplication.bean.model.BaseEntity;

public class CourtDanceSpotSimpleVO extends BaseEntity {

    //舞点名称
    private String danceSpotName;

    private int logoImageId;

    //舞团数量描述
    private String teamDesc;

    //舞团数量描述
    private String memberDesc;

    //小区内、小区外
    private String positionDesc;

    //与用户当前距离描述
    private String distanceDesc;


    public CourtDanceSpotSimpleVO(String danceSpotName) {
        this.danceSpotName = danceSpotName;
    }

    public String getMemberDesc() {
        return memberDesc;
    }

    public void setMemberDesc(String memberDesc) {
        this.memberDesc = memberDesc;
    }

    public String getDanceSpotName() {
        return danceSpotName;
    }

    public void setDanceSpotName(String danceSpotName) {
        this.danceSpotName = danceSpotName;
    }

    public int getLogoImageId() {
        return logoImageId;
    }

    public void setLogoImageId(int logoImageId) {
        this.logoImageId = logoImageId;
    }

    public String getTeamDesc() {
        return teamDesc;
    }

    public void setTeamDesc(String teamDesc) {
        this.teamDesc = teamDesc;
    }

    public String getDistanceDesc() {
        return distanceDesc;
    }

    public void setDistanceDesc(String distanceDesc) {
        this.distanceDesc = distanceDesc;
    }


    public String getPositionDesc() {
        return positionDesc;
    }

    public void setPositionDesc(String positionDesc) {
        this.positionDesc = positionDesc;
    }
}
