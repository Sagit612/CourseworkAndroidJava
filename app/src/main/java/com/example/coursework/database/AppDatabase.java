package com.example.coursework.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.coursework.dao.HikeDao;
import com.example.coursework.models.Hike;
import com.example.coursework.models.Observation;

@Database(version = 1, entities = {Hike.class, Observation.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract HikeDao hikeDao();
}
