
package com.foko.pojo.player;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlayerObject {

    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("people")
    @Expose
    private List<Person> people = null;

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

}
