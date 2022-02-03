package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.alibaba.fastjson.JSONObject;
import com.example.myapplication.bean.DataInterface;
import com.example.myapplication.bean.dto.CourtDanceGroupSearchDto;
import com.example.myapplication.bean.dto.CourtDanceSpotSearchDto;
import com.example.myapplication.bean.vo.CourtDanceGroupVO;
import com.example.myapplication.bean.vo.CourtDanceSpotVO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.*;
//import com.lee.hof.sys.bean.model.*;



public class CourtDanceFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static CourtDanceFragment newInstance(String label, Retrofit retrofit) {
        Bundle args = new Bundle();
        args.putString("label", label);

        CourtDanceFragment fragment = new CourtDanceFragment();
        fragment.setArguments(args);
        fragment.setRetrofit(retrofit);
        fragment.setLabel(label);
        return fragment;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    String label;

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    Retrofit retrofit;

    ListView listView;

    SimpleAdapter simpleAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_tab,container,false);
        listView =view.findViewById(R.id.my_list);
        try {
            simpleAdapter = new SimpleAdapter(getActivity(),
                    getSpotData(),
                    R.layout.dance_spot_item,
                    new String[]{"dance_spot_name","dance_spot_img","team_desc","member_desc","position_desc","distance_desc"},
                    new int[]{R.id.dance_spot_name,R.id.dance_spot_img,R.id.team_desc,R.id.member_desc,R.id.position_desc,R.id.distance_desc});
        } catch (IOException e) {
            e.printStackTrace();
        }
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = listView.getAdapter().getItem(position).toString();
        Toast.makeText(getActivity(),"position:"+position+"text"+text ,Toast.LENGTH_SHORT).show();
    }

    private List<Map<String,Object>> getSpotData() throws IOException {
        final int [] images={R.drawable.jpg109951166783204006,R.drawable.jpg109951165144824473,R.drawable.jpg109951166562822795};
        final List<Map<String,Object>> list= new ArrayList<>();
        final DataInterface myApi =retrofit.create(DataInterface.class);

        CourtDanceSpotSearchDto searchDto = new CourtDanceSpotSearchDto();
        searchDto.setUserId(2L);

        Call<List<CourtDanceSpotVO>> spots =  myApi.listCourtDanceSpots(searchDto);
        spots.enqueue(new Callback<List<CourtDanceSpotVO>>() {
            //网络数据请求成功的回调方法
            @Override
            public void onResponse(Call<List<CourtDanceSpotVO>> call, Response<List<CourtDanceSpotVO>> response) {
                List<CourtDanceSpotVO> danceSpots = response.body();
                System.out.println(JSONObject.toJSONString(response.body()));
                for(int i=0; i<3; i++){
                    Map map = new HashMap();
                    map.put("dance_spot_img",images[i]);
                    map.put("dance_spot_name", danceSpots.get(i).getName());
                    map.put("team_desc", danceSpots.get(i).getTeamCount() == 0 ? "还没有舞队在此，快去创建吧!":String.format("%s支舞队",danceSpots.get(i).getTeamCount()));
                    map.put("member_desc", String.format("%s个加入成员" , danceSpots.get(i).getMemberCount()));
                    map.put("position_desc", danceSpots.get(i).getDetailDesc());
                    map.put("distance_desc", String.format("距您%s千米",danceSpots.get(i).getDistance()));
                    list.add(map);
                }
            }
            //网络数据请求失败的回调方法
            @Override
            public void onFailure(Call<List<CourtDanceSpotVO>> call, Throwable t) {
                 Toast.makeText(getActivity(), "网络请求错误：" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        return list;
    }

    private List<Map<String,Object>> getGroupData() throws IOException {
        final int [] images={R.drawable.jpg109951166783204006,R.drawable.jpg109951165144824473,R.drawable.jpg109951166562822795};
        final List<Map<String,Object>> list= new ArrayList<>();
        final DataInterface myApi =retrofit.create(DataInterface.class);
        CourtDanceGroupSearchDto groupSearchDto = new CourtDanceGroupSearchDto();
        Call<List<CourtDanceGroupVO>> spots =  myApi.listCourtDanceGroups(groupSearchDto);
        spots.enqueue(new Callback<List<CourtDanceGroupVO>>() {
            //网络数据请求成功的回调方法
            @Override
            public void onResponse(Call<List<CourtDanceGroupVO>> call, Response<List<CourtDanceGroupVO>> response) {
                List<CourtDanceGroupVO> danceGroupVOS = response.body();
                System.out.println(JSONObject.toJSONString(response.body()));
                for(int i=0; i < 3; i++){
                    Map map = new HashMap();
                    map.put("dance_spot_img",images[i]);
                    map.put("dance_spot_name",danceGroupVOS.get(i).getName());
                    map.put("team_desc",danceGroupVOS.get(i).getDetailDesc());
                    map.put("member_desc",danceGroupVOS.get(i).getMemberDesc());
                    map.put("distance_desc","12KM");
                    list.add(map);
                }
            }
            //网络数据请求失败的回调方法
            @Override
            public void onFailure(Call<List<CourtDanceGroupVO>> call, Throwable t) {
                Toast.makeText(getActivity(), "网络请求错误：" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return list;
    }
}
