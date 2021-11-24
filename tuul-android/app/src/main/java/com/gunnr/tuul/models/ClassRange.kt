package com.gunnr.tuul.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "classrange")
data class ClassRange(
    @PrimaryKey @ColumnInfo(name = "indx") var indx: Int,
    @ColumnInfo(name = "highPercent") var highPercent: Int?,
    @ColumnInfo(name = "lowPercent") var lowPercent: Int?,
    @ColumnInfo(name = "sequence") var sequence: Int?,
    @ColumnInfo(name = "classification") var classification: String?
)