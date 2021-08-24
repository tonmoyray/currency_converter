package com.ray.currencyconverter.data.local.db.entity

import androidx.room.*
import com.ray.currencyconverter.utils.MapTypeConverter
import org.jetbrains.annotations.NotNull

/**
 * <h1>Rates</h1>
 * <p>
 *
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
@Entity(
    tableName = "rate"
)
data class Rate(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rate_id")
    @NotNull
    var rateId: Int?,

    @ColumnInfo(name = "timestamp", index = true)
    @NotNull
    var timestamp: Long,

    @ColumnInfo(name = "source")
    @NotNull
    var source: String,

    @ColumnInfo(name = "success")
    @NotNull
    var success: Boolean,

    @ColumnInfo(name = "errorMessage")
    @NotNull
    var errorMessage: String,

    @ColumnInfo(name = "rates")
    @NotNull
    var rates: Map<String, Double>,

    @ColumnInfo(name = "currencies")
    @TypeConverters(MapTypeConverter::class)
    @NotNull
    var currencies: List<String>
)