package com.example.myapplication.bean.dto;


import com.example.myapplication.bean.model.BaseInput;

public class CourtDanceGroupSearchDto extends BaseInput {

    /*
    跳舞点名称
     */
    private Long courtDanceSpotId;

    /*
    舞点基本信息描述
     */
    private String detailDesc;


    private String danceType;

    private String name;


    private int pageNum = 0;

    private int pageSize = 99;


    public Long getCourtDanceSpotId() {
        return courtDanceSpotId;
    }

    public void setCourtDanceSpotId(Long courtDanceSpotId) {
        this.courtDanceSpotId = courtDanceSpotId;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public String getDanceType() {
        return danceType;
    }

    public void setDanceType(String danceType) {
        this.danceType = danceType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
