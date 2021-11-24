package com.gunnr.tuul.services.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gunnr.tuul.models.Division

@Dao
interface DivisionDao {
    @Query("SELECT * FROM division")
    fun getAllDivisions(): List<Division>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun createAll(objects: List<Division>)
}