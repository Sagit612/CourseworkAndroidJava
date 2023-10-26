package com.example.coursework.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "observations")
public class Observation implements Serializable, Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "observation_id")
    private long observationId;

    @ColumnInfo(name = "hike_id")
    private long hikeId;

    @ColumnInfo(name = "observation")
    private String observation;

    @ColumnInfo(name = "time_of_observation")
    private String timeOfObservation;

    @ColumnInfo(defaultValue = "", name = "comment")
    private String comment;

    public Observation(long hikeId, String observation, String timeOfObservation, String comment) {
        this.hikeId = hikeId;
        this.observation = observation;
        this.timeOfObservation = timeOfObservation;
        this.comment = comment;
    }

    protected Observation(Parcel in) {
        observationId = in.readLong();
        hikeId = in.readLong();
        observation = in.readString();
        timeOfObservation = in.readString();
        comment = in.readString();
    }

    public static final Creator<Observation> CREATOR = new Creator<Observation>() {
        @Override
        public Observation createFromParcel(Parcel in) {
            return new Observation(in);
        }

        @Override
        public Observation[] newArray(int size) {
            return new Observation[size];
        }
    };

    public long getObservationId() {
        return observationId;
    }

    public void setObservationId(long observationId) {
        this.observationId = observationId;
    }

    public long getHikeId() {
        return hikeId;
    }

    public void setHikeId(long hikeId) {
        this.hikeId = hikeId;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getTimeOfObservation() {
        return timeOfObservation;
    }

    public void setTimeOfObservation(String timeOfObservation) {
        this.timeOfObservation = timeOfObservation;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeLong(observationId);
        parcel.writeLong(hikeId);
        parcel.writeString(observation);
        parcel.writeString(timeOfObservation);
        parcel.writeString(comment);
    }
}
