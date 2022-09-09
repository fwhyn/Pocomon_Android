package com.fwhyn.pocomon.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fwhyn.pocomon.domain.utils.CustomTimer
import com.fwhyn.pocomon.ui.utils.UiConstant

class MainViewModel : ViewModel() {
    var exit = false
        private set

    private val timer = object : CustomTimer(viewModelScope, UiConstant.MIL_EXIT_DELAY, UiConstant
        .MIL_TIMER_INTERVAL) {
        override fun onTick(remainingTime: Long) {
            // no implementation
        }

        override fun onFinish() {
            exit = false
        }
    }

    fun setExitTimer() {
        exit = true

        if (timer.isFinished) {
            timer.start()
        } else {
            timer.cancel()
            timer.start()
        }
    }
}