package com.subiyantoro.panintitest.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["name"], unique = true)])
data class City(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String
)
