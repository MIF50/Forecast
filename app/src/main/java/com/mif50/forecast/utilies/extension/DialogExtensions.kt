package com.mif50.forecast.utilies.extension

import android.app.Activity
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

fun Activity.showAlertDialog(
    title: String,
    message: String,
    actionOne: String,
    actionOneClicked: DialogInterface.OnClickListener,
    actionTwo: String,
    actionTwoClicked: DialogInterface.OnClickListener
) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(actionOne, actionOneClicked)
        .setNegativeButton(actionTwo, actionTwoClicked)
        .show()
}

fun Activity.showAlertDialog(
    title: Int,
    message: Int,
    actionOne: Int,
    actionOneClicked: DialogInterface.OnClickListener,
    actionTwo: Int,
    actionTwoClicked: DialogInterface.OnClickListener
) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(actionOne, actionOneClicked)
        .setNegativeButton(actionTwo, actionTwoClicked)
        .show()
}


fun Activity.showAlertDialog(
    title: Int,
    message: Int,
    neutralText: Int,
    neutralClicked: DialogInterface.OnClickListener
) {
    AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setNeutralButton(neutralText, neutralClicked)
        .show()
}