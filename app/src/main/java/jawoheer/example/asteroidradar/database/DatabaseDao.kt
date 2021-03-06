package jawoheer.example.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao{
    @Query("select * from database_asteroid_list")
    fun getAsteroids(): LiveData<List<DatabaseAsteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: DatabaseAsteroid)

    @Query("select * from picture_of_day")
    fun getPictureOfDay(): LiveData<DatabasePictureOfDay>

    @Insert
    fun insertPictureOfDay(picOfDay: DatabasePictureOfDay)
}
