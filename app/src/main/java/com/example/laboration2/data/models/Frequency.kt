package com.example.laboration2.data.models

sealed class Frequency {
    data class TimeMinuteBased(val minutes: Int) : Frequency()
    data class TimeHourBased(val hours: Int) : Frequency()
    data class RepetitionBased(val timesPerWeek: Int) : Frequency()

    override fun toString(): String {
        return when (this) {
            is TimeMinuteBased -> "$minutes minutes"
            is TimeHourBased -> "$hours hours"
            is RepetitionBased -> "$timesPerWeek times per week"
        }
    }
}
