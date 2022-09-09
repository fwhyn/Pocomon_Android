package com.fwhyn.pocomon.ui.utils

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.ui.info.InfoActivity

class UiConstant {
    companion object{
        const val CATCH_DIALOG = "catch_dialog"
        const val POKEMON_KEY = "pokemon_key"

        const val INFO_ACTIVITY_CODE = 99

        const val MIL_EXIT_DELAY = 3000L
        const val MIL_TIMER_INTERVAL = 1000L

        fun startInfoActivity(activity: Activity, launcher: ActivityResultLauncher<Intent>, pokemon: Pokemon) {
            val intent = Intent(activity, InfoActivity::class.java).apply { putExtra(POKEMON_KEY, pokemon) }
            launcher.launch(intent)
        }
    }
}