package com.example.movietracker.dao.tvshow

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TvShowDao {

    @Query("SELECT * FROM tvshow")
    fun getAll(): LiveData<List<TvShow>>

    @Query("SELECT * FROM tvshow WHERE tvshow_name LIKE :tvshow_name")
    fun findByTvShowName(tvshow_name: String): TvShow

    @Query("SELECT * FROM tvshow WHERE tvshow_name LIKE :filter ")
    fun getTvShowByFilter(filter: String): LiveData<List<TvShow>>

    @Insert
    fun insertAll(vararg tvShow: TvShow)

    @Delete
    fun delete(tvShow: TvShow)

    @Update
    fun updateTvShow(vararg tvShow: TvShow)
}