package com.muhammadfaishalrizqipratama0094.cookit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.muhammadfaishalrizqipratama0094.cookit.model.Resep

@Database(entities = [Resep::class], version = 2, exportSchema = false)
abstract class ResepDb : RoomDatabase() {

    abstract val dao: ResepDao

    companion object {

        @Volatile
        private var INSTANCE: ResepDb? = null

        fun getInstance(context: Context): ResepDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ResepDb::class.java,
                        "recipe.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

