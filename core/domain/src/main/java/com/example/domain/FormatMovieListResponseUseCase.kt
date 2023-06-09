package com.example.domain

import com.example.model.response.Response
import com.example.model.ui.Movie
import javax.inject.Inject

private const val IMAGE_BASE_PATH = "https://image.tmdb.org/t/p/w500"

class FormatMovieListResponseUseCase @Inject constructor(): IFormatMovieListResponseUseCase {

    override operator fun invoke(response: Response): List<Movie> {
        return response.results?.map { movieResponse ->
            Movie(
                id = movieResponse.id ?: 1,
                name = movieResponse.name ?: "",
                overview = movieResponse.overview ?: "",
                posterPath = "$IMAGE_BASE_PATH${movieResponse.posterPath ?: ""}",
                firstAirDate = movieResponse.firstAirDate ?: "",
                voteAverage = movieResponse.voteAverage ?: 1.0
            )
        } ?: emptyList()

    }
}