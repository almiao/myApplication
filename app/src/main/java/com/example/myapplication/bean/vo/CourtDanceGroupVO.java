package com.example.myapplication.bean.vo;


import com.example.myapplication.bean.model.BaseOutput;

public class CourtDanceGroupVO extends BaseOutput {
    private Long id;

    //舞团名称 百望山舞团
    private String name;

    private String logoImageId;

    private String danceGroupName;

    //成员数量描述 30位成员
    private String memberDesc;

    private String detailDesc;

    //今日打卡描述 今日已有3位伙伴打卡加入
    private String todayEnrollDesc;

    //舞种描述 舞种类型
    private String danceType;

    //与所在舞点
    private String danceSpotName;

    //活跃度排名
    private String rankDesc;

    //得分
    private String score;

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

    public String getLogoImageId() {
        return logoImageId;
    }

    public void setLogoImageId(String logoImageId) {
        this.logoImageId = logoImageId;
    }

    public String getDanceGroupName() {
        return danceGroupName;
    }

    public void setDanceGroupName(String danceGroupName) {
        this.danceGroupName = danceGroupName;
    }

    public String getMemberDesc() {
        return memberDesc;
    }

    public void setMemberDesc(String memberDesc) {
        this.memberDesc = memberDesc;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public String getTodayEnrollDesc() {
        return todayEnrollDesc;
    }

    public void setTodayEnrollDesc(String todayEnrollDesc) {
        this.todayEnrollDesc = todayEnrollDesc;
    }

    public String getDanceType() {
        return danceType;
    }

    public void setDanceType(String danceType) {
        this.danceType = danceType;
    }

    public String getDanceSpotName() {
        return danceSpotName;
    }

    public void setDanceSpotName(String danceSpotName) {
        this.danceSpotName = danceSpotName;
    }

    public String getRankDesc() {
        return rankDesc;
    }

    public void setRankDesc(String rankDesc) {
        this.rankDesc = rankDesc;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
