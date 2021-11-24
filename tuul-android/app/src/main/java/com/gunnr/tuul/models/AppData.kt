package com.gunnr.tuul.models

import android.content.Context
import com.gunnr.tuul.services.db.AppDatabase

data class AppData(
    var context: Context,
    var Classifiers: List<Classifier>? = listOf(),
    var HHFS: List<HHF>? = listOf(),
    var Divisions: List<Division>? = listOf(),
    var ClassRanges: List<ClassRange>? = listOf(),
    var Db: AppDatabase? = null
) {
    init {
        val db = AppDatabase.getAppDataBase(context)
        Classifiers = db.ClassifierDao().getAllClassifiers()
        HHFS = db.HHFDao().getAllHHFs()
        Divisions = db.DivisionDao().getAllDivisions()
        ClassRanges = db.ClassRangeDao().getAllClassRanges()
        Db = db
    }
}