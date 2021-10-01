package com.philips.training.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * (C) Koninklijke Philips N.V., 2021.
 * All rights reserved.
 */

@Dao
interface RoomEntryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(roomEntry: RoomEntry)

    @Query("SELECT * FROM entries ORDER BY name ASC")
    fun getAlphabetizedWords(): List<RoomEntry>

}