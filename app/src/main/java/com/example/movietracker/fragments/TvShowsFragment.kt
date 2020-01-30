package com.example.movietracker.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movietracker.R
import com.example.movietracker.adapters.TvShowAdapter
import com.example.movietracker.dao.tvshow.TvShow
import com.example.movietracker.utils.SwipeToDeleteCallback
import com.example.movietracker.view_models.TvShowViewModel
import kotlinx.android.synthetic.main.fragment_tv_shows.view.*


class TvShowsFragment : Fragment() {

    private lateinit var tvShowViewModel: TvShowViewModel
    private lateinit var adapter: TvShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tv_shows, container, false)
        tvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel::class.java)
        view.tvShows_recycler_view.layoutManager = LinearLayoutManager(context)
        adapter = TvShowAdapter()
        view.tvShows_recycler_view.adapter = adapter
        tvShowViewModel.getTvShows()?.observe(this, Observer<List<TvShow>> { t -> adapter.setTvShows(t!!) })
        enableSwipeToDeleteAndUndo(view.tvShows_recycler_view)
        return view
    }

    private fun enableSwipeToDeleteAndUndo(recyclerView: RecyclerView) {
        val swipeToDeleteCallback: SwipeToDeleteCallback =
            object : SwipeToDeleteCallback(this.context!!) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                    val position = viewHolder.adapterPosition
                    val item: TvShow = adapter.getData()?.get(position)!!
                    tvShowViewModel.deleteTvShow(item)
                }
            }
        val itemTouchhelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchhelper.attachToRecyclerView(recyclerView)
    }
}
