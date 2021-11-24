package com.gunnr.tuul.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "HHF")
data class HHF(
    @PrimaryKey @ColumnInfo (name = "id") var id: Int,
    @ColumnInfo(name = "classifier") var classifier: Int?,
    @ColumnInfo(name = "division") var division: Int?,
    @ColumnInfo(name = "hhf") var hhf: Double?,
    @ColumnInfo(name = "updated") var updated: String?
)