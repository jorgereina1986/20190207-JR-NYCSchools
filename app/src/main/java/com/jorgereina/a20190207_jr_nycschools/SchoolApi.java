package com.jorgereina.a20190207_jr_nycschools;

import com.jorgereina.a20190207_jr_nycschools.data.School;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SchoolApi {

    @GET("97mf-9njv.json")
    Call<List<School>> getSchools();
}
