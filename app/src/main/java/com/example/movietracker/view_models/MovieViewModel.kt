package com.example.movietracker.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.movietracker.dao.movie.Movie
import com.example.movietracker.repositories.MovieRepository

class MovieViewModel(application: Application) : AndroidViewModel(application) {

    var repository = MovieRepository(application)

    fun getMovies() = repository.getMovies()

    fun getMovies(name : String) = repository.getMoviesByFilter(name)

    fun insertMovie(movie: Movie) {
        repository.insertMovies(movie)
    }

    fun deleteMovie(movie: Movie) {
        repository.removeMovie(movie)
    }
}