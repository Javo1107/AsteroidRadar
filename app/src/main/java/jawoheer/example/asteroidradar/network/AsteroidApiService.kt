package jawoheer.example.asteroidradar.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import jawoheer.example.asteroidradar.util.Constants
import jawoheer.example.asteroidradar.domain.PictureOfDay
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


interface AsteroidApiService {
    @GET("/neo/rest/v1/feed")
    suspend fun getJsonObject(@Query("start_date") start_date: String?,
                              @Query("end_date") end_date: String?,
                                @Query("api_key") api_key: String): Deferred<String>

    @GET("/planetary/apod")
    suspend fun getPictureOfDay(@Query("api_key") apiKey: String): PictureOfDay
}

var okHttpClient = OkHttpClient.Builder()
    .connectTimeout(1, TimeUnit.MINUTES)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)
    .build()


private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder().client(okHttpClient)
    .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

object AsteroidApi {
    val retrofitService: AsteroidApiService by lazy { retrofit.create(AsteroidApiService::class.java) }
}