package com.gunnr.tuul.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity(tableName = "classifier")
data class Classifier(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "classifier") var classifier: String?,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "version") var version: String?,
    @ColumnInfo(name = "rounds") var rounds: Int?,
    @ColumnInfo(name = "scoring") var scoring: String?,
    @ColumnInfo(name = "pageCount") var pageCount: Int?,
    @ColumnInfo(name = "link") var link: String?,
    @ColumnInfo(name = "thumb") var thumb: String?,
    @ColumnInfo(name = "imageFile") var imageFile: String?,
    @ColumnInfo(name = "wsbFile") var wsbFile: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun toString(): String {
        return "$classifier $name"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(classifier)
        parcel.writeString(name)
        parcel.writeString(version)
        parcel.writeValue(rounds)
        parcel.writeString(scoring)
        parcel.writeValue(pageCount)
        parcel.writeString(link)
        parcel.writeString(thumb)
        parcel.writeString(imageFile)
        parcel.writeString(wsbFile)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Classifier> {
        override fun createFromParcel(parcel: Parcel): Classifier {
            return Classifier(parcel)
        }

        override fun newArray(size: Int): Array<Classifier?> {
            return arrayOfNulls(size)
        }
    }
}