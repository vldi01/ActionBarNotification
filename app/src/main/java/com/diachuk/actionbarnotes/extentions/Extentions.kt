package com.diachuk.actionbarnotes.extentions

import com.diachuk.actionbarnotes.NotesApp
import org.koin.java.KoinJavaComponent.get

fun getString(resId: Int): String {
    return get(NotesApp::class.java).getString(resId)
}