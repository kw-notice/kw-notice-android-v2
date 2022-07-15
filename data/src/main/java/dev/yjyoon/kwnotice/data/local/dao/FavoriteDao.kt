package dev.yjyoon.kwnotice.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.yjyoon.kwnotice.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert
    suspend fun add(favorite: FavoriteEntity)

    @Delete
    suspend fun delete(favorite: FavoriteEntity)

    @Query("SELECT id FROM favorite WHERE type = 'KwHome'")
    suspend fun getKwHomeIds(): List<Long>

    @Query("SELECT id FROM favorite WHERE type = 'SwCentral'")
    suspend fun getSwCentralIds(): List<Long>
}
