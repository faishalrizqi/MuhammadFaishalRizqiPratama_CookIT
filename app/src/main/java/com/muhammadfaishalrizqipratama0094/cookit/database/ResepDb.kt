package com.muhammadfaishalrizqipratama0094.cookit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.muhammadfaishalrizqipratama0094.cookit.model.Resep

@Database(entities = [Resep::class], version = 3, exportSchema = false)
abstract class ResepDb : RoomDatabase() {

    abstract val dao: ResepDao

    companion object {
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE resep ADD COLUMN isRecycled INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE resep ADD COLUMN deletedDate TEXT DEFAULT NULL")
            }
        }

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
                    )
                        .addMigrations(MIGRATION_2_3)
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}