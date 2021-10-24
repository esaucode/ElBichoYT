package com.esaudev.elbichoyt.utils

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.esaudev.elbichoyt.utils.Constants.VALUE_REQUIRED
import com.google.android.material.textfield.TextInputEditText

fun Activity.color(@ColorRes color : Int ) = ContextCompat.getColor(this, color)


fun Activity.toast(text: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, length).show()
}

fun Activity.isInputEmpty(editText: TextInputEditText, errorMessage: Boolean = false) : Boolean {
    return if (TextUtils.isEmpty(editText.text.toString().trim { it <= ' ' })){
        if (errorMessage){
            editText.error = VALUE_REQUIRED
        }
        true
    } else {
        false
    }
}

fun Activity.isInputEmpty(editText: EditText, errorMessage: Boolean = false) : Boolean {
    return if (TextUtils.isEmpty(editText.text.toString().trim { it <= ' ' })){
        if (errorMessage){
            editText.error = VALUE_REQUIRED
        }
        true
    } else {
        false
    }
}

fun Activity.isOtherInputEmpty(editText: TextInputEditText, radioButton: RadioButton, errorMessage: Boolean = false) : Boolean {
    return if (TextUtils.isEmpty(editText.text.toString().trim { it <= ' ' }) and radioButton.isChecked ){
        if (errorMessage){
            editText.error = VALUE_REQUIRED
        }
        true
    } else{
        false
    }
}

fun TextInputEditText.getTextTrimmed(text : TextInputEditText) : String {
    return text.text.toString().trim { it <= ' ' }
}

fun ImageView.load(url: String){
    if (url.isNotEmpty()){
        Glide.with(this.context).load(url).into(this)
    }
}

fun ImageView.loadURI(url: Uri){
    Glide.with(this.context).load(url).centerCrop().into(this)
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}