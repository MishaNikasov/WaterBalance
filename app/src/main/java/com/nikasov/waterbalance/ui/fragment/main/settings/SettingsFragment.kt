package com.nikasov.waterbalance.ui.fragment.main.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.nikasov.waterbalance.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}