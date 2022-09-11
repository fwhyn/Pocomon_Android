/*
 * Created by   : Yana Wahyuna
 * Update Date  : 2022/06/28
 *
 * CustomDialogManager is used for storing dialog's flag and tag that is needed by CustomDialog class
 */
package com.fwhyn.pocomon.ui.common.dialog

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.util.*

open class CustomDialogManager : ViewModel() {
    private val dialogJob = MutableLiveData<Deque<Array<String>>>()

    /* Queue is needed when show or dismiss dialog is executed when activity is onPause state
       * when onPause state, show or dismiss command will be suspended until activity is onResume state and it will be
       * assigned as queue */
    private val mQueue: Deque<Array<String>> = ArrayDeque()

    // use this to show dialog
    fun showDialog(tag: String) {
        val data = arrayOf(tag, DO_SHOW)
        mQueue.offer(data)
        dialogJob.postValue(mQueue)
    }

    // use this to dismiss dialog
    fun dismissDialog(tag: String) {
        val data = arrayOf(tag, DO_DISMISS)
        mQueue.offer(data)
        dialogJob.postValue(mQueue)
    }

    /**
     * checkQueue function is intended to get tag and command (do_show, do_dismiss)
     */
    private fun checkQueue(): Array<String>? {
        // command will be got and deleted after got
        val data = mQueue.poll()
        if (data != null) {
            // when mJob is changed and getDialogJob is observed then onChanged callback function will be executed
            dialogJob.postValue(mQueue)
        }
        return data
    }

    private fun callDialog(fragmentActivity: FragmentActivity, dialogCallback: CustomDialog.DialogCallback) {
        val customDialog = CustomDialog.getInstance().apply {
            this.fragmentActivity = fragmentActivity
            this.dialogCallback = dialogCallback
        }

        dialogJob.observe(fragmentActivity) {
            // get data from queue
            // getting data will be done as looping process until data = null
            val data = checkQueue()
            if (data != null) {
                val dataTag = data[0]
                val dataCommand = data[1]

                // check dialog flag whether need to be shown or dismissed
                if (dataCommand == DO_SHOW) {
                    customDialog.isCancelable = true
                    customDialog.show(fragmentActivity.supportFragmentManager, dataTag)
                }
                if (dataCommand == DO_DISMISS) {
                    val manager = fragmentActivity.supportFragmentManager
                    CustomDialog.dismissDialogFragment(manager)
                }
            }
        }
    }

    companion object {
        private const val DO_SHOW = "do_show"
        private const val DO_DISMISS = "do_dismiss"

        fun initDialog(fragmentActivity: FragmentActivity, dialogCallback: CustomDialog.DialogCallback): CustomDialogManager {
            val customDialogManager = ViewModelProvider(fragmentActivity)[CustomDialogManager::class.java]
            customDialogManager.callDialog(fragmentActivity, dialogCallback)

            return customDialogManager
        }

        fun initDialog(
            fragmentActivity: FragmentActivity,
            dialogCallback: CustomDialog.DialogCallback,
            viewModel: CustomDialogManager
        ): CustomDialogManager {
            viewModel.callDialog(fragmentActivity, dialogCallback)

            return viewModel
        }
    }
}