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

        const val CATCH_DIALOG = "catch_dialog"
        const val POKEMON_KEY = "pokemon_key"
        const val ACTIVITY_CODE_KEY = "pokemon_key"

        // activity code
        const val DEFAULT_ACTIVITY_CODE = -1
        const val INFO_ACTIVITY_CODE = 101
        const val CAUGHT_ACTIVITY_CODE = 102

        const val MIL_EXIT_DELAY = 3000L
        const val MIL_TIMER_INTERVAL = 1000L

        fun startInfoActivity(activity: Activity, launcher: ActivityResultLauncher<Intent>, pokemon: Pokemon) {
            startInfoActivity(activity, launcher, pokemon, DEFAULT_ACTIVITY_CODE)
        }

        fun startInfoActivity(activity: Activity, launcher: ActivityResultLauncher<Intent>, pokemon: Pokemon,
                              activityCode: Int = DEFAULT_ACTIVITY_CODE) {
            val bundle = Bundle().apply {
                putInt(ACTIVITY_CODE_KEY, activityCode)

                // I don't know why but please put this in the end of the put
                putSerializable(POKEMON_KEY, pokemon)
            }

            val intent = Intent(activity, InfoActivity::class.java).apply {
                putExtras(bundle)
            }
            launcher.launch(intent)
        }
    }
}