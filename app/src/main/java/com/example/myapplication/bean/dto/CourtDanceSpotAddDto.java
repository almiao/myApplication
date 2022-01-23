package com.example.myapplication.bean.dto;


import com.example.myapplication.bean.model.BaseInput;

public class CourtDanceSpotAddDto extends BaseInput {



    /*
    跳舞点名称
     */
    private String name;

    /*
    舞点基本信息描述
     */
    private String detailDesc;

    /*
    注意事项
     */
    private String attentionDesc;

    /*
    logo图片
    */
    private String logoImgId;

    private String danceTypes;

    /*
    经度
    */
    private Double longitude;

    /*
    纬度
     */
    private Double latitude;



}
