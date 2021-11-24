package com.gunnr.tuul.services.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gunnr.tuul.models.HHF

@Dao
interface HHFDao {
    @Query("SELECT * FROM HHF")
    fun getAllHHFs(): List<HHF>

    @Query("SELECT * FROM HHF WHERE division=:division AND classifier=:classifier LIMIT 1")
    fun getHHF(classifier: Int?, division: Int?): HHF

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun createAll(objects: List<HHF>)
}