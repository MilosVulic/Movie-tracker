package com.example.movietracker.repositories

import android.app.Application
import com.example.movietracker.dao.AppDatabase
import com.example.movietracker.dao.movie.Movie
import com.example.movietracker.dao.movie.MovieDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MovieRepository(application: Application) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var movieDao: MovieDao?

    init {
        val db = AppDatabase(application)
        movieDao = db.movieDao()
    }

    fun getMovies() = movieDao?.getAll()

    fun getMoviesByFilter(name : String) = movieDao?.getMovieByFilter(name)

    fun insertMovies(movie: Movie) {
        launch { addMovie(movie) }
    }

    fun removeMovie(movie: Movie) {
        launch { deleteMovie(movie) }
    }

    private suspend fun addMovie(movie: Movie) {
        withContext(Dispatchers.IO) {
            movieDao?.insertAll(movie)
        }
    }

    private suspend fun deleteMovie(movie: Movie) {
        withContext(Dispatchers.IO) {
            movieDao?.delete(movie)
        }
    }
}