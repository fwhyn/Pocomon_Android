package com.fwhyn.pocomon.ui.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.ui.info.InfoActivity

class UiConstant {
    companion object{
        const val TAG = "PocomonApp"
        const val SAVE_TAG = "save_tag"
        const val TRY_TO_CATCH = "try_to_catch"

        const val CATCH_DIALOG = "catch_dialog"
        const val CATCH_FAILED_DIALOG = "catch_failed_dialog"
        const val DELETE_DIALOG = "delete_dialog"
        const val LOADING_DIALOG = "loading_dialog"

        const val POKEMON_KEY = "pokemon_key"
        const val ACTIVITY_CODE_KEY = "activity_code_key "

        // activity code
        const val DEFAULT_ACTIVITY_CODE = -1
        const val INFO_ACTIVITY_CODE = 101
        const val CAUGHT_ACTIVITY_CODE = 102

        // timer
        const val MIL_EXIT_DELAY = 3000L
        const val MIL_TIMER_INTERVAL = 1000L

        // other code
        const val MAX_FAILURE = 5
    }
}