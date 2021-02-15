package com.ricardoteixeira.movietvshowsexplorer.app.presentation.utils

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation

fun View.blink(
    times: Int = Animation.INFINITE,
    duration: Long = 500L,
    offset: Long = 20L,
    minAlpha: Float = 0.0f,
    maxAlpha: Float = 1.0f,
    repeatMode: Int = Animation.REVERSE
) {
    startAnimation(AlphaAnimation(minAlpha, maxAlpha).also { anim ->
        anim.duration = duration
        anim.startOffset = offset
        anim.repeatMode = repeatMode
        anim.repeatCount = times
    })
}