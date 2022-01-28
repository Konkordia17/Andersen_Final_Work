package com.example.andersen_final_work.presentation.ui.episodes

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.andersen_final_work.R
import com.example.andersen_final_work.databinding.FragmentDialogEpisodesBinding
import com.example.andersen_final_work.extensions.onItemSelect
import com.example.andersen_final_work.domain.models.FilterEpisodes

class EpisodeDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentDialogEpisodesBinding
    private var season = ""
    private var series = ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val args = arguments?.getParcelable<FilterEpisodes>(KEY_EPISODE)
        val seasonIndex = resources.getStringArray(R.array.Seasons).indexOf(args?.season)
        val seriesIndex = resources.getStringArray(R.array.Series).indexOf(args?.series)
        val builder = AlertDialog.Builder(requireContext())
        binding = FragmentDialogEpisodesBinding.inflate(LayoutInflater.from(requireContext()))
        initSpinners(seasonIndex, seriesIndex)

        val dialog = builder
            .setMessage(R.string.select_categories)
            .setView(binding.root)
            .setPositiveButton(resources.getString(R.string.OK)) { _, _ ->
                val filterData = FilterEpisodes(season, series)
                (parentFragment as EpisodesFragment).filter(filterData)
            }
            .setNegativeButton(resources.getString(R.string.Delete_filters)) { _, _ ->
                (parentFragment as EpisodesFragment).filter(FilterEpisodes("", ""))
            }
            .create()
        return dialog
    }

    private fun initSpinners(seasonIndex: Int, seriesIndex: Int) {
        binding.seasonsSpinner.setSelection(seasonIndex)
        binding.seriesSpinner.setSelection(seriesIndex)
        binding.seasonsSpinner.onItemSelect { season = it }
        binding.seriesSpinner.onItemSelect { series = it }
    }

    companion object {
        private const val KEY_EPISODE = "Key_Episode"
        fun newInstance(filter: FilterEpisodes): EpisodeDialogFragment {
            val dialog = EpisodeDialogFragment()
            val args = Bundle().apply {
                putParcelable(KEY_EPISODE, filter)
            }
            dialog.arguments = args
            return dialog
        }
    }
}