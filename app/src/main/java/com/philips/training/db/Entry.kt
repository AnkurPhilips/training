package com.philips.training.db

/**
 * (C) Koninklijke Philips N.V., 2021.
 * All rights reserved.
 */
interface Entry {
    companion object{
        const val TABLE_NAME = "Entries"
        const val COLUMN_ID = "id"
        const val COLUMN_DATA = "data"
    }
    val id: Int
    val data: String
}