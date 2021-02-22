package com.ricardoteixeira.movietvshowsexplorer.app.utils

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateUtil
@Inject
constructor(
    private val dateFormat: SimpleDateFormat
) {

    // date format YYYY-MM-DD HH:mm:ss

    //recycler view timestamp
    fun removeTimeFromDateString(sd: String): String {
        return sd.substring(0, sd.indexOf(""))
    }

    //Firebase timestamp conversion
    fun convertFirebaseTimestampToStringDate(timestamp: Timestamp): String {
        return dateFormat.format(timestamp.toDate())
    }

    fun convertStringDateToFirebaseTimestamp(date: String): Timestamp {
        return com.google.firebase.Timestamp(dateFormat.parse(date))
    }

    fun getCurrentTimeStamp(): String {
        return dateFormat.format(Date())
    }

}