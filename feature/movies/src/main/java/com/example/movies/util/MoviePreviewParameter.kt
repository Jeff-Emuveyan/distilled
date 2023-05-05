package com.example.movies.util

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.model.ui.Movie

class MoviePreviewParameter: PreviewParameterProvider<Movie> {

    override val values: Sequence<Movie> = sequenceOf(
        Movie(666,
            "Tom and Jerry",
            "When Walter White, a New Mexico chemistry teacher," +
                " is diagnosed with Stage III cancer and given a prognosis of " +
                "only two years left to live. He becomes filled with a sense of " +
                "fearlessness and an unrelenting desire to secure his family's financial " +
                "future at any cost as he enters the dangerous world of drugs and crime.",
            "https://image.tmdb.org/t/p/w500/uKvVjHNqB5VmOrdxqAt2F7J78ED.jpg",
            "2020-05-06", 1.4)
    )
}