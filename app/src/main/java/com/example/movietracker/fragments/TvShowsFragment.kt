package com.example.movietracker.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movietracker.R
import com.example.movietracker.adapters.TvShowAdapter
import com.example.movietracker.dao.tvshow.TvShow
import com.example.movietracker.view_models.TvShowViewModel
import kotlinx.android.synthetic.main.fragment_tv_shows.view.*


class TvShowsFragment : Fragment() {

    private lateinit var tvShowViewModel: TvShowViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tv_shows, container, false)
        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel::class.java)
        view.tvShows_recycler_view.layoutManager = LinearLayoutManager(context)
        val adapter = TvShowAdapter(tvShowViewModel)
        view.tvShows_recycler_view.adapter = adapter
        tvShowViewModel.getTvShows()?.observe(this, Observer<List<TvShow>> { t -> adapter.setTvShows(t!!) })
        return view
    }
}
