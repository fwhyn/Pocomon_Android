/*
 * Created by   : Yana Wahyuna
 * Update Date  : 2022/06/27
 *
 * CustomDialog class is a common class for creating dialog that using DialogFragment to show it
 */
package com.fwhyn.pocomon.ui.common.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

class CustomDialog(private val fragmentActivity: FragmentActivity, private val dialogCallback: DialogCallback?) :
    DialogFragment() {
    private var mTag: String? = null
    private var mBuilder: Builder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mTag = tag

        /* get DialogCallback implementation from targeted activity */
//        val dialogCallback: DialogCallback? = try {
//            fragmentActivity as DialogCallback?
//        } catch (e: ClassCastException) {
//            /*
//             * if ClassCastException occurred, it means there is no implements CustomDialog.DialogCallback
//             * then set null mActivity because there is no ClickListener implementation
//             */
//            null
//        }
        if (dialogCallback != null) {
            mBuilder = dialogCallback.onCreateDialog(mTag)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var title: String? = null
        var message: String? = null
        var positiveText: String? = null
        var neutralText: String? = null
        var negativeText: String? = null
        var positiveButtonListener: ClickListener? = null
        var neutralButtonListener: ClickListener? = null
        var negativeButtonListener: ClickListener? = null
        @StyleRes var themeResId = 0
        var isNotDefaultButtonStyle = false
        var view: View? = null
        var customTitle: View? = null
        
        if (mBuilder != null) {
            title = mBuilder!!.title
            message = mBuilder!!.message
            positiveText = mBuilder!!.positiveText
            neutralText = mBuilder!!.neutralText
            negativeText = mBuilder!!.negativeText
            positiveButtonListener = mBuilder!!.positiveButtonListener
            neutralButtonListener = mBuilder!!.neutralButtonListener
            negativeButtonListener = mBuilder!!.negativeButtonListener
            themeResId = mBuilder!!.themeResId
            isNotDefaultButtonStyle = mBuilder!!.isNotDefaultButtonStyle
            view = mBuilder!!.view
            customTitle = mBuilder!!.customTitle
        } else {
            Log.e(TAG, "Error getting builder")
        }

        /* create dialog using AlertDialog and LayoutInflater */
        val alertDialog: AlertDialog
        val builder: AlertDialog.Builder = if (themeResId != 0) {
            AlertDialog.Builder(fragmentActivity, themeResId)
        } else {
            AlertDialog.Builder(fragmentActivity)
        }

        title?.let {
            builder.setTitle(title)
        }

        if (message != null) {
            builder.setMessage(message)
        }

        /* onClick using lambda expressions (->) */
        if (positiveText != null) {
            val finalPositiveButtonListener = positiveButtonListener
            builder.setPositiveButton(positiveText) { _, which: Int ->
                finalPositiveButtonListener?.onClickDialog(
                    mTag,
                    which
                )
            }
        }
        if (neutralText != null) {
            val finalNeutralButtonListener = neutralButtonListener
            builder.setNeutralButton(
                neutralText
            ) { dialog: DialogInterface?, which: Int ->
                finalNeutralButtonListener?.onClickDialog(
                    mTag,
                    which
                )
            }
        }
        if (negativeText != null) {
            val finalNegativeButtonListener = negativeButtonListener
            builder.setNegativeButton(
                negativeText
            ) { dialog: DialogInterface?, which: Int ->
                finalNegativeButtonListener?.onClickDialog(
                    mTag,
                    which
                )
            }
        }
        if (view != null) {
            builder.setView(view)
        }
        if (customTitle != null) {
            builder.setCustomTitle(customTitle)
        }
        alertDialog = builder.create()

        /* when button need to same as localize string, we need to disable AllCaps setting*/if (isNotDefaultButtonStyle) {
            alertDialog.setOnShowListener { thisDialog: DialogInterface ->
                (thisDialog as AlertDialog).getButton(DialogInterface.BUTTON_POSITIVE).isAllCaps =
                    false
                thisDialog.getButton(DialogInterface.BUTTON_NEGATIVE).isAllCaps = false
                thisDialog.getButton(DialogInterface.BUTTON_NEUTRAL).isAllCaps = false
            }
        }
        return alertDialog
    }

    override fun onCancel(dialog: DialogInterface) {
        /* called when dialog is showing, dialog cancelable is true, user taps outside dialog or presses back button */
        val cancelListener: CancelListener? = try {
                fragmentActivity as CancelListener?
            } catch (e: ClassCastException) {
                /*
             * if ClassCastException occurred, it means there is no implements CustomDialog.ClickListener
             * then set null mActivity because there is no ClickListener implementation
             */
                null
            }
        cancelListener?.onCancelDialog(mTag)
        super.onCancel(dialog)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        dismissDialogFragment(manager, tag)
        super.showNow(manager, tag)
    }

    // interface
    interface DialogCallback {
        fun onCreateDialog(tag: String?): Builder?
    }

    interface ClickListener {
        fun onClickDialog(tag: String?, whichButton: Int)
    }

    interface CancelListener {
        fun onCancelDialog(tag: String?)
    }

    // inner class
    class Builder {
        var positiveButtonListener: ClickListener? = null
        var neutralButtonListener: ClickListener? = null
        var negativeButtonListener: ClickListener? = null
        var title: String? = null
        var message: String? = null
        var positiveText: String? = null
        var neutralText: String? = null
        var negativeText: String? = null

        @StyleRes
        var themeResId = 0
        var isNotDefaultButtonStyle = false
        var view: View? = null
        var customTitle: View? = null

        constructor() {}

        constructor(@StyleRes themeResId: Int): this() {
            this.themeResId = themeResId
        }

        fun setTitle(title: String?): Builder {
            this.title = title
            return this
        }

        fun setMessage(message: String?): Builder {
            this.message = message
            return this
        }

        fun setPositiveButton(positiveText: String?, listener: ClickListener?): Builder {
            this.positiveText = positiveText
            positiveButtonListener = listener
            return this
        }

        fun setNeutralButton(neutralText: String?, listener: ClickListener?): Builder {
            this.neutralText = neutralText
            neutralButtonListener = listener
            return this
        }

        fun setNegativeButton(negativeText: String?, listener: ClickListener?): Builder {
            this.negativeText = negativeText
            negativeButtonListener = listener
            return this
        }

        fun setNotDefaultButtonStyle(notDefaultButtonStyle: Boolean): Builder {
            isNotDefaultButtonStyle = notDefaultButtonStyle
            return this
        }

        fun setView(view: View?): Builder {
            this.view = view
            return this
        }

        fun setCustomTitle(customTitle: View?): Builder {
            this.customTitle = customTitle
            return this
        }
    }

    companion object {
        private const val TAG = "CustomDialog"

        @Volatile
        private var INSTANCE : CustomDialog? = null

        fun getInstance(fragmentActivity: FragmentActivity, dialogCallback: DialogCallback?) : CustomDialog {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = CustomDialog(fragmentActivity, dialogCallback)
                INSTANCE = instance

                return instance
            }
        }

        /**
         * delete previous fragment with current tag if it exists
         */
        fun dismissDialogFragment(manager: FragmentManager, tag: String?) {
            val previous = manager.findFragmentByTag(tag) as CustomDialog? ?: return
            previous.dialog ?: return
            previous.dismiss()
        }
    }
}