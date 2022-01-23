package com.example.myapplication;

import com.example.myapplication.bean.dto.CourtDanceGroupSearchDto;
import com.example.myapplication.bean.dto.CourtDanceSpotSearchDto;
import com.example.myapplication.bean.vo.CourtDanceGroupVO;
import com.example.myapplication.bean.vo.CourtDanceSpotVO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.List;

public interface DataInterface {    


    @POST("/courtDance/spot/list")
    Call<List<CourtDanceSpotVO>> listCourtDanceSpots(@Body CourtDanceSpotSearchDto danceSpotSearchDto);

    @POST("/courtDance/group/list")
    Call<List<CourtDanceGroupVO>> listCourtDanceGroups(@Body CourtDanceGroupSearchDto danceGroupSearchDto);



}
