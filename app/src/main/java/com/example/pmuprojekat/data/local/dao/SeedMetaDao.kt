package com.example.pmuprojekat.data.local.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pmuprojekat.data.local.entity.SeedMetaEntity

@Dao
interface SeedMetaDao {

    @Query("SELECT * FROM seed_meta WHERE id = 'questions_seed' LIMIT 1")
    suspend fun getSeedMeta(): SeedMetaEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSeedMeta(meta: SeedMetaEntity)
}