package com.example.coursework.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "hikes")
public class Hike implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "hike_id")
    private long hikeId;

    @ColumnInfo(name = "name_of_the_hike")
    private String name;
    @ColumnInfo(name = "location")
    private String location;
    @ColumnInfo(name = "date_of_the_hike")
    private String date;
    @ColumnInfo(name = "parking_available")
    private String parkingAvailable;

    @ColumnInfo(name = "length_of_the_hike")
    private String lengthOfTheHike;
    @ColumnInfo(name = "difficulty_level")
    private String level;
    @ColumnInfo(defaultValue = "", name = "description")
    private String description;

    public Hike(String name, String location, String date, String parkingAvailable, String lengthOfTheHike, String level, String description) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.parkingAvailable = parkingAvailable;
        this.lengthOfTheHike = lengthOfTheHike;
        this.level = level;
        this.description = description;
    }

    public long getHikeId() {
        return hikeId;
    }

    public void setHikeId(long hikeId) {
        this.hikeId = hikeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getParkingAvailable() {
        return parkingAvailable;
    }

    public void setParkingAvailable(String parkingAvailable) {
        this.parkingAvailable = parkingAvailable;
    }

    public String getLengthOfTheHike() {
        return lengthOfTheHike;
    }

    public void setLengthOfTheHike(String lengthOfTheHike) {
        this.lengthOfTheHike = lengthOfTheHike;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
