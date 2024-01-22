package com.example.chat.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface


// for show the message of failure or response
fun Activity.showMessage(
    message: String,
    posActionName: String? = null,
    posAction: OnDialogActionClick? = null,
    negActionName: String? = null,
    negAction: OnDialogActionClick? = null,
    isCancelable: Boolean = true
): AlertDialog {
    val dialogBuilder = AlertDialog.Builder(this)
    dialogBuilder.setMessage(message)
    if (posActionName != null) {
        DialogInterface.OnClickListener { dialog, id ->
            dialog.dismiss()
            posAction?.onActionClick()
        }
    }
    if (negActionName != null) {
        DialogInterface.OnClickListener { dialog, id ->
            dialog.dismiss()
            negAction?.onActionClick()
        }
    }

    dialogBuilder.setCancelable(isCancelable)

    return dialogBuilder.show()
}



