package com.subiyantoro.panintitest.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(indices = [Index(value = ["id", "name"], unique = true)], tableName = "Weather")
data class WeatherResponse(
    @Ignore var coord: Coord? = null,
    @Ignore var weather: List<Weather>? = null,
    var base: String? = null,
    @Ignore var main: Main? = null,
    var visibility: String? = null,
    @Ignore var wind: Wind? = null,
    @Ignore var clouds: Clouds? = null,
    @Ignore var dt: Long? = null,
    @Ignore var sys: Sys? = null,
    var timezone: Int? = null,
    @PrimaryKey var id: Long? = null,
    var name: String,
    var cod: Int? = null
): Serializable {
    constructor(): this(null, null, null, null, null, null, null, null, null, null, null, "")
}
