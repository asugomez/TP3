package com.example.tp3.ui.main

import android.os.Bundle
import android.preference.PreferenceActivity
import androidx.annotation.Nullable
import com.example.tp3.R

@Suppress("DEPRECATION")
class SettingsActivity() : PreferenceActivity(){

    public override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.root_preferences)

    }

}
