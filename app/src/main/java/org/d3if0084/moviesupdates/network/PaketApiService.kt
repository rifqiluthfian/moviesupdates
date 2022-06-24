package org.d3if0084.moviesupdates.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if0084.moviesupdates.model.Paket
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

interface PaketApiService {
    @GET("paket.json")
    suspend fun getPaket(): List<Paket>
}
object PaketApi {
    val service: PaketApiService by lazy {
        retrofit.create(PaketApiService::class.java)
    }

    fun getPaketUrl(nama: String): String {
        return "$BASE_URL$nama.png"
    }
}
enum class ApiStatus2 { LOADING, SUCCESS, FAILED }