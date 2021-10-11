package com.subiyantoro.panintitest.models

import java.io.Serializable

data class Wind(
    var speed: Double,
    var deg: Int,
    var gust: Double
): Serializable
