package jawoheer.example.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseAsteroid::class, DatabasePictureOfDay::class], version = 2, exportSchema = false)
abstract class AsteroidDatabase: RoomDatabase(){
    abstract val asteroidDao: AsteroidDao

}

private lateinit var INSTANCE: AsteroidDatabase

fun getDatabase(context: Context): AsteroidDatabase{
    synchronized(AsteroidDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                AsteroidDatabase::class.java,
                "asteroids").fallbackToDestructiveMigration().build()
        }
    }
    return INSTANCE
}


