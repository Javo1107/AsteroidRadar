package jawoheer.example.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import jawoheer.example.asteroidradar.R
import jawoheer.example.asteroidradar.database.AsteroidDatabase
import jawoheer.example.asteroidradar.database.getDatabase
import jawoheer.example.asteroidradar.domain.Asteroid
import jawoheer.example.asteroidradar.util.Constants
import jawoheer.example.asteroidradar.domain.PictureOfDay
import jawoheer.example.asteroidradar.network.AsteroidApi
import jawoheer.example.asteroidradar.network.parseStringToAsteroidList
import jawoheer.example.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid?>()
    val navigateToSelectedAsteroid: LiveData<Asteroid?>
        get() = _navigateToSelectedAsteroid

    private val app = application
    private val database = getDatabase(app)
    private val asteroidsRepository = AsteroidsRepository(database, app.applicationContext)

    init {
//        getAsteroidObject()
//        setImageOfDay()
//        viewModelScope.launch {
//            asteroidsRepository.refreshData()
//        }
    }

    val astero = asteroidsRepository.asteroids
    val picOfDay = asteroidsRepository.picOfDay
    fun displayAsteroidDetails(asteroid: Asteroid) {
        _navigateToSelectedAsteroid.value = asteroid
    }

    fun displayAsteroidDetailsComplete() {
        _navigateToSelectedAsteroid.value = null
    }

    private fun setImageOfDay() {
        viewModelScope.launch {
            try {
                asteroidsRepository.refreshPictureOfDay()
            } catch (e: Exception) {
                Log.i("MainViewModel", e.toString())
            }
        }
    }

    private fun getAsteroidObject() {
        viewModelScope.launch {
            try {
                val responseString = AsteroidApi.retrofitService.getJsonObject(
                    "2021-08-10",
                    "2021-08-16",
                    app.resources.getString(R.string.ASTEROID_API_KEY)
                )
//                _asteroids.value = parseStringToAsteroidList(responseString)
            } catch (e: Exception) {
                Log.i("MainViewModel", e.toString())
            }
        }
    }


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}