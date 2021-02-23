package com.codepath.greghealy.flixster

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.codepath.greghealy.flixster.adapters.MovieAdapter
import com.codepath.greghealy.flixster.models.Movie
import com.codepath.greghealy.flixster.models.MovieBackup
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private val NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
    private var movies: ArrayList<Movie> = ArrayList()
    private val movieAdapter by lazy { MovieAdapter(this, movies )}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                    movies.addAll(Movie.fromJsonArray(results))
                    movieAdapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    Log.d(TAG, "Hit json exception", e)
                    e.printStackTrace()
                }
            }
        })
    }
}