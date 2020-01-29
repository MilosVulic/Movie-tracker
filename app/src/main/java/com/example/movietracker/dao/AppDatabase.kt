package com.example.movietracker.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movietracker.dao.movie.MovieDao
import com.example.movietracker.dao.movie.Movie
import com.example.movietracker.dao.tvshow.TvShow
import com.example.movietracker.dao.tvshow.TvShowDao

@Database(entities = [Movie::class, TvShow::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao

    companion object {

        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "movie-tracker.db").build()
    }
}