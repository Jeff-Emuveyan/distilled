package com.example.model.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(val id: Long?,
                         val name: String?,
                         val overview: String?,
                         @SerializedName("poster_path")
                         val posterPath: String?,
                         @SerializedName("first_air_date")
                         val firstAirDate: String?,
                         @SerializedName("vote_average")
                         val voteAverage: Double?)