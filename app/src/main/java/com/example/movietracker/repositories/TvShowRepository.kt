package com.example.movietracker.repositories

import android.app.Application
import com.example.movietracker.dao.AppDatabase
import com.example.movietracker.dao.tvshow.TvShow
import com.example.movietracker.dao.tvshow.TvShowDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class TvShowRepository(application: Application) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var tvShowDao: TvShowDao?

    init {
        val db = AppDatabase(application)
        tvShowDao = db.tvShowDao()
    }

    fun getTvShows() = tvShowDao?.getAll()

    fun getTvShowsByName(name: String) = tvShowDao?.getTvShowByFilter(name)

    fun insertTvShow(tvShow: TvShow) {
        launch { addTvShow(tvShow) }
    }

    fun removeTvShow(tvShow: TvShow) {
        launch { deleteTvShow(tvShow) }
    }

    fun updateShow(tvShow: TvShow) {
        launch { updateTvShow(tvShow) }
    }

    private suspend fun addTvShow(tvShow: TvShow) {
        withContext(Dispatchers.IO) {
            tvShowDao?.insertAll(tvShow)
        }
    }

    private suspend fun deleteTvShow(tvShow: TvShow) {
        withContext(Dispatchers.IO) {
            tvShowDao?.delete(tvShow)
        }
    }

    private suspend fun updateTvShow(tvShow: TvShow) {
        withContext(Dispatchers.IO) {
            tvShowDao?.updateTvShow(tvShow)
        }
    }
}