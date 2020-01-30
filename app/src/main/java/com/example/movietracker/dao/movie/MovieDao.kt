package com.example.movietracker.dao.movie

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getAll(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie WHERE movie_name LIKE :movie_name")
    fun findByMovieName(movie_name: String): Movie

    @Query("SELECT * FROM movie WHERE movie_name LIKE :filter ")
    fun getMovieByFilter(filter: String): LiveData<List<Movie>>

    @Insert
    fun insertAll(vararg todo: Movie)

    @Delete
    fun delete(todo: Movie)

    @Update
    fun updateTodo(vararg todos: Movie)
}