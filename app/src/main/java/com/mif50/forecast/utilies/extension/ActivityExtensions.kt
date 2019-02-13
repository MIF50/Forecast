package com.mif50.forecast.utilies.extension

import android.app.Activity
import android.content.Intent
import com.mif50.forecast.utilies.ui.LayoutRes

fun Activity.getLayoutRes(): LayoutRes {
    val annotation = this::class.java.annotations.find { it is LayoutRes } as? LayoutRes
    if (annotation != null) {
        return annotation
    } else {
        throw KotlinNullPointerException("Please add the LayoutRes annotation at the top of the class")
    }
}

inline fun <reified T: Any> Activity.startActivity() {
    startActivity(Intent(this, T::class.java))
}

inline fun <reified T: Any> Activity.startActivity(data: Intent) {
    startActivity(Intent(this, T::class.java).putExtras(data))
}

inline fun <reified T: Any> Activity.startActivityForResualt(requestCode: Int) {
    startActivityForResult(Intent(this, T::class.java), requestCode)
}

inline fun <reified T: Any> Activity.startActivityForResualt(data: Intent, requestCode: Int) {
    startActivityForResult(Intent(this, T::class.java).putExtras(data), requestCode)
}