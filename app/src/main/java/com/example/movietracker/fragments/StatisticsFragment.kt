package com.example.movietracker.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anychart.anychart.AnyChart
import com.anychart.anychart.DataEntry
import com.anychart.anychart.Pie
import com.anychart.anychart.ValueDataEntry
import com.example.movietracker.R
import com.example.movietracker.adapters.MovieAdapter
import com.example.movietracker.dao.movie.Movie
import com.example.movietracker.view_models.MovieViewModel
import kotlinx.android.synthetic.main.fragment_statistics.view.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import kotlin.coroutines.CoroutineContext


class StatisticsFragment : Fragment(), CoroutineScope {

    private val pie = AnyChart.pie()
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Main + job

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_statistics, container, false)
        job = Job()
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        getListOfMovies(view)
        return view
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    private suspend fun createChart(pie: Pie, list: List<Movie>?) {

        val map = withContext(Dispatchers.Default) {
            calculate(list)
        }

        val data = arrayListOf<DataEntry>()
        // val map = calculate()
        for ((k, v) in map) {
            data.add(ValueDataEntry(k, v))
        }

        pie.setData(data)
        pie.setRadius("45%")

        pie.labels.setWidth(50.0)
        pie.labels.setHeight(25.0)
        pie.labels.background.setEnabled(true)
        pie.labels.background.fill("Black", 0.4)
        pie.labels.setHAlign("center")
        pie.labels.setVAlign("center")
        pie.setTitle("Movie Genre Distribution")

        pie.legend.setPosition("bottom")
        pie.legend.setAlign("center")
        pie.legend.setMargin(0.0, 0.0, 40.0, 0.0)
        pie.legend.setItemsLayout("horizontalExpandable")
        pie.legend.setMaxWidth("100%")
        pie.legend.setMaxHeight("100%")
    }

    private fun calculate(list: List<Movie>?): Map<String, Int> {
        // val list = MoviesFragment.list
        val map = mutableMapOf(
            "Action" to 0,
            "Drama" to 0,
            "Horror" to 0,
            "Comedy" to 0,
            "Thriller" to 0,
            "Western" to 0,
            "Romantic" to 0
        )

        if (list != null) {
            for (i in list.indices) {
                when {
                    list[i].movieGenre == "Action" -> map["Action"] =
                        map.getValue("Action").toString().toInt() + 1
                    list[i].movieGenre == "Drama" -> map["Drama"] =
                        map.getValue("Drama").toString().toInt() + 1
                    list[i].movieGenre == "Horror" -> map["Horror"] =
                        map.getValue("Horror").toString().toInt() + 1
                    list[i].movieGenre == "Comedy" -> map["Comedy"] =
                        map.getValue("Comedy").toString().toInt() + 1
                    list[i].movieGenre == "Thriller" -> map["Thriller"] =
                        map.getValue("Thriller").toString().toInt() + 1
                    list[i].movieGenre == "Western" -> map["Western"] =
                        map.getValue("Western").toString().toInt() + 1
                    list[i].movieGenre == "Romantic" -> map["Romantic"] =
                        map.getValue("Romantic").toString().toInt() + 1
                }
            }
        }
        return map
    }

    private fun getListOfMovies(view: View) {
        val adapter = MovieAdapter()
        movieViewModel.getMovies()?.observe(this, Observer<List<Movie>> { t ->
            adapter.setMovies(t!!)
            launch {
                createChart(pie, t)
                view.any_chart_view.setChart(pie)
            }
        })
    }
}
