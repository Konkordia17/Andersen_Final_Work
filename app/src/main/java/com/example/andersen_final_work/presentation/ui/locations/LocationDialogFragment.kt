package com.example.andersen_final_work.presentation.ui.locations

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.andersen_final_work.R
import com.example.andersen_final_work.databinding.FragmentDialogLocationBinding
import com.example.andersen_final_work.domain.extensions.onItemSelect
import com.example.andersen_final_work.domain.models.FilterLocation

class LocationDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentDialogLocationBinding
    private var type = ""
    private var dimension = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val currentFilter = arguments?.getParcelable<FilterLocation>(KEY_LOOCATION)
        val typeIndex = resources.getStringArray(R.array.Type).indexOf(currentFilter?.type)
        val dimensionIndex =
            resources.getStringArray(R.array.Dimension).indexOf(currentFilter?.dimension)
        val builder = AlertDialog.Builder(requireContext())
        binding = FragmentDialogLocationBinding.inflate(LayoutInflater.from(requireContext()))
        initSpinners(typeIndex, dimensionIndex)
        val dialog = builder
            .setMessage(R.string.select_categories)
            .setView(binding.root)
            .setPositiveButton("OK") { _, _ ->
                val filterData = FilterLocation(type, dimension)
                (parentFragment as LocationsFragment).filter(filterData)
            }
            .setNegativeButton("Снять все фильтры") { _, _ ->
                (parentFragment as LocationsFragment).filter(FilterLocation("", ""))
            }
            .create()
        return dialog
    }

    private fun initSpinners(typeIndex: Int, dimensionIndex: Int) {
        binding.typeSpinner.setSelection(typeIndex)
        binding.dimensionSpinner.setSelection(dimensionIndex)
        binding.typeSpinner.onItemSelect { type = it }
        binding.dimensionSpinner.onItemSelect { dimension = it }
    }

    companion object {
        private const val KEY_LOOCATION = "Key_Location"
        fun newInstance(filter: FilterLocation): LocationDialogFragment {
            val dialog = LocationDialogFragment()
            val args = Bundle().apply {
                putParcelable(KEY_LOOCATION, filter)
            }
            dialog.arguments = args
            return dialog
        }
    }
}