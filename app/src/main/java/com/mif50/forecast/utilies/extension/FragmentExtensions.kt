package com.mif50.forecast.utilies.extension

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.mif50.forecast.utilies.ui.LayoutRes

fun Fragment.getLayoutRes(): LayoutRes {
    val annotation = this::class.java.annotations.find { it is LayoutRes } as? LayoutRes
    if (annotation != null) {
        return annotation
    } else {
        throw KotlinNullPointerException("Please add the LayoutRes annotation at the top of the class")
    }
}


fun Fragment.showLongToast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun Fragment.showLongToast(message: Int) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun Fragment.showLongSnackBar(rootView: View, message: String) {
    Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show()
}

fun Fragment.showLongSnackBar(rootView: View, message: Int) {
    Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show()
}

fun Fragment.showLongSnackBarWithAction(
    rootView: View,
    message: String,
    actionTitle: String,
    click: View.OnClickListener
) {
    Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).setAction(actionTitle, click).show()
}

fun Fragment.showLongSnackBarWithAction(
    rootView: View,
    message: String,
    actionTitle: Int,
    click: View.OnClickListener
) {
    Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).setAction(actionTitle, click).show()
}