package com.example.data

import com.example.model.response.MovieResponse
import com.example.model.response.Response

fun getSuccessfulResponse(): Response {
    return Response(
        page = 1,
        results = listOf(
            MovieResponse(
                1,
                "Lord of the Rings",
                "Description",
                "www.wwe.com",
                "2020-04-30",
                9.9
            ),
            MovieResponse(
                2,
                "King Kong",
                "Description",
                "www.wwe.com",
                "2020-05-31",
                9.9
            )
        )
    )
}