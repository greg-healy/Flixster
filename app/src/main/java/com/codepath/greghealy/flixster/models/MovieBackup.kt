package com.codepath.greghealy.flixster.models

import org.json.JSONArray
import org.json.JSONObject
import org.parceler.Parcel
import java.util.*

@Parcel
class MovieBackup {
    lateinit var backdropPath: String
    lateinit var posterPath: String
    lateinit var title: String
    lateinit var overview: String
    var voteAverage: Double = 0.0

    constructor() {}

    constructor(jsonObject: JSONObject) {
        backdropPath = "https://image.tmdb.org/t/p/w342${jsonObject.getString("backdrop_path")}"
        posterPath = "https://image.tmdb.org/t/p/w342${jsonObject.getString("poster_path")}"
        title = jsonObject.getString("title")
        overview = jsonObject.getString("overview")
        voteAverage = jsonObject.getDouble("vote_average")
    }

    companion object {
        @JvmStatic
        fun fromJsonArray(movieJsonArray: JSONArray) : List<MovieBackup> {
            val movies = ArrayList<MovieBackup>()
            for(i in 0 until movieJsonArray.length()) {
                movies.add(MovieBackup(movieJsonArray.getJSONObject(i)))
            }
            return movies
        }
    }
}