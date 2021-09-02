package jawoheer.example.asteroidradar.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import jawoheer.example.asteroidradar.R
import jawoheer.example.asteroidradar.database.AsteroidDatabase
import jawoheer.example.asteroidradar.database.asDomainModel
import jawoheer.example.asteroidradar.domain.Asteroid
import jawoheer.example.asteroidradar.domain.PictureOfDay
import jawoheer.example.asteroidradar.network.AsteroidApi
import jawoheer.example.asteroidradar.network.asDatabaseModel
import jawoheer.example.asteroidradar.network.parseStringToAsteroidList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsteroidsRepository(private val database: AsteroidDatabase, private val context: Context) {

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()) {
            it.asDomainModel()
        }

    suspend fun refreshData() {
        withContext(Dispatchers.IO) {
            val responseString = AsteroidApi.retrofitService.getJsonObject(
                "2021-08-10",
                "2021-08-16",
                context.resources.getString(R.string.ASTEROID_API_KEY)
            )
            val asteroids = parseStringToAsteroidList(responseString)
            database.asteroidDao.insertAll(*asteroids.asDatabaseModel())
        }
    }

    val picOfDay: LiveData<PictureOfDay> =
        Transformations.map(database.asteroidDao.getPictureOfDay()) {
            it?.asDomainModel()
        }

    suspend fun refreshPictureOfDay() {
        withContext(Dispatchers.IO) {
            val pic =
                AsteroidApi.retrofitService.getPictureOfDay(context.resources.getString(R.string.ASTEROID_API_KEY))
            database.asteroidDao.insertPictureOfDay(pic.asDatabaseModel())
        }
    }
}