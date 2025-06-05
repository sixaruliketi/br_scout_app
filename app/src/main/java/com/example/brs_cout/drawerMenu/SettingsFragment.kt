package com.example.brs_cout.drawerMenu

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.example.brs_cout.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)

        changeLanguage()
        changeTheme()
        gotToHelpPage()
        aboutUs()
    }

    private fun aboutUs() {
        val aboutUsPref: Preference? = findPreference("about")
        aboutUsPref?.setOnPreferenceClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AboutUsFragment())
                .addToBackStack(null)
                .commit()
            true
        }
    }

    private fun gotToHelpPage(){
        val helpPref: Preference? = findPreference("help")
        helpPref?.setOnPreferenceClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HelpFragment())
                .addToBackStack(null)
                .commit()
            true
        }
    }



    private fun changeTheme(){

        val darkModeSwitch = findPreference<SwitchPreferenceCompat>("dark_mode")
        darkModeSwitch?.setOnPreferenceChangeListener { _, newValue ->
            val isDarkMode = newValue as Boolean
            val mode = if (isDarkMode) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(mode)
            true // Save the preference
        }

    }
    private fun changeLanguage(){

        val languagePref = findPreference<ListPreference>("language")
        languagePref?.setOnPreferenceChangeListener { _, _ ->
            activity?.recreate()
            true
        }

    }
}