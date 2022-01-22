package com.example.andersen_final_work.presentation.ui.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.andersen_final_work.Contract
import com.example.andersen_final_work.R
import com.example.andersen_final_work.app.App
import com.example.andersen_final_work.databinding.FragmentEpisodesBinding
import com.example.andersen_final_work.domain.extensions.getCurrentPosition
import com.example.andersen_final_work.domain.models.FilterEpisodes
import javax.inject.Inject

class EpisodesFragment : Fragment(R.layout.fragment_episodes) {
    private lateinit var binding: FragmentEpisodesBinding
    private lateinit var episodeAdapter: EpisodesAdapter

    @Inject
    lateinit var vmFactory: EpisodesViewModelFactory
    val viewModel by viewModels<EpisodesViewModel>(factoryProducer = { vmFactory })
    var currentFilter = FilterEpisodes("", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).appComponent.inject(this)
        viewModel.getEpisodes()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEpisodesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observeViewModel()

        binding.searchEpisodes.addTextChangedListener {
            val enteredText = binding.searchEpisodes.text.toString()
            viewModel.findByName(enteredText)
        }
        binding.filterListButton.setOnClickListener {
            EpisodeDialogFragment.newInstance(currentFilter)
                .show(childFragmentManager, "EpisodesDialog")
        }
    }

    private fun observeViewModel() {
        viewModel.episodes.observe(viewLifecycleOwner) {
            episodeAdapter.submitList(it)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }
        viewModel.isLoadingData.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), Contract.NOT_DATA, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun initList() {
        episodeAdapter = EpisodesAdapter { episode ->
            val action =
                EpisodesFragmentDirections.actionEpisodesFragmentToEpisodeDetailFragment(episode)
            findNavController().navigate(action)
        }
        with(binding.episodesList) {
            adapter = episodeAdapter
            layoutManager = GridLayoutManager(context, 2)
            val dividerItemDecoration =
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(dividerItemDecoration)
            setHasFixedSize(true)
            setOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val position = getCurrentPosition()
                    viewModel.onPositionChanged(position)
                }
            })
        }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.updateAfterSwipe()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    fun filter(filter: FilterEpisodes) {
        viewModel.findBySeason(filter.season)
        viewModel.findBySeries(filter.series)
        currentFilter = FilterEpisodes(filter.season, filter.series)
    }
}