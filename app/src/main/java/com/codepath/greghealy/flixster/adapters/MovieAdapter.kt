package com.codepath.greghealy.flixster.adapters

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.greghealy.flixster.MovieDetailsActivity
import com.codepath.greghealy.flixster.R
import com.codepath.greghealy.flixster.models.Movie
import com.codepath.greghealy.flixster.models.MovieBackup
import org.parceler.Parcels

class MovieAdapter(private val context: Context, private val movies: List<Movie>): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
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
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition

            if (position != RecyclerView.NO_POSITION) {
                val movie: Movie= movies[position]
                val intent = Intent(context, MovieDetailsActivity::class.java)
                intent.putExtra(Movie::class.java.simpleName, Parcels.wrap(movie))
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(movieView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int)  {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}