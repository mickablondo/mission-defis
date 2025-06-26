package dev.mickablondo.missiondefis.data

import androidx.room.*
import dev.mickablondo.missiondefis.model.Challenge
import kotlinx.coroutines.flow.Flow

@Dao
interface ChallengeDao {
    @Query("SELECT * FROM challenges WHERE childId = :childId")
    fun getByChild(childId: Int): Flow<List<Challenge>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(challenge: Challenge)

    @Update
    suspend fun update(challenge: Challenge)

    @Delete
    suspend fun delete(challenge: Challenge)

    @Query("DELETE FROM challenges WHERE childId = :childId")
    suspend fun deleteByChild(childId: Int)
}
