package jawoheer.example.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import jawoheer.example.asteroidradar.database.AsteroidDatabase
import jawoheer.example.asteroidradar.database.asDomainModel
import jawoheer.example.asteroidradar.domain.Asteroid
import jawoheer.example.asteroidradar.network.AsteroidApi
import jawoheer.example.asteroidradar.network.asDatabaseModel
import jawoheer.example.asteroidradar.network.parseStringToAsteroidList
import jawoheer.example.asteroidradar.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsteroidsRepository (private val database: AsteroidDatabase){

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAsteroids()){
            it.asDomainModel()
        }

    suspend fun refreshData(){
        withContext(Dispatchers.IO){
            val responseString = AsteroidApi.retrofitService.getJsonObject("2021-08-10",
                "2021-08-16",
                Constants.API_KEY).await()
            val asteroids = parseStringToAsteroidList(responseString)
            database.asteroidDao.insertAll(*asteroids.asDatabaseModel())
        }
    }
}