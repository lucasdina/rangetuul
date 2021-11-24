package com.gunnr.tuul.services.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gunnr.tuul.models.ClassRange

@Dao
interface ClassRangeDao {
    @Query("SELECT * FROM classrange")
    fun getAllClassRanges(): List<ClassRange>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun createAll(objects: List<ClassRange>)
}