package com.subiyantoro.panintitest.models

import java.io.Serializable

data class Sys(
    var country: String,
    var sunrise: Long,
    var sunset: Long
): Serializable
