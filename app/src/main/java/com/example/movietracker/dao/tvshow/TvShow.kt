package com.example.movietracker.dao.tvshow

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tvshow")
data class TvShow(
    @ColumnInfo(name = "tvshow_name") var tvshowName: String?,
    @ColumnInfo(name = "seasson_number") var numberOfSeassons: Int?,
    @ColumnInfo(name = "tvshow_year") var tvshowYear: Int?,
    @ColumnInfo(name = "tvshow_description") var tvshowDescription: String?,
    @ColumnInfo(name = "tvshow_uri") var tvshowUri: String?,
    @ColumnInfo(name = "twshow_seasson_current", defaultValue = "0") var tvshowCurrentSeasson: Int,
    @ColumnInfo(name = "twshow_episode_current", defaultValue = "0") var tvshowCurrentEpisode: Int
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_tvshow")
    var idTvShow: Int = 0
}