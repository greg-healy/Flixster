package com.codepath.greghealy.flixster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.codepath.greghealy.flixster.adapters.MovieAdapter
import com.codepath.greghealy.flixster.models.Movie
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
    private val TAG = "MainActivity"
    private var movies = ArrayList<Movie>()
    private val movieAdapter by lazy { MovieAdapter(this, movies) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create the adapter
        val rvMovies = findViewById<RecyclerView>(R.id.rvMovies)
        rvMovies.adapter = movieAdapter
        rvMovies.layoutManager = LinearLayoutManager(this)

        val client = AsyncHttpClient()
        client.get(NOW_PLAYING_URL, object: JsonHttpResponseHandler() {
            override fun onFailure(statusCode: Int, headers: Headers, response: String, throwable: Throwable) {
                Log.d(TAG, "onFailure")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.d(TAG, "onSuccess")
                val jsonObject: JSONObject = json.jsonObject
                try {
                    val results: JSONArray = jsonObject.getJSONArray("results")
                    Log.i(TAG, "Results: $results")
                    movies.addAll(Movie.fromJsonArray(results))
                    movieAdapter.notifyDataSetChanged()
                    Log.i(TAG, "Movies: ${movies.size}")
                } catch (e: JSONException) {
                    Log.d(TAG, "Hit json exception", e)
                    e.printStackTrace()
                }
            }
        })
    }
}