package com.example.andersen_final_work.presentation.ui.characters

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.andersen_final_work.R
import com.example.andersen_final_work.databinding.FragmentDialogCharactersBinding
import com.example.andersen_final_work.domain.extensions.onItemSelect
import com.example.andersen_final_work.domain.models.Filter

class CharactersDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentDialogCharactersBinding
    private var species = ""
    private var status = ""
    private var gender = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val currentFilter = arguments?.getParcelable<Filter>(KEY)
        val speciesIndex = resources.getStringArray(R.array.Species).indexOf(currentFilter?.species)
        val statusIndex = resources.getStringArray(R.array.Status).indexOf(currentFilter?.status)
        val genderIndex = resources.getStringArray(R.array.Gender).indexOf(currentFilter?.gender)
        val builder = AlertDialog.Builder(requireContext())
        binding = FragmentDialogCharactersBinding.inflate(LayoutInflater.from(requireContext()))
        initSpinners(speciesIndex, statusIndex, genderIndex)
        val dialog = builder
            .setMessage(R.string.select_categories)
            .setView(binding.root)
            .setPositiveButton("OK") { _, _ ->
                val filterData = Filter(species, status, gender)
                (parentFragment as CharactersFragment).filter(filterData)
            }
            .setNegativeButton("Снять все фильтры") { _, _ ->
                (parentFragment as CharactersFragment).filter(Filter("", "", ""))
            }
            .create()
        return dialog
    }

    private fun initSpinners(speciesIndex: Int, statusIndex:Int, genderIndex:Int) {
        binding.speciesSpinner.setSelection(speciesIndex)
        binding.statusSpinner.setSelection(statusIndex)
        binding.genderSpinner.setSelection(genderIndex)
        binding.speciesSpinner.onItemSelect { species = it }
        binding.statusSpinner.onItemSelect { status = it }
        binding.genderSpinner.onItemSelect { gender = it }
    }

    companion object {
        private const val KEY = "Key"
        fun newInstance(filter: Filter): CharactersDialogFragment {
            val dialog = CharactersDialogFragment()
            val args = Bundle().apply {
                putParcelable(KEY, filter)
            }
            dialog.arguments = args
            return dialog
        }
    }
}