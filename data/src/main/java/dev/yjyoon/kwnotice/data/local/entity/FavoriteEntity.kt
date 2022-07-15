package dev.yjyoon.kwnotice.data.local.entity

import androidx.room.Entity
import dev.yjyoon.kwnotice.domain.model.Favorite
import java.time.LocalDate

@Entity(
    tableName = "favorite",
    primaryKeys = ["id", "type"]
)
data class FavoriteEntity(
    val id: Long,
    val title: String,
    val type: Type,
    val date: LocalDate,
    val url: String
) {
    enum class Type { KwHome, SwCentral }
}

fun Favorite.toData() = FavoriteEntity(
    id = id,
    title = title,
    type = when (type) {
        Favorite.Type.KwHome -> FavoriteEntity.Type.KwHome
        Favorite.Type.SwCentral -> FavoriteEntity.Type.SwCentral
    },
    date = date,
    url = url
)
