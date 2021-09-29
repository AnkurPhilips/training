package com.philips.training.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import java.lang.Exception

/**
 * (C) Koninklijke Philips N.V., 2021.
 * All rights reserved.
 */
class EntriesDao(context: Context) {
    private var entriesSqliteOpenHelper: EntriesSqliteOpenHelper = EntriesSqliteOpenHelper(context)
    private lateinit var sqLiteDatabase: SQLiteDatabase

    fun openDB(){
        try {
            sqLiteDatabase = entriesSqliteOpenHelper.writableDatabase
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun closeDB(){
        try {
            if(::sqLiteDatabase.isInitialized && sqLiteDatabase.isOpen){
                sqLiteDatabase.close()
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun getEntry(entry: Entry): Entry? {
        val selection = getSelectionQuery(entry)
        val entries = getAllEntries(selection)
        return entries.firstOrNull()
    }

    fun deleteEntry(entry: Entry){
        try {
            val data = getEntry(entry)?.data ?: ""
            Log.i("Entry to be del", data)
            sqLiteDatabase.delete(Entry.TABLE_NAME, getSelectionQuery(entry), null)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun getSelectionQuery(entry: Entry) = "${Entry.COLUMN_DATA} = '${entry.data}'"

    fun insertEntry(entry: Entry){
        try {
            val contentValues = ContentValues()
            contentValues.put(Entry.COLUMN_DATA, entry.data)
            sqLiteDatabase.insert(Entry.TABLE_NAME, null, contentValues)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun getAllEntries(selection: String? = null): ArrayList<Entry> {
        val entries = ArrayList<Entry>()
        try {
            val cursor = sqLiteDatabase.query(Entry.TABLE_NAME, null, selection, null, null, null, null)
            val indexID = cursor.getColumnIndexOrThrow(Entry.COLUMN_ID)
            val indexData = cursor.getColumnIndexOrThrow(Entry.COLUMN_DATA)
            while (cursor.moveToNext()){
                val entry = NoteEntry(cursor.getString(indexData), cursor.getInt(indexID))
                entries.add(entry)
            }
            cursor.close()
        } catch (e: Exception){
            e.printStackTrace()
        } finally {
            return entries
        }
    }
}