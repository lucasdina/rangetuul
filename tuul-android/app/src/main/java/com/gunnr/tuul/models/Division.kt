package com.gunnr.tuul.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "division")
data class Division(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "longName") var longName: String?,
    @ColumnInfo(name = "shortName") var shortName: String?
) {
    override fun toString(): String {
        return "$longName"
    }
}