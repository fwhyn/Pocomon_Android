package com.fwhyn.pocomon.domain.utils.loader

class Loader(var status: ProcessStatus, var loadOwner: String) {
    enum class ProcessStatus {
        LOADING,
        FINISH,
    }
}