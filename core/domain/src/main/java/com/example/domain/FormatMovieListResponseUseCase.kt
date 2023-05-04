package com.example.domain

import com.example.model.response.Response
import com.example.model.ui.Movie
import javax.inject.Inject

private const val IMAGE_BASE_PATH = "https://image.tmdb.org/t/p/w500"

class FormatMovieListResponseUseCase @Inject constructor() {

    operator fun invoke(response: Response): List<Movie> {
        return response.result?.map { movieResponse ->
            Movie(
                name = movieResponse.name ?: "",
                overview = movieResponse.overview ?: "",
                posterPath = "$IMAGE_BASE_PATH${movieResponse.posterPath ?: ""}",
                firstAirDate = movieResponse.firstAirDate ?: ""
            )
        } ?: emptyList()

    }
}