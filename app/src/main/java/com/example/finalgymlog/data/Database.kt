package com.example.finalgymlog.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.finalgymlog.data.Session

@Database(entities = [Session::class], version = 1, exportSchema = false)
abstract class SessionDatabase: RoomDatabase() {

    abstract fun sessionDao(): SessionDao

    companion object {
        @Volatile
        private var INSTANCE:SessionDatabase? = null

        fun getDatabase(context: Context):SessionDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SessionDatabase::class.java,
                    "session_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}



@Database(entities = [Exo::class], version = 1, exportSchema = false)
abstract class ExoDatabase: RoomDatabase() {

    abstract fun exoDao(): ExoDao

    companion object {
        @Volatile
        private var INSTANCE:ExoDatabase? = null

        fun getDatabase(context: Context):ExoDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExoDatabase::class.java,
                    "exo_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}