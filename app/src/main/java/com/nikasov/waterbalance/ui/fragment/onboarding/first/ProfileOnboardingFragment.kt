package com.nikasov.waterbalance.ui.fragment.onboarding.first

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nikasov.waterbalance.R
import kotlinx.android.synthetic.main.fragment_profile_onboarding.*

class ProfileOnboardingFragment : Fragment(R.layout.fragment_profile_onboarding) {

    private val viewModel : ProfileOnboardingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSexList()
    }

    private fun initSexList() {
        val items= (resources.getTextArray(R.array.sex)).asList()
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        sexList.setAdapter(adapter)
    }
}