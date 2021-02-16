package com.codepath.greghealy.flixster.models

import org.json.JSONArray
import org.json.JSONObject

class Movie(jsonObject: JSONObject) {
    val backdropPath: String = "https://image.tmdb.org/t/p/w342${jsonObject.getString("backdrop_path")}"
    val posterPath: String = "https://image.tmdb.org/t/p/w342${jsonObject.getString("poster_path")}"
    val title: String = jsonObject.getString("title")
    val overview: String = jsonObject.getString("overview")

    companion object {
        fun fromJsonArray(movieJsonArray: JSONArray) : List<Movie> {
            val movies = ArrayList<Movie>()
            for(i in 0 until movieJsonArray.length()) {
                movies.add(Movie(movieJsonArray.getJSONObject(i)))
            }
            return movies
        }
    }
}