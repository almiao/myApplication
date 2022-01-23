package com.example.myapplication.bean.dto;


import com.example.myapplication.bean.model.BaseInput;



public class CourtDanceSpotUpdateDto extends BaseInput {

    private Long id;

    /*
    跳舞点名称
     */
    private String danceSpotName;

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
