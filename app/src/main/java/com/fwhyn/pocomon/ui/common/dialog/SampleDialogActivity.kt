/*
 * Created by   : Yana Wahyuna
 * Update Date  : 2022/06/28
 *
 * SampleDialogActivity for CustomDialog
 */

package com.fwhyn.pocomon.ui.common.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fwhyn.pocomon.R
import com.fwhyn.pocomon.ui.common.dialog.CustomDialog.ClickListener
import com.fwhyn.pocomon.ui.common.dialog.CustomDialog.DialogCallback

// 0. we have to implement CustomDialog.DialogCallback and CustomDialog.ClickListener (if dialog has button)
class SampleDialogActivity : AppCompatActivity(), DialogCallback, ClickListener {
    // 2. to show custom dialog, we need CustomDialogManager
    private var mCustomDialogManager: CustomDialogManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_dialog)

        // 3. initialization dialog manager
        mCustomDialogManager = CustomDialogManager.initDialog(this)
        val dialogButton = findViewById<Button>(R.id.dialog_button)
        dialogButton.setOnClickListener { // 4. show dialog with specified tag
            mCustomDialogManager!!.showDialog(TAG_TEST_DIALOG)
        }
    }

    // callback for creating dialog
    override fun onCreateDialog(tag: String?): CustomDialog.Builder? {
        // 5. specify the intended dialog that want to be created here
        var builder: CustomDialog.Builder? = null
        when (tag) {
            TAG_TEST_DIALOG -> {
                val inflater = LayoutInflater.from(this@SampleDialogActivity)
                val updateView = inflater.inflate(R.layout.progress_layout, null)
                builder = CustomDialog.Builder()
                    .setTitle("test title")
                    .setMessage("test message")
                    .setPositiveButton("Ok", this)
                    .setNegativeButton("Cancel", this)
                    .setView(updateView)
                    .setNotDefaultButtonStyle(true)
            }
            else -> {}
        }
        return builder
    }

    // callback for dialog click
    override fun onClickDialog(tag: String?, whichButton: Int) {
        when (tag) {
            TAG_TEST_DIALOG -> when (whichButton) {
                DialogInterface.BUTTON_POSITIVE -> Toast.makeText(this, "ok", Toast.LENGTH_LONG)
                    .show()
                DialogInterface.BUTTON_NEGATIVE -> Toast.makeText(
                    this,
                    "canceled",
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {}
        }
    }

    companion object {
        // 1. tag for showing specific dialog
        private const val TAG_TEST_DIALOG = "tag_test_dialog"
    }
}