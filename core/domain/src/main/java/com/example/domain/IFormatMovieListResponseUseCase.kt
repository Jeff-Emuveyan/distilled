package com.example.domain

import com.example.model.response.Response
import com.example.model.ui.Movie

interface IFormatMovieListResponseUseCase {

    operator fun invoke(response: Response): List<Movie>
}