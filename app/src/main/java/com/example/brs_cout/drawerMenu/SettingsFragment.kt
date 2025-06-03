package com.example.brs_cout.drawerMenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.example.brs_cout.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)

        val languagePref = findPreference<ListPreference>("language")
        languagePref?.setOnPreferenceChangeListener { _, _ ->
            activity?.recreate()
            true
        }

    }
}