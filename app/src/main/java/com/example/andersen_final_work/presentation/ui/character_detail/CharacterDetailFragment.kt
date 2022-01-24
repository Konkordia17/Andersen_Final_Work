package com.example.andersen_final_work.presentation.ui.character_detail

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.andersen_final_work.Contract
import com.example.andersen_final_work.R
import com.example.andersen_final_work.app.App
import com.example.andersen_final_work.databinding.FragmentCharacterDetailBinding
import com.example.andersen_final_work.domain.models.Character
import com.example.andersen_final_work.presentation.ui.episodes.EpisodesAdapter
import javax.inject.Inject

class CharacterDetailFragment : Fragment(R.layout.fragment_character_detail) {
    private lateinit var binding: FragmentCharacterDetailBinding
    private var episodesAdapter: EpisodesAdapter? = null

    @Inject
    lateinit var vmFactory: CharacterDetailViewModelFactory
    private val args: CharacterDetailFragmentArgs by navArgs()
    private var idLocation = ""
    private var idOrigin = ""

    val viewModel by viewModels<CharacterDetailViewModel>(factoryProducer = { vmFactory })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).appComponent.inject(this)
        viewModel.getSingleCharacter(args.character.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        initEpisodes()
        binding.characterLocation.setOnClickListener {
            if (binding.characterLocation.text == "unknown") {
                toast(Contract.NOT_DATA_ABOUT_LOCATION)
            } else {
                val action =
                    CharacterDetailFragmentDirections.actionCharacterDetailFragmentToLocationDetailsFragment(
                        idLocation
                    )
                findNavController().navigate(action)
            }
        }
        binding.characterOrigin.setOnClickListener {
            if (binding.characterOrigin.text == "unknown") {
                toast(Contract.NOT_DATA_ABOUT_LOCATION)
            } else {
                val action =
                    CharacterDetailFragmentDirections.actionCharacterDetailFragmentToLocationDetailsFragment(
                        idOrigin
                    )
                findNavController().navigate(action)
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getSingleCharacter(args.character.id)
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun initEpisodes() {
        episodesAdapter = EpisodesAdapter { episode ->
            val action =
                CharacterDetailFragmentDirections.actionCharacterDetailFragmentToEpisodeDetailFragment(
                    episode
                )
            findNavController().navigate(action)
        }
        with(binding.episodesRV) {
            adapter = episodesAdapter
            layoutManager = LinearLayoutManager(context)
            val dividerItemDecoration =
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(dividerItemDecoration)
            setHasFixedSize(true)
        }
    }

    private fun observe() {
        viewModel.singleCharacter.observe(viewLifecycleOwner) { character ->
            character?.let { bindingData(character) }
        }
        viewModel.episodes.observe(viewLifecycleOwner) {
            episodesAdapter?.submitList(it)
        }
        viewModel.loader.observe(viewLifecycleOwner) {
            binding.characterDetailProgressBar.isVisible = it
        }
        viewModel.unknown.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        viewModel.location.observe(viewLifecycleOwner) {
            idLocation = it
        }
        viewModel.origin.observe(viewLifecycleOwner) {
            idOrigin = it
        }
    }

    private fun bindingData(character: Character) {
        Glide.with(requireView())
            .load(character.image)
            .into(binding.imageCharacterDF)

        with(binding) {
            with(character) {
                characterName.text = name
                characterSpecies.text = species
                characterStatus.text = status
                characterGender.text = gender
                characterType.text = type
                characterOrigin.text = origin.name
                characterLocation.text = location.name
            }
        }
        viewModel.setEpisodes(character)
        viewModel.setLocation(character)
    }

    private fun toast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }
}