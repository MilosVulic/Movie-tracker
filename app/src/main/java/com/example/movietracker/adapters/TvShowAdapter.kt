package com.example.movietracker.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.movietracker.activities.ContentDetails
import com.example.movietracker.R
import com.example.movietracker.config.GlideApp
import com.example.movietracker.dao.tvshow.TvShow
import com.example.movietracker.view_models.TvShowViewModel

class TvShowAdapter(var tvShowsViewModel: TvShowViewModel) :
    RecyclerView.Adapter<TvShowAdapter.TvShowHolder>() {

    private var listTvShows: List<TvShow> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.tvshow_row, parent, false)
        return TvShowHolder(itemView)
    }

    override fun onBindViewHolder(holder: TvShowHolder, position: Int) {
        val currentTvShow = listTvShows[position]
        holder.textViewTvShowName.text = currentTvShow.tvshowName
        holder.textViewDescription.text = currentTvShow.tvshowDescription
        holder.textViewTvShowRelealse.text = currentTvShow.tvshowYear.toString()
        holder.textViewNumberOfSeassons.text = currentTvShow.numberOfSeassons.toString()
        displayImage(holder.itemView.context, holder.imageView, currentTvShow.tvshowUri)

        holder.buttonDelete.setOnClickListener {
            tvShowsViewModel.deleteTvShow(currentTvShow)
        }

        holder.cardViewTvShow.setOnClickListener { v ->
            val intent = Intent(v.context, ContentDetails::class.java)
            intent.putExtra("ob", currentTvShow)
            v.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listTvShows.size
    }

    fun setTvShows(tvShows: List<TvShow>) {
        this.listTvShows = tvShows
        notifyDataSetChanged()
    }

    private fun displayImage(context: Context, imageView: ImageView, imageUri: String?) {
        val uri = imageUri?.toUri()
        GlideApp.with(context)
            .load(uri)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }

    inner class TvShowHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var textViewTvShowName: TextView = itemView.findViewById(R.id.textViewTvShowName)
        var textViewDescription: TextView = itemView.findViewById(R.id.textViewTvShowDescription)
        var textViewTvShowRelealse: TextView = itemView.findViewById(R.id.textViewTvShowYear)
        var textViewNumberOfSeassons: TextView = itemView.findViewById(R.id.textViewNumberOfSeassons)
        var buttonDelete: Button = itemView.findViewById(R.id.buttonDeleteTvShow)
        var imageView: ImageView = itemView.findViewById(R.id.imageViewTvShow)
        var cardViewTvShow: CardView = itemView.findViewById(R.id.card_view_tv_show)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
        }
    }
}