package com.foko.pojo;

import com.google.gson.annotations.SerializedName;

public class TeamsDetailsObject {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    public TeamsDetailsObject(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*    {
        "id" : 1,
            "name" : "New Jersey Devils",
            "link" : "/api/v1/teams/1",
            "venue" : {
        "name" : "Prudential Center",
                "link" : "/api/v1/venues/null",
                "city" : "Newark",
                "timeZone" : {
            "id" : "America/New_York",
                    "offset" : -5,
                    "tz" : "EST"
        }
    },
        "abbreviation" : "NJD",
            "teamName" : "Devils",
            "locationName" : "New Jersey",
            "firstYearOfPlay" : "1982",
            "division" : {
        "id" : 18,
                "name" : "Metropolitan",
                "nameShort" : "Metro",
                "link" : "/api/v1/divisions/18",
                "abbreviation" : "M"
    },
        "conference" : {
        "id" : 6,
                "name" : "Eastern",
                "link" : "/api/v1/conferences/6"
    },
        "franchise" : {
        "franchiseId" : 23,
                "teamName" : "Devils",
                "link" : "/api/v1/franchises/23"
    },
        "shortName" : "New Jersey",
            "officialSiteUrl" : "http://www.newjerseydevils.com/",
            "franchiseId" : 23,
            "active" : true
    }*/

}
