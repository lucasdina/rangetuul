package com.gunnr.tuul

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gunnr.tuul.models.Classifier

@Dao
interface ClassifierDao {
    @Query("SELECT * FROM classifier")
    fun getAllClassifiers(): List<Classifier>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun createAll(objects: List<Classifier>)
}