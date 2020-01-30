package com.example.movietracker.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.movietracker.R
import com.example.movietracker.dao.tvshow.TvShow
import com.example.movietracker.view_models.TvShowViewModel
import kotlinx.android.synthetic.main.activity_tvshow_info.*

class ContentDetails : AppCompatActivity(), View.OnClickListener {

    private lateinit var tvShowViewModel: TvShowViewModel
    private lateinit var show: TvShow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow_info)
        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel::class.java)
        buttonIncrementEp.setOnClickListener(this)
        buttonIncrementSe.setOnClickListener(this)
        show = intent.getSerializableExtra("ob") as TvShow
        textViewSerijalCurrent.text = show.tvshowCurrentSeasson.toString()
        textViewEpizodaCurrent.text = show.tvshowCurrentEpisode.toString()
        textViewTvShowName.text = show.tvshowName
        textViewTvShowDescription.text = show.tvshowDescription
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonIncrementEp -> incrementEp()
            R.id.buttonIncrementSe -> incrementSe()
        }
    }

    private fun incrementEp() {
        textViewEpizodaCurrent.text =
            textViewEpizodaCurrent.text.toString().toInt().inc().toString()
        show.tvshowCurrentEpisode = show.tvshowCurrentEpisode.inc()
        tvShowViewModel.updateTvShow(show)
    }

    private fun incrementSe() {
        textViewSerijalCurrent.text =
            textViewSerijalCurrent.text.toString().toInt().inc().toString()
        show.tvshowCurrentSeasson = show.tvshowCurrentSeasson.inc()
        show.tvshowCurrentEpisode = 0
        textViewEpizodaCurrent.text = 0.toString()
        tvShowViewModel.updateTvShow(show)
    }
}
