package com.philips.training.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * (C) Koninklijke Philips N.V., 2021.
 * All rights reserved.
 */
class EntriesSqliteOpenHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_QUERY)
    }
    override fun onUpgrade(db: SQLiteDatabase?,
                           oldVersion: Int,
                           newVersion: Int) {
    }
    companion object{
        const val CREATE_QUERY = "CREATE TABLE ${Entry.TABLE_NAME} ( " +
                "${Entry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "${Entry.COLUMN_DATA} VARCHAR(10) )"

        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "EntriesDB.sqlite"
    }
}