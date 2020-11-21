package io.redditapp.utils

import android.content.res.Resources
import java.text.SimpleDateFormat
import java.util.*

const val DF_DAY_OF_WEEK = "EEE"
const val DF_WEEK_DAY_MONTH = "EEE, dd MMM"
const val DF_DATE_STANDARD = "yyyy-MM-dd HH:mm"
const val DF_HOUR = "HH"

const val ONE_MINUTE: Long = 1000 * 60
const val ONE_HOUR: Long = ONE_MINUTE * 60
const val ONE_DAY: Long = ONE_HOUR * 24
const val ONE_WEEK: Long = ONE_DAY * 7
const val ONE_MONTH: Long = ONE_DAY * 30
const val ONE_YEAR: Long = ONE_DAY * 365

object DateUtils {

    fun formatDateFromMillis(millis: Long?, format: String = DF_WEEK_DAY_MONTH): String {
        val d = SimpleDateFormat(format)
        return "" + d.format(if (millis != null) Date(millis) else Date())
    }

    fun formatTimeAgoFromMillis(millis: Long, resources: Resources) {
        val now = Date(System.currentTimeMillis())
        val date = Date(millis)
        val different = System.currentTimeMillis() - millis
        val difDate = Date(different)


    }

}