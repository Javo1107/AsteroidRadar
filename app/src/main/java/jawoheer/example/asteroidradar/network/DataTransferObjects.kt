package jawoheer.example.asteroidradar.network


import com.squareup.moshi.Json
import jawoheer.example.asteroidradar.database.DatabaseAsteroid
import jawoheer.example.asteroidradar.database.DatabasePictureOfDay


//data class NetworkAsteroidContainer(val asteroids: List<NetworkAsteroid>)
data class NetworkPictureOfDay(
    val date: String,
    @Json(name = "media_type") val mediaType: String,
    val title: String,
    val url: String
)

fun NetworkPictureOfDay.asDatabaseModel(): DatabasePictureOfDay {
    return (
            DatabasePictureOfDay(
                date,
                mediaType,
                title,
                url
            )
            )
}

data class NetworkAsteroid(
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

fun List<NetworkAsteroid>.asDatabaseModel(): Array<DatabaseAsteroid> {
    return map {
        DatabaseAsteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toTypedArray()
}