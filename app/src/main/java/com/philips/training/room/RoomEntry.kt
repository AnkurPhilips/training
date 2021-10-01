package com.philips.training.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * (C) Koninklijke Philips N.V., 2021.
 * All rights reserved.
 */

@Entity(tableName = "entries")
data class RoomEntry(@PrimaryKey @ColumnInfo(name = "name") val name: String)