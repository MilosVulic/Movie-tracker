package com.example.movietracker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.movietracker.R
import com.example.movietracker.config.GlideApp
import com.example.movietracker.dao.movie.Movie


class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    private var listMovies: List<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_row, parent, false)
        return MovieHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val currentMovie = listMovies[position]
        holder.textViewMovieName.text = currentMovie.movieName
        holder.textViewMovieDuration.text = currentMovie.movieDuration.toString() + " min"
        holder.textViewMovieYearRelease.text = currentMovie.movieYear.toString()
        holder.textViewMovieGenre.text = currentMovie.movieGenre
        displayImage(holder.itemView.context, holder.imageView, currentMovie.movieUri)
    }

    override fun getItemCount(): Int {
        return listMovies.size
    }

    fun setMovies(movies: List<Movie>) {
        this.listMovies = movies
        notifyDataSetChanged()
    }

    fun getData(): List<Movie?>? {
        return listMovies
    }

    private fun displayImage(context: Context, imageView: ImageView, imageUri: String?) {
        val uri = imageUri?.toUri()
        GlideApp.with(context)
            .load(uri)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }

    inner class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewMovieName: TextView = itemView.findViewById(R.id.textVieMovieName)
        var textViewMovieGenre: TextView = itemView.findViewById(R.id.textViewMovieGenre)
        var textViewMovieYearRelease: TextView = itemView.findViewById(R.id.textViewMovieYear)
        var textViewMovieDuration: TextView = itemView.findViewById(R.id.textViewMovieDuration)
        var imageView: ImageView = itemView.findViewById(R.id.imageViewMovie)
    }
}