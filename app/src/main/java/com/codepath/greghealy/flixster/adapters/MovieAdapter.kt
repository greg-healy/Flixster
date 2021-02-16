package com.codepath.greghealy.flixster.adapters

import android.content.Context
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.greghealy.flixster.R
import com.codepath.greghealy.flixster.models.Movie

class MovieAdapter(private val context: Context, private val movies: List<Movie>): RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle : TextView = this.itemView.findViewById(R.id.tvTitle)
        private val tvOverview : TextView = this.itemView.findViewById(R.id.tvOverview)
        private val ivPoster: ImageView = this.itemView.findViewById(R.id.ivPoster)

        fun bind(movie: Movie) {
            tvTitle.text = movie.title
            tvOverview.text = movie.overview
            val imageUrl = if (itemView.context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                movie.backdropPath
            } else {
                movie.posterPath
            }
            Glide.with(itemView.context).load(imageUrl).into(ivPoster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ViewHolder(movieView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}