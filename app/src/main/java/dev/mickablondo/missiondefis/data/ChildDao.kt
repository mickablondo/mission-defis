package dev.mickablondo.missiondefis.data

import androidx.room.*
import dev.mickablondo.missiondefis.model.Child
import kotlinx.coroutines.flow.Flow

@Dao
interface ChildDao {
    @Query("SELECT * FROM children")
    fun getAll(): Flow<List<Child>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(child: Child): Long

    @Delete
    suspend fun delete(child: Child)
}
