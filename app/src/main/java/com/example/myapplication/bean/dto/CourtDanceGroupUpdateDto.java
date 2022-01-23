package com.example.myapplication.bean.dto;


import com.example.myapplication.bean.model.BaseInput;

public class CourtDanceGroupUpdateDto extends BaseInput {

    private Long id;

    //舞团名称 百望山舞团
    private String name;

    private String logoImgId;


    private Long courtDanceSpotId;

    //舞种描述 舞种类型
    private String danceTypes;

    //基本信息描述
    private String detailDesc;

    //注意事项
    private String attentionDesc;

}
