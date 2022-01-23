package com.example.myapplication.bean.vo;


import com.example.myapplication.bean.model.BaseOutput;

import java.sql.Timestamp;

public class CourtDanceSpotVO extends BaseOutput {

    private Long id;
    /*
    跳舞点名称
     */
    private String name ="";

    /*
    舞点基本信息描述
     */
    private String detailDesc = "";

    /*
    logo图片
    */
    private String logoImgId ="";

    /*
    注意事项
     */
    private String attentionDesc = "";

    private String createByUserName = "";

    private String danceTypes ="";

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    private String createTime;

    private String updateTime;


    private int teamCount = 0;

    private int memberCount = 0 ;

    private String distance = "";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public String getLogoImgId() {
        return logoImgId;
    }

    public void setLogoImgId(String logoImgId) {
        this.logoImgId = logoImgId;
    }

    public String getAttentionDesc() {
        return attentionDesc;
    }

    public void setAttentionDesc(String attentionDesc) {
        this.attentionDesc = attentionDesc;
    }

    public String getCreateByUserName() {
        return createByUserName;
    }

    public void setCreateByUserName(String createByUserName) {
        this.createByUserName = createByUserName;
    }

    public String getDanceTypes() {
        return danceTypes;
    }

    public void setDanceTypes(String danceTypes) {
        this.danceTypes = danceTypes;
    }


    public int getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(int teamCount) {
        this.teamCount = teamCount;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
