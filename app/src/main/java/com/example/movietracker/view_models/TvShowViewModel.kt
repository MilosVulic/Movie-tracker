package com.example.movietracker.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.movietracker.dao.tvshow.TvShow
import com.example.movietracker.repositories.TvShowRepository

class TvShowViewModel(application: Application) : AndroidViewModel(application) {

    var repository = TvShowRepository(application)

    fun getTvShows() = repository.getTvShows()

    fun getTvShowsByName(name : String) = repository.getTvShowsByName(name)

    fun insertTvShow(tvShow: TvShow) {
        repository.insertTvShow(tvShow)
    }

    fun deleteTvShow(tvShow: TvShow) {
        repository.removeTvShow(tvShow)
    }

    fun updateTvShow(tvShow: TvShow) {
        repository.updateShow(tvShow)
    }
}