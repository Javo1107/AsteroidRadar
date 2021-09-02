package jawoheer.example.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import jawoheer.example.asteroidradar.domain.Asteroid
import jawoheer.example.asteroidradar.domain.PictureOfDay

@Entity(tableName = "picture_of_day")
data class DatabasePictureOfDay constructor(
    @PrimaryKey
    val date: String,
    val mediaType: String,
    val title: String,
    val url: String
)

fun DatabasePictureOfDay.asDomainModel(): PictureOfDay{
    return (
            PictureOfDay(
                date,
                mediaType,
                title,
                url)
            )
}

@Entity(tableName = "database_asteroid_list")
data class DatabaseAsteroid constructor(
    @PrimaryKey
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid>{
    return map{
        Asteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}