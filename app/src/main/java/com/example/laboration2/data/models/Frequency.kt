package com.example.laboration2.data.models

sealed class Frequency {
    data class TimeBased(val hours: Int, val minutes: Int) : Frequency()
    data class RepetitionBased(val timesPerWeek: Int) : Frequency()

    override fun toString(): String {
        return when (this) {
            is TimeBased -> {
                val hoursText = if (hours > 0) "$hours hours " else ""
                val minutesText = if (minutes > 0) "$minutes minutes" else ""
                "$hoursText$minutesText".trim()
            }
            is RepetitionBased -> "$timesPerWeek times per week"
        }
    }
}
