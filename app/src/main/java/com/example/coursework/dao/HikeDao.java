package com.example.coursework.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.coursework.models.Hike;
import com.example.coursework.models.HikeWithObservations;
import com.example.coursework.models.Observation;

import java.util.List;

@Dao
public interface HikeDao {
    @Insert
    long insertHike(Hike hike);

    @Update
    int updateHike(Hike hike);

    @Update
    int updateObservation(Observation observation);

    @Delete
    void deleteHike(Hike hike);

    @Delete
    void deleteObservation(Observation observation);

    @Query("DELETE FROM observations WHERE hike_id == :id")
    void deleteObservationByHikeId(long id);

    @Query("DELETE FROM hikes")
    void deleteAllHikes();

    @Query("DELETE FROM observations")
    void deleteAllObservations();

    @Query("SELECT * FROM hikes")
    List<Hike> getAllHikes();

    @Query("SELECT * FROM hikes")
    List<HikeWithObservations> getHikeWithObservations();

    @Query("SELECT * FROM hikes WHERE name_of_the_hike = :name")
    List<HikeWithObservations> getHikeWithObservationsByName(String name);


    @Query("SELECT * FROM observations WHERE hike_id == :hikeId")
    List<Observation> getObservationsByHikeId(long hikeId);

    @Insert
    long insertObservation(Observation observation);
}
