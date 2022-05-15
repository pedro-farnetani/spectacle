package com.pedrofarnetani.spectacle.extensions

import android.text.Editable

val Editable?.text: String
    get() = this?.toString().orEmpty()