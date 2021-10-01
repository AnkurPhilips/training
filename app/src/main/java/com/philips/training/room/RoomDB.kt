package com.philips.training.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.lang.Exception

/**
 * (C) Koninklijke Philips N.V., 2021.
 * All rights reserved.
 */
@Database(entities = arrayOf(RoomEntry::class), version = 1, exportSchema = false)
abstract class RoomDB : RoomDatabase() {

    abstract fun roomEntryDao(): RoomEntryDao


    companion object {

        fun getInstance(context: Context): RoomDB?{
            var roomDB: RoomDB? = null
            try {
                roomDB = Room.databaseBuilder(context.applicationContext, RoomDB::class.java, "roomt.sqlite").build()
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
            return roomDB
        }
    }



}