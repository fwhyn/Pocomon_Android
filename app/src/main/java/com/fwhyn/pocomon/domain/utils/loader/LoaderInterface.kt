package com.fwhyn.pocomon.domain.utils.loader

import kotlinx.coroutines.CoroutineScope

interface LoaderInterface {
    fun onStart(tag: String)
    fun onLoad(coroutineScope: CoroutineScope)
    fun onFinish(tag: String)
}