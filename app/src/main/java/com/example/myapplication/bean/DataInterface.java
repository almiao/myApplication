package com.example.myapplication.bean;

import android.database.Observable;
import com.example.myapplication.bean.dto.CourtDanceGroupSearchDto;
import com.example.myapplication.bean.dto.CourtDanceSpotSearchDto;
import com.example.myapplication.bean.vo.CourtDanceGroupVO;
import com.example.myapplication.bean.vo.CourtDanceSpotVO;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

import java.util.List;

public interface DataInterface {    


    @POST("/courtDance/spot/list")
    Call<List<CourtDanceSpotVO>> listCourtDanceSpots(@Body CourtDanceSpotSearchDto danceSpotSearchDto);

    @POST("/courtDance/group/list")
    Call<List<CourtDanceGroupVO>> listCourtDanceGroups(@Body CourtDanceGroupSearchDto danceGroupSearchDto);

    public String Url = "http://yun918.cn/study/public/";

    @Multipart
    @POST("/file/upload")
    Observable<FileUploadBean> upload(@Part("key") RequestBody requestBody, @Part MultipartBody.Part
            file);

}
