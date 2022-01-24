package com.example.andersen_final_work.presentation.ui.location_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.andersen_final_work.Contract
import com.example.andersen_final_work.R
import com.example.andersen_final_work.app.App
import com.example.andersen_final_work.databinding.FragmentLocationDetailsBinding
import com.example.andersen_final_work.domain.models.Locations
import com.example.andersen_final_work.presentation.ui.characters.CharactersAdapter
import javax.inject.Inject

class LocationDetailsFragment : Fragment(R.layout.fragment_location_details) {
    private lateinit var binding: FragmentLocationDetailsBinding

    @Inject
    lateinit var vmFactory: LocationDetailViewModelFactory
    val viewModel by viewModels<LocationDetailsViewModel>(factoryProducer = { vmFactory })
    private val args: LocationDetailsFragmentArgs by navArgs()
    private lateinit var charactersAdapter: CharactersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).appComponent.inject(this)
        viewModel.getLocation(args.idLocation)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        initResidentsList()
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getLocation(args.idLocation)
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun observe() {
        viewModel.location.observe(viewLifecycleOwner) {
            it?.let { setLocation(it) } ?: toast(Contract.NOT_DATA_ABOUT_LOCATION)
        }
        viewModel.residents.observe(viewLifecycleOwner) {
            charactersAdapter.submitList(it)
        }
        viewModel.unknown.observe(viewLifecycleOwner) {
            toast(it)
        }
        viewModel.loader.observe(viewLifecycleOwner) {
            binding.locationDetailProgressBar.isVisible = it
        }
    }

    private fun initResidentsList() {
        charactersAdapter = CharactersAdapter { character ->
            val action =
                LocationDetailsFragmentDirections.actionLocationDetailsFragmentToCharacterDetailFragment(
                    character
                )
            findNavController().navigate(action)
        }
        with(binding.residentsRV) {
            adapter = charactersAdapter
            layoutManager = GridLayoutManager(context, 2)
            val dividerItemDecoration =
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            addItemDecoration(dividerItemDecoration)
            setHasFixedSize(true)
        }
    }

    private fun setLocation(location: Locations) {
        with(binding) {
            nameLocation.text = location.name
            dimensionLocation.text = location.dimension
            createdLocation.text = location.created
        }
        viewModel.setResidents(location)
    }

    private fun toast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }
}