package com.suleymanuren.shoppingapp.util

import android.view.View

fun String.isValidEmail(email: String) =
    isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

fun View.hide(){
    visibility = View.GONE
}

fun View.show(){
    visibility = View.VISIBLE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}
fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}


