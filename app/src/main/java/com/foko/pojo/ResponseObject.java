package com.foko.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseObject {

    @SerializedName("teams")
    private List<TeamsDetailsObject> teams;

    public ResponseObject(List<TeamsDetailsObject> teams) {
        this.teams = teams;
    }

    public List<TeamsDetailsObject> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamsDetailsObject> teams) {
        this.teams = teams;
    }
}
