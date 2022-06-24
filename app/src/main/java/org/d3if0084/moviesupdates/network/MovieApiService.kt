package org.d3if0084.moviesupdates.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if0084.moviesupdates.model.Movie
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/" +
        "rifqiluthfian/moviesupdates/static-api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
interface MovieApiService {
    @GET("movie.json")
    suspend fun getMovie(): List<Movie>
}
object MovieApi {
    val service: MovieApiService by lazy {
        retrofit.create(MovieApiService::class.java)
    }

    fun getMovieUrl(nama: String): String {
        return "$BASE_URL$nama.jpg"
    }
}
enum class ApiStatus { LOADING, SUCCESS, FAILED }