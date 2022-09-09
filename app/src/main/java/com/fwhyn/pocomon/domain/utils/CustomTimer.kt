package com.fwhyn.pocomon.domain.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

abstract class CustomTimer(private val scope: CoroutineScope, private val finishTime: Long, private val interval: Long) {
    private val mutex: Mutex = Mutex()
    private var job: Job? = null

    var isFinished: Boolean = true
        private set

    abstract fun onTick(remainingTime: Long)
    abstract fun  onFinish()

    private suspend fun timer(finishTime: Long = 0, interval: Long = 0) {
        mutex.withLock {
            var timeToFinish = finishTime
            isFinished = false

            while (timeToFinish > 0) {
                onTick(timeToFinish)
                delay(interval)
                timeToFinish -= interval
            }

            // finish counting
            isFinished = true
            onFinish()
        }
    }

    fun cancel(): Boolean {
        return if (job?.isActive == true) {
            job?.cancel()
            true
        } else {
            false
        }
    }

    fun start() {
        job = scope.launch {
            timer(finishTime, interval)
        }
    }
}