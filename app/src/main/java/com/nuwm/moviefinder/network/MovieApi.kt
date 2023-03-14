package com.nuwm.moviefinder.network

import com.nuwm.moviefinder.models.CastList
import com.nuwm.moviefinder.models.MovieDetails
import com.nuwm.moviefinder.models.MovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieApi {

    @GET("3/movie/top_rated/?api_key=a143b2488bf72e7081edb871e0db3a7c")
    fun getTopMovie(@Query("page") page: Int): Call<MovieList>

    @GET("3/movie/now_playing/?api_key=a143b2488bf72e7081edb871e0db3a7c")
    fun getNewMovie(@Query("page") page: Int): Call<MovieList>

    @GET("3/movie/upcoming/?api_key=a143b2488bf72e7081edb871e0db3a7c")
    fun getUpcomingMovie(@Query("page") page: Int): Call<MovieList>

    @GET("3/movie/popular/?api_key=a143b2488bf72e7081edb871e0db3a7c")
    fun getPopularMovie(@Query("page") page: Int): Call<MovieList>

    @GET("3/movie/{id}?api_key=a143b2488bf72e7081edb871e0db3a7c")
    fun getMovieInfoFromId(
        @Path("id") idMovie: String
    ): Call<MovieDetails>

    @GET("3/movie/{id}/credits?api_key=a143b2488bf72e7081edb871e0db3a7c")
    fun getCastList(@Path("id") idMovie: String?): Call<CastList>

}