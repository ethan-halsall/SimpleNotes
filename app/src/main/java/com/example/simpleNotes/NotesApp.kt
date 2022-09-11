package com.example.simpleNotes

import android.app.Application
import com.google.android.material.color.DynamicColors

class NotesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}