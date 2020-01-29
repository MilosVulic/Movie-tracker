package com.example.movietracker.dao.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class Movie(
    @ColumnInfo(name = "movie_name") var movieName: String?,
    @ColumnInfo(name = "movie_genre") var movieGenre: String?,
    @ColumnInfo(name = "movie_duration") var movieDuration: Int?,
    @ColumnInfo(name = "movie_year") var movieYear: Int?,
    @ColumnInfo(name = "movie_uri") var movieUri: String?
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_movie")
    var idMovie: Int = 0
}