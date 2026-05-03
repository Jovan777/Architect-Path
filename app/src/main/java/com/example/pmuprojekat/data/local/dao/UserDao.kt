package com.example.pmuprojekat.data.local.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pmuprojekat.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM users WHERE isActive = 1 LIMIT 1")
    fun observeActiveUser(): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE userId = :userId LIMIT 1")
    suspend fun getUserById(userId: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUser(user: UserEntity)

    @Query("""
        UPDATE users
        SET currentLevel = :level,
            lastActiveAt = :updatedAt
        WHERE userId = :userId
    """)
    suspend fun updateCurrentLevel(
        userId: String,
        level: String,
        updatedAt: Long = System.currentTimeMillis()
    )
}