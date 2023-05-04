package com.example.network.movie
import com.example.model.response.Response
import retrofit2.http.GET

private const val API_KEY = "25a8f80ba018b52efb64f05140f6b43c"
private const val LANGUAGE = "en-US"

interface MovieApi {

    @GET("top_rated")
    suspend fun getMovies(apiKey: String = API_KEY,
                          language: String = LANGUAGE,
                          pageNumber: Int): Response?
}