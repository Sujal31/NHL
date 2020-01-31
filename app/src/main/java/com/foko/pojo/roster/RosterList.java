
package com.foko.pojo.roster;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RosterList {

    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("roster")
    @Expose
    private List<Roster> roster = null;
    @SerializedName("link")
    @Expose
    private String link;

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public List<Roster> getRoster() {
        return roster;
    }

    public void setRoster(List<Roster> roster) {
        this.roster = roster;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

   /* @Override
    public int compareTo(Object o) {
        return this.getRoster().get(0).getPerson().getFullName().compareTo(((Roster)o).getPerson().getFullName());
    }*/
}
