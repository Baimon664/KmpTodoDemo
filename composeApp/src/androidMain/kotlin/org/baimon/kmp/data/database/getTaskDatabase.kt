package org.baimon.kmp.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers

fun getTaskDatabaseBuilder(ctx: Context): RoomDatabase.Builder<TaskDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("task.db")
    return Room.databaseBuilder<TaskDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}