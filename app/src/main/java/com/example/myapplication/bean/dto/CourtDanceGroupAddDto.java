package com.example.myapplication.bean.dto;


import com.example.myapplication.bean.model.BaseInput;

public class CourtDanceGroupAddDto extends BaseInput {


    /*
    跳舞组名称
     */
    private String name;

    /*
    跳舞点名称
     */
    private Long courtDanceSpotId;


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



}
