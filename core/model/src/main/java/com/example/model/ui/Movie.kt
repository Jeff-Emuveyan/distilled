package com.example.model.ui

data class Movie(val id: Long,
                 val name: String,
                 val overview: String,
                 val posterPath: String,
                 val firstAirDate: String,
                 val voteAverage: Double)