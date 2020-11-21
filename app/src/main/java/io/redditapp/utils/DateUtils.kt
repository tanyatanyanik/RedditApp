package io.redditapp.utils

import android.content.res.Resources
import io.redditapp.R
import java.text.SimpleDateFormat
import java.util.*

const val DF_WEEK_DAY_MONTH = "EEE, dd MMM"
const val DF_DATE_STANDARD = "yyyy-MM-dd HH:mm"

const val ONE_MINUTE: Long = 1000 * 60
const val ONE_HOUR: Long = ONE_MINUTE * 60
const val ONE_DAY: Long = ONE_HOUR * 24
const val ONE_WEEK: Long = ONE_DAY * 7
const val ONE_MONTH: Long = ONE_DAY * 30
const val ONE_YEAR: Long = ONE_DAY * 365

object DateUtils {

    fun formatDateFromMillis(millis: Long?, format: String = DF_DATE_STANDARD): String {
        val d = SimpleDateFormat(format)
        return "" + d.format(if (millis != null) Date(millis) else Date())
    }

    fun formatTimeAgoFromMillis(millis: Long, resources: Resources): String {
        val dif = System.currentTimeMillis() - millis
        if (dif > ONE_YEAR) {
            val n = (dif / ONE_YEAR).toInt()
            return if (n == 1) resources.getString(R.string.one_year_ago)
            else resources.getString(R.string.years_ago, n.toString())
        }
        if (dif > ONE_MONTH) {
            val n = (dif / ONE_MONTH).toInt()
            return if (n == 1) resources.getString(R.string.one_month_ago)
            else resources.getString(R.string.months_ago, n.toString())
        }
        if (dif > ONE_WEEK) {
            val n = (dif / ONE_WEEK).toInt()
            return if (n == 1) resources.getString(R.string.one_weeks_ago)
            else resources.getString(R.string.weeks_ago, n.toString())
        }
        if (dif > ONE_DAY) {
            val n = (dif / ONE_DAY).toInt()
            return if (n == 1) resources.getString(R.string.one_day_ago)
            else resources.getString(R.string.days_ago, n.toString())
        }
        if (dif > ONE_HOUR) {
            val n = (dif / ONE_HOUR).toInt()
            return if (n == 1) resources.getString(R.string.one_hour_ago)
            else resources.getString(R.string.hours_ago, n.toString())
        }
        if (dif > ONE_MINUTE) {
            val n = (dif / ONE_MINUTE).toInt()
            return if (n == 1) resources.getString(R.string.one_minute_ago)
            else resources.getString(R.string.minutes_ago, n.toString())
        }
        return resources.getString(R.string.now)
    }

}