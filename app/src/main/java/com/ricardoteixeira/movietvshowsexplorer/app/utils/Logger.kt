package com.ricardoteixeira.movietvshowsexplorer.app.utils
import com.google.firebase.crashlytics.FirebaseCrashlytics

import android.util.Log

var isUnitTest = false
const val TAG = "AppDebug"
const val DEBUG = true

fun printLogD(className: String?, message: String) {

    if (DEBUG && !isUnitTest) {
        Log.d(TAG, "$className: $message")
    } else if (DEBUG && isUnitTest) {
        println("$className: $message")
    }
}

fun cLog(msg: String?) {
    msg?.let {
        if(!DEBUG){
            FirebaseCrashlytics.getInstance().log(it)
        }
    }
}