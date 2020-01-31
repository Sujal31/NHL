package com.foko.utilities;

import com.foko.pojo.ResponseObject;
import com.foko.pojo.player.PlayerObject;
import com.foko.pojo.roster.RosterList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetTeamDataService {


    @GET("teams")
    Call<ResponseObject> getAllTeams();

    @GET("teams/{id}/roster")
    Call<RosterList> getRosterList(@Path("id") int id);

    @GET("people/{id}")
    Call<PlayerObject> getPlayer(@Path("id") int id);
}
