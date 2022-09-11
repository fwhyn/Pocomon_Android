package com.fwhyn.pocomon.ui.utils

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import com.fwhyn.pocomon.domain.model.Pokemon
import com.fwhyn.pocomon.ui.info.InfoActivity

class UiUtil {
    companion object{
        fun startInfoActivity(activity: Activity, launcher: ActivityResultLauncher<Intent>, pokemon: Pokemon) {
            startInfoActivity(activity, launcher, pokemon, UiConstant.DEFAULT_ACTIVITY_CODE)
        }

        fun startInfoActivity(activity: Activity, launcher: ActivityResultLauncher<Intent>, pokemon: Pokemon,
                              activityCode: Int = UiConstant.DEFAULT_ACTIVITY_CODE
        ) {
            val bundle = Bundle().apply {
                putInt(UiConstant.ACTIVITY_CODE_KEY, activityCode)
                putSerializable(UiConstant.POKEMON_KEY, pokemon)
            }

            val intent = Intent(activity, InfoActivity::class.java).apply {
                putExtras(bundle)
            }

            launcher.launch(intent)
        }

        fun disableEditText(editText: EditText) {
            setEditText(editText, focusable = false, enabled = false, cursorVisible = false)
//           keyListener = null
//           setBackgroundColor(Color.TRANSPARENT)
        }

        fun enableEditText(editText: EditText) {
            setEditText(editText, focusable = true, enabled = true, cursorVisible = true)
            editText.isFocusableInTouchMode = true
        }

        private fun setEditText(
            editText: EditText,
            focusable: Boolean,
            enabled: Boolean,
            cursorVisible: Boolean) {
            with(editText) {
                isFocusable = focusable
                isEnabled = enabled
                isCursorVisible = cursorVisible
                setBackgroundColor(Color.TRANSPARENT)
            }
        }
    }
}