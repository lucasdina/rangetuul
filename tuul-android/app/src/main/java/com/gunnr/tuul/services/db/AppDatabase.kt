package com.gunnr.tuul.services.db

import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.google.gson.GsonBuilder
import com.gunnr.tuul.models.Classifier
import com.gunnr.tuul.ClassifierDao
import com.gunnr.tuul.models.HHF
import com.gunnr.tuul.models.ClassRange
import com.gunnr.tuul.models.Division
import java.util.concurrent.Executors


@Database(entities = [Classifier::class, HHF::class, Division::class, ClassRange::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ClassifierDao(): ClassifierDao
    abstract fun HHFDao(): HHFDao
    abstract fun DivisionDao(): DivisionDao
    abstract fun ClassRangeDao(): ClassRangeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase {
            val rdc = object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    val classifiersString = context.applicationContext.assets.open("classifiers.json").bufferedReader()
                        .use { it.readText() }
                    val hhfsString = context.applicationContext.assets.open("hhfs.json").bufferedReader()
                        .use { it.readText() }
                    val divisionsString = context.applicationContext.assets.open("divisions.json").bufferedReader()
                        .use { it.readText() }
                    val classRangesString = context.applicationContext.assets.open("classRanges.json").bufferedReader()
                        .use { it.readText() }
                    val gson = GsonBuilder().create()

                    Executors.newSingleThreadScheduledExecutor()
                        .execute {
                            INSTANCE!!.ClassifierDao()
                                .createAll(gson.fromJson(classifiersString, Array<Classifier>::class.java).toList())
                            INSTANCE!!.HHFDao().createAll(gson.fromJson(hhfsString, Array<HHF>::class.java).toList())
                            INSTANCE!!.DivisionDao()
                                .createAll(gson.fromJson(divisionsString, Array<Division>::class.java).toList())
                            INSTANCE!!.ClassRangeDao()
                                .createAll(gson.fromJson(classRangesString, Array<ClassRange>::class.java).toList())

                            super.onCreate(db)
                        }
                }
            }

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "tuulDB2.db")
                    .addCallback(rdc).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }
}