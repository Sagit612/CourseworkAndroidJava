package com.example.coursework.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class HikeWithObservations {
    @Embedded public Hike hike;
    @Relation(
            parentColumn = "hike_id",
            entityColumn = "hike_id"
    )
    public List<Observation> observations;
}
