package com.codepath.greghealy.flixster

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.codepath.greghealy.flixster.models.Movie
import com.codepath.greghealy.flixster.models.MovieBackup
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import kotlinx.android.synthetic.main.activity_movie_details.*
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.parceler.Parcels

class MovieDetailsActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private val TAG = MovieDetailsActivity::class.java.simpleName
    private var videoId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val movie = Parcels.unwrap<Movie>(intent.getParcelableExtra(Movie::class.java.simpleName))
        val VIDEOS_URL = "https://api.themoviedb.org/3/movie/${movie.id}/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"

        val client = AsyncHttpClient()
        client.get(VIDEOS_URL, object: JsonHttpResponseHandler() {
            override fun onFailure(p0: Int, p1: Headers?, p2: String?, p3: Throwable?) {
                Log.d(TAG, "onFailure")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                val jsonObject: JSONObject = json.jsonObject
                try {
                    val results: JSONArray = jsonObject.getJSONArray("results")
                    if (results.length() == 0) {
                        return
                    }
                    videoId = results.getJSONObject(0).getString("key")
                    val playerView: YouTubePlayerView = findViewById(R.id.player)
                    playerView.initialize(getString(R.string.youtube_api_key), this@MovieDetailsActivity)
                } catch (e: JSONException) {
                    Log.d(TAG, "Hit json exception", e)
                    e.printStackTrace()
                }
            }
        })

        tvDetailsTitle.text = movie.title
        tvDetailsOverview.text = movie.overview
        rbVoteAverage.rating = (movie.voteAverage / 2.0f).toFloat()
    }

    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        p1!!.cueVideo(videoId)
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        Log.e(TAG, "onInitializationFailure")
    }
}