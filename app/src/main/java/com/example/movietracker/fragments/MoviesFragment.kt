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
import com.example.movietracker.adapters.MovieAdapter
import com.example.movietracker.dao.movie.Movie
import com.example.movietracker.utils.SwipeToDeleteCallback
import com.example.movietracker.view_models.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movies.view.*


class MoviesFragment : Fragment() {

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var adapter: MovieAdapter

    companion object {
        var list: List<Movie>? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies, container, false)
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        view.movies_recycler_view.layoutManager = LinearLayoutManager(context)
        adapter = MovieAdapter()
        view.movies_recycler_view.adapter = adapter
        movieViewModel.getMovies()?.observe(this, Observer<List<Movie>> { t ->
            adapter.setMovies(t!!)
            list = t
        })
        enableSwipeToDeleteAndUndo(view.movies_recycler_view)
        return view
    }

    private fun enableSwipeToDeleteAndUndo(recyclerView: RecyclerView) {
        val swipeToDeleteCallback: SwipeToDeleteCallback =
            object : SwipeToDeleteCallback(this.context!!) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                    val position = viewHolder.adapterPosition
                    val item: Movie = adapter.getData()?.get(position)!!
                    movieViewModel.deleteMovie(item)
                }
            }
        val itemTouchhelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchhelper.attachToRecyclerView(recyclerView)
    }

}
