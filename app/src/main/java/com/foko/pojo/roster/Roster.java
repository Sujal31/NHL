
package com.foko.pojo.roster;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Roster implements Comparable<Roster> {

    @SerializedName("person")
    @Expose
    private Person person;
    @SerializedName("jerseyNumber")
    @Expose
    private String jerseyNumber;
    @SerializedName("position")
    @Expose
    private Position position;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(String jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public int compareTo(Roster roster) {
        return this.getPerson().getFullName().compareTo(roster.getPerson().getFullName());
    }
}
