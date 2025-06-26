package dev.mickablondo.missiondefis.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.mickablondo.missiondefis.model.Child
import dev.mickablondo.missiondefis.model.Challenge

@Database(entities = [Child::class, Challenge::class], version = 1)
abstract class MissionDatabase : RoomDatabase() {
    abstract fun childDao(): ChildDao
    abstract fun challengeDao(): ChallengeDao

    companion object {
        @Volatile
        private var INSTANCE: MissionDatabase? = null

        fun getDatabase(context: Context): MissionDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MissionDatabase::class.java,
                    "mission_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
