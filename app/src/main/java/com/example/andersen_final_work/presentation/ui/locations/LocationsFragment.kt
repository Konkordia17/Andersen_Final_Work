package com.example.andersen_final_work.presentation.ui.locations

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
import com.example.andersen_final_work.databinding.FragmentLocationsBinding
import com.example.andersen_final_work.domain.models.FilterLocation
import javax.inject.Inject

class LocationsFragment : Fragment(R.layout.fragment_locations) {
    private lateinit var binding: FragmentLocationsBinding
    private lateinit var locationAdapter: LocationAdapter

    @Inject
    lateinit var vmFactory: LocationsViewModelFactory
    val viewModel by viewModels<LocationsViewModel>(factoryProducer = { vmFactory })
    var currentFilter = FilterLocation("", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).appComponent.inject(this)
        viewModel.getLocations()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        observeViewModel()

        binding.searchLocation.addTextChangedListener {
            val enteredText = binding.searchLocation.text.toString()
            viewModel.findByName(enteredText)
        }
        binding.filterListButton.setOnClickListener {
            LocationDialogFragment.newInstance(currentFilter)
                .show(childFragmentManager, "EpisodesDialog")
        }
    }

    private fun observeViewModel() {
        viewModel.locations.observe(viewLifecycleOwner) {
            locationAdapter.submitList(it)
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
        locationAdapter = LocationAdapter {
            val action =
                LocationsFragmentDirections.actionLocationsFragmentToLocationDetailsFragment(it.id.toString())
            findNavController().navigate(action)
        }

        with(binding.locationList) {
            adapter = locationAdapter
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

    fun filter(filter: FilterLocation) {
        viewModel.findBySeason(filter.type)
        viewModel.findBySeries(filter.dimension)
        currentFilter = FilterLocation(filter.type, filter.dimension)
    }

    fun RecyclerView.getCurrentPosition(): Int {
        return (this.layoutManager as GridLayoutManager).findLastVisibleItemPosition()
    }
}